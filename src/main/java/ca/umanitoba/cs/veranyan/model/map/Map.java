package ca.umanitoba.cs.veranyan.model.map;

import ca.umanitoba.cs.veranyan.model.Activity;
import com.google.common.base.Preconditions;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;

/**
 * The Map is the class that contains all the {@link Obstacle}
 * instances and {@link Activity} instances of an {@link ca.umanitoba.cs.veranyan.model.Exerciser}.
 */
public class Map {
    private static Map singleton;

    private final int width; // should be > 0
    private final int length; // should be > 0
    private final List<Obstacle> obstacles;
    private final SortedSet<Activity> activities;

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
        if (singleton == null) {
            singleton = new Map(width, length);
            singleton.checkMap();
        }

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
    private Map(int width, int length) {
        this.width = width;
        this.length = length;
        this.obstacles = new ArrayList<>();

        // activities should not have duplicates.
        // activities are put in ascending order in the Set (ordered by start time).
        this.activities = new TreeSet<>(new Comparator<Activity>() {
            @Override
            public int compare(Activity o1, Activity o2) {
                return o1.getStart().compareTo(o2.getStart());
            }
        });

        checkMap();
    }

    /**
     * @return the (positive) width of the Map singleton. Must be positive.
     */
    public int getWidth() {
        checkMap();

        return width;
    }

    /**
     * @return the (positive) length of the Map singleton. Must be positive.
     */
    public int getLength(){
        checkMap();

        return length;
    }

    /**
     * @return the unmodifiable list of Obstacles on the Map. Must not be {@code null}.
     */
    public List<Obstacle> getObstacles() {
        checkMap();
        
        return Collections.unmodifiableList(obstacles);
    }

    /**
     * Adds an Obstacle to the Map instance.
     * Obstacle must not be out of Map instance boundaries.
     *
     * @param topLeftX a non-negative integer indicating the upper-left x-coordinate of rectangular Obstacle.
     *                 Must be less than Map width.
     * @param topLeftY a non-negative integer indicating the upper-left y-coordinate of rectangular Obstacle.
     *                 Must be less than Map length.
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
     * Removes an Obstacle from the Map by index.
     * @param index the index of the Obstacle to remove.
     */
    public void removeObstacle(int index){
        checkMap();

        obstacles.remove(index);

        checkMap();
    }

    /**
     * @return the unmodifiable list of activities on the Map. Must not be {@code null}.
     */
    public SortedSet<Activity> getActivities() {
        checkMap();

        return Collections.unmodifiableSortedSet(activities);
    }

    /**
     * Adds an activity to the Map instance.
     * @param activity the activity instance to add to Map. Must not be {@code null}.
     */
    public void addActivity(Activity activity){
        checkMap();

        activities.add(activity);

        checkMap();
    }

    /**
     * Removes an activity from the Map by index.
     * @param index the index of the activity to remove.
     */
    public void removeActivity(int index){
        checkMap();

        Iterator<Activity> iterator = activities.iterator();

        // omitting previous elements to reach element at appropriate index;
        for(int j = 0; j < index; j++)
            iterator.next();

        activities.remove(iterator.next());

        checkMap();
    }

    /**
     * Determines whether a point on Map is within any of the Obstacle.
     * @param x the x-coordinate of the point on the Map.
     * @param y the y-coordinate of the point on the Map.
     * @return true if (x, y) is in any Obstacle on the Map; false otherwise.
     */
    public boolean isInObstacle(int x, int y) {
        // does not contain a checkMap() invariant check in order to avoid stack overflow.
        // NOTE: refer to the checkMap() implementation for further details.

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
        checkMap();

        boolean isInRoute = false;

        Iterator<Activity> iterator = activities.iterator();

        while(iterator.hasNext() && !isInRoute)
            isInRoute = iterator.next().getRoute().contains(x, y);

        checkMap();

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
        checkMap();

        Iterator<Activity> iterator = activities.iterator();

        // omitting previous elements to reach element at appropriate index;
        for(int i = 0; i < index; i++)
            iterator.next();

        checkMap();

        return iterator.next().getRoute().contains(x, y);
    }

    /**
     * Ensures Map invariants are not violated.
     */
    private void checkMap(){
        Preconditions.checkState(width >= 1, "width cannot be negative or 0.");
        Preconditions.checkState(length >= 1, "length cannot be negative or 0.");
        Preconditions.checkNotNull(obstacles, "obstacles cannot be null.");
        Preconditions.checkNotNull(activities, "activities cannot be null.");

        // checks obstacle not null and is within bounds
        for(var obstacle : obstacles){
            Preconditions.checkNotNull(obstacle, "obstacles entries cannot be null.");
            Preconditions.checkState(obstacle.bottomRightCoord().x() < width,
                    "Obstacle width cannot be out of bounds.");
            Preconditions.checkState(obstacle.bottomRightCoord().y() < length,
                    "Obstacle length cannot be out of bounds.");
        }

        // checks activity not null and route is within bounds
        // checks for obstacles and routes not overlapping
        for(var activity : activities){
            Preconditions.checkNotNull(activity, "activities entries cannot be null.");

            Route route = activity.getRoute(); // the route of the activity
            for(int i = 0; i < route.getStepsAmount(); i++){
                var coordinate = route.getCoordinate(i); // current coordinate

                // route within bounds check
                Preconditions.checkState(coordinate.x() < width,
                        "route cannot be out of bounds.");
                Preconditions.checkState(coordinate.y() < length,
                        "route cannot be out of bounds.");

                // no overlap between route and other obstacles
                // method isInObstacle cannot call checkMap() to avoid stack overflow
                Preconditions.checkState(
                        !isInObstacle(coordinate.x(), coordinate.y())
                );
            }
        }
    }
}
