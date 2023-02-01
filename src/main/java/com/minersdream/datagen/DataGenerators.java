package com.minersdream.datagen;

import com.minersdream.MinersDream;
import com.minersdream.util.TagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = MinersDream.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(new ModRecipeProvider(generator));
        generator.addProvider(new ModLootTableProvider(generator));
        generator.addProvider(new ModBlocksStateProvider(generator, existingFileHelper));
        generator.addProvider(new ModItemModelProvider(generator, existingFileHelper));
        generator.addProvider(new TagsProvider.Blocks(generator, MinersDream.MOD_ID, existingFileHelper));
        generator.addProvider(new TagsProvider.Items(generator, (new TagsProvider.Blocks(generator, MinersDream.MOD_ID, existingFileHelper)),
                MinersDream.MOD_ID, existingFileHelper));
    }
}
