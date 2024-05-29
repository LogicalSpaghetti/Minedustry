package me.spaghetti.minedustry.block.entity;

import me.spaghetti.minedustry.Minedustry;
import me.spaghetti.minedustry.block.custom.ModBlocks;
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

    public static void registerBlockEntities() {
        Minedustry.LOGGER.info("Registering block entities for " + Minedustry.MOD_ID);
    }
}