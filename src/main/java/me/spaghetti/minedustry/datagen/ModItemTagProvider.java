package me.spaghetti.minedustry.datagen;


import me.spaghetti.minedustry.item.ModItems;
import me.spaghetti.minedustry.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ModTags.Items.STEAM_GENERATOR_FUEL)
                .add(ModItems.COAL)
                .add(ModItems.SPORE_POD)
                .add(ModItems.PYRATITE)
                .add(ModItems.BLAST_COMPOUND);
    }
}
