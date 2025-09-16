# Narek Veranyan's Exercise Tracker Project

# Vision Statement 
> Build software that allows cyclists to track activities
> over a map, record gear, and measure performance changes over time.

# Resources
* found information about bike types: https://www.edinburghbicycle.com/info/blog/types-of-bikes-buying-guide

## Class Diagram

```mermaid
classDiagram
    class Cyclist {
        -List~Activity~ activities
        -Collection~Bicycle~ bikes

%%        +addActivity() void
%%        +addBike(text) void
    }
    
    class Map {
        %% should be > 0
        -int width
        %% should be > 0
        -int length
        -Set~Obstacle~ obstacles
        
        %% params should be > 0
        +addObstacle(int, int, int, int) void
    }
    
    class MapPrinter {
        -Map map
        
        +print() void
    }
    
    class Obstacle {
        %% should be > 0
        -int width
        %% should be > 0
        -int length
        -NaturalNumber coordinateX
        -NaturalNumber coordinateY
    }
    
    class Route {
        List~NaturalNumber~ coordinatesX
        List~NaturalNumber~ coordinatesY
    }
    
    class Activity {
        -Bicycle bike
        -Route route
        -PositiveNumber duration
    }
    
    class Bicycle {
        -text name
        -text type
        -positiveNumber wheelSize
    }
    
    
    %% class relationships
    Cyclist --* Activity
    Cyclist --* Bicycle
    Activity --* Route
    Map --* Obstacle
    MapPrinter --o Map

    Activity --o Bicycle
```