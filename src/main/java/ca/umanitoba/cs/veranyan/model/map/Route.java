package ca.umanitoba.cs.veranyan.model.map;

import ca.umanitoba.cs.veranyan.model.Direction;

import java.util.ArrayList;

public class Route {
    private final ArrayList<Integer> coordinatesX;
    private final ArrayList<Integer> coordinatesY;

    public Route(int x, int y){
        this.coordinatesX = new ArrayList<>();
        this.coordinatesY = new ArrayList<>();

        this.coordinatesX.add(x);
        this.coordinatesY.add(y);
    }

    public int getCurrentX(){
        return this.coordinatesX.get(this.coordinatesX.size()-1);
    }

    public int getCurrentY(){
        return this.coordinatesY.get(this.coordinatesY.size()-1);
    }

    public void addCoordinate(int x, int y){
        this.coordinatesX.add(x);
        this.coordinatesY.add(y);
    }


    /**
     * @param direction the Direction to move
     * @param steps the number of steps of the move
     */
    public void move(Direction direction, int steps) {
//        if (direction == null)
//            throw new IllegalArgumentException("Expected Direction as parameter. Received null");
//        if (steps < 0)
//            throw new IllegalArgumentException("steps cannot be negative");
//
//        int[] currentLocation = {this.coordinatesX.getLast(), this.coordinatesY.getLast()};
//        switch (direction) {
//            case Direction.UP:
//                for (int i = 1; i <= steps; i++) {
//                    this.coordinatesX.add(currentLocation[0]);
//                    this.coordinatesY.add(currentLocation[1] - i);
//                }
//                break;
//            case Direction.DOWN:
//                for (int i = 1; i <= steps; i++) {
//                    this.coordinatesX.add(currentLocation[0]);
//                    this.coordinatesY.add(currentLocation[1] + i);
//                }
//                break;
//            case Direction.LEFT:
//                for (int i = 1; i <= steps; i++) {
//                    this.coordinatesX.add(currentLocation[0] - i);
//                    this.coordinatesY.add(currentLocation[1]);
//                }
//                break;
//            case Direction.RIGHT:
//                for (int i = 1; i <= steps; i++) {
//                    this.coordinatesX.add(currentLocation[0] + i);
//                    this.coordinatesY.add(currentLocation[1]);
//                }
//                break;
//        }
    }

    public boolean contains(int x, int y){
        boolean isInRoute = false;

        for(int i = 0; i < this.coordinatesX.size() && !isInRoute; i++)
            isInRoute = (x == this.coordinatesX.get(i)) && (y == this.coordinatesY.get(i));

        return isInRoute;
    }
}
