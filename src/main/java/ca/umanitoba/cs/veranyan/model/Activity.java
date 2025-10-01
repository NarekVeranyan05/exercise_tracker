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
    private final List<Integer> coordinatesX;
    private final List<Integer> coordinatesY;
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
        this.coordinatesX = new ArrayList<>();
        this.coordinatesX.add(x);
        this.coordinatesY = new ArrayList<>();
        this.coordinatesY.add(y);
        this.avgSpeed = 0;

        checkActivity();
    }

    /**
     * A step is a single coordinate on the Map. A step corresponds to 10 meters.
     *
     * @return the number of steps for the activity.
     */
    public int getNumberOfSteps(){
        return coordinatesX.size();
    }

    /**
     * Gets the x-coordinate of a point at a particular index in route.
     * @param index the index of the point; represents the point of the index-th step.
     * @return the x-coordinate of the point.
     */
    public int getCoordinateX(int index) {
        return coordinatesX.get(index);
    }

    /**
     * Gets the y-coordinate of a point at a particular index in route.
     * @param index the index of the point; represents the point of the index-th step.
     * @return the y-coordinate of the point.
     */
    public int getCoordinateY(int index) {
        return coordinatesY.get(index);
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
     * Note: getAvgSpeed() can be called only when Activity has been ended using endActivity()
     * @return the average speed (meters per second) throughout the Activity.
     */
    public double getAvgSpeed() {
        // average speed is determined only after the activity is finished.
        Preconditions.checkNotNull(end, "end cannot be null");

        return avgSpeed;
    }

    /**
     * Ends the Activity. Activity route cannot be modified after end.
     */
    public void endActivity(){
        if(end == null) {
            end = LocalDateTime.now();
            avgSpeed = (getNumberOfSteps() * METERS_PER_STEP) /
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
        Preconditions.checkState(direction >= 1 && direction <= 4, "There are only 4 directions.");
        Preconditions.checkState(steps > 0, "Number of steps cannot be negative.");

        int currX = coordinatesX.get(coordinatesX.size()-1); // current position x-coordinate
        int currY = coordinatesY.get(coordinatesY.size()-1); // current position y-coordinate

        switch(direction){
            case 1: // move up
                for(int i = 1; i <= steps; i++){
                    coordinatesX.add(currX);
                    coordinatesY.add(currY - i);
                }
                break;
            case 2: // move right
                for(int i = 1; i <= steps; i++){
                    coordinatesX.add(currX + i);
                    coordinatesY.add(currY);
                }
                break;
            case 3: // move down
                for(int i = 1; i <= steps; i++){
                    coordinatesX.add(currX);
                    coordinatesY.add(currY + i);
                }
                break;
            case 4: // move left
                for(int i = 1; i <= steps; i++){
                    coordinatesX.add(currX - i);
                    coordinatesY.add(currY);
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
        boolean contains = false;

        for(int i = 0; i < coordinatesX.size() && !contains; i++)
            contains = (x == coordinatesX.get(i)) && (y == coordinatesY.get(i));

        return contains;
    }

    /**
     * Ensures Activity invariants are not violated.
     */
    private void checkActivity(){
        Preconditions.checkNotNull(gear, "gear cannot be null");
        Preconditions.checkNotNull(start, "start cannot be null");
        Preconditions.checkNotNull(coordinatesX, "coordinatesX cannot be null");
        Preconditions.checkState(!coordinatesX.isEmpty(), "coordinatesX cannot be empty");
        Preconditions.checkNotNull(coordinatesY, "coordinatesY cannot be null");
        Preconditions.checkState(!coordinatesY.isEmpty(), "coordinatesY cannot be empty");
        Preconditions.checkState(avgSpeed >= 0, "avgSpeed cannot be negative");

        // all x-coordinates and y-coordinates are >= 0
        for(int x : coordinatesX)
            Preconditions.checkState(x >= 0, "x-coordinate cannot be negative");
        for(int y: coordinatesY)
            Preconditions.checkState(y >= 0, "y-coordinate cannot be negative");

        // Map will ensure Activity route is within boundaries
    }
}
