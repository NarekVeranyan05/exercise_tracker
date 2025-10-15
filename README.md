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
# User Flow Diagram

### log-in and sign-up

```mermaid
flowchart
    subgraph log-in and sign-up 
        login[[log-in screen]]
        login == sign up ==> profileInsertion
        login == log in ==> checkExistence
        
        %% signup        
        profileInsertion[profile insertion]
        
        profileInsertion == candidate profile ==> createProfile
        
        createProfile{create profile}
        
        createProfile -. profile already exists .-> profileInsertion
        
        createProfile -. profile created .-> mainMenu
        
        %% login
        checkExistence{check profiles exist}
        
        checkExistence -. no profile in system .-> login
        
        checkExistence -. profiles exist .-> profileSelection
        
        profileSelection[profile selection]
        
        profileSelection == selected profile ==> loadProfile

        loadProfile{load profile}
        
        loadProfile -. profile selected .-> mainMenu
       
        mainMenu[[main menu]]
    end
```

### update profile

```mermaid
flowchart
    subgraph update profile
        updateScreen[[update screen]]
        
        updateScreen == name ==> nameInsertion
        
        updateScreen == add gear ==> gearInsertion
        
        updateScreen == remove gear ==> gearSelection
        
        %% change name
        nameInsertion[name insertion]
        
        nameInsertion == name ==> changeName
        
        changeName{change name}
        
        changeName -. name is set for another profile .-> nameInsertion
        
        changeName -. name changed .-> mainMenu
        
        %% add gear
        
        gearInsertion[gear insertion]
        
        gearInsertion == gear ==> addGear

        addGear{add gear}
        
        addGear -. gear already exists .-> gearInsertion
        
        addGear -. gear added .-> mainMenu
        
        %% remove gear
        
        gearSelection[gear selection]
        
        gearSelection == selected gear ==> removeGear
        
        removeGear{remove gear}
        
        removeGear -. must have 1 gear left .-> updateScreen
        
        removeGear -. gear removed .-> mainMenu
        
        mainMenu[[main menu]]
    end
```

# Domain Model Diagram

Here's my domain model:

> changes:
> 1. Exerciser class renamed Profile
> 2. Added name attribute to Profile, changed invariant accordingly
> 3. changed method to Profile::addGear(Gear) boolean

```mermaid 
classDiagram
    class Profile {
        -Map map
        -String name
        -SortedSet~Gear~ gears
        
        +getName() String
        +setName() void
        +getMap() Map
        +addMap(Map) void
        +removeMap() void
        +getGears() SortedSet~Gear~
        +getGear(int) Gear
        +addGear(Gear) boolean
        +removeGear(int) void
    }
    
    note for Profile"invariants:
        * name != null
        * name.length >= 1
        * gears != null
        * gears.length >= 1
        * loop: no entry is null in gears"
    
    Profile --* Gear
    Profile --* Map
    
    class Gear {
        -GearType type
        -String name
        -double avgSpeed
    }
    
    note for Gear"invariants:
        * type != null
        * name != null
        * name.length() >= 1
        * avgSpeed > 0"
    
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
        +getTotalNumSteps(LocalDate, ChronoUnit) int
        +getObstacles() List~Obstacle~
        +addObstacle(int, int, int, int) void
        +removeObstacle(int) void
        +getActivities() SortedSet~Activity~
        +addActivivity(Activity) void
        +removeActivity(int) void
        +isInObstacle(int, int) boolean
        +isInRoute(int, int) boolean
        +isInRoute(int, int, int) boolean
    }
    
    note for Map"invariants:
        * width >= 1
        * length >= 1
        * obstacles != null
        * activities != null
        * loop: no entry is null in obstacles
        * loop: no obstacle is out of boundaries
        * loop: no entry is null in activities
        * loop: no activity route is out of boundaries
        * routes and obstacles don't overlap"
    
    Map --* Obstacle
    Map --* Activity
    
    class Activity{
        -Gear gear
        -LocalDateTime start
        -LocalDateTime end
        -Route route
        -double avgSpeed
        
        +getAvgSpeed() double
        +getStart() LocalDateTime
        +getEnd() LocalDateTime
        +getGear() Gear
        +getRoute() Route
        +endActivity() void
    }
    
    note for Activity"invariants:
        * gear != null
        * start != null
        * route != null
        * avgSpeed >= 0"
    
    Activity --o Gear
    Activity --* Route
    
    class Route {
        -List~Coordinate~ coordinates
        
        +getStepsAmount() int
        +getCoordinate(int) Coordinate
        +move(int, int) void
        +contains(int, int) boolean
    }
    
    note for Route"invariants:
        * coordinates != null
        * coordinates.size() >= 1"
    
    Route --* Coordinate
    
    class Obstacle {
        -Coordinate topLeftCoord
        -Coordinate bottomRightCoord
        
        +contains(int, int) boolean
    }
    
    note for Obstacle"invariants:
        * topLeftCoord != null
        * bottomRightCoord != null
        * bottomRightCoord is lower and to the right of topLeftCoord"
    
    Obstacle --* Coordinate
    
    class Coordinate {
        -int x
        -int y
    }
    
    note for Coordinate "invariants:
        * x >= 0
        * y >= 0"
```