package me.spaghetti.minedustry.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import me.spaghetti.minedustry.util.IEntityDataSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

// todo: doesn't always work
public class CopperSyncDataS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        if (client.player != null) {
            ((IEntityDataSaver) client.player).getPersistentData().putInt("copper", buf.readInt());
        }
    }
}
