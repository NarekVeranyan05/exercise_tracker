package ca.umanitoba.cs.veranyan.model;

import ca.umanitoba.cs.veranyan.model.gear.Gear;
import ca.umanitoba.cs.veranyan.model.gear.GearType;
import ca.umanitoba.cs.veranyan.model.map.Map;
import com.google.common.base.Preconditions;

import java.util.*;

/**
 * The Profile is the class representing the user of this
 * application. It contains the {@link Map}, all the {@link Gear}
 * instances that the user has purchased to later choose from and to
 * a particular Activity, and the {@link Activity} instances.
 */
public class Profile {
    private String name;
    private final SortedSet<Gear> gears;
    private final SortedSet<Activity> activities;
    private final Set<Profile> friends;

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

        // activities should not have duplicates.
        // activities are put in ascending order in the Set (ordered by start time).
        this.activities = new TreeSet<>(new Comparator<Activity>() {
            @Override
            public int compare(Activity o1, Activity o2) {
                return o1.getStart().compareTo(o2.getStart());
            }
        });

        this.friends = new TreeSet<>();

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
     * @return the unmodifiable list of activities on the Map. Must not be {@code null}.
     */
    public SortedSet<Activity> getActivities(int amount) {
        checkProfile();;

        return Collections.unmodifiableSortedSet(activities);
    }

    /**
     * Adds an activity to the Map instance.
     * @param activity the activity instance to add to Map. Must not be {@code null}.
     */
    public void addActivity(Activity activity){
        checkProfile();;

        activities.add(activity);

        checkProfile();;
    }

    /**
     * Removes an activity from the Map by index.
     * @param index the index of the activity to remove.
     */
    public void removeActivity(int index){
        checkProfile();

        Iterator<Activity> iterator = activities.iterator();

        // omitting previous elements to reach element at appropriate index;
        for(int j = 0; j < index; j++)
            iterator.next();

        activities.remove(iterator.next());

        checkProfile();;
    }

    /**
     * Ensures Profile invariants are not violated.
     */
    private void checkProfile(){
        /*
            private String name;
    private final SortedSet<Gear> gears;
    private final SortedSet<Activity> activities;
    private final Set<Profile> friends;
         */

        Preconditions.checkNotNull(name, "name cannot be null.");
        Preconditions.checkState(!name.isBlank(), "name cannot be blank.");
        Preconditions.checkNotNull(gears, "gears cannot be null.");
        Preconditions.checkState(!gears.isEmpty(), "gears should have at least one entry.");
        Preconditions.checkNotNull(activities, "activities cannot be null.");
        Preconditions.checkNotNull(friends, "friends cannot be null.");

        // Gear cannot be null
        for (var gear : gears)
            Preconditions.checkNotNull(gear, "gears entry cannot be null.");

        // Friend cannot be null, Profile cannot follow themselves
        for (var friend : friends){
            Preconditions.checkNotNull(friend, "friend profile cannot be null");
            Preconditions.checkState(this != friend, "profile cannot follow themselves");
        }

        // Activity cannot be null
        for (var activity : activities)
            Preconditions.checkNotNull(activity, "activities entries cannot be null.");
    }

}
