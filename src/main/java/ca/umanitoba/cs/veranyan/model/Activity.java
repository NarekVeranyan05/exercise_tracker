package ca.umanitoba.cs.veranyan.model;

import ca.umanitoba.cs.veranyan.model.gear.Gear;
import com.google.common.base.Preconditions;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * An Activity is a class that contains all the information about
 * a particular cycling exercise. It contains the {@link Gear} used
 * for that particular exercise. Activities are stored on the {@link java.util.Map}.
 */
public class Activity {
    private static final int METERS_PER_STEP = 10;

    private final Gear gear;
    private final LocalDateTime start;
    private LocalDateTime end; // activity end is initialised with null until ended explicitly using endActivity()
    private final List<Coordinate> coordinates;
    private double avgSpeed; // value determined at activity end

    /**
     * Constructor for Activity
     * @param gear the Gear to add to the Activity. Must not be {@code null}.
     * @param x the starting point's x-coordinate for the Activity's route. Must be non-negative and within Map boundaries.
     * @param y the starting point's y-coordinate for the Activity's route. Must be non-negative and within Map boundaries.
     */
    public Activity(Gear gear, int x, int y) {
        this.gear = gear;
        this.start = LocalDateTime.now(); // end not initialised
        this.coordinates = new ArrayList<>();
        this.coordinates.add(new Coordinate(x, y));
        this.avgSpeed = 0;

        checkActivity();
    }

    /**
     * A step is a single coordinate on the Map. A step corresponds to 10 meters.
     *
     * @return the number of steps for the activity.
     */
    public int getStepsAmount(){
        return coordinates.size();
    }

    /**
     * Note: getAvgSpeed() can be called only when Activity has been ended using endActivity()
     * @return the average speed (meters per second) throughout the Activity.
     */
    public double getAvgSpeed() {
        // average speed is determined only after the activity is finished.
        Preconditions.checkNotNull(end, "end cannot be null");

        return avgSpeed;
    }

    /**
     * Gets the Coordinate of a point at a particular index in route.
     * @param index the index of the point; represents the point of the index-th step.
     * @return the Coordinate of the point.
     */
    public Coordinate getCoordinate(int index) {
        return coordinates.get(index);
    }

    /**
     * @return the start time for the Activity.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * @return the end time for the Activity.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * @return the Gear used in the Activity.
     */
    public Gear getGear() {
        return gear;
    }

    /**
     * Ends the Activity. Activity route cannot be modified after end.
     */
    public void endActivity(){
        checkActivity();

        if(end == null) {
            end = LocalDateTime.now();
            avgSpeed = (getStepsAmount() * METERS_PER_STEP) /
                    ((double) Duration.between(start, end).getSeconds());
        }

        checkActivity();
    }

    /**
     * Makes a move in a particular direction on the Map, adding indicated number of coordinates to the Activity route.
     * @param direction the Direction to move. Must be any of [UP = 1, RIGHT = 2, DOWN = 3, LEFT = 4].
     * @param steps the number of steps of the move. Must be non-negative.
     */
    public void move(int direction, int steps){
        checkActivity();

        var currCoordinate = coordinates.get(coordinates.size()-1); // the current (x, y) coordinate

        switch(direction){
            case 1: // move up
                for(int i = 1; i <= steps; i++) {
                    coordinates.add(new Coordinate(
                            currCoordinate.x(), currCoordinate.y() - i
                    ));
                }
                break;
            case 2: // move right
                for(int i = 1; i <= steps; i++){
                    coordinates.add(new Coordinate(
                           currCoordinate.x() + i, currCoordinate.y()
                    ));
                }
                break;
            case 3: // move down
                for(int i = 1; i <= steps; i++){
                    coordinates.add(new Coordinate(
                            currCoordinate.x(), currCoordinate.y() + i
                    ));
                }
                break;
            case 4: // move left
                for(int i = 1; i <= steps; i++){
                    coordinates.add(new Coordinate(
                            currCoordinate.x() - i, currCoordinate.y()
                    ));
                }
                break;
        }

        checkActivity();
    }

    /**
     * Determines whether a point is within the Activity.
     * @param x the x-coordinate of the point.
     * @param y the y-coordinate of the point.
     * @return true if (x, y) is in the Activity's route; false otherwise.
     */
    public boolean contains(int x, int y){
        checkActivity();

        boolean contains = false;

        for(int i = 0; i < coordinates.size() && !contains; i++)
            contains = (x == coordinates.get(i).x()) && (y == coordinates.get(i).y());

        checkActivity();

        return contains;
    }

    /**
     * Ensures Activity invariants are not violated.
     */
    private void checkActivity(){
        Preconditions.checkNotNull(gear, "gear cannot be null.");
        Preconditions.checkNotNull(start, "start cannot be null.");
        Preconditions.checkNotNull(coordinates, "coordinates cannot be null.");
        Preconditions.checkState(!coordinates.isEmpty(), "coordinates cannot be empty.");
        Preconditions.checkState(avgSpeed >= 0, "avgSpeed cannot be negative.");

        // Coordinate will ensure coordinates are non-negative
        // Map will ensure Activity route is within boundaries
    }
}
