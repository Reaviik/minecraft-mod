package com.minersdream.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ITags {
    public static class Blocks {
        public static final TagKey<Block> ASNIUM_ORE = tag("ores/asnium");
        //public static final TagKey<Block> ASNIUM_BLOCK = tag("storage_blocks/asnium");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Items {
        public static final TagKey<Item> ASNIUM_ORE = tag("ores/asnium");
        //public static final TagKey<Item> ASNIUM_BLOCK = tag("storage_blocks/asnium");
        public static final TagKey<Item> ASNIUM_RAW = tag("raw_materials/asnium");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }

}