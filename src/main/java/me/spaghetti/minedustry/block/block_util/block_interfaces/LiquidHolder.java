package me.spaghetti.minedustry.block.block_util.block_interfaces;

import net.minecraft.util.Clearable;

public interface LiquidHolder extends Clearable {



    public enum acceptanceType {
        any(),
        coolant(),
        specific()
    }
}
