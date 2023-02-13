package com.minersdream.recipe;

import com.minersdream.MinersDream;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MinersDream.MOD_ID);

    public static final RegistryObject<RecipeSerializer<BlockTesteRecipe>> BLOCK_TESTE_SERIALIZER =
            SERIALIZERS.register("block_teste", () -> BlockTesteRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<MinerMk1Recipe>> MINER_MK1_SERIALIZER =
            SERIALIZERS.register("miner_mk1", () -> MinerMk1Recipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<FurnaceSmelterRecipe>> SMELTER_RECIPE_SERIALIZER =
            SERIALIZERS.register("smelter", () -> FurnaceSmelterRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
