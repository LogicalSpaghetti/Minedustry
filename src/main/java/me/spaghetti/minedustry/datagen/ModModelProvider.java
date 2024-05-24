package me.spaghetti.minedustry.datagen;

import me.spaghetti.minedustry.Minedustry;
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
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.BERYLLIUM, Models.GENERATED);
        itemModelGenerator.register(ModItems.LEAD, Models.GENERATED);
        Minedustry.LOGGER.info("Spaghetti spaghetti");
    }
}

