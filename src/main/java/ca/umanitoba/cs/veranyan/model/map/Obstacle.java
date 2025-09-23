package ca.umanitoba.cs.veranyan.model.map;

/*
width should be > 0
length should be > 0
upperLeftX should be > 0
upperLeftY should be > 0
lowerRightX should be > 0
lowerRightX should be > 0
 */
public record Obstacle(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY){
    public Obstacle {
        // validate width and length
        final int width = lowerRightX - upperLeftX + 1;
        final int length = lowerRightY - upperLeftY + 1;

        if (width <= 0 || length <= 0)
            throw new IllegalArgumentException(
                    String.format("cannot accept non-positive width and length, received width %d and length %d.",
                            width, length));
    }

    public boolean contains(int x, int y){
        return (x >= this.upperLeftX()) && (x <= this.lowerRightX()) &&
                (y >= this.upperLeftY()) && (y <= this.lowerRightY());
    }
}