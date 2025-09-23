package ca.umanitoba.cs.veranyan.output;

import ca.umanitoba.cs.veranyan.model.map.Map;

import java.sql.SQLOutput;

public class MapPrinter {
    public static final String OBSTACLE = "*";
    public static final String EMPTY = ".";
    public static final String ROUTE = ">";

    private final Map m;

    public MapPrinter(final Map map){
        if(map == null)
            throw new IllegalArgumentException("Map instance required as a parameter. Received null.");

        this.m = map;
    }

    /**
     * Prints the map grid, including obstacles
     */
    public void printRoutes(){
        for (int j = 1; j <= this.m.getLength(); j++){ // y-coordinates
            for (int i = 1; i <= this.m.getWidth(); i++){ // x-coordinates
                if(this.m.isInObstacle(i, j))
                    System.out.print(OBSTACLE + " ");
                else if(this.m.isInRoute(i, j)) // searches in all routes
                    System.out.println(ROUTE + " ");
                else System.out.println(EMPTY + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printRoute(int index){
        for (int j = 1; j <= this.m.getLength(); j++){ // y-coordinates
            for (int i = 1; i <= this.m.getWidth(); i++){ // x-coordinates
                if(this.m.isInObstacle(i, j))
                    System.out.print(OBSTACLE + " ");
                else if(this.m.isInRoute(index, i, j)) // searches in particular route
                    System.out.println(ROUTE + " ");
                else System.out.println(EMPTY + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
