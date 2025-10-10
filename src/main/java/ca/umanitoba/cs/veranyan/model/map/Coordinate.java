package ca.umanitoba.cs.veranyan.model.map;

import com.google.common.base.Preconditions;

/**
 * A Coordinate instance contains the x- and y-values of a point
 * (x, y) on the {@link Map} grid.
 * @param x the non-negative x-component of the point (x, y) represented by the instance.
 * @param y the non-negative y-component of the point (x, y) represented by the instance.
 */
public record Coordinate(int x, int y) {
    /**
     * Compact constructor for Coordinate.
     * @param x the non-negative x-component of the point (x, y) represented by the instance.
     * @param y the non-negative y-component of the point (x, y) represented by the instance.
     */
    public Coordinate {
        // validating record arguments as preconditions before field initialisation.
        Preconditions.checkState(x >= 0, "x-coordinate cannot be negative.");
        Preconditions.checkState(y >= 0, "y-coordinate cannot be negative.");
    }
}
