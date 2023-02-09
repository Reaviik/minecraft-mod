package com.minersdream.integration;

import com.minersdream.MinersDream;
import com.minersdream.recipe.MinerMk1Recipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIMinersdreamPlugin implements IModPlugin {

    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MinersDream.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                MinerMk1RecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<MinerMk1Recipe> recipes = rm.getAllRecipesFor(MinerMk1Recipe.Type.INSTANCE);
        registration.addRecipes(new RecipeType<>(MinerMk1RecipeCategory.UID, MinerMk1Recipe.class), recipes);
    }
}
