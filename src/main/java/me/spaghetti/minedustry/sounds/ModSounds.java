package me.spaghetti.minedustry.sounds;

import me.spaghetti.minedustry.Minedustry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent cryo_ambient = register("cryo_ambient");
    public static final SoundEvent place = register("place");
    public static final SoundEvent breaks = register("breaks");
    public static final SoundEvent boom = register("boom");
    public static final SoundEvent none = register("none");

    private static SoundEvent register(String path) {
        Identifier id = new Identifier(Minedustry.MOD_ID, path);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        Minedustry.LOGGER.info("Registering Sounds for " + Minedustry.MOD_ID);
    }
}
