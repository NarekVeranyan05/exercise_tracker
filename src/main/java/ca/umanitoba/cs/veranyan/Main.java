package ca.umanitoba.cs.veranyan;

import ca.umanitoba.cs.veranyan.model.Activity;
import ca.umanitoba.cs.veranyan.model.Profile;
import ca.umanitoba.cs.veranyan.model.gear.Gear;
import ca.umanitoba.cs.veranyan.model.gear.GearType;
import ca.umanitoba.cs.veranyan.model.map.Map;
import ca.umanitoba.cs.veranyan.model.map.Obstacle;
import ca.umanitoba.cs.veranyan.model.map.Route;
import ca.umanitoba.cs.veranyan.output.ActivityPrinter;
import ca.umanitoba.cs.veranyan.output.GearPrinter;
import ca.umanitoba.cs.veranyan.output.MapPrinter;
import ca.umanitoba.cs.veranyan.output.ObstaclePrinter;
import com.google.common.base.Preconditions;

import java.util.Scanner;

/**
 * The main class is the exercise-tracking manager
 * to interact with the application.
 * @implNote start the main method in order to run the exercise tracker program.
 */
public class Main{
    private static Scanner scnr;
    private static Profile exerciser;

    /**
     * Prints out to standard output stream (System.out).
     * Takes input from standard input stream (System.in).
     * @param args arguments from command line.
     */
    public static void main(String[] args) {
        scnr = new Scanner(System.in);

        // account set-up process. Initial gear needs to be added to create the account
        System.out.println("Welcome to the exercise tracker.");
        System.out.println("Getting started with your account...");
        System.out.println("Add new gear to the account:");
        addGear();
        System.out.println("Account creation completed. Let's get started.\n");

        printMenu();

        // prompting user input to start processing
        int optionResponse = promptInt("Enter selected option number or -1 to quit");
        while(optionResponse != -1){
            switch (optionResponse){
                case 1:
                    addGear();
                    break;
                case 2:
                    addMap();
                    break;
                case 3:
                    addObstacle();
                    break;
                case 4:
                    addActivity();
                    break;
                case 5:
                    showMap(true);
                    break;
                case 6:
                    showGear();
                    break;
                case 7:
                    showObstacles();
                    break;
                case 8:
                    showActivities();
                    break;
                case 9:
                    showActivity();
                    break;
                case 10:
                    removeGear();
                    break;
                case 11:
                    removeActivity();
                    break;
                case 12:
                    removeObstacle();
                    break;
                case 13:
                    removeMap();
                    break;
                default:
                    System.out.println("Invalid entry. Please try again.");
            }

            System.out.println();
            printMenu();
            optionResponse = promptInt("Enter selected option number or -1 to quit");
        }
    }

    /**
     * Prompts user to add new {@link Gear}. Prints out to standard output stream (System.out).
     * Takes input from standard input stream (System.in).
     */
    private static void addGear() {
        String profileName = promptString("Enter your profile name");

        // prints out GearType values
        System.out.println("Gear types:");
        int i = 1;
        for(var type : GearType.values()){
            System.out.print(i + ". ");
            System.out.println(type);
            i++;
        }
        int gearTypeNumber = promptInt(
                String.format("Enter select gear type number (should be value from 0 to %d inclusive)", --i));

        var type = GearType.values()[gearTypeNumber-1];
        scnr.nextLine();
        String name = promptString("Enter gear name (cannot be blank)");
        double avgSpeed = promptDouble("Enter the average speed in meters per second (must be positive)");

        if(exerciser == null)
            exerciser = new Profile(profileName, new Gear(type, name, avgSpeed));
        else exerciser.addGear(new Gear(type, name, avgSpeed));
    }

    /**
     * Prompts user to add new {@link Map}.
     * Prints out to standard output stream (System.out).
     * Takes input from standard input stream (System.in).
     */
    private static void addMap() {
        Preconditions.checkNotNull(exerciser, "exerciser cannot be null");

        if(exerciser.getMap() == null){
            int width = promptInt("Enter width for the map (must be greater than 0)");
            int length = promptInt("Enter length for the map (must be greater than 0)");

            exerciser.addMap(Map.getInstance(width, length));
            showMap(false);
        }
        else
            System.out.println("Map already exists. You first have to remove the existing map to create a new one.");
    }

