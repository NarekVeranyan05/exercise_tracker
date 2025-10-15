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
 * The Profile is the class representing the user of this
 * application. It contains the {@link Map}, all the {@link Gear}
 * instances that the user has purchased to later choose from and to
 * a particular Activity, and the {@link Activity} instances.
 */
public class Profile {
    private Map map; // map singleton
    private String name;
    private final SortedSet<Gear> gears;

    /**
     * Constructor for Profile.
     * Requires to add a Gear to a Profile.
     * @param profileName the name of the Profile.
     * @param gear the initial gear to add
     */
    public Profile(String profileName, Gear gear){
        name = profileName;

        // Gears are ordered by name. Duplicates not allowed
        this.gears = new TreeSet<>(new Comparator<Gear>() {
            @Override
            public int compare(Gear o1, Gear o2) {
                return o1.name().compareToIgnoreCase(o2.name());
            }
        });

        // adding initial gear
        this.gears.add(gear);

        checkProfile();
    }

    /**
     * @return the Profile name
     */
    public String getName() {
        checkProfile();

        return name;
    }

    /**
     * Changes the name of the Profile
     * @param name the new name of the Profile
     */
    public void setName(String name) {
        checkProfile();

        this.name = name;

        checkProfile();
    }

    /**
     * @return the Map singleton added to the system. Or null if no map added. May be {@code null}.
     */
    public Map getMap() {
        checkProfile();

        return map;
    }

    /**
     * Adds a new Map to the system.
     * @param map the Map singleton to add (must not be {@code null})
     */
    public void addMap(Map map){
        checkProfile();

        Preconditions.checkNotNull(map, "map cannot be null.");
        Preconditions.checkState(this.map == null, "previous map was not removed.");
        checkProfile();

        this.map = map;

        checkProfile();
    }

    /**
     * Removes the Map from the system.
     */
    public void removeMap(){
        checkProfile();

        map = null;
    }

    /**
     * @return the unmodifiable list of Gears. Must not be {@code null}
     */
    public SortedSet<Gear> getGears(){
        checkProfile();

        return Collections.unmodifiableSortedSet(gears);
    }

    /**
     * @param index the index of the Gear.
     * @return the Gear at the given index. Must not be {@code null}.
     */
    public Gear getGear(int index){
        checkProfile();

        Iterator<Gear> gearIterator = gears.iterator();

        // omitting all elements until reaching the element at index
        for(int i = 0; i < index; i++)
            gearIterator.next();

        checkProfile();

        return gearIterator.next();
    }

    /**
     * Adds new Gear to the Profile.
     * @param gear the Gear to be added
     */
    public boolean addGear(Gear gear){
        checkProfile();

        boolean isAdded = gears.add(gear);

        checkProfile();
        return isAdded;
    }

    /**
     * Removes a Gear whose index matches from the Profile.
     * Profile must have at least one Gear.
     * @param index the index of the Gear object to remove.
     */
    public void removeGear(int index){
        checkProfile();

        Iterator<Gear> iterator = gears.iterator();

        // omitting all elements until reaching the element at index
        for(int j = 0; j < index; j++)
            iterator.next();

        gears.remove(iterator.next());

        checkProfile();
    }

    /**
     * Ensures Profile invariants are not violated.
     */
    private void checkProfile(){
        Preconditions.checkNotNull(name, "name cannot be null.");
        Preconditions.checkState(!name.isBlank(), "name cannot be blank.");
        Preconditions.checkNotNull(gears, "gears cannot be null.");
        Preconditions.checkState(!gears.isEmpty(), "gears should have at least one entry.");

        // Gear cannot be null
        for (var gear : gears)
            Preconditions.checkNotNull(gear, "gears entry cannot be null.");
    }

}
