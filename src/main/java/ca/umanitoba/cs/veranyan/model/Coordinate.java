package ca.umanitoba.cs.veranyan.model;

import com.google.common.base.Preconditions;

public record Coordinate(int x, int y) {
    public Coordinate {
        Preconditions.checkState(x >= 0, "x-coordinate cannot be negative.");
        Preconditions.checkState(y >= 0, "y-coordinate cannot be negative.");
    }
}
