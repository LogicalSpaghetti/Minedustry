package me.spaghetti.minedustry;

import me.spaghetti.minedustry.block.entity.ModBlockEntities;
import me.spaghetti.minedustry.block.entity.conveyor.ConveyorBlockEntityRenderer;
import me.spaghetti.minedustry.fluid.ModFluids;
import me.spaghetti.minedustry.screen.graphite_press.GraphitePressScreen;
import me.spaghetti.minedustry.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;

public class MinedustryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        HandledScreens.register(ModScreenHandlers.GRAPHITE_PRESS_SCREEN_HANDLER, GraphitePressScreen::new);

        BlockEntityRendererFactories.register(ModBlockEntities.CONVEYOR_BLOCK_ENTITY, ConveyorBlockEntityRenderer::new);

        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.CRYOFLUID, ModFluids.FLOWING_CRYOFLUID,
                new SimpleFluidRenderHandler(
                        new Identifier("minecraft:block/water_still"),
                        new Identifier("minecraft:block/water_flow"),
                        0x006ECDEC // ARGB, Alpha doesn't work
                ));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                ModFluids.CRYOFLUID, ModFluids.FLOWING_CRYOFLUID);
    }
}
