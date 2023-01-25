package com.minersdream.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import javax.annotation.Nullable;

public class Coal extends Item {
    public Coal(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 5000;
    }
}
