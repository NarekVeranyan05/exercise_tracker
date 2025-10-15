package ca.umanitoba.cs.veranyan.ui;

import ca.umanitoba.cs.veranyan.logic.ProfileRegistry;
import ca.umanitoba.cs.veranyan.model.Profile;
import ca.umanitoba.cs.veranyan.model.gear.Gear;
import ca.umanitoba.cs.veranyan.output.ProfilePrinter;

import java.util.Iterator;
import java.util.Scanner;

public class LoginDisplay {
    private final ProfileRegistry profileRegistry;
    private final Scanner keyboard;

    public LoginDisplay(ProfileRegistry profileRegistry) {
        this.profileRegistry = profileRegistry;
        this.keyboard = new Scanner(System.in);
    }

    public void startLogin() {
        System.out.println("Welcome to Track-Me-Riding");
        int choice = promptChoice();

        // FIXME happy path
        if(choice == 1)
            login();
        else if (choice == 2)
            signup();
    }

    public void login() {
        if(profileRegistry.isEmpty()){
            System.out.println("You have to create a profile to log in");
        }
        else{
            for(var profile : profileRegistry.getProfiles()){
                new ProfilePrinter(profile).print();
                System.out.println();
            }

            Profile selectedProfile = promptProfileSelection();
            profileRegistry.loadProfile(selectedProfile);
        }
    }

    public void signup() {
        Profile candidate = null;

        do {
            if(candidate != null)
                System.out.println("Profile with matching name already exists. Try again.");

            candidate = promptProfileInsertion();
        } while(!profileRegistry.addProfile(candidate));
    }

    private int promptChoice() {
        System.out.println("Select one of the following options:");
        System.out.println("1. Log in");
        System.out.println("2. Sign up");

        System.out.println("Enter a corresponding number: ");

        // FIXME happy path
        return keyboard.nextInt();
    }

    private Profile promptProfileSelection(){
        System.out.println("Enter profile name: ");

        // FIXME happy path (name matches)
        String name = keyboard.next();

        Profile selectedProfile = null;

        Iterator<Profile> iterator = profileRegistry.getProfiles().iterator();
        while(iterator.hasNext() && selectedProfile == null){
            Profile next = iterator.next();
            if(next.getName().equals(name))
                selectedProfile = next;
        }

        return selectedProfile;
    }

    private Profile promptProfileInsertion() {
        System.out.println("Enter profile name: ");
        String name = keyboard.next();

        Gear initGear = new GearInsertionDisplay().promptGearInsertion();

        return new Profile(name, initGear);
    }
}
