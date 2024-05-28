package me.spaghetti.minedustry.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum GraphitePressCorner implements StringIdentifiable
{
    NORTH_EAST("north_east"),
    NORTH_WEST("north_west"),
    SOUTH_EAST("south_east"),
    SOUTH_WEST("south_west");

    private final String name;

    GraphitePressCorner(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
