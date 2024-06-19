package me.spaghetti.minedustry.util;

import net.minecraft.nbt.NbtCompound;

public class CopperData {
    public static int addCopper(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int copper = nbt.getInt("copper");

        if (copper + amount >= 100) {
            copper = 100;
        } else {
            copper += amount;
        }

        nbt.putInt("copper", copper);
        // sync the data
        return copper;
    }

    public static int removeCopper(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int copper = nbt.getInt("copper");

        if (copper - amount < 0) {
            copper = 0;
        } else {
            copper -= amount;
        }

        nbt.putInt("copper", copper);
        // syncCopper(copper, (ServerPlayerEntity) player);
        return copper;
    }
}
