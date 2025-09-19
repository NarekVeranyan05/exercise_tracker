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
        %% order by date        
        -List~Activity~ activities 
        -Collection~Gear~ gears
        
        +addActivity() void
        +removeActivity(PositiveNumber) TrueFalse
        +addGear() void
        +removeGear(PositiveNumber) TrueFalse
    }
    
    class Map {
        %% should be > 0
        -int width
        %% should be > 0
        -int length
        -Set~Obstacle~ obstacles
        -Route route
        
        %% should return > 0
        +getWidth() int
        %% should return > 0
        +getLength() int
        +getObstacles() Set~Obstacle~
        %% params should be > 0
        +addObstacle(int, int, int, int) boolean
    }
    
    class Obstacle {
        %% should be > 0
        -int upperLeftX
        %% should be > 0
        -int upperLeftY
        %% should be > 0
        -int lowerRightX
        %% should be > 0
        -int lowerRightY
        
        %% should return > 0
        upperLeftX() int
        upperLeftY() int
        lowerRigthX() int
        lowerRigthY() int
    }
    
    class Route {
        List~NaturalNumber~ coordinatesX
        List~NaturalNumber~ coordinatesY
    }
    
    class Activity {
        -Gear gear
        -Date start
        -Date end
        -Route route
        
        +setGear(Gear) void
    }
    
    class Gear {
        -GearType type
        -text name
    }
    
    class MapPrinter {
        -Map map
        
        +print() void
    }
    
    
    
    
    %% class relationships
    Exerciser --* Activity
    Exerciser --* Gear
    Activity --* Route
    Map --* Obstacle
    
    Activity --o Gear
    Map --o Route
    MapPrinter --o Map
```