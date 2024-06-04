package me.spaghetti.minedustry;

import me.spaghetti.minedustry.block.entity.ModBlockEntities;
import me.spaghetti.minedustry.block.entity.renderer.ConveyorBlockEntityRenderer;
import me.spaghetti.minedustry.screen.GraphitePressScreen;
import me.spaghetti.minedustry.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class MinedustryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        HandledScreens.register(ModScreenHandlers.GRAPHITE_PRESS_SCREEN_HANDLER, GraphitePressScreen::new);

        BlockEntityRendererFactories.register(ModBlockEntities.CONVEYOR_BLOCK_ENTITY, ConveyorBlockEntityRenderer::new);
    }
}
