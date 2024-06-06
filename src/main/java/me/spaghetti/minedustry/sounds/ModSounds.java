package me.spaghetti.minedustry.sounds;

import me.spaghetti.minedustry.Minedustry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent CRYO_AMBIENT = register("cryo_ambient");

    private static SoundEvent register(String path) {
        Identifier id = new Identifier(Minedustry.MOD_ID, path);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        Minedustry.LOGGER.info("Registering Sounds for " + Minedustry.MOD_ID);
    }
}
