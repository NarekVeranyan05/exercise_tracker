* Title: Track-Me-Riding
* Author: Narek Veranyan (veranyan@myumanitoba.ca)
* Student number: 8040209
* Date: October __, 2025
---

# Overview
> Track-Me-Riding is an implementation of an exercise tracker software for COMP 2450 
> specifically designed for tracking cycling activities. The software offers
>
>   * A user-defined grid-structured map to track an activity.
>   * There are obstacles that can be added to the map.
>   * There are gears to be recorded and later added to an activity.

## Vision Statement
> Build software that allows exercises to track exercises
> over a map, share information about their exercises,
> and measure performance over time.

## Resources
* used the following existing system to come up with classes in the domain model: <https://www.strava.com/>
* found information about bike types: <https://www.edinburghbicycle.com/info/blog/types-of-bikes-buying-guide>

## Running
This project was built using Maven and developed in IntelliJ IDEA.
The project can be run using several options:

1. Open the `Main.java` class and click the green play button 
    in the top right corner.
2. Run Maven on the command line:
    ```
    mvn compile exec:java -Dexec.mainClass="ca.umanitoba.cs.veranyan.Main"   
    ```


# Domain model

Here's my domain model:

```mermaid 
classDiagram
    class Exerciser {
        -Map map
        -SortedSet~Gear~ gears
        
        +getGears() SortedSet~Gear~
        +getGear(int) Gear
        +addGear(GearType, String, double)
        +removeGear(int) void
        +getMap() Map
        +addMap(Map) void
        +removeMap() void
    }
    
    note for Exerciser"invariants:
        * gears != null
        * gears.length >= 1
        * loop: no entry is null in gears
    "

    Exerciser --* Gear
    Exerciser --* Map
    
    class Gear {
        -GearType type
        -String name
        -double avgSpeed
    }
    
    note for Gear"invariants:
        * type != null
        * name != null
        * name.length() >= 1
        * avgSpeed > 0
    "
    
    Gear --* GearType
    
    class GearType {
        <<enumeration>>
        ROAD_BIKE,
        MOUNTAIN_BIKE,
        COMMUTER_BIKE,
        ELECTRIC_BIKE,
        TANDEM_BIKE
    }

    class Map {
        -int width
        -int length
        -List~Obstacle~ obstacles
        -SortedSet~Activity~ activities

        +getWidth() int
        +getLength() int
        +getObstacles() List~Obstacle~
        +getActivities() SortedSet~Activity~
        +addObstacle(int, int, int, int) void
        +removeObstacle(int) void
        +addActivity(Activity) void
        +removeActivity(int) void
        +isInFeature(MapFeatureType type, int, int) boolean
    }

    note for Map"invariants:
        * width >= 1
        * length >= 1
        * obstacles != null
        * activities != null
        * loop: no entry is null in obstacles
        * loop: no obstacle is out of boundaries
        * loop: no entry is null in activities
        * loop: no activity is out of boundaries
        * activities and obstacles don't overlap"

    Map --* Obstacle
    Map --* Activity
    
    class Activity{
        -Gear gear
        -LocalDateTime start
        -LocalDateTime end
        -List~Coordinate~ coordinates
        -double avgSpeed
        
        +getStepsAmount() int
        +getAvgSpeed() double
        +getCoordinate(int) Coordinate
        +getStart() LocalDateTime
        +getEnd() LocalDateTime
        +getGear() Gear
        +endActivity() void
        +move(Direction, int) void
        +contains(int, int) boolean
    }
    
    note for Activity"invariants:
        * gear != null
        * start != null
        * coordinates != null
        * coordinates.size() >= 1
        * avgSpeed >= 0"

    Activity --o Gear
    Activity --* Coordinate
    
    class Obstacle {
        -Coordinate topLeftCoord
        -Coordinate bottomRightCoord
        
        +contains(int, int) boolean
    }
    
    note for Obstacle"invariants:
        * upperLeftX >= 0
        * upperLeftY >= 0
        * lowerRightX > upperLeftX
        * lowerRightY > upperLeftY"
    
    Obstacle --* Coordinate
        
    class Coordinate {
        -int x
        -int y
        
        +getX() int
        +getY() int
    }
    
    note for Coordinate "invariants:
        * x >= 0
        * y >= 0
    "
    
%%    class ExerciserPrinter{
%%        -Exerciser exerciser
%%        
%%        +print() void
%%    }
%%    
%%    note for ExerciserPrinter"invariants:
%%        * exerciser != null
%%    "
%%    
%%    ExerciserPrinter --o Exerciser
%%    
%%    class GearPrinter {
%%        -Gear gear
%%        
%%        +print() void
%%    }
%%    
%%    note for GearPrinter"invariants:
%%        * gear != null
%%    "
%%    
%%    GearPrinter --o Gear
%%
%%    class MapPrinter {
%%        -Map map
%%        
%%        printMap() void
%%        printMap(int) void
%%    }
%%    
%%    note for MapPrinter"invariants:
%%        * map != null
%%    "
%%    
%%    MapPrinter --o Map
%%    
%%    class ActivityPrinter {
%%        -Activity activity
%%        
%%        print() void
%%    }
%%    
%%    note for ActivityPrinter"invariants:
%%        * activity != null
%%    "
%%
%%    class ObstaclePrinter {
%%        -Obstacle obstacle
%%
%%        +print() void
%%    }
%%
%%    note for ObstaclePrinter"invariants:
%%        * obstacles != null
%%    "
%%
%%    ObstaclePrinter --o Obstacle
%%    
%%    ActivityPrinter --o Activity
```