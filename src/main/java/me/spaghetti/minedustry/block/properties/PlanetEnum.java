package me.spaghetti.minedustry.block.properties;

import net.minecraft.util.StringIdentifiable;

public enum PlanetEnum implements StringIdentifiable
{
    EREKIR("erekir"),
    SERPULO("serpulo"),
    ;

    private final String name;

    PlanetEnum(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
