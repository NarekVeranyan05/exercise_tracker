package ca.umanitoba.cs.veranyan.model;

import ca.umanitoba.cs.veranyan.model.collections.ArraySet;
import ca.umanitoba.cs.veranyan.model.gear.Gear;
import ca.umanitoba.cs.veranyan.model.gear.GearType;
import ca.umanitoba.cs.veranyan.model.map.Map;

public class Exerciser {
    private ArraySet<Gear> gears;
    private ArraySet<Activity> activities;

    public Exerciser(){
        this.activities = new ArraySet<>();
        this.gears = new ArraySet<>();
    }

    public void addGear(GearType type, String name, int wheelSize){
        this.gears.add(new Gear(type, name, wheelSize));
    }

    public void removeGear(int i){
        this.gears.removeByIndex(i);
    }

    public void addActivity(Map map, int i, int x, int y){
        this.activities.add(new Activity(map, this.gears.get(i), x, y));
    }

    public void removeActivity(int i){
        this.activities.removeByIndex(i);
    }

}
