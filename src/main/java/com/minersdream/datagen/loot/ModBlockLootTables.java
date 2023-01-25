package com.minersdream.datagen.loot;

import com.minersdream.block.ModBlocks;
import com.minersdream.item.ModItems;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLoot {

    @Override
    protected void addTables() {
        // DROP THEM SELFs
        this.dropSelf(ModBlocks.ASNIUM_BLOCK.get());
        this.dropSelf(ModBlocks.ASPHALT.get());
        this.dropSelf(ModBlocks.BLOCK_TESTE.get());

        // DROP ORES
        this.add(ModBlocks.ASNIUM_ORE.get(),
                (block) -> createOreDrop(ModBlocks.ASNIUM_ORE.get(), ModItems.RAW_ASNIUM.get()));
        this.add(ModBlocks.DEEPSLATE_ASNIUM_ORE.get(),
                (block) -> createOreDrop(ModBlocks.DEEPSLATE_ASNIUM_ORE.get(), ModItems.RAW_ASNIUM.get()));

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }


}
