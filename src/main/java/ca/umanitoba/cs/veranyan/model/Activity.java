package ca.umanitoba.cs.veranyan.model;

import ca.umanitoba.cs.veranyan.model.gear.Gear;
import ca.umanitoba.cs.veranyan.model.map.Map;
import ca.umanitoba.cs.veranyan.model.map.Route;

import java.time.LocalDateTime;

public class Activity {
    private final Map map;
    private final Gear gear;
    private LocalDateTime start;
    private LocalDateTime end;
    private Route route;

    public Activity(Map map, Gear gear, int x, int y) {
        this.map = map;
        this.gear = gear;
        this.route = new Route(x, y);
    }

    public Route getRoute() {
        return route;
    }

    public void startActivity(){
        this.start = LocalDateTime.now();
    }

    public void endActivity(){
        // FIXME cannot reassign end or assign a value when activity hasn't started
        this.end = LocalDateTime.now();
    }

    /**
     * @param direction the Direction to move
     * @param steps the number of steps of the move
     */
    public void move(Direction direction, int steps){
        // FIXME validate map
        // FIXME implement
    }
}
