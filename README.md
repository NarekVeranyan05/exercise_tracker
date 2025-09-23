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
%%    class TrackerManager {
%%        Exerciser exerciser
%%        Map map
%%        
%%        +Read(Scanner) void 
%%    }
%%    
    
    class Exerciser {
        %% order by date
        -String name
        -ArrayList~Activity~ activities 
        -Set~Gear~ gears
        
        +addActivity() boolean
        %% should be >= 0
        +removeActivity(int) boolean
        +addGear() void
        %% should be >= 0
        +removeGear(int) boolean
    }
    
    note for Exerciser " Invariants
        * name != null
        * name.length() >= 1
        * activities != null
        * gears != null
        * gears.length >= 1 
    "
    
    class Map {
        %% should be > 0
        -int width
        %% should be > 0
        -int length
        -ArrayList~Obstacle~ obstacles
        -ArrayList~Route~ routes
        
        %% should return > 0
        +getWidth() int
        %% should return > 0
        +getLength() int
        %% params should be > 0
        +addObstacle(int, int, int, int) boolean
        +isInObstacle(int x, int y) boolean
        +isInRoute(int x, int y) boolean
    }
    
    note for Map "Invariants
        * width >= 1
        * length >= 1
        * obstacles != null
    "
    
    class Obstacle {
        %% should be > 0
        -int upperLeftX
        %% should be > 0
        -int upperLeftY
        %% should be > 0
        -int lowerRightX
        %% should be > 0
        -int lowerRightY
        
        +contains(int x, int y) boolean
    }
    
    class Route {
        ArrayList~Integer~ coordinatesX
        ArrayList~Integer~ coordinatesY

        +move(Direction, int) void
        +contains(int x, int y) boolean
    }
    
    class Activity {
        -Route route
        -Gear gear
        -LocalDate start
        -LocalDate end
     
        +startActivity() void
        +endActivity() void
    }
    
    %% FIXME how to state end > start
    note for Activity "Invariants
        * gear != null
        * start != null
        * end != null
        * end > start
        * route != null
    "
    
    class Gear {
        -GearType type
        -String name
    }
    
    note for Gear "Invariants
        * type != null
        * name != null
        * name.length() >= 1
    "
    
    class MapPrinter {
        -Map map
        
        +printRoutes() void
        +printRoute(int i)
    }

    note for MapPrinter "Invariants
        * map != null
    "
    
    
    
    %% class relationships
    Exerciser --* Activity
    Exerciser --* Gear
    Activity --* Route
    Map --* Obstacle
    
    Map --o Route
    Activity --o Gear
    MapPrinter --o Map
```