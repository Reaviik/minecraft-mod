package com.minersdream.datagen;

import com.minersdream.MinersDream;
import com.minersdream.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlocksStateProvider extends BlockStateProvider {

    public ModBlocksStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, MinersDream.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.ASNIUM_BLOCK.get());
        simpleBlock(ModBlocks.ASNIUM_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_ASNIUM_ORE.get());
        simpleBlock(ModBlocks.ASPHALT.get());
    }
}
