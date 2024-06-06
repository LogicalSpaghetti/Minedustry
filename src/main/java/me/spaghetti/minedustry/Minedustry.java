package me.spaghetti.minedustry;

import me.spaghetti.minedustry.block.ModBlocks;
import me.spaghetti.minedustry.block.entity.ModBlockEntities;
import me.spaghetti.minedustry.fluid.ModFluids;
import me.spaghetti.minedustry.item.ModItemGroups;
import me.spaghetti.minedustry.item.ModItems;
import me.spaghetti.minedustry.screen.ModScreenHandlers;
import me.spaghetti.minedustry.sounds.ModSounds;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static me.spaghetti.minedustry.block.helpers.SlotRandomizer.getInventoryOffsets;

public class Minedustry implements ModInitializer {
	public static final String MOD_ID = "minedustry";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModFluids.registerModFluids();

		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();

		ModSounds.registerSounds();
	}

}