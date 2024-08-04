package me.spaghetti.minedustry.block.properties;

import net.minecraft.util.StringIdentifiable;

public enum EPlanet implements StringIdentifiable
{
    EREKIR("erekir"),
    SERPULO("serpulo"),
    ;

    private final String name;

    EPlanet(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
