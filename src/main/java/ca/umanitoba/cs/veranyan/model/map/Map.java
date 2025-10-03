package ca.umanitoba.cs.veranyan.model.map;

import ca.umanitoba.cs.veranyan.assets.MapFeatureType;
import ca.umanitoba.cs.veranyan.model.Activity;
import ca.umanitoba.cs.veranyan.model.Coordinate;
import ca.umanitoba.cs.veranyan.model.gear.Gear;
import com.google.common.base.Preconditions;

import java.util.*;

/**
 * The Map is the class that contains all the {@link Obstacle}
 * instances and {@link Route}.
 */
public class Map {
    private static Map singleton;

    private final int width; // should be > 0
    private final int length; // should be > 0
    private final List<Obstacle> obstacles;
    private final List<Route> routes;

    /**
     * The width and length will be used to instantiate a new Map
     * singleton if the previous singleton has been destroyed.
     *
     * @param width the width for a new Map. Must be positive.
     * @param length the length for a new Map. Must be positive.
     * @return the new or already-existing Map singleton instance.
     */
    public static Map getInstance(int width, int length) {
        // create new singleton instance if none exists
        if (singleton == null)
            singleton = new Map(width, length);

        return singleton;
    }

    /**
     * Destroys the singleton Map instance.
     */
    public static void destroyInstance(){
        singleton = null;
    }

    /**
     * Constructor for a new Map singleton.
     * @param width the width for a new map. Must be positive.
     * @param length the length for a new map. Must be positive.
     */
    private Map(final int width, final int length) {
        this.width = width;
        this.length = length;
        this.obstacles = new ArrayList<>();
        this.routes = new ArrayList<>();

        checkMap();
    }

    /**
     * @return the (positive) width of the Map singleton.
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the (positive) length of the Map singleton.
     */
    public int getLength() {
        return length;
    }

    /**
     * @return the unmodifiable list of Obstacles on the Map
     */
    public List<Obstacle> getObstacles() {
        checkMap();
        
        return Collections.unmodifiableList(obstacles);
    }

    /**
     * @return the unmodifiable list of activities on the Map
     */
    public List<Route> getRoutes() {
        checkMap();

        return Collections.unmodifiableList(routes);
    }

    /**
     * Adds an Obstacle to the Map instance.
     * Obstacle must not be out of Map instance boundaries.
     *
     * @param topLeftX a non-negative integer indicating the upper-left x-coordinate of rectangular Obstacle. Must be less than Map width
     * @param topLeftY a non-negative integer indicating the upper-left y-coordinate of rectangular Obstacle. Must be less than Map length
     * @param bottomRightX a non-negative integer indicating the lower-right x-coordinate of rectangular Obstacle.
     *                    Must be less than Map width, greater than or equal to topLeftX.
     * @param bottomRightY a non-negative integer indicating the lower-right y-coordinate of rectangular Obstacle.
     *                    Must be less than Map length, greater than or equal to topLeftY.
     */
    public void addObstacle(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
        checkMap();

        obstacles.add(new Obstacle(
                new Coordinate(topLeftX, topLeftY),
                new Coordinate(bottomRightX, bottomRightY)
        ));

        checkMap();
    }

    /**
     * Removes an Obstacle from the Map.
     * @param index the index of the Obstacle to remove.
     */
    public void removeObstacle(int index){
        checkMap();

        obstacles.remove(index);

        checkMap();
    }

    /**
     * Adds a Route to the Map instance.
     * Route must not be out of Map instance boundaries.
     * @param route the Route instance to be added.
     */
    public void addRoute(Route route) {
        checkMap();

        routes.add(route);

        checkMap();
    }

    /**
     * Removes a Route from the Map.
     * @param route the Route to remove.
     */
    public void removeRoute(Route route){
        checkMap();

        routes.remove(route);

        checkMap();
    }

    /**
     * Determines whether a point on Map is within any of the Obstacle.
     * @param x the x-coordinate of the point on the Map.
     * @param y the y-coordinate of the point on the Map.
     * @return true if (x, y) is in any Obstacle on the Map; false otherwise.
     */
    public boolean isInObstacle(int x, int y) {
        boolean isInObstacle = false;
        // going over all the obstacles
        for(int i = 0; i < obstacles.size() && !isInObstacle; i++)
            isInObstacle = obstacles.get(i).contains(x, y);

        return isInObstacle;
    }

    /**
     * Determines whether a point on Map is within any of the Routes.
     * @param x the x-coordinate of the point on the Map.
     * @param y the y-coordinate of the point on the Map.
     * @return true if (x, y) is in Route on the Map; false otherwise.
     */
    public boolean isInRoute(int x, int y){
        boolean isInRoute = false;

        for(int i = 0; i < routes.size() && !isInRoute; i++)
            isInRoute = routes.get(i).contains(x, y);

        return isInRoute;
    }

    /**
     * Determines whether a point on Map is within a particular Route.
     * @param index the index of the Activity to search in.
     * @param x the x-coordinate of the point on the Map.
     * @param y the x-coordinate of the point on the Map.
     * @return true if (x, y) is in Route on the Map; false otherwise.
     */
    public boolean isInRoute(int index, int x, int y){
        return routes.get(index).contains(x, y);
    }

    /**
     * Ensures Map invariants are not violated.
     */
    private void checkMap(){
        Preconditions.checkState(width >= 1, "width cannot be negative or 0.");
        Preconditions.checkState(length >= 1, "length cannot be negative or 0.");
        Preconditions.checkNotNull(obstacles, "obstacles cannot be null.");
        Preconditions.checkNotNull(routes, "routes cannot be null.");

        // checks obstacle not null and is within bounds
        for(var obstacle : obstacles){
            Preconditions.checkNotNull(obstacle, "obstacles entries cannot be null.");
            Preconditions.checkState(obstacle.bottomRightCoord().x() < width,
                    "Obstacle width cannot be out of bounds.");
            Preconditions.checkState(obstacle.bottomRightCoord().y() < length,
                    "Obstacle length cannot be out of bounds.");
        }

        // checks activity not null and is within bounds
        for(var route : routes){
            Preconditions.checkNotNull(route, "routes entries cannot be null.");
            for(int i = 0; i < route.getStepsAmount(); i++){
                Preconditions.checkState(route.getCoordinate(i).x() < width,
                        "route entry cannot be out of bounds.");
                Preconditions.checkState(route.getCoordinate(i).y() < length,
                        "route entry cannot be out of bounds.");
            }
        }

        // checking for obstacles and activities not overlapping, coordinates within bounds
        for(int x = 0; x < width; x++){ // x-coordinate
            for(int y = 0; y < length; y++) {// y-coordinate

                // it's not the case that (x, y) is both in an activity and an obstacle
                Preconditions.checkState(!(isInRoute(x, y) && isInObstacle(x, y)),
                        String.format("Route cannot pass through an Obstacle at (%d, %d).", x, y));
            }
        }
    }
}
