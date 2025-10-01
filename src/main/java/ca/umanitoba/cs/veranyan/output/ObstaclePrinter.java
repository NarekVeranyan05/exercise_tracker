package ca.umanitoba.cs.veranyan.output;

import ca.umanitoba.cs.veranyan.model.map.Obstacle;
import com.google.common.base.Preconditions;

/**
 * The printer class for an {@link Obstacle}
 */
public class ObstaclePrinter {
    private final Obstacle obstacle;

    /**
     * Constructor for ObstaclePrinter
     * @param obstacle the Obstacle to be printed. Must not be {@code null}.
     */
    public ObstaclePrinter(Obstacle obstacle){
        this.obstacle = obstacle;

        checkObstaclePrinter();
    }

    /**
     * Prints an Obstacle and its properties. This method prints to standard output (`System.out`).
     */
    public void print(){
        int width = obstacle.lowerRightX() - obstacle.upperLeftX() + 1;
        int length = obstacle.lowerRightY() - obstacle.upperLeftY() + 1;
        System.out.printf("Obstacle has width %d and length %d.\n", width, length);
        System.out.printf("Coordinates of upper-left and lower-right vertices: (%d, %d), (%d, %d).",
                obstacle.upperLeftX(), obstacle.upperLeftY(), obstacle.lowerRightX(), obstacle.lowerRightY());
    }

    /**
     * Ensures Obstacle invariants are not violated.
     */
    private void checkObstaclePrinter() {
        Preconditions.checkNotNull(obstacle, "obstacle cannot be null");
    }

}
