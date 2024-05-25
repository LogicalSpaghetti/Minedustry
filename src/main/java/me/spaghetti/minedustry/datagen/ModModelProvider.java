package me.spaghetti.minedustry.datagen;

import me.spaghetti.minedustry.block.custom.ModBlocks;
import me.spaghetti.minedustry.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        //blockStateModelGenerator.registerSimpleState(ModBlocks.RAT_HAT_SEWING_TABLE);
        blockStateModelGenerator.registerSimpleState(ModBlocks.GRAPHITE_PRESS);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (int i = 0; i < ModItems.ITEMS.length; i++) {
            itemModelGenerator.register(ModItems.ITEMS[i], Models.GENERATED);
        }
    }
}

