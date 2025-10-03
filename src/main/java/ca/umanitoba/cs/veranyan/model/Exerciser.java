package ca.umanitoba.cs.veranyan.model;

import ca.umanitoba.cs.veranyan.model.gear.Gear;
import ca.umanitoba.cs.veranyan.model.gear.GearType;
import ca.umanitoba.cs.veranyan.model.map.Map;
import com.google.common.base.Preconditions;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;

/**
 * The Exerciser is the class representing the user of this
 * application. It contains the {@link Map}, all the {@link Gear}
 * instances that the user has purchased to later choose from and to
 * a particular activity, and the {@link Activity} instances.
 */
public class Exerciser {
    private Map map; // map singleton
    private final SortedSet<Gear> gears;
    private final SortedSet<Activity> activities;

    /**
     * Constructor for Exerciser.
     * Requires to add a Gear to an Exerciser.
     * @param type the GearType of initial Gear to be added to Exerciser. Cannot be null.
     * @param name the name of initial Gear to be added to Exerciser. Cannot be null or blank.
     * @param avgSpeed the average speed of the initial Gear to be added to Exerciser. Must be positive.
     */
    public Exerciser(GearType type, String name, double avgSpeed){
        // Gears are ordered by name. Duplicates not allowed
        this.gears = new TreeSet<>(new Comparator<Gear>() {
            @Override
            public int compare(Gear o1, Gear o2) {
                return o1.name().compareToIgnoreCase(o2.name());
            }
        });

        // activities should not have duplicates.
        // activities are put in ascending order in the Set.
        this.activities = new TreeSet<>(new Comparator<Activity>() {
            @Override
            public int compare(Activity o1, Activity o2) {
                return o1.getStart().compareTo(o2.getStart());
            }
        });

        // adding new Gear instance
        this.gears.add(new Gear(type, name, avgSpeed));

        checkExerciser();
    }

    /**
     * @return the Map singleton added to the system. Or null if no map added.
     */
    public Map getMap() {
        return map;
    }

    /**
     * Adds a new Map to the system.
     * @param map the Map singleton to add (must not be {@code null})
     */
    public void addMap(Map map){
        Preconditions.checkNotNull(map, "map cannot be null.");
        Preconditions.checkState(this.map == null, "previous map was not removed.");
        checkExerciser();

        this.map = map;

        checkExerciser();
    }

    /**
     * Removes the Map from the system.
     */
    public void removeMap(){
        map = null;
    }

    /**
     * @return the unmodifiable list of Gears.
     */
    public SortedSet<Gear> getGears(){
        return Collections.unmodifiableSortedSet(gears);
    }

    /**
     * @param index the index of the Gear.
     * @return the Gear at the given index.
     */
    public Gear getGear(int index){
        Iterator<Gear> gearIterator = gears.iterator();

        for(int i = 0; i < index; i++)
            gearIterator.next();

        return gearIterator.next();
    }

    /**
     * Adds new Gear to the Exerciser.
     * @param type the GearType of initial Gear to be added to Exerciser. Cannot be null.
     * @param name the name of initial Gear to be added to Exerciser. Cannot be null or blank.
     * @param avgSpeed the average speed of the initial Gear to be added to Exerciser. Must be positive.
     */
    public void addGear(GearType type, String name, double avgSpeed){
        checkExerciser();

        gears.add(new Gear(type, name, avgSpeed));

        checkExerciser();
    }

    /**
     * Removes a Gear whose index matches from the Exerciser.
     * Exerciser must have at least one Gear.
     * @param index the index of the Gear object to remove.
     */
    public void removeGear(int index){
        checkExerciser();

        Iterator<Gear> iterator = gears.iterator();
        for(int j = 0; j < index; j++)
            iterator.next();

        gears.remove(iterator.next());

        checkExerciser();
    }

    /**
     * @return the unmodifiable list of activities on the Map
     */
    public SortedSet<Activity> getActivities() {
        return Collections.unmodifiableSortedSet(activities);
    }

    /**
     * @param index the index of the Activity.
     * @return the Activity at the given index.
     */
    public Activity getActivity(int index){
        Iterator<Activity> activityIterator = activities.iterator();

        for(int i = 0; i < index; i++)
            activityIterator.next();

        return activityIterator.next();
    }

    /**
     * Adds an activity to the Map instance.
     * @param activity the activity instance to add to Map.
     */
    public void addActivity(Activity activity){
        checkExerciser();

        activities.add(activity);

        checkExerciser();
    }

    /**
     * Removes an activity from the Map.
     * @param index the index of the activity to remove
     */
    public void removeActivity(int index){
        checkExerciser();

        Iterator<Activity> iterator = activities.iterator();
        for(int j = 0; j < index; j++)
            iterator.next();

        activities.remove(iterator.next());

        checkExerciser();
    }

    /**
     * Ensures Exerciser invariants are not violated.
     */
    private void checkExerciser(){
        Preconditions.checkNotNull(gears, "gears cannot be null.");
        Preconditions.checkState(!gears.isEmpty(), "gears should have at least one entry.");
        Preconditions.checkNotNull(activities, "activities cannot be null.");

        // Gear cannot be null
        for (var gear : gears)
            Preconditions.checkNotNull(gear, "gears entry cannot be null.");

        // Activity cannot be null
        for (var activity : activities)
            Preconditions.checkNotNull(activity, "activities entry cannot be null.");
    }

}
