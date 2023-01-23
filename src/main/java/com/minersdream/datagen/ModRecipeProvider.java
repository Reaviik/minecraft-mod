package com.minersdream.datagen;

import com.google.common.collect.ImmutableList;
import com.minersdream.block.ModBlocks;
import com.minersdream.item.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    // LISTs

    protected static final ImmutableList<ItemLike> ETERIUM_SMELTABLES = ImmutableList.of(ModBlocks.ETERIUM_ORE.get(), ModBlocks.DEEPSLATE_ETERIUM_ORE.get(), ModItems.RAW_ETERIUM.get());

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {

        // WORKBENCH RECIPES

        ShapedRecipeBuilder.shaped(ModBlocks.ETERIUM_BLOCK.get())
                .define('C', ModItems.ETERIUM.get())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("has_eterium", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.ETERIUM.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModItems.ETERIUM.get(), 9)
                .requires(ModBlocks.ETERIUM_BLOCK.get())
                .unlockedBy("has_eterium_block", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.ETERIUM_BLOCK.get()).build()))
                .save(pFinishedRecipeConsumer);



        // ORE MELTING RECIPES

        oreSmelting(pFinishedRecipeConsumer, ETERIUM_SMELTABLES, ModItems.ETERIUM.get(), 0.7F, 200, "eterium");
    }
}
