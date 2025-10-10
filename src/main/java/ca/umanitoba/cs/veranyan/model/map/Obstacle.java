package ca.umanitoba.cs.veranyan.model.map;

import com.google.common.base.Preconditions;

/**
 * An obstacle is an object which a {@link Route}
 * cannot pass through. Obstacles are on the {@link Map}.
 * An obstacle must be within {@link Map} boundaries.
 * @param topLeftCoord the Coordinate of the top-left vertex of the obstacle. Must be non-negative.
 * @param bottomRightCoord the Coordinate of the bottom-right vertex of the obstacle. Must be non-negative.
 */
public record Obstacle(Coordinate topLeftCoord, Coordinate bottomRightCoord){
    public Obstacle {
        // validating record arguments as preconditions before field initialisation.
        Preconditions.checkNotNull(topLeftCoord, "coordinate cannot be null.");
        Preconditions.checkNotNull(bottomRightCoord, "coordinate cannot be null.");

        Preconditions.checkState(bottomRightCoord.x() >= topLeftCoord.x(),
                "bottom-right x-coordinate cannot be less than than top-left x-coordinate.");
        Preconditions.checkState(bottomRightCoord.y() >= topLeftCoord.y(),
                "bottom-right y-coordinate cannot be less than top-left y-coordinate.");
    }

    /**
     * Determines whether a point is withing the Obstacle.
     * @param x the x-coordinate of the point.
     * @param y the y-coordinate of the point.
     * @return true if (x, y) is within Obstacle boundaries; false otherwise.
     */
    public boolean contains(int x, int y) {
        /*
        (x, y) is within bounds if its x-coordinate sits in between the top-left and bottom-right x-coordinates of
        the Obstacle, and if its x-coordinate sits in between the top-left and bottom-right y-coordinates of the Obstacle.
         */
        return (x >= topLeftCoord.x()) && (x <= bottomRightCoord.x()) &&
                (y >= topLeftCoord.y()) && (y <= bottomRightCoord.y());
    }
}