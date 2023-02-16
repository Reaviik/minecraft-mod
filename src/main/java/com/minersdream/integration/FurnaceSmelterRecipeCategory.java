package com.minersdream.integration;

import com.minersdream.MinersDream;
import com.minersdream.block.ModBlocks;
import com.minersdream.recipe.FurnaceSmelterRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class FurnaceSmelterRecipeCategory implements IRecipeCategory<FurnaceSmelterRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(MinersDream.MOD_ID, "smelter");
    public final static ResourceLocation TEXTURE = new ResourceLocation(MinersDream.MOD_ID, "textures/gui/jei_furnace_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public FurnaceSmelterRecipeCategory(IGuiHelper helper) {
    this.background = helper.createDrawable(TEXTURE, 0, 0,176, 134);
    this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.FURNACE.get()));

    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends FurnaceSmelterRecipe> getRecipeClass() {
        return FurnaceSmelterRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TextComponent("Smelter");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull FurnaceSmelterRecipe recipe, @NotNull IFocusGroup focus){
        builder.addSlot(RecipeIngredientRole.INPUT, 73, 89).addIngredients(Ingredient.of((ModBlocks.OVERCLOCK.get())));
        builder.addSlot(RecipeIngredientRole.INPUT, 107, 89).addIngredients(Ingredient.of(ModBlocks.OVERCLOCK.get()));
        builder.addSlot(RecipeIngredientRole.INPUT, 141, 89).addIngredients(Ingredient.of(ModBlocks.OVERCLOCK.get()));
        builder.addSlot(RecipeIngredientRole.INPUT, 28, 32).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 116, 32).addItemStack(recipe.getResultItem());
    }


}
