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
        addDrop(ModBlocks.GRAPHITE_PRESS);
        addDrop(ModBlocks.CONVEYOR);

        addDrop(ModBlocks.ARKYIC_STONE);
    }

}
