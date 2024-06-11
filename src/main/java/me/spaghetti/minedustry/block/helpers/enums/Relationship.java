package me.spaghetti.minedustry.block.helpers.enums;

import net.minecraft.util.StringIdentifiable;

public enum Relationship implements StringIdentifiable {
    COMMAND("command"),
    CHILD("child");

    private final String name;

    Relationship(String name) {
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
