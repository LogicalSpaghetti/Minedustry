package me.spaghetti.minedustry;

import me.spaghetti.minedustry.block.ModBlocks;
import me.spaghetti.minedustry.block.ModBlockEntities;
import me.spaghetti.minedustry.event.PlayerTickHandler;
import me.spaghetti.minedustry.fluid.ModFluids;
import me.spaghetti.minedustry.item.ModItemGroups;
import me.spaghetti.minedustry.item.ModItems;
import me.spaghetti.minedustry.networking.ModPackets;
import me.spaghetti.minedustry.screen.ModScreenHandlers;
import me.spaghetti.minedustry.sounds.ModSounds;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code Minedustry} class handles the global initialization of the mod.
 * <p>
 * All mod content should be in initialized here unless it's client-side-exclusive.
 * <p>
 * Client-side-exclusive code should be initialized in {@linkplain MinedustryClient}.
 * @author  LogicalSpaghetti
 * @see     me.spaghetti.minedustry.MinedustryClient
 * @see     me.spaghetti.minedustry.MinedustryDataGenerator
 */

public class Minedustry implements ModInitializer {

	    /**
     * Used as a consistent, unique identifier for content associated with this mod
     */
	public static final String MOD_ID = "minedustry";

	/**
	 * the recommended way to log information to the console
	*/
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	/**
	 * All content should be initialized here unless it's client exclusive
	 * @see MinedustryClient
	 */
	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlockEntities.registerBlockEntities();
		ModBlocks.registerModBlocks();
		ModFluids.registerModFluids();

		ModScreenHandlers.registerScreenHandlers();

		ModSounds.registerSounds();

		ModPackets.registerC2SPackets();
		ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler());
	}
}