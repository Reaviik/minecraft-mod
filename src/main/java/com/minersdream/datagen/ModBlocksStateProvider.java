package com.minersdream.datagen;

import com.minersdream.MinersDream;
import com.minersdream.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlocksStateProvider extends BlockStateProvider {

    public ModBlocksStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, MinersDream.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.ETERIUM_BLOCK.get());
        simpleBlock(ModBlocks.ETERIUM_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_ETERIUM_ORE.get());

    }
}
