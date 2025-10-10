package ca.umanitoba.cs.veranyan.model.gear;

import com.google.common.base.Preconditions;

/**
 * A Gear. A gear is a particular bike that's used during a cycling activity.
 * @param type the GearType of the gear. Must not be {@code null}.
 * @param name the name of the gear. Must not be {@code null} or blank.
 * @param avgSpeed the average speed of the gear. Must be positive.
 */
public record Gear(GearType type, String name, double avgSpeed){
    /**
     * Compact constructor for Gear
     * @param type the GearType of the gear. Must not be {@code null}.
     * @param name the name of the gear. Must not be {@code null} or blank.
     * @param avgSpeed the average speed of the gear. Must be positive.
     */
    public Gear{
        // validating record arguments as preconditions before field initialisation.
        Preconditions.checkNotNull(type, "type of Gear cannot be null.");
        Preconditions.checkNotNull(name, "name cannot be null.");
        Preconditions.checkState(!name.isBlank(), "name cannot be blank.");
        Preconditions.checkState(avgSpeed > 0, "avgSpeed cannot be negative or 0.");
    }
}
