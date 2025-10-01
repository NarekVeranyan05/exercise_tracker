package ca.umanitoba.cs.veranyan.output;

import ca.umanitoba.cs.veranyan.model.Activity;
import ca.umanitoba.cs.veranyan.model.gear.Gear;
import com.google.common.base.Preconditions;

/**
 * The printer class for a {@link Gear}
 */
public class GearPrinter {
    private final Gear gear;

    /**
     * Constructor for GearPrinter.
     * @param gear the Gear to be printed. Must not be {@code null}.
     */
    public GearPrinter(Gear gear){
        this.gear = gear;

        checkGearPrinter();
    }

    /**
     * Prints out a Gear. This method prints to standard output (`System.out`).
     */
    public void print(){
        System.out.print("Gear " + gear.name() + " of type "  + gear.type() +
                " | average speed = " + gear.avgSpeed() + " meters per second.");
    }

    /**
     * Ensures GearPrinter invariants are not violated.
     */
    private void checkGearPrinter(){
        Preconditions.checkNotNull(gear,"gear cannot be null");
    }
}
