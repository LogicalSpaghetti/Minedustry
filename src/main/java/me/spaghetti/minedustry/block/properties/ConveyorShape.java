package me.spaghetti.minedustry.block.properties;

import net.minecraft.util.StringIdentifiable;

public enum ConveyorShape implements StringIdentifiable
{
    STRAIGHT("straight"),
    OMNI("omni"),

    ASCENDING("ascending"),
    DESCENDING("descending"),

    // left/right refers to the side of the conveyor when the primary direction is headed away from the perspective
    LEFT_CURVE("left"),
    RIGHT_CURVE("right"),
    LEFT_SIDE_STRAIGHT("left_straight"),
    RIGHT_SIDE_STRAIGHT("right_straight"),
    RIGHT_LEFT("right_left"),
    ;

    private final String name;

    ConveyorShape(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public boolean isAscending() {
        return this == ASCENDING;
    }

    public boolean isDescending() {
        return this == DESCENDING;
    }

    public boolean isSloped() {
        return isAscending() || isDescending();
    }
}
