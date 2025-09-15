# Narek Veranyan's Exercise Tracker Project

# Vision Statement 
> Build an exercise tracker that allows the cyclist to track activities
> over a map, record gear, and measure performance changes over time.

# Resources
* found information about bike types: https://www.edinburghbicycle.com/info/blog/types-of-bikes-buying-guide

## Class Diagram

```mermaid
classDiagram
    class Cyclist {
        -List~Activity~ activities
        -Collection~Gear~ gears

%%        +addActivity() void
%%        +addGear(text) void
    }
    
    class Map {
        -PositiveNumber width
        -PositiveNumber length
        -Set~Obstacle~ obstacles
        
%%        +addObstacle(WholeNumber, WholeNumber, WholeNumber, WholeNumber) void
    }
    
    class Obstacle {
        -PositveNumber width
        -PositiveNumber length
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

    Activity --o Bicycle
```