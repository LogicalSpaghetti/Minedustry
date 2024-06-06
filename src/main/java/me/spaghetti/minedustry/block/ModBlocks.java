package me.spaghetti.minedustry.block;

import me.spaghetti.minedustry.Minedustry;
import me.spaghetti.minedustry.block.entity.conveyor.ConveyorBlock;
import me.spaghetti.minedustry.block.entity.graphite_press.GraphitePressBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block GRAPHITE_PRESS = registerBlock("graphite_press",
            new GraphitePressBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));
    public static final Block CONVEYOR = registerBlock("conveyor",
            new ConveyorBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block ARKYIC_STONE = registerBlock("arkyic_stone",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(Minedustry.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(Minedustry.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        Minedustry.LOGGER.info("Registering Mod Blocks for " + Minedustry.MOD_ID);
    }
}
