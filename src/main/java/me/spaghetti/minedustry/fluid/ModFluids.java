package me.spaghetti.minedustry.fluid;

import me.spaghetti.minedustry.Minedustry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModFluids {
    public static FlowableFluid CRYOFLUID = Registry.register(Registries.FLUID,
            new Identifier(Minedustry.MOD_ID, "soap_water"), new CryoFluidFluid.Still());
    public static FlowableFluid FLOWING_CRYOFLUID = Registry.register(Registries.FLUID,
            new Identifier(Minedustry.MOD_ID, "flowing_soap_water"), new CryoFluidFluid.Flowing());
    public static Block CRYOFLUID_BLOCK = Registry.register(Registries.BLOCK, new Identifier(Minedustry.MOD_ID, "soap_water_block"),
            new FluidBlock(ModFluids.CRYOFLUID, FabricBlockSettings.copyOf(Blocks.WATER)){ });
    public static Item CRYOFLUID_BUCKET = Registry.register(Registries.ITEM, new Identifier(Minedustry.MOD_ID, "soap_water_bucket"),
            new BucketItem(ModFluids.CRYOFLUID, new FabricItemSettings()));

    public static void registerModFluids() {
        Minedustry.LOGGER.info("Registering Mod Fluids for " + Minedustry.MOD_ID);
    }
}
