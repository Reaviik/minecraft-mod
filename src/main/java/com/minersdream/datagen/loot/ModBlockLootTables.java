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
        this.dropSelf(ModBlocks.ETERIUM_BLOCK.get());
        this.dropSelf(ModBlocks.SPEEDY_BLOCK.get());

        // DROP ORES
        this.add(ModBlocks.ETERIUM_ORE.get(),
                (block) -> createOreDrop(ModBlocks.ETERIUM_ORE.get(), ModItems.RAW_ETERIUM.get()));
        this.add(ModBlocks.DEEPSLATE_ETERIUM_ORE.get(),
                (block) -> createOreDrop(ModBlocks.DEEPSLATE_ETERIUM_ORE.get(), ModItems.RAW_ETERIUM.get()));

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }


}
