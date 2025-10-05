package ca.umanitoba.cs.veranyan.output;

import ca.umanitoba.cs.veranyan.model.map.Map;
import com.google.common.base.Preconditions;

/**
 * The printer class for the {@link Map}
 */
public class MapPrinter {
    // symbols to display on grid
    public static final String OBSTACLE = "*";
    public static final String EMPTY = ".";
    public static final String ROUTE = ">";

    private final Map map;

    /**
     * Constructor for MapPrinter.
     * @param map the Map singleton to be printed. Must not be {@code null}.
     */
    public MapPrinter(Map map){
        this.map = map;

        checkMapPrinter();
    }

    /**
     * Prints the Map and all its Activities. This method prints to standard output (`System.out`).
     */
    public void print(){
        checkMapPrinter();

        System.out.println("Legend:");
        System.out.printf("Grid layout: %dx%d.\n", map.getWidth(), map.getLength());
        System.out.println("Obstacle coordinate: " + OBSTACLE);
        System.out.println("Route coordinate: " + ROUTE);
        System.out.println("Empty coordinate: " + EMPTY);

        /*
        calculating largest number of digits for both x- and y-coordinates
        in order to use for indenting and proper output formatting.
         */

        int maxXLen;
        if(map.getWidth() > 9)
            maxXLen = (int) Math.log10(map.getWidth() - 1) + 1; // the number of digits in the largest x-coordinate
        else{ // map width 1 crash prevention
            maxXLen = 1;
        }

        int maxYLen;
        if(map.getLength() > 9)
            maxYLen = (int) Math.log10(map.getLength() - 1) + 1; // the number of digits in the largest y-coordinate
        else { // map length 1 crash prevention
            maxYLen = 1;
        }

        // printing x-coordinates
        System.out.printf("%" + (maxYLen+1) + "s", ""); // indent for y-coordinate
        for(int i = 0; i < map.getWidth(); i++)
            System.out.printf(" %" + maxXLen + "d", i);
        System.out.println();

        for (int y = 0; y < map.getLength(); y++){ // y-coordinates
            System.out.printf("%" + maxYLen + "d|", y); // printing the y-coordinate

            for (int x = 0; x < map.getWidth(); x++){ // x-coordinates
                if(map.isInObstacle(x, y)) // searches in all obstacles
                    System.out.printf(" %" + maxXLen + "s", OBSTACLE);
                else if(map.isInRoute(x, y)) // searches in all activities
                    System.out.printf(" %" + maxXLen + "s", ROUTE);
                else System.out.printf(" %" + maxXLen + "s", EMPTY);
            }
            System.out.println();
        }
        System.out.println();

        checkMapPrinter();
    }

    /**
     * Prints the Map and its Activity at index. This method prints to standard output (`System.out`).
     * @param index the index of the Activity to be printed on the grid.
     */
    public void print(int index){
        System.out.println("Legend:");
        System.out.printf("Grid layout: %dx%d.\n", map.getWidth(), map.getLength());
        System.out.println("Obstacle coordinate: " + OBSTACLE);
        System.out.println("Route coordinate: " + ROUTE);
        System.out.println("Empty coordinate: " + EMPTY);

        /*
        calculating largest number of digits for both x- and y-coordinates
        in order to use for indenting and proper output formatting.
         */

        int maxXLen;
        if(map.getWidth() > 9)
            maxXLen = (int) Math.log10(map.getWidth() - 1) + 1; // the number of digits in the largest x-coordinate
        else{ // map width 1 crash prevention
            maxXLen = 1;
        }

        int maxYLen;
        if(map.getLength() > 9)
            maxYLen = (int) Math.log10(map.getLength() - 1) + 1; // the number of digits in the largest y-coordinate
        else { // map length 1 crash prevention
            maxYLen = 1;
        }

        // printing x-coordinates
        System.out.printf("%" + (maxYLen+1) + "s", ""); // indent for y-coordinate
        for(int i = 0; i < map.getWidth(); i++)
            System.out.printf(" %" + maxXLen + "d", i);
        System.out.println();

        for (int j = 0; j < map.getLength(); j++){ // y-coordinates
            System.out.printf("%" + maxYLen + "d|", j); // printing the y-coordinate

            for (int i = 0; i < map.getWidth(); i++){ // x-coordinates
                if(map.isInObstacle(i, j)) // searches in all obstacles
                    System.out.printf(" %" + maxXLen + "s", OBSTACLE);
                else if(map.isInRoute(index, i, j)) // searches in Activity at index
                    System.out.printf(" %" + maxXLen + "s", ROUTE);
                else System.out.printf(" %" + maxXLen + "s", EMPTY);
            }
            System.out.println();
        }
        System.out.println();

        checkMapPrinter();
    }

    /**
     * Ensures MapPrinter invariants are not violated.
     */
    private void checkMapPrinter(){
        Preconditions.checkNotNull(map, "map cannot be null.");
    }
}
