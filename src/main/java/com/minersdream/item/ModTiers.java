package com.minersdream.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
    public static final ForgeTier ETERIUM = new ForgeTier(4, 2652, 14.0f, 10.0f, 48, BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(ModItems.ETERIUM_INGOT.get()));
}