    /**
     * Prompts user to add new {@link Obstacle}.
     * Prints out to standard output stream (System.out).
     * Takes input from standard input stream (System.in).
     */
    private static void addObstacle() {
        if(exerciser.getMap() != null){
            showMap(false);

            System.out.println("---Obstacle coordinates must be within map boundaries---");
            int upperLeftX = promptInt("Enter upper-left x-coordinate");
            int upperLeftY = promptInt("Enter upper-left y-coordinate");
            int lowerRightX = promptInt("Enter lower-right x-coordinate");
            int lowerRightY = promptInt("Enter lower-right y-coordinate");

            exerciser.getMap().addObstacle(upperLeftX, upperLeftY, lowerRightX, lowerRightY);
        }
        else System.out.println("There is no map added to the system. Add a map first in order to add obstacles.");
    }

    /**
     * Prompts user to add new {@link Obstacle}.
     * Prints out to standard output stream (System.out).
     * Takes input from standard input stream (System.in).
     */
    private static void addActivity() {
        if(exerciser.getMap() != null){
            // gear selection
            showGear();
            int gearNumber = promptInt(
                    String.format("Enter selected gear number for the activity (must be from 1 to %d inclusive)",
                            exerciser.getGears().size()));

            // starting point selection
            showMap(false);
            System.out.println("---Activity starting coordinates must be within map boundaries---");
            int x = promptInt("Enter starting point x-coordinate");
            int y = promptInt("Enter starting point y-coordinate");

            // creating the activity
            Route route = new Route(x, y);
            Activity activity = new Activity(exerciser.getGear(gearNumber-1), route);
            exerciser.getMap().addActivity(activity);

            // movement throughout the activity
            int makeStep = promptInt("Enter 1 to move or -1 to end activity");
            while(makeStep != -1) {
                System.out.println("Directions: ");
                System.out.println("1. UP");
                System.out.println("2. RIGHT");
                System.out.println("3. DOWN");
                System.out.println("4. LEFT");

                System.out.println("---Activity route must be within map boundaries---");
                int directionNumber = promptInt("Enter selected direction number (must be from 1 to 4 inclusive)");
                int numberOfSteps = promptInt("Enter number of steps (must be non-negative)");
                route.move(directionNumber, numberOfSteps);

                showMap(false);
                makeStep = promptInt("Enter 1 to move or -1 to end activity");
            }
            activity.endActivity();
        }
        else System.out.println("There is no map added to the system. Add a map first in order to add activities.");
    }

    /**
     * Displays {@link Map}.
     * Prints out to standard output stream (System.out).
     * @param summarise true if summed distance of all routes is to be printed, false otherwise
     */
    private static void showMap(boolean summarise) {
        if(exerciser.getMap() != null)
            new MapPrinter(exerciser.getMap()).print(summarise);
        else System.out.println("There is no map added to the system. Add a map first in order to add obstacles.");
    }

    /**
     * Displays gears. Prints out to standard output stream (System.out)
     */
    private static void showGear() {
        if(exerciser != null){
            int i = 1;
            for(var gear : exerciser.getGears()){
                System.out.print(i + ". ");
                new GearPrinter(gear).print();
                System.out.println();
                i++;
            }
        }
        else System.out.println("No gear added yet.");
    }

    /**
     * Displays obstacles. Prints out to standard output stream (System.out)
     */
    private static void showObstacles() {
        if(exerciser.getMap() != null && !exerciser.getMap().getObstacles().isEmpty()){
            int i = 1;
            for(Obstacle obstacle : exerciser.getMap().getObstacles()) {
                System.out.print(i + ". ");
                new ObstaclePrinter(obstacle).print();
                System.out.println();
                i++;
            }
        }
        else System.out.println("There are no obstacles added yet.");
    }

    /**
     * Displays activities. Prints out to standard output stream (System.out).
     */
    private static void showActivities() {
        Map map = exerciser.getMap();

        if(map != null && !map.getActivities().isEmpty()){
            int i = 1;
            for(Activity activity : map.getActivities()){
                System.out.print(i + ". ");
                new ActivityPrinter(activity).print();
                System.out.println();
                i++;
            }
        }
        else System.out.println("There are no activities added yet.");
    }

