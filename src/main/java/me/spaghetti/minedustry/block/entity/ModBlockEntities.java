package me.spaghetti.minedustry.block.entity;

import me.spaghetti.minedustry.Minedustry;
import me.spaghetti.minedustry.block.production.arc_furnace.SiliconArcFurnaceBlockEntity;
import me.spaghetti.minedustry.block.entity.conveyor.ConveyorBlockEntity;
import me.spaghetti.minedustry.block.ModBlocks;
import me.spaghetti.minedustry.block.entity.graphite_press.GraphitePressBlockEntity;
import me.spaghetti.minedustry.block.entity.silicon_smelter.SiliconSmelterBlockEntity;
import me.spaghetti.minedustry.block.entity.steam_generator.SteamGeneratorBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<GraphitePressBlockEntity> GRAPHITE_PRESS_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Minedustry.MOD_ID, "graphite_press_be"),
                    FabricBlockEntityTypeBuilder.create(GraphitePressBlockEntity::new,
                            ModBlocks.GRAPHITE_PRESS).build());
    public static final BlockEntityType<SiliconSmelterBlockEntity> SILICON_SMELTER_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Minedustry.MOD_ID, "silicon_smelter_be"),
                    FabricBlockEntityTypeBuilder.create(SiliconSmelterBlockEntity::new,
                            ModBlocks.SILICON_SMELTER).build());
    public static final BlockEntityType<SiliconArcFurnaceBlockEntity> SILICON_ARC_FURNACE_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Minedustry.MOD_ID, "silicon_arc_furnace_be"),
                    FabricBlockEntityTypeBuilder.create(SiliconArcFurnaceBlockEntity::new,
                            ModBlocks.SILICON_ARC_FURNACE).build());
    public static final BlockEntityType<SteamGeneratorBlockEntity> STEAM_GENERATOR_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Minedustry.MOD_ID, "steam_generator_be"),
                    FabricBlockEntityTypeBuilder.create(SteamGeneratorBlockEntity::new,
                            ModBlocks.STEAM_GENERATOR).build());

    public static final BlockEntityType<ConveyorBlockEntity> CONVEYOR_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Minedustry.MOD_ID, "conveyor_be"),
                    FabricBlockEntityTypeBuilder.create(ConveyorBlockEntity::new,
                            ModBlocks.CONVEYOR).build());

    public static void registerBlockEntities() {
        Minedustry.LOGGER.info("Registering block entities for " + Minedustry.MOD_ID);
    }
}
