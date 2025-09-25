# Narek Veranyan's Exercise Tracker Project

# Vision Statement
> Build software that allows exercises to track exercises
> over a map, log and share information about their exercises,
> and measure performance over time

# Resources
* found information about bike types: https://www.edinburghbicycle.com/info/blog/types-of-bikes-buying-guide

## Class Diagram

```mermaid 
classDiagram
    class Exerciser {
        -ArraySet~Gear~ gears
        -ArraySet~Activity~ activities
        
        +addGear(GearType, String, int)
        +removeGear(int) void
        +addActivity(int) void 
        +removeActivity(int) void
    }
    
    note for Exerciser"invariants:
        * gears != null
        * gears.length >= 1
        * activities != null
    "
    
    class Gear {
        -GearType type
        -String name
        -int avgSpeed
    }
    
    note for Gear"invariants:
        * type != null
        * name != null
        * name.length() >= 1
        * speed > 0
    "
    
    class GearType {
        <<enumeration>>
        ROAD_BIKE,
        MOUNTAIN_BIKE,
        COMMUTER_BIKE,
        ELECTRIC_BIKE,
        TANDEM_BIKE
    }
    
    class Activity{
        -Map map
        -LocalDateTime start
        -LocalDateTime end
        %% should have gear to start activity
        -Gear gear
        -Route route
        
        +startActivity() void
        +getRoute() Route
        +move(Direction, int) void
        +endActivity() void
    }
    
    note for Activity"invariants:
        * gear != null
        * route != null
    "
        
    class Route {
        %% each entry must be >= 0
        -ArrayList~Integer~ coordinatesX
        %% each entry must be >= 0
        -ArrayList~Integer~ coordinatesY
        
        +getCurrentX() int
        +getCurrentY() int
        +getDistance() int
        +addCoordinate(int, int) void
        +contains(int, int) boolean
    }
    
    note for Route"invariants:
        * coordinatesX != null
        * coordinatesX.size() >= 1
        * coordinatesY != null
        * coordinatesY.size() >= 1
    "
    
    class Direction {
        <<enumeration>>
        NORTH,
        SOUTH,
        EAST,
        WEST
    }
    
    class Map {
        -int width
        -int length
        -ArraySet~Obstacle~ obstacles
        
        +getObstacles() ArraySet~Obstacle~
        +addObstacle(int, int, int, int)
    }
    
    note for Map"invariants:
        * width >= 1
        * length >= 1
        * obstacles != null"
    
    class Obstacle {
        -int upperLeftX
        -int upperLeftY
        -int lowerRightX
        -int lowerRightY
        
        +contains(int, int) void
    }
    
    note for Obstacle"invariants
        * upperLeftX >= 0
        * upperLeftY >= 0
        * lowerRightX > upperLeftX
        * lowerRightY > upperLeftY
    "
    
    class GearPrinter {
        -ArraySet~Gear~ gears
        
        +setGears(ArrayList~Gear~) void
        +printGears() void
    }
    
    note for GearPrinter"invariants
        * gears != null
    "
    
    class RoutePrinter {
        -Map map
        -ArrayList~Route~ routes
        
        +setRoutes(ArrayList~Route~) void
        +printRoutes() void
    }

    note for RoutePrinter"invariants
        * map != null
        * routes != null
    "
    
    class ObstaclePrinter {
        -ArraySet~Obstacle~ obstacles
        
        +setObstacles(ArraySet~Obstacle~) void
        +printObstacles() void
    }

    note for ObstaclePrinter"invariants
        * obstacles != null
    "
    
    %% class relationships
    Exerciser --* Activity
    Exerciser --* Gear
    Activity --* Route
    Map --* Obstacle
    Gear --* GearType
    
    Activity --o Gear
    Activity --o Map
    GearPrinter --o Gear
    RoutePrinter --o Route
    RoutePrinter --o Map
    ObstaclePrinter --o Obstacle

    Route --> Direction
```