package me.spaghetti.minedustry.datagen;

import me.spaghetti.minedustry.block.ModBlocks;
import me.spaghetti.minedustry.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.GRAPHITE_PRESS)
                .add(ModBlocks.CONVEYOR);

        for (int i = 0; i < ModBlocks.ENVIRONMENT_BLOCKS.length; i++) {
            getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.ENVIRONMENT_BLOCKS[i]);
        }
    }
}

