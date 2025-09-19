package ca.umanitoba.cs.veranyan.model.map;

import java.util.ArrayList;

public class Map {
    private static Map singleton;

    private final int width; // should be > 0
    private final int length; // should be > 0
    private final ArrayList<Obstacle> obstacles;

    // cannot create instances from external
    private Map(final int width, final int length) {
        this.width = width;
        this.length = length;
        this.obstacles = new ArrayList<>();
    }

    public static Map getInstance(int width, int length) {
        // create new singleton instance if none exists
        if (singleton == null) {
            if (width <= 0 || length <= 0)
                throw new IllegalArgumentException(
                        String.format("cannot accept non-positive width and length, received width %d and length %d.",
                                width, length));

            singleton = new Map(width, length);
        }

        return singleton;
    }

    /**
     * @return the non-negative number equaling the width of map
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the non-negative number equaling the length of map
     */
    public int getLength() {
        return length;
    }

    /**
     * @return the obstacles on the map
     */
    public ArrayList<Obstacle> getObstacles() {
        return (ArrayList<Obstacle>) this.obstacles.clone();
    }

    /**
     * Adds an obstacle to the Map instance
     * Note: rectangle vertex coordinates should not exceed Map
     * @param upperLeftX a positive integer indicating the upper-left x-coordinate of rectangular obstacle
     * @param upperLeftY a positive integer indicating the upper-left x-coordinate of rectangular obstacle
     * @param lowerRightX a positive integer indicating the lower-right x-coordinate of rectangular obstacle
     * @param lowerRightY a positive integer indicating the lower-right y-coordinate of rectangular obstacle
     */
    public boolean addObstacle(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY) {
        // validate boundaries
        if (!(upperLeftX > 0 && lowerRightX <= this.width && upperLeftY > 0 && lowerRightY <= this.length))
            throw new IllegalArgumentException(
                    String.format("Obstacle out of bounds (%d, %d)", this.width, this.length));

        this.obstacles.add(new Obstacle(upperLeftX, upperLeftY, lowerRightX, lowerRightY));

        return true; // FIXME catch the error and return false whenever creation impossible
    }
}
