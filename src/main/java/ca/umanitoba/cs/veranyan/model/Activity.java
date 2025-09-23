package ca.umanitoba.cs.veranyan.model;

import ca.umanitoba.cs.veranyan.model.map.Route;

import java.time.LocalDateTime;

public class Activity {
    private final Gear gear;
    private LocalDateTime start;
    private LocalDateTime end;
    private Route route;

    public Activity(Gear gear, Route route) {
        this.gear = gear;
        this.route = route;
    }

    public void startActivity(){
        this.start = LocalDateTime.now();
    }

    public void finishActivity(){
        this.end = LocalDateTime.now();
    }


}
