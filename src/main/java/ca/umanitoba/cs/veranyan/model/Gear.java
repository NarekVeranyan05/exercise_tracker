package ca.umanitoba.cs.veranyan.model;


// initial implementation draft
public record Gear(String name, GearType type, int wheelSize){
    public Gear{
        // validate parameters
        if(wheelSize <=0)
            throw new IllegalArgumentException("Cannot accept non-positive wheel size");
        if(name == null || name.isEmpty())
            throw new IllegalArgumentException("Cannot accept empty name.");
        if(type == null)
            throw new IllegalArgumentException("Cannot accept null type.");
    }
}
