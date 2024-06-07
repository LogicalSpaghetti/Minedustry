package me.spaghetti.minedustry.screen;

import me.spaghetti.minedustry.Minedustry;
import me.spaghetti.minedustry.screen.graphite_press.GraphitePressScreenHandler;
import me.spaghetti.minedustry.screen.silicon_smelter.SiliconSmelterScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<GraphitePressScreenHandler> GRAPHITE_PRESS_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Minedustry.MOD_ID, "graphite_press"),
                    new ExtendedScreenHandlerType<>(GraphitePressScreenHandler::new));
    public static final ScreenHandlerType<SiliconSmelterScreenHandler> SILICON_SMELTER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Minedustry.MOD_ID, "silicon_smelter"),
                    new ExtendedScreenHandlerType<>(SiliconSmelterScreenHandler::new));

    public static void registerScreenHandlers() {
        Minedustry.LOGGER.info("Registering Minedustry Screen Handlers for " + Minedustry.MOD_ID);
    }
}
