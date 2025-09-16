package ca.umanitoba.cs.veranyan.domain;

public class Obstacle {
    private int width; // should be > 0
    private int length; // should be > 0
    private int x; // the x coordinate of the upper left vertex (has to be >= 0) of the rectangular obstacle
    private int y; // the y coordinate of the upper left vertex (has to be >=0) of the rectangular obstacle

    public Obstacle(final int width, final int length, final int x, final int y){
        this.width = width;
        this.length = length;
        this.x = x;
        this.y = y;
    }

    public boolean isInObstacle(int x, int y){
        boolean isInObstacle = false;

        // checks if (x, y) pair is within the obstacle
        if(x >= this.x && x <= (this.x + width - 1) && y >= this.y && y <= (this.y + length -1))
            isInObstacle = true;

        return isInObstacle;
    }
}
