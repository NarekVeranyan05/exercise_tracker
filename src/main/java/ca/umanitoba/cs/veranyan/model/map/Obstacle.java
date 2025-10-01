package ca.umanitoba.cs.veranyan.model.map;

import com.google.common.base.Preconditions;

/**
 * An obstacle is an object which an {@link ca.umanitoba.cs.veranyan.model.Activity}
 * cannot pass through. Obstacles are on the {@link Map}
 * @param upperLeftX the non-negative upper-left x-coordinate of the obstacle on the map
 * @param upperLeftY the non-negative upper-left y-coordinate of the obstacle on the map
 * @param lowerRightX the non-negative lower-right x-coordinate of the obstacle on the map
 * @param lowerRightY the non-negative lower-right y-coordinate of the obstacle on the map
 */
public record Obstacle(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY) {
    public Obstacle {
        // validating record arguments as preconditions before field initialisation.
        Preconditions.checkState(upperLeftX >= 0, "x-coordinate cannot be negative.");
        Preconditions.checkState(lowerRightY >= 0, "y-coordinate cannot be negative.");
        Preconditions.checkState(lowerRightX >= upperLeftX,
                "lower right x-coordinate cannot be less than than upper left x-coordinate.");
        Preconditions.checkState(lowerRightY >= upperLeftY,
                "lower right y-coordinate cannot be less than upper left y-coordinate.");
    }

    /**
     * Determines whether a point is withing the Obstacle.
     * @param x the x-coordinate of the point.
     * @param y the y-coordinate of the point.
     * @return true if (x, y) is within Obstacle boundaries; false otherwise.
     */
    public boolean contains(int x, int y) {
        /*
        (x, y) is within bounds if its x-coordinate sits in between the upper-left and lower-right x-coordinates of
        the Obstacle, and if its x-coordinate sits in between the upper-left and lower-right y-coordinates of the Obstacle.
         */
        return (x >= this.upperLeftX()) && (x <= this.lowerRightX()) &&
                (y >= this.upperLeftY()) && (y <= this.lowerRightY());
    }
}