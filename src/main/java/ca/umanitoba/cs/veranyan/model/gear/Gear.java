package ca.umanitoba.cs.veranyan.model.gear;


// initial implementation draft
public record Gear(GearType type, String name, int avgSpeed){
    public Gear{
        // validate parameters
        if(avgSpeed <=0)
            throw new IllegalArgumentException("Cannot accept non-positive wheel size");
        if(name == null || name.isEmpty())
            throw new IllegalArgumentException("Cannot accept empty name.");
        if(type == null)
            throw new IllegalArgumentException("Cannot accept null type.");
    }
}
