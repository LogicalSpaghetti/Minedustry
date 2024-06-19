package me.spaghetti.minedustry.event;

import me.spaghetti.minedustry.util.CopperData;
import me.spaghetti.minedustry.util.IEntityDataSaver;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Random;

public class PlayerTickHandler implements ServerTickEvents.StartTick {
    @Override
    public void onStartTick(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if(new Random().nextFloat() <= 0.005f) {
                IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);
                CopperData.removeCopper(dataPlayer,1);
                player.sendMessage(Text.literal("Removed Copper"));
                player.sendMessage(Text.literal("Removed Copper, new count: " + dataPlayer.getPersistentData().getInt("copper")));
            }
        }
    }
}
