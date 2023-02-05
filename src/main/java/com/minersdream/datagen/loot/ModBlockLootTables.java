package com.minersdream.datagen.loot;

import com.minersdream.block.ModBlocks;
import com.minersdream.item.ModItems;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLoot {

    @Override
    protected void addTables() {
        // DROP THEM SELFs
        this.dropSelf(ModBlocks.ASNIUM_BLOCK.get());
        this.dropSelf(ModBlocks.ASPHALT.get());
        this.dropSelf(ModBlocks.BLOCK_TESTE.get());
        this.dropSelf(ModBlocks.FOUNDATION.get());
        this.dropSelf(ModBlocks.MINER_ZERO.get());
        this.dropSelf(ModBlocks.FENCE.get());
        this.dropSelf(ModBlocks.MINER_MK1.get());
        this.dropSelf(ModBlocks.OVERCLOCK.get());
        this.dropSelf(ModBlocks.ASNIUM_LAMP.get());
        this.dropSelf(ModBlocks.HORIZONTAL_CONVEIOR.get());
        this.dropSelf(ModBlocks.DIAGONAL_CONVEIOR.get());
        this.dropSelf(ModBlocks.CONER_CONVEIOR.get());

        // MINER MK1
        this.add(ModBlocks.MINER_MK1_SEPARADOR.get(),
                (block) -> createSingleItemTableWithSilkTouch(Blocks.AIR, Blocks.AIR));
        this.add(ModBlocks.MINER_MK1_GERENCIADOR_PARTE1.get(),
                (block) -> createSingleItemTableWithSilkTouch(Blocks.AIR, Blocks.AIR));
        this.add(ModBlocks.MINER_MK1_GERENCIADOR_PARTE2.get(),
                (block) -> createSingleItemTableWithSilkTouch(Blocks.AIR, Blocks.AIR));
        this.add(ModBlocks.MINER_MK1_VACUM.get(),
                (block) -> createSingleItemTableWithSilkTouch(Blocks.AIR, Blocks.AIR));
        this.add(ModBlocks.MINER_MK1_BACK.get(),
                (block) -> createSingleItemTableWithSilkTouch(Blocks.AIR, Blocks.AIR));
        this.add(ModBlocks.MINER_MK1_BROCA.get(),
                (block) -> createSingleItemTableWithSilkTouch(Blocks.AIR, Blocks.AIR));
        this.add(ModBlocks.MINER_MK1_PLATAFORMA.get(),
                (block) -> createSingleItemTableWithSilkTouch(Blocks.AIR, Blocks.AIR));

        this.add(ModBlocks.MINER_MK1_MOTOR.get(),
                (block) -> createSingleItemTable(ModBlocks.MINER_MK1.get()));



//        this.add(ModBlocks.IRON_RESOURCE_NODE.get(),
//                (block) -> createSingleItemTableWithSilkTouch(Blocks.AIR, Blocks.AIR));

        // SOURCE NODES
        this.add(ModBlocks.IRON_RESOURCE_NODE.get(),
                (block) -> createSingleItemTable(Items.RAW_IRON));



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
