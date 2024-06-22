package me.spaghetti.minedustry.block;

import me.spaghetti.minedustry.Minedustry;
import me.spaghetti.minedustry.block.energy.BeamNodeBlock;
import me.spaghetti.minedustry.block.production.arc_furnace.SiliconArcFurnaceBlock;
import me.spaghetti.minedustry.block.distribution.conveyor.ConveyorBlock;
import me.spaghetti.minedustry.block.production.graphite_press.GraphitePressBlock;
import me.spaghetti.minedustry.block.production.silicon_smelter.SiliconSmelterBlock;
import me.spaghetti.minedustry.block.energy.steam_generator.SteamGeneratorBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block GRAPHITE_PRESS = registerBlock("graphite-press",
            new GraphitePressBlock(FabricBlockSettings.copyOf(Blocks.GLASS).sounds(BlockSoundGroup.DEEPSLATE_TILES)));
    public static final Block SILICON_SMELTER = registerBlock("silicon-smelter",
            new SiliconSmelterBlock(FabricBlockSettings.copyOf(ModBlocks.GRAPHITE_PRESS)));
    public static final Block SILICON_ARC_FURNACE = registerBlock("silicon-arc-furnace",
            new SiliconArcFurnaceBlock(FabricBlockSettings.copyOf(ModBlocks.GRAPHITE_PRESS).nonOpaque()));

    public static final Block STEAM_GENERATOR = registerBlock("steam-generator",
            new SteamGeneratorBlock(FabricBlockSettings.copyOf(ModBlocks.GRAPHITE_PRESS)));
    public static final Block BEAM_NODE = registerBlock("beam-node",
            new BeamNodeBlock(FabricBlockSettings.copyOf(ModBlocks.GRAPHITE_PRESS)));

    public static final Block CONVEYOR = registerBlock("conveyor",
            new ConveyorBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block[] BASIC_BLOCKS = new Block[]{
            GRAPHITE_PRESS,
            SILICON_SMELTER,
            SILICON_ARC_FURNACE,
            STEAM_GENERATOR,
    };

    public static final Block ARKYIC_STONE = registerBlock("arkyic-stone",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block ARKYIC_WALL = registerBlock("arkyic-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block BASALT = registerBlock("basalt",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block BERYLLIC_STONE_WALL = registerBlock("beryllic-stone-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block BERYLLIC_STONE = registerBlock("beryllic-stone",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block BLUEMAT = registerBlock("bluemat",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block CARBON_STONE = registerBlock("carbon-stone",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block CARBON_WALL = registerBlock("carbon-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block CHAR = registerBlock("char",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block CORE_ZONE = registerBlock("core-zone",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block CRATER_STONE = registerBlock("crater-stone",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block CRYSTAL_FLOOR = registerBlock("crystal-floor",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block CRYSTALLINE_STONE_WALL = registerBlock("crystalline-stone-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block CRYSTALLINE_STONE = registerBlock("crystalline-stone",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block DACITE_WALL = registerBlock("dacite-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block DACITE = registerBlock("dacite",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block DARK_METAL = registerBlock("dark-metal",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block DARK_PANEL_ = registerBlock("dark-panel-",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block DARKSAND = registerBlock("darksand",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block DENSE_RED_STONE = registerBlock("dense-red-stone",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block DIRT_WALL = registerBlock("dirt-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block DIRT = registerBlock("dirt",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block DUNE_WALL = registerBlock("dune-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block FERRIC_CRATERS = registerBlock("ferric-craters",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block FERRIC_STONE_WALL = registerBlock("ferric-stone-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block FERRIC_STONE = registerBlock("ferric-stone",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block GRAPHITIC_WALL = registerBlock("graphitic-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block GRASS = registerBlock("grass",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block HOTROCK = registerBlock("hotrock",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block ICE_SNOW = registerBlock("ice-snow",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block ICE_WALL = registerBlock("ice-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block ICE = registerBlock("ice",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block MAGMAROCK = registerBlock("magmarock",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block METAL_FLOOR_DAMAGED = registerBlock("metal-floor-damaged",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block METAL_FLOOR = registerBlock("metal-floor",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block MOSS = registerBlock("moss",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block MUD = registerBlock("mud",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block RED_ICE_WALL = registerBlock("red-ice-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block RED_ICE = registerBlock("red-ice",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block RED_STONE_WALL = registerBlock("red-stone-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block RED_STONE = registerBlock("red-stone",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block REDMAT = registerBlock("redmat",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block REGOLITH_WALL = registerBlock("regolith-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block REGOLITH = registerBlock("regolith",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block RHYOLITE_CRATER = registerBlock("rhyolite-crater",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block RHYOLITE_WALL = registerBlock("rhyolite-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block RHYOLITE = registerBlock("rhyolite",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block ROUGH_RHYOLITE = registerBlock("rough-rhyolite",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block SALT_WALL = registerBlock("salt-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block SALT = registerBlock("salt",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block SAND_FLOOR = registerBlock("sand-floor",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block SAND_WALL = registerBlock("sand-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block SHALE_WALL = registerBlock("shale-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block SHALE = registerBlock("shale",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block SHRUBS = registerBlock("shrubs",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block SNOW_WALL = registerBlock("snow-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block SNOW = registerBlock("snow",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block SPORE_MOSS = registerBlock("spore-moss",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block SPORE_WALL = registerBlock("spore-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block STONE_WALL = registerBlock("stone-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block STONE = registerBlock("stone",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block YELLOW_STONE_PLATES = registerBlock("yellow-stone-plates",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block YELLOW_STONE_WALL = registerBlock("yellow-stone-wall",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block YELLOW_STONE = registerBlock("yellow-stone",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));

    public static final Block[] ENVIRONMENT_BLOCKS = new Block[] {
            ARKYIC_STONE,
            ARKYIC_WALL,
            BASALT,
            BERYLLIC_STONE_WALL,
            BERYLLIC_STONE,
            BLUEMAT,
            CARBON_STONE,
            CARBON_WALL,
            CHAR,
            CORE_ZONE,
            CRATER_STONE,
            CRYSTAL_FLOOR,
            CRYSTALLINE_STONE_WALL,
            CRYSTALLINE_STONE,
            DACITE_WALL,
            DACITE,
            DARK_METAL,
            DARK_PANEL_,
            DARKSAND,
            DENSE_RED_STONE,
            DIRT_WALL,
            DIRT,
            DUNE_WALL,
            FERRIC_CRATERS,
            FERRIC_STONE_WALL,
            FERRIC_STONE,
            GRAPHITIC_WALL,
            GRASS,
            HOTROCK,
            ICE_SNOW,
            ICE_WALL,
            ICE,
            MAGMAROCK,
            METAL_FLOOR_DAMAGED,
            METAL_FLOOR,
            MOSS,
            MUD,
            RED_ICE_WALL,
            RED_ICE,
            RED_STONE_WALL,
            RED_STONE,
            REDMAT,
            REGOLITH_WALL,
            REGOLITH,
            RHYOLITE_CRATER,
            RHYOLITE_WALL,
            RHYOLITE,
            ROUGH_RHYOLITE,
            SALT_WALL,
            SALT,
            SAND_FLOOR,
            SAND_WALL,
            SHALE_WALL,
            SHALE,
            SHRUBS,
            SNOW_WALL,
            SNOW,
            SPORE_MOSS,
            SPORE_WALL,
            STONE_WALL,
            STONE,
            YELLOW_STONE_PLATES,
            YELLOW_STONE_WALL,
            YELLOW_STONE,
    };

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(Minedustry.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        /*return*/ Registry.register(Registries.ITEM, new Identifier(Minedustry.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        Minedustry.LOGGER.info("Registering Mod Blocks for " + Minedustry.MOD_ID);
    }
}
