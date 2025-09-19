package ca.umanitoba.cs.veranyan.model;

import ca.umanitoba.cs.veranyan.model.map.Route;

import java.time.LocalDateTime;

public class Activity {
    private final Gear gear;
    private final LocalDateTime start;
    private LocalDateTime end;
    private Route route;

    public Activity(Gear gear, int startX, int startY) {
        this.gear = gear;
        this.start = LocalDateTime.now();
    }

    public void move(Direction direction, int steps){

    }
}
