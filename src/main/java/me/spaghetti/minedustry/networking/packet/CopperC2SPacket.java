package me.spaghetti.minedustry.networking.packet;

import me.spaghetti.minedustry.util.CopperData;
import me.spaghetti.minedustry.util.IEntityDataSaver;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CopperC2SPacket {
    public static final String copperIncreasingMessage = "message.minedustry.increasedCopper";

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // Everything here happens ONLY on the server!
        ServerWorld world = player.getServerWorld();

        // notify the player
        player.sendMessage(Text.translatable(copperIncreasingMessage)
                .fillStyle(Style.EMPTY.withColor(Formatting.GOLD)), true);
        // play a sound
        world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_COPPER_BREAK, SoundCategory.PLAYERS,
                0.5f, world.random.nextFloat() * 0.1f + 0.9f);
        // add copper to player.
        CopperData.addCopper(((IEntityDataSaver) player), 1);
        // output how much copper the player has.
        player.sendMessage(Text.literal("Copper: " + ((IEntityDataSaver) player).getPersistentData().getInt("copper"))
                .fillStyle(Style.EMPTY.withColor(Formatting.RED)), true);
    }
}
