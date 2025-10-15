package ca.umanitoba.cs.veranyan.ui;

import ca.umanitoba.cs.veranyan.model.gear.Gear;
import ca.umanitoba.cs.veranyan.model.gear.GearType;

import java.util.Scanner;

public class GearInsertionDisplay {
    Scanner keyboard = new Scanner(System.in);

    public Gear promptGearInsertion(){
        GearType type = promptGearType();

        System.out.println("Enter gear name: ");
        String name = keyboard.next();

        // FIXME happy path -- avgSpeed positive
        System.out.println("Enter gear average speed: ");
        int avgSpeed = keyboard.nextInt();

        return new Gear(type, name, avgSpeed);
    }

    private GearType promptGearType(){
        for(int i = 0; i < GearType.values().length; i++)
            System.out.println((i+1) + ". " + GearType.values()[i].toString());

        System.out.println("Select gear type by number: ");

        // FIXME happy path -- number match
        int choice = keyboard.nextInt();

        return GearType.values()[choice-1];
    }
}
