package me.spaghetti.minedustry.datagen;

import me.spaghetti.minedustry.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.CONVEYOR);

        for (int i = 0; i < ModBlocks.ENVIRONMENT_BLOCKS.length; i++) {
            addDrop(ModBlocks.ENVIRONMENT_BLOCKS[i]);
        }
        for (int i = 0; i < ModBlocks.BASIC_BLOCKS.length; i++) {
            addDrop(ModBlocks.BASIC_BLOCKS[i]);
        }
    }

}
