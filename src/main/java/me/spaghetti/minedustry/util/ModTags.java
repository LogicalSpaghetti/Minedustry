package me.spaghetti.minedustry.util;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {

    public static class Blocks {

        public static TagKey<Block> createTag(String id) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(id));
        }
    }

    public static class Fluids {
        public static final TagKey<Fluid> CRYOFLUID = createTag("cryofluid");

        public static TagKey<Fluid> createTag(String id) {
            return TagKey.of(RegistryKeys.FLUID, new Identifier(id));
        }
    }

    public static class Items {
        public static final TagKey<Item> STEAM_GENERATOR_FUEL =
                createTag("steam_generator_fuel");

        public static TagKey<Item> createTag(String id) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(id));
        }
    }



}
