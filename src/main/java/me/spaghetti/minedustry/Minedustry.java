package me.spaghetti.minedustry;

import me.spaghetti.minedustry.block.custom.ModBlocks;
import me.spaghetti.minedustry.item.ModItemGroups;
import me.spaghetti.minedustry.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Minedustry implements ModInitializer {
	public static final String MOD_ID = "minedustry";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
	}
}