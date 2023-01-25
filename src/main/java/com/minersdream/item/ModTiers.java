package com.minersdream.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
    public static final ForgeTier ASNIUM = new ForgeTier(4, 2652, 14.0f, 10.0f, 44, BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(ModItems.ASNIUM_INGOT.get()));
}
