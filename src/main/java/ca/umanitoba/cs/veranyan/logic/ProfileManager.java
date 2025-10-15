package ca.umanitoba.cs.veranyan.logic;

import ca.umanitoba.cs.veranyan.model.Activity;
import ca.umanitoba.cs.veranyan.model.Profile;
import ca.umanitoba.cs.veranyan.model.gear.Gear;
import com.google.common.base.Preconditions;

import java.util.SortedSet;

public class ProfileManager {
    private Profile profile;

    public ProfileManager(Profile profile){
        this.profile = profile;
    }

    public boolean addGear(Gear gear){
        Preconditions.checkNotNull(gear, "Gear cannot be null");

        return profile.addGear(gear);
    }

    public boolean removeGear(int index){
        int numGears = profile.getGears().size();

        Preconditions.checkState(index >= 0, "index cannot be negative");
        Preconditions.checkState(index < numGears, "index cannot be larger than " +
                "number of gears of profile.");

        boolean isLast = profile.getGears().size() == 1;

        if(!isLast){
            profile.removeGear(index);
            Preconditions.checkState(profile.getGears().size() == (numGears - 1), "gear was not removed");
        }

        return isLast;
    }
}
