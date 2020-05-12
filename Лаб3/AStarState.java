import java.util.*;
/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState
{
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;

    /** Объявление и инициализация поля для открытых вершин. **/
    private Map<Location, Waypoint> open_waypoints = new HashMap<Location, Waypoint> ();

    /** Объявление и инициализация поля для закрытых вершин. **/
    private Map<Location, Waypoint> closed_waypoints = new HashMap<Location, Waypoint> ();


    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * This method scans through all open waypoints, and returns the waypoint
     * with the minimum total cost.  If there are no open waypoints, this method
     * returns <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint()
    {
        // Если нет открытых вершин, возвращаем пустое значение
        if (numOpenWaypoints() == 0)
        return null;

        Set open_waypoint_keys = open_waypoints.keySet();
        Iterator i = open_waypoint_keys.iterator();
        Waypoint best = null;
        float best_cost = Float.MAX_VALUE;

        // Сканирует все открытые вершины.
        while (i.hasNext())
        {
            // Сохраняет текущее местоположение.
            Location location = (Location)i.next();
            // Сохраняет текущую вершину.
            Waypoint waypoint = open_waypoints.get(location);
            // Сохраняет общий вес для текущего местоположения.
            float waypoint_total_cost = waypoint.getTotalCost();

            if (waypoint_total_cost < best_cost)
            {
                best = open_waypoints.get(location);
                best_cost = waypoint_total_cost;
            }
        }
        // Возвращает вершину с минимальным весом.
        return best;
    }

    /** Этот метод добавляет вершину в множество открытых вершин.
     * Если нет открытой вершины в местоположении новой вершины,
     * то данная вершина просто добавляется в множество.
     * Если в наборе «открытых вершин» уже есть вершина для этой локации,
     * добавьте новую вершину только в том случае,
     * если стоимость пути до новой вершины меньше стоимости пути до текущей.
     */
    public boolean addOpenWaypoint(Waypoint newWP) {

        // Находит местоположения новой вершины
        Location location = newWP.getLocation();

        // Проверят открыта ли вершина на новом местоположении
        if (open_waypoints.containsKey(location))
        {
            // Если уже существует открытая вершина в новом местоположении,
            // проверить новую вершину с прошлым значением
            // и сравнить с текущей вершиной
            Waypoint current_waypoint = open_waypoints.get(location);
            if (newWP.getPreviousCost() < current_waypoint.getPreviousCost())
            {
                // Если значение новой вершины меньше,
                // чем текущей, новая вершина
                // заменяет старую и вовзращает true
                open_waypoints.put(location, newWP);
                return true;
            }
            // Если значение новой вершины не меньше
            // текущей, возвращаем false
            return false;
        }
        open_waypoints.put(location, newWP);
        return true;
    }


    /** Возвращает текущий номер открытой вершины. **/
    public int numOpenWaypoints()
    {
        return open_waypoints.size();
    }


    /**
     * перемещает вершину из набора «открытых вершин» в набор «закрытых вершин».
     * Так как вершины обозначены местоположением, метод принимает местоположение вершины.
     **/
    public void closeWaypoint(Location loc)
    {
        Waypoint waypoint = open_waypoints.remove(loc);
        closed_waypoints.put(loc, waypoint);
    }

    /**
     *  функция должна возвращать значение true,
     *  если указанное местоположение встречается
     *  в наборе закрытых вершин, и false в противном случае.
     **/
    public boolean isLocationClosed(Location loc)
    {
        return closed_waypoints.containsKey(loc);
    }
}
