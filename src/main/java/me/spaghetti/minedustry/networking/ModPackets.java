package me.spaghetti.minedustry.networking;

import me.spaghetti.minedustry.Minedustry;
import me.spaghetti.minedustry.networking.packet.ConveyorSyncDataS2CPacket;
import me.spaghetti.minedustry.networking.packet.CopperC2SPacket;
import me.spaghetti.minedustry.networking.packet.CopperSyncDataS2CPacket;
import me.spaghetti.minedustry.networking.packet.ExampleC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

// communicates between the client and server
public class ModPackets {
    public static final Identifier increaseCopperId = new Identifier(Minedustry.MOD_ID, "increasing_copper");
    public static final Identifier copperSyncId = new Identifier(Minedustry.MOD_ID, "copper_sync");
    public static final Identifier exampleId = new Identifier(Minedustry.MOD_ID, "example");
    public static final Identifier conveyorSyncId = new Identifier(Minedustry.MOD_ID, "conveyor_sync");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(exampleId, ExampleC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(increaseCopperId, CopperC2SPacket::receive);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(copperSyncId, CopperSyncDataS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(conveyorSyncId, ConveyorSyncDataS2CPacket::receive);
    }


}
