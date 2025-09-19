package ca.umanitoba.cs.veranyan.output;

import ca.umanitoba.cs.veranyan.model.map.Map;

public class MapPrinter {
    public static final String OBSTACLE = "*";
    public static final String EMPTY = ".";
    public static final String ROUTE = ">";

    private final Map m;

    public MapPrinter(final Map map){
        this.m = map;
    }

    /**
     * Prints the map grid, including obstacles
     */
    public void print(){
        var obstacles = this.m.getObstacles();

        for (int j = 1; j <= this.m.getLength(); j++){ // y-coordinates
            for (int i = 1; i <= this.m.getWidth(); i++){ // x-coordinates
                boolean isInObstacle = false;
                for(var obstacle : obstacles){
                    var coordinates = new int[][] {{obstacle.upperLeftX(), obstacle.upperLeftY()}, {obstacle.lowerRightX(), obstacle.lowerRightY()}};
                    isInObstacle = ((i >= coordinates[0][0]) && (i <= coordinates[1][0]) &&
                            ((j >= coordinates[0][1]) && (j <= coordinates[1][1])));
                    if(isInObstacle) break;
                }

                if(isInObstacle)
                    System.out.print(OBSTACLE + " ");
                else
                    System.out.print(EMPTY + " ");
                // FIXME add Route path entry option as well
            }
            System.out.println();
        }
        System.out.println();
    }
}
