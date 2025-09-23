package ca.umanitoba.cs.veranyan.model;

import ca.umanitoba.cs.veranyan.model.collections.ArraySet;

import java.util.ArrayList;

public class Exerciser {
    private ArrayList<Activity> activities;
    private ArraySet<Gear> gears;

    public Exerciser(){
        this.activities = new ArrayList<>();
        this.gears = new ArraySet<>();
    }

    public boolean addActivity(int i){
        if(this.gears.isEmpty()) // should have gear to add an activity
            return false;
        else {
            activities.add(new Activity(this.gears.get(i)));
            return true;
        }
    }

    public boolean removeActivity(int i){
        if(i < 0 || i >= this.activities.size())
            return false;
        else{
            this.activities.remove(i);
            return true;
        }
    }

    public void addGear(String name, GearType type, int wheelSize){
        this.gears.add(new Gear(name, type, wheelSize));
    }

    public boolean removeGear(int i){
        if(i < 0 || i >= this.gears.size())
            return false;
        else {
            this.gears.remove(i);
            return true;
        }
    }
}
