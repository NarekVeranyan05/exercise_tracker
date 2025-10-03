package ca.umanitoba.cs.veranyan.model.map;

import ca.umanitoba.cs.veranyan.model.Coordinate;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

public class Route {
    List<Coordinate> coordinates;

    public Route(int x, int y){
        coordinates = new ArrayList<>();
        coordinates.add(new Coordinate(x, y));
    }
    
    public int getStepsAmount(){
        return coordinates.size();
    }

    /**
     * @param index the index of the Coordinate to return.
     * @return the Coordinate at a particular index.
     */
    public Coordinate getCoordinate(int index){
        return coordinates.get(index);
    }

    /**
     * Makes a move in a particular direction on the Map, adding indicated number of coordinates to the Activity route.
     * @param direction the Direction to move. Must be any of [UP = 1, RIGHT = 2, DOWN = 3, LEFT = 4].
     * @param steps the number of steps of the move. Must be non-negative.
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

        for(int i = 0; i < coordinates.size() && !contains; i++)
            contains = (x == coordinates.get(i).x()) && (y == coordinates.get(i).y());

        checkRoute();

        return contains;
    }

    private void checkRoute(){
        Preconditions.checkNotNull(coordinates, "coordinates cannot be null.");
        Preconditions.checkState(!coordinates.isEmpty(), "coordinates must have at least one entry.");
    }
}
