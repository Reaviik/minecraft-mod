package com.minersdream.datagen;

import com.google.common.collect.ImmutableList;
import com.minersdream.block.ModBlocks;
import com.minersdream.datagen.custom.BlockTesteRecipeBuilder;
import com.minersdream.item.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    // LISTs

    protected static final ImmutableList<ItemLike> ASNIUM_SMELTABLES = ImmutableList.of(ModBlocks.ASNIUM_ORE.get(), ModBlocks.DEEPSLATE_ASNIUM_ORE.get(), ModItems.RAW_ASNIUM.get());
    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {

        // WORKBENCH RECIPES

        ShapedRecipeBuilder.shaped(ModBlocks.ASNIUM_BLOCK.get())
                .define('C', ModItems.ASNIUM_INGOT.get())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("has_asnium_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.ASNIUM_INGOT.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModItems.ASNIUM_INGOT.get(), 9)
                .requires(ModBlocks.ASNIUM_BLOCK.get())
                .unlockedBy("has_asnium_block", inventoryTrigger(ItemPredicate.Builder.item()
                .of(ModBlocks.ASNIUM_BLOCK.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.ASNIUM_PICKAXE.get())
                .define('I', ModBlocks.ASNIUM_BLOCK.get())
                .define('S', Items.OBSIDIAN)
                .pattern("III")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy("has_asnium_block", inventoryTrigger(ItemPredicate.Builder.item()
                .of(ModBlocks.ASNIUM_BLOCK.get()).build()))
                .save(pFinishedRecipeConsumer);



        // ORE MELTING RECIPES

        oreSmelting(pFinishedRecipeConsumer, ASNIUM_SMELTABLES, ModItems.ASNIUM_INGOT.get(), 0.7F, 200, "asnium_ingot");

        // BLOCK TESTE ASNIUM

        new BlockTesteRecipeBuilder(ModItems.RAW_ASNIUM.get(), ModItems.ASNIUM_INGOT.get(), 4)
                .unlockedBy("has_raw_asnium", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.RAW_ASNIUM.get()).build())).save(pFinishedRecipeConsumer);
    }
}
