package me.spaghetti.minedustry.item;

import me.spaghetti.minedustry.Minedustry;
import me.spaghetti.minedustry.block.ModBlocks;
import me.spaghetti.minedustry.fluid.ModFluids;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup MINEDUSTRY_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Minedustry.MOD_ID, "minedustry"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup.minedustry"))
                    .icon(() -> new ItemStack(ModItems.BERYLLIUM)).entries((displayContext, entries) -> {
                        // add items here:
                for (int i = 0; i < ModItems.ITEMS.length; i++) {
                    entries.add(new ItemStack(ModItems.ITEMS[i]));
                }
                        entries.add(ModBlocks.GRAPHITE_PRESS);
                        entries.add(ModBlocks.CONVEYOR);

                        entries.add(ModBlocks.ARKYIC_STONE);

                        entries.add(ModFluids.CRYOFLUID_BUCKET);
                    }).build());

    public static void registerItemGroups() {
        Minedustry.LOGGER.info("Registering Item Group: {} for " + Minedustry.MOD_ID, MINEDUSTRY_GROUP.getDisplayName());
    }
}

