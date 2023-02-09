package com.minersdream.integration;

import com.minersdream.MinersDream;
import com.minersdream.block.ModBlocks;
import com.minersdream.recipe.MinerMk1Recipe;
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

public class MinerMk1RecipeCategory implements IRecipeCategory<MinerMk1Recipe> {
    public final static ResourceLocation UID = new ResourceLocation(MinersDream.MOD_ID, "miner_mk1");
    public final static ResourceLocation TEXTURE = new ResourceLocation(MinersDream.MOD_ID, "textures/gui/jei_miner_mk1_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public MinerMk1RecipeCategory(IGuiHelper helper) {
    this.background = helper.createDrawable(TEXTURE, 0, 0,176, 134);
    this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MINER_MK1_MOTOR.get()));

    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }
    @Override
    public Class<? extends MinerMk1Recipe> getRecipeClass() {
        return MinerMk1Recipe.class;
    }
    @Override
    public Component getTitle() {
        return new TextComponent("Miner Mark 1");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull MinerMk1Recipe recipe, @NotNull IFocusGroup focus){
        builder.addSlot(RecipeIngredientRole.INPUT, 73, 89).addIngredients(Ingredient.of((ModBlocks.OVERCLOCK.get())));
        builder.addSlot(RecipeIngredientRole.INPUT, 107, 89).addIngredients(Ingredient.of(ModBlocks.OVERCLOCK.get()));
        builder.addSlot(RecipeIngredientRole.INPUT, 141, 89).addIngredients(Ingredient.of(ModBlocks.OVERCLOCK.get()));
        builder.addSlot(RecipeIngredientRole.INPUT, 81, 48).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 81, 12).addItemStack(recipe.getResultItem());
    }


}
