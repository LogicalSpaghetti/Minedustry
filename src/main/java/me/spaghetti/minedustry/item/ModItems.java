package me.spaghetti.minedustry.item;

import me.spaghetti.minedustry.Minedustry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {




    public static final Item BERYLLIUM = registerItem("beryllium",
            new Item(new FabricItemSettings()));
    public static final Item LEAD = registerItem("lead",
            new Item(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Minedustry.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Minedustry.LOGGER.info("Registering Mod Items for " + Minedustry.MOD_ID);
    }
}
