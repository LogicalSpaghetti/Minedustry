package me.spaghetti.minedustry.item;

import me.spaghetti.minedustry.Minedustry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item COPPER = registerBasicItem("copper");
    public static final Item LEAD = registerBasicItem("lead");
    public static final Item METAGLASS = registerBasicItem("metaglass");
    public static final Item GRAPHITE = registerBasicItem("graphite");
    public static final Item SAND = registerBasicItem("sand");
    public static final Item COAL = registerBasicItem("coal", 20, 100, 0, 0);
    public static final Item TITANIUM = registerBasicItem("titanium");
    public static final Item THORIUM = registerBasicItem("thorium", 20, 0, 100, 0);
    public static final Item SCRAP = registerBasicItem("scrap");
    public static final Item SILICON = registerBasicItem("silicon");
    public static final Item PLASTANIUM = registerBasicItem("plastanium", 20, 10, 0,0);
    public static final Item PHASE_FABRIC = registerBasicItem("phase_fabric", 0, 0, 60, 0);
    public static final Item SURGE_ALLOY = registerBasicItem("surge_alloy", 0, 0, 0, 75);
    public static final Item SPORE_POD = registerBasicItem("spore_pod", 0, 115, 0, 0);
    public static final Item BLAST_COMPOUND = registerBasicItem("blast_compound", 120, 40, 0, 0);
    public static final Item PYRATITE = registerBasicItem("pyratite", 40, 140, 0, 0);
    public static final Item BERYLLIUM = registerBasicItem("beryllium");
    public static final Item TUNGSTEN = registerBasicItem("tungsten");
    public static final Item OXIDE = registerBasicItem("oxide");
    public static final Item CARBIDE = registerBasicItem("carbide");

    public static final Item[] ITEMS = new Item[] {
            COPPER,
            LEAD,
            METAGLASS,
            GRAPHITE,
            SAND,
            COAL,
            TITANIUM,
            THORIUM,
            SCRAP,
            SILICON,
            PLASTANIUM,
            PHASE_FABRIC,
            SURGE_ALLOY,
            SPORE_POD,
            BLAST_COMPOUND,
            PYRATITE,
            BERYLLIUM,
            TUNGSTEN,
            OXIDE,
            CARBIDE
    };

    private static Item registerBasicItem(String name, int explosiveness, int flammability, int radioactivity, int charge) {
        return registerItem(name,
                new BasicMindustryItem(new FabricItemSettings(), explosiveness, flammability, radioactivity, charge));
    }
    private static Item registerBasicItem(String name) {
        return registerItem(name,
                new BasicMindustryItem(new FabricItemSettings()));
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Minedustry.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Minedustry.LOGGER.info("Registering Mod Items for " + Minedustry.MOD_ID);
    }
}
