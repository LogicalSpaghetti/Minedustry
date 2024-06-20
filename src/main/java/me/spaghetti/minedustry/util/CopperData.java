package me.spaghetti.minedustry.util;

import me.spaghetti.minedustry.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

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
        syncCopper(copper, (ServerPlayerEntity) player);
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
        syncCopper(copper, (ServerPlayerEntity) player);
        return copper;
    }

    public static void syncCopper(int thirst, ServerPlayerEntity player) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(thirst);
        ServerPlayNetworking.send(player, ModPackets.copperSyncId, buffer);
    }
}