    /**
     * Displays a single {@link Activity}'s {@link Route} on the {@link Map}.
     * Prints out to standard output stream (System.out).
     * Takes input from the standard output stream (System.in).
     */
    private static void showActivity() {
        Map map = exerciser.getMap();

        if(map != null && !map.getActivities().isEmpty()){
            showActivities();
            int activityNumber = promptInt("Enter selected activity number");

            new MapPrinter(map).print(activityNumber - 1);
        }
        else System.out.println("There are no activities added yet.");
    }

    /**
     * Removes {@link Gear} from the system.
     * Prints out to standard output stream (System.out).
     * Takes input from the standard output stream (System.in).
     */
    private static void removeGear() {
        if(exerciser != null){
            showGear();
            if(exerciser.getGears().size() == 1)
                System.out.println("At least one gear must be left in the list of gears. Add new gear to remove one.");
            else {
                int gearNumber = promptInt(
                        String.format("Enter selected gear number (must be from 1 to %d inclusive)",
                                exerciser.getGears().size()));
                exerciser.removeGear(gearNumber - 1);
            }
        }
        else System.out.println("No gears to remove.");
    }

    /**
     * Removes an {@link Activity} from the system.
     * Prints out to standard output stream (System.out).
     * Takes input from the standard output stream (System.in).
     */
    private static void removeActivity() {
        Map map = exerciser.getMap();

        if(map != null && !map.getActivities().isEmpty()) {
            showActivities();
            int activityNumber = promptInt(
                    String.format("Enter selected activity number (must be from 1 to %d inclusive)",
                            map.getActivities().size()));

            map.removeActivity(activityNumber - 1);
        }
        else System.out.println("No activities to remove.");
    }

    /**
     * Removes an {@link Obstacle} from the system.
     * Prints out to standard output stream (System.out).
     * Takes input from the standard output stream (System.in).
     */
    private static void removeObstacle() {
        Map map = exerciser.getMap();

        if(map != null && !map.getObstacles().isEmpty()) {
            showObstacles();
            int obstacleNumber = promptInt(
                    String.format("Enter selected obstacle number (must be from 1 to %d inclusive)",
                            map.getObstacles().size()));

            map.removeObstacle(obstacleNumber-1);
        }
        else System.out.println("No obstacles to remove.");
    }

    /**
     * Removes the {@link Map} singleton from the system.
     * Prints out to standard output stream (System.out).
     */
    private static void removeMap() {
        if(exerciser.getMap() != null) {
            exerciser.removeMap(); // removing reference in the exerciser
            Map.destroyInstance();
            System.out.println("Map removed successfully");
        }
        else System.out.println("No map to remove.");
    }

    /**
     * Prints the control menu.
     * Prints out to standard output stream (System.out).
     */
    public static void printMenu(){
        System.out.println("""
        Select one of the options below:
        1.  ADD GEAR
        2.  ADD MAP
        3.  ADD OBSTACLE
        4.  ADD ACTIVITY
        5.  SHOW MAP
        6.  SHOW GEAR
        7.  SHOW OBSTACLES
        8.  SHOW ACTIVITIES
        9.  SHOW ACTIVITY
        10. REMOVE GEAR
        11. REMOVE ACTIVITY
        12. REMOVE OBSTACLE
        13. REMOVE MAP
        """);
    }

    /**
     * Prompts to enter an integer. Reads input from a Scanner with unspecified input stream.
     * @param prompt the description of what needs to be passed as input.
     * @return the value that was passed as input.
     */
    private static int promptInt(String prompt){
        System.out.print(prompt + ": ");
        return scnr.nextInt();
    }

    /**
     * Prompts to enter a double. Reads input from a Scanner with unspecified input stream.
     * @param prompt the description of what needs to be passed as input.
     * @return the value that was passed as input.
     */
    private static double promptDouble(String prompt) {
        System.out.print(prompt + ": ");
        return scnr.nextDouble();
    }

    /**
     * Prompts to enter a String. Reads input from a Scanner with unspecified input stream.
     * @param prompt the description of what needs to be passed as input.
     * @return the value that was passed as input.
     */
    private static String promptString(String prompt){
        System.out.print(prompt + ": ");
        return scnr.nextLine();
    }
}