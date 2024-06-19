package me.spaghetti.minedustry.block.abstractions;

import net.minecraft.util.Clearable;

public interface LiquidHolder extends Clearable {



    public enum acceptanceType {
        any(),
        coolant(),
        specific();
    }
}
