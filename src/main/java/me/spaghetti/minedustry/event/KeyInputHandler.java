package me.spaghetti.minedustry.event;

import me.spaghetti.minedustry.networking.ModPackets;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String keyCategoryMinedustry = "key.category.minedustry.minedustry";
    public static final String keyIncreaseCopper = "key.drink.minedustry.increase_copper";

    public static KeyBinding drinkingKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (drinkingKey.wasPressed()) {
                // reached when our custom key was pressed
                assert client.player != null; // you can't click if you don't exist!

                // summons a cow on the player
                // ClientPlayNetworking.send(ModPackets.exampleId, PacketByteBufs.create());

                ClientPlayNetworking.send(ModPackets.increaseCopperId, PacketByteBufs.create());
            }
        });
    }

    public static void register() {
        drinkingKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                keyIncreaseCopper, // String identifier
                InputUtil.Type.KEYSYM, // MOUSE if it's a mouse input by default
                GLFW.GLFW_KEY_O, // default character
                keyCategoryMinedustry // String category identifier
        ));

        registerKeyInputs();
    }
}
