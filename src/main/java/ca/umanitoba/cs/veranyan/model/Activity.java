package ca.umanitoba.cs.veranyan.model;

import ca.umanitoba.cs.veranyan.model.gear.Gear;
import ca.umanitoba.cs.veranyan.model.map.Route;
import com.google.common.base.Preconditions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * An Activity is a class that contains all the information about
 * a particular cycling exercise. It contains the {@link Gear} used
 * for that particular exercise. Activities are stored on the {@link Exerciser}.
 */
public class Activity {
    private static final int METERS_PER_STEP = 10;

    private final Gear gear;
    private final LocalDateTime start;
    private LocalDateTime end; // activity end is initialised with null until ended explicitly using endActivity()
    private final Route route;
    private double avgSpeed; // value determined at activity end

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
     * Note: getAvgSpeed() can be called only when Activity has been ended using endActivity()
     * @return the average speed (meters per second) throughout the Activity.
     */
    public double getAvgSpeed() {
        // average speed is determined only after the activity is finished.
        Preconditions.checkNotNull(end, "end cannot be null");

        return avgSpeed;
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
     * @return the Route used in the Activity.
     */
    public Route getRoute(){
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
        // MapGrid will ensure Route is within boundaries
    }
}
