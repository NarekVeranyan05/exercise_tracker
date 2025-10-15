package ca.umanitoba.cs.veranyan.model;

import ca.umanitoba.cs.veranyan.model.gear.Gear;
import ca.umanitoba.cs.veranyan.model.map.Route;
import com.google.common.base.Preconditions;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * An Activity is a class that contains all the information about
 * a particular cycling exercise. It contains the {@link Gear} used
 * for that particular exercise. Activities are stored in the {@link Profile} instance.
 */
public class Activity {
    private static final int METERS_PER_STEP = 10; // a step is one coordinate on the Map grid.

    private final Gear gear; // the gear to be used in Activity
    private final LocalDateTime start; // the start time of Activity
    private LocalDateTime end; // Activity end is initialised with null until ended explicitly using endActivity()
    private final Route route;
    private double avgSpeed; // value determined at Activity end

    /**
     * Constructor for Activity
     * @param gear the Gear to add to the Activity. Must not be {@code null}.
     * @param route the Route to add to the Activity. Must not be {@code null}.
     */
    public Activity(Gear gear, Route route) {
        this.gear = gear;
        this.start = LocalDateTime.now(); // end not initialised
        this.route = route;
        this.avgSpeed = 0;

        checkActivity();
    }

    /**
     * @implNote getAvgSpeed() can be called only when Activity has been ended using endActivity().
     * @return the average speed (meters per second) throughout the Activity.
     */
    public double getAvgSpeed() {
        // average speed is determined only after the activity is finished.
        Preconditions.checkNotNull(end, "end cannot be null");
        checkActivity();

        return avgSpeed;
    }

    /**
     * @return the start time for the Activity. Must not be {@code null}.
     */
    public LocalDateTime getStart() {
        checkActivity();

        return start;
    }

    /**
     * @return the end time for the Activity. May return {@code null} if Activity did not end.
     */
    public LocalDateTime getEnd() {
        checkActivity();

        return end;
    }

    /**
     * @return the Gear used in the Activity. Must not be {@code null}.
     */
    public Gear getGear() {
        checkActivity();

        return gear;
    }

    /**
     * @return the Route used in the Activity. Must not be {@code null}.
     */
    public Route getRoute(){
        checkActivity();

        return route;
    }

    /**
     * Ends the Activity. Activity route cannot be modified after end.
     */
    public void endActivity(){
        checkActivity();

        if(end == null) {
            end = LocalDateTime.now();
            avgSpeed = (route.getStepsAmount() * METERS_PER_STEP) /
                    ((double) Duration.between(start, end).getSeconds());
        }

        checkActivity();
    }

    /**
     * Ensures Activity invariants are not violated.
     */
    private void checkActivity(){
        Preconditions.checkNotNull(gear, "gear cannot be null.");
        Preconditions.checkNotNull(start, "start cannot be null.");
        Preconditions.checkNotNull(route, "route cannot be null.");
        Preconditions.checkState(avgSpeed >= 0, "avgSpeed cannot be negative.");

        // Coordinate will ensure coordinates are non-negative
        // Map will ensure Route is within boundaries
    }
}
