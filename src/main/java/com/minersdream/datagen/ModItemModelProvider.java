package com.minersdream.datagen;

import com.minersdream.MinersDream;
import com.minersdream.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MinersDream.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // SIMPLE ITEMS
        //simpleItem(ModItems.RAW_ASNIUM.get());

        // HANDHELD ITEMS
        //handheldItem(ModItems.ASNIUM_PICKAXE.get());
    }

    private ItemModelBuilder simpleItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(),
            new ResourceLocation("item/generated")).texture("layer0",
            new ResourceLocation(MinersDream.MOD_ID, "item/" + item.getRegistryName().getPath()));
    }

    private ItemModelBuilder handheldItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(MinersDream.MOD_ID, "item/" + item.getRegistryName().getPath()));
    }
}
