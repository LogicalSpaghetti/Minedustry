package me.spaghetti.minedustry;

import me.spaghetti.minedustry.block.ModBlockEntities;
import me.spaghetti.minedustry.block.ModBlocks;
import me.spaghetti.minedustry.block.blocks.conveyor.conveyor.ConveyorBlockEntityRenderer;
import me.spaghetti.minedustry.client.ClientPlayConnectionJoin;
import me.spaghetti.minedustry.client.CopperHudOverlay;
import me.spaghetti.minedustry.event.KeyInputHandler;
import me.spaghetti.minedustry.fluid.ModFluids;
import me.spaghetti.minedustry.networking.ModPackets;
import me.spaghetti.minedustry.screen.arc_furnace.SiliconArcFurnaceScreen;
import me.spaghetti.minedustry.screen.graphite_press.GraphitePressScreen;
import me.spaghetti.minedustry.screen.ModScreenHandlers;
import me.spaghetti.minedustry.screen.silicon_smelter.SiliconSmelterScreen;
import me.spaghetti.minedustry.screen.steam_generator.SteamGeneratorScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;

import java.lang.constant.ConstantDesc;

/**
 * The {@code MinedustryClient} class handles the global initialization of the mod.
 * <p>
 * Only client-side exclusive features should be initialized here, everything else should be initialized in {@linkplain Minedustry}.
 * @see     me.spaghetti.minedustry.Minedustry
 * @see     me.spaghetti.minedustry.MinedustryDataGenerator
 */

public class MinedustryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        registerClientScreenHandlers();

        BlockEntityRendererFactories.register(ModBlockEntities.CONVEYOR_BLOCK_ENTITY, ConveyorBlockEntityRenderer::new);

        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.CRYOFLUID, ModFluids.FLOWING_CRYOFLUID,
                new SimpleFluidRenderHandler(
                        new Identifier("minecraft:block/water_still"),
                        new Identifier("minecraft:block/water_flow"),
                        0x006ECDEC // ARGB, Alpha doesn't work?
                ));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                ModFluids.CRYOFLUID, ModFluids.FLOWING_CRYOFLUID);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SILICON_ARC_FURNACE, RenderLayer.getTranslucent());

        KeyInputHandler.register();
        ModPackets.registerS2CPackets();
        HudRenderCallback.EVENT.register(new CopperHudOverlay());
        ClientPlayConnectionEvents.JOIN.register(new ClientPlayConnectionJoin());
    }

    private static void registerClientScreenHandlers() {
        HandledScreens.register(ModScreenHandlers.GRAPHITE_PRESS_SCREEN_HANDLER, GraphitePressScreen::new);
        HandledScreens.register(ModScreenHandlers.SILICON_SMELTER_SCREEN_HANDLER, SiliconSmelterScreen::new);
        HandledScreens.register(ModScreenHandlers.SILICON_ARC_FURNACE_SCREEN_HANDLER, SiliconArcFurnaceScreen::new);
        HandledScreens.register(ModScreenHandlers.STEAM_GENERATOR_SCREEN_HANDLER, SteamGeneratorScreen::new);
    }
}
