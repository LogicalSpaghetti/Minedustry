package me.spaghetti.minedustry.item;

import me.spaghetti.minedustry.Minedustry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup RAT_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Minedustry.MOD_ID, "rat"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup.minedustry"))
                    .icon(() -> new ItemStack(ModItems.BERYLLIUM)).entries((displayContext, entries) -> {
                        // add items here:
                        entries.add(ModItems.BERYLLIUM);
                        entries.add(ModItems.LEAD);


                    }).build());

    public static void registerItemGroups() {
        Minedustry.LOGGER.info("Registering Item Groups for " + Minedustry.MOD_ID);
    }
}

