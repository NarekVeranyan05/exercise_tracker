package ca.umanitoba.cs.veranyan.logic;

import ca.umanitoba.cs.veranyan.model.Profile;
import ca.umanitoba.cs.veranyan.model.gear.Gear;
import com.google.common.base.Preconditions;

import java.util.*;

public class ProfileRegistry {
    private final SortedSet<Profile> profiles;
    private Profile currentProfile;

    public ProfileRegistry(){
        profiles = new TreeSet<>(Comparator.comparing(Profile::getName, String::compareToIgnoreCase));
    }

    public SortedSet<Profile> getProfiles() {
        return profiles;
    }

    public Profile getCurrentProfile() {
        // when requested to get the current profile, the profile has to be loaded
        Preconditions.checkNotNull(currentProfile, "currentProfile cannot be null");

        return currentProfile;
    }

    public boolean addProfile(Profile profile){
        return profiles.add(profile);
    }

    public boolean replaceCurrentProfile(Profile replacement){
        Preconditions.checkNotNull(currentProfile, "cannot replace a null currentProfile");
        Preconditions.checkNotNull(replacement, "replacement cannot be null");

        String currName = currentProfile.getName();
        boolean isDuplicate = false;

        Iterator<Profile> iterator = profiles.iterator();
        while(iterator.hasNext() && !isDuplicate) {
            var next = iterator.next();
            isDuplicate = (next != currentProfile) && (next.getName().equalsIgnoreCase(replacement.getName()));
        }

        if(!isDuplicate)
            currentProfile = replacement;

        return isDuplicate;
    }

    public void loadProfile(Profile profile){
        Preconditions.checkNotNull(profile, "profile cannot be null");

        boolean isAdded = false;
        Iterator<Profile> iterator = profiles.iterator();
        while(iterator.hasNext() && !isAdded)
            isAdded = (iterator.next() == profile);

        Preconditions.checkState(isAdded, "Profile has to be added to be loaded.");

        currentProfile = profile;

        // FIXME is it reasonable to check this
        Preconditions.checkNotNull(currentProfile, "currentProfile cannot be null after loading");
    }

    public boolean isEmpty(){
        return profiles.isEmpty();
    }
}
