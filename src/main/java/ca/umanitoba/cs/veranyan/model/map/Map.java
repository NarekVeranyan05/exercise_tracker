package ca.umanitoba.cs.veranyan.model.map;

import ca.umanitoba.cs.veranyan.model.collections.ArraySet;

import java.util.ArrayList;

public class Map {
    private static Map singleton;

    private final int width; // should be > 0
    private final int length; // should be > 0
    private final ArraySet<Obstacle> obstacles;

    // cannot create instances from external
    private Map(final int width, final int length) {
        this.width = width;
        this.length = length;
        this.obstacles = new ArraySet<>();
    }

    public static Map getInstance(int width, int length) {
        // create new singleton instance if none exists
        if (singleton == null) {
            // FIXME add condition
            singleton = new Map(width, length);
        }

        return singleton;
    }

    public static void destroyInstance(){
        singleton = null;
    }

    public ArraySet<Obstacle> getObstacles() {
        return obstacles;
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
        if (!(upperLeftX >= 0 && upperLeftX < lowerRightX  && lowerRightX < this.width &&
                upperLeftY >= 0 && upperLeftY < lowerRightY && lowerRightY < this.length))
            throw new IllegalArgumentException(
                    String.format("Obstacle out of bounds (%d, %d)", this.width, this.length));

        this.obstacles.add(new Obstacle(upperLeftX, upperLeftY, lowerRightX, lowerRightY));

        return true; // FIXME catch the error and return false whenever creation impossible
    }

    public boolean isInObstacle(int x, int y) {
        boolean isInObstacle = false;

        for(var obstacle : this.obstacles){
            isInObstacle = obstacle.contains(x, y);
            if(isInObstacle) break;
        }

        return isInObstacle;
    }
}
