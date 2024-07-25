package me.spaghetti.minedustry;

import me.spaghetti.minedustry.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

/**
 * The {@code MinedustryDataGenerator} class handles the automatic generation of json files
 * @author  LogicalSpaghetti
 * @see     me.spaghetti.minedustry.Minedustry
 * @see     me.spaghetti.minedustry.MinedustryDataGenerator
 * @since 1.0
 */

public class MinedustryDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModFluidTagProvider::new);
		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModRecipeProvider::new);
	}
}
