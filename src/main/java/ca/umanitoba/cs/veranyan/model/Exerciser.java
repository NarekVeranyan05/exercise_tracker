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
 * a particular Activity, and the {@link Activity} instances.
 */
public class Exerciser {
    private Map map; // map singleton
    private final SortedSet<Gear> gears;

    /**
     * Constructor for Exerciser.
     * Requires to add a Gear to an Exerciser.
     * @param type the GearType of initial Gear to be added to Exerciser. Cannot be {@code null}.
     * @param name the name of initial Gear to be added to Exerciser. Cannot be {@code null} or blank.
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

        // adding initial gear
        this.gears.add(new Gear(type, name, avgSpeed));

        checkExerciser();
    }

    /**
     * @return the Map singleton added to the system. Or null if no map added. May be {@code null}.
     */
    public Map getMap() {
        checkExerciser();

        return map;
    }

    /**
     * Adds a new Map to the system.
     * @param map the Map singleton to add (must not be {@code null})
     */
    public void addMap(Map map){
        checkExerciser();

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
        checkExerciser();

        map = null;
    }

    /**
     * @return the unmodifiable list of Gears. Must not be {@code null}
     */
    public SortedSet<Gear> getGears(){
        checkExerciser();

        return Collections.unmodifiableSortedSet(gears);
    }

    /**
     * @param index the index of the Gear.
     * @return the Gear at the given index. Must not be {@code null}.
     */
    public Gear getGear(int index){
        checkExerciser();

        Iterator<Gear> gearIterator = gears.iterator();

        // omitting all elements until reaching the element at index
        for(int i = 0; i < index; i++)
            gearIterator.next();

        checkExerciser();

        return gearIterator.next();
    }

    /**
     * Adds new Gear to the Exerciser.
     * @param type the GearType of initial Gear to be added to Exerciser. Cannot be {@code null}.
     * @param name the name of initial Gear to be added to Exerciser. Cannot be {@code null} or blank.
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

        // omitting all elements until reaching the element at index
        for(int j = 0; j < index; j++)
            iterator.next();

        gears.remove(iterator.next());

        checkExerciser();
    }

    /**
     * Ensures Exerciser invariants are not violated.
     */
    private void checkExerciser(){
        Preconditions.checkNotNull(gears, "gears cannot be null.");
        Preconditions.checkState(!gears.isEmpty(), "gears should have at least one entry.");

        // Gear cannot be null
        for (var gear : gears)
            Preconditions.checkNotNull(gear, "gears entry cannot be null.");
    }

}
