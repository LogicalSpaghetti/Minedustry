package me.spaghetti.minedustry.networking.sync;

import me.spaghetti.minedustry.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class ConveyorSync {
    public static void syncInventory(ServerWorld serverWorld, DefaultedList<ItemStack> inventory, BlockPos pos) {
/*        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(thirst);
        ServerPlayNetworking.send(player, ModPackets.copperSyncId, buffer);*/
        PacketByteBuf buf = PacketByteBufs.create();
        NbtCompound nbt = new NbtCompound();
        Inventories.writeNbt(nbt, inventory);

        buf.writeBlockPos(pos);
        buf.writeNbt(nbt);
        List<ServerPlayerEntity> players = serverWorld.getPlayers();
        // todo: make it only send to players who are close enough
        for (ServerPlayerEntity player : players) {
            ServerPlayNetworking.send(player, ModPackets.conveyorSyncId, buf);
        }
    }
}
