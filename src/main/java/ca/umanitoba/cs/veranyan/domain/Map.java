package ca.umanitoba.cs.veranyan.domain;

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

    public static Map getInstance(final int width, final int length) {
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
     * Adds an obstacle to the Map instance
     * Note: rectangle vertex coordinates should not exceed Map
     * @param width a positive integer
     * @param length a positive integer
     * @param upperLeftX a positive integer indicating the upper-left x-coordinate of rectangular obstacle
     * @param upperLeftY a positive integer indicating the upper-left x-coordinate of rectangular obstacle
     */
    public void addObstacle(int width, int length, int upperLeftX, int upperLeftY) {
        // validate width and length
        if (width <= 0 || length <= 0)
            throw new IllegalArgumentException(
                    String.format("cannot accept non-positive width and length, received width %d and length %d.",
                            width, length));

        // compute other diagonal vertex coordinates
        int lowerRightX = upperLeftX + width - 1;
        int lowerRightY = upperLeftY + length - 1;

        // validate boundaries
        if (!(upperLeftX > 0 && lowerRightX <= this.width && upperLeftY > 0 && lowerRightY <= this.length))
            throw new IllegalArgumentException(
                    String.format("Obstacle out of bounds (%d, %d)", this.width, this.length));

        this.obstacles.add(new Obstacle(width, length, upperLeftX, upperLeftY));
    }

//    @Override
//    public String toString(){
//        StringBuilder mapGrid = new StringBuilder();
//
//        for (int j = 1; j <= this.length; j++){ // y-coordinates
//            for (int i = 1; i <= this.width; i++){ // x-coordinates
//                boolean isInObstacle = false;
//                for(var obstacle : this.obstacles){
//                    isInObstacle = obstacle.isInObstacle(i, j);
//                    if(isInObstacle) break;
//                }
//
//                if(isInObstacle) mapGrid.append(OBSTACLE + " ");
//                else mapGrid.append(EMPTY + " ");
//                // FIXME add Route path entry option as well
//            }
//            mapGrid.append("\n");
//        }
//        mapGrid.append("\n");
//
//        return mapGrid.toString();
//    }
}
