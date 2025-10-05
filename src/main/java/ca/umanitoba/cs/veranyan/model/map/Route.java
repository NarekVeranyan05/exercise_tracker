package ca.umanitoba.cs.veranyan.model.map;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * A Route is the path of an {@link ca.umanitoba.cs.veranyan.model.Activity} through the {@link Map} grid.
 * Contains information about the path taken and its distance.
 * A Route cannot overlap with any {@link Obstacle} on the Map.
 */
public class Route {
    List<Coordinate> coordinates;

    /**
     * A constructor for Route. A Route cannot overlap with any {@link Obstacle} on the Map.
     * @param x the non-negative x-coordinate of the starting point (x, y) of the Route.
     * @param y the non-negative y-coordinate of the starting point (x, y) of the Route.
     */
    public Route(int x, int y){
        coordinates = new ArrayList<>();
        coordinates.add(new Coordinate(x, y)); // adding starting point (x, y)

        checkRoute();
    }

    /**
     * @return the number of steps that the {@link ca.umanitoba.cs.veranyan.model.Exerciser}
     * passed in the {@link ca.umanitoba.cs.veranyan.model.Activity}.
     * @implNote a step is one coordinate on the {@link Map} grid.
     */
    public int getStepsAmount(){
        checkRoute();

        return coordinates.size();
    }

    /**
     * @param index the index of the Coordinate to return.
     * @return the Coordinate at a particular index. Must not be {@code null}.
     */
    public Coordinate getCoordinate(int index){
        checkRoute();

        return coordinates.get(index);
    }

    /**
     * Makes a move in a particular direction on the Map, adding indicated number of coordinates to the Activity route.
     * @param direction the direction to move. Must be any of and only of [UP = 1, RIGHT = 2, DOWN = 3, LEFT = 4].
     * @param steps the number of steps of the move. Must be non-negative.
     * @implNote a step is one coordinate on the {@link Map} grid.
     */
    public void move(int direction, int steps){
        checkRoute();

        var currCoordinate = coordinates.get(coordinates.size()-1); // the current (x, y) coordinate

        switch(direction){
            case 1: // move up
                for(int i = 1; i <= steps; i++) {
                    coordinates.add(new Coordinate(
                            currCoordinate.x(), currCoordinate.y() - i
                    ));
                }
                break;
            case 2: // move right
                for(int i = 1; i <= steps; i++){
                    coordinates.add(new Coordinate(
                            currCoordinate.x() + i, currCoordinate.y()
                    ));
                }
                break;
            case 3: // move down
                for(int i = 1; i <= steps; i++){
                    coordinates.add(new Coordinate(
                            currCoordinate.x(), currCoordinate.y() + i
                    ));
                }
                break;
            case 4: // move left
                for(int i = 1; i <= steps; i++){
                    coordinates.add(new Coordinate(
                            currCoordinate.x() - i, currCoordinate.y()
                    ));
                }
                break;
        }

        checkRoute();
    }

    /**
     * Determines whether a point is within the Route.
     * @param x the x-coordinate of the point.
     * @param y the y-coordinate of the point.
     * @return true if (x, y) is in the Route; false otherwise.
     */
    public boolean contains(int x, int y){
        checkRoute();

        boolean contains = false;

        // going over all coordinates in the Route.
        for(int i = 0; i < coordinates.size() && !contains; i++)
            contains = (x == coordinates.get(i).x()) && (y == coordinates.get(i).y());

        checkRoute();

        return contains;
    }

    /**
     * Ensures Route invariants are not violated.
     */
    private void checkRoute(){
        Preconditions.checkNotNull(coordinates, "coordinates cannot be null.");
        Preconditions.checkState(!coordinates.isEmpty(), "coordinates must have at least one entry.");
    }
}
