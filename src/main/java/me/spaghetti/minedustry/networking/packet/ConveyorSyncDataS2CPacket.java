package me.spaghetti.minedustry.networking.packet;

import me.spaghetti.minedustry.block.blocks.conveyor.conveyor.ConveyorBlockEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class ConveyorSyncDataS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        ClientWorld world = client.world;
        BlockPos pos = buf.readBlockPos();
        NbtCompound nbt = buf.readNbt();
        assert world != null;
        ConveyorBlockEntity conveyor = (ConveyorBlockEntity) world.getBlockEntity(pos);
        if (conveyor == null) {
            return;
        }
        conveyor.setInventory(nbt);
    }
}
