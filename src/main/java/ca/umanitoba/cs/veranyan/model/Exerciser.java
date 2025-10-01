package ca.umanitoba.cs.veranyan.model;

import ca.umanitoba.cs.veranyan.model.gear.Gear;
import ca.umanitoba.cs.veranyan.model.gear.GearType;
import ca.umanitoba.cs.veranyan.model.map.Map;
import com.google.common.base.Preconditions;

import java.util.*;

/**
 * The Exerciser is the class representing the user of this
 * application. It contains the {@link Map} and all the {@link Gear}
 * instances that the user has purchased to later choose from and to
 * a particular activity.
 */
public class Exerciser {
    private Map map; // map singleton
    private final SortedSet<Gear> gears;

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

        // adding new Gear instance
        this.gears.add(new Gear(type, name, avgSpeed));

        checkExerciser();
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
        this.gears.add(new Gear(type, name, avgSpeed));

        checkExerciser();
    }

    /**
     * Removes a Gear whose index matches from the Exerciser.
     * Exerciser must have at least one Gear.
     * @param index the index of the Gear object to remove.
     */
    public void removeGear(int index){
        checkExerciser();

        Iterator<Gear> iterator = this.gears.iterator();
        for(int j = 0; j < index; j++)
            iterator.next();

        this.gears.remove(iterator.next());

        checkExerciser();
    }

    public Map getMap() {
        return map;
    }

    /**
     * Adds a new Map to the system.
     * @param map the Map singleton to add (must not be {@code null})
     */
    public void addMap(Map map){
        Preconditions.checkNotNull(map, "map cannot be null.");
        this.map = map;
    }

    /**
     * Removes the Map from the system.
     */
    public void removeMap(){
        this.map = null;
    }

    /**
     * Ensures Exerciser invariants are not violated.
     */
    private void checkExerciser(){
        Preconditions.checkNotNull(this.gears, "gears cannot be null");
        Preconditions.checkState(!this.gears.isEmpty(), "gears should have at least one entry");

        // Gear cannot be null
        for (Gear gear : this.gears)
            Preconditions.checkNotNull(gear, "gears entry cannot be null");
    }

}
