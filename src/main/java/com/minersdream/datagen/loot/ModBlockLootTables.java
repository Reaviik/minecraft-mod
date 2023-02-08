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
        this.dropSelf(ModBlocks.FENCE_VERTICAL.get());
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
        this.add(ModBlocks.COPPER_RESOURCE_NODE.get(),
                (block) -> createSingleItemTable(Items.RAW_COPPER));
        this.add(ModBlocks.GOLD_RESOURCE_NODE.get(),
                (block) -> createSingleItemTable(Items.RAW_GOLD));
        this.add(ModBlocks.COAL_RESOURCE_NODE.get(),
                (block) -> createSingleItemTable(Items.COAL));
        this.add(ModBlocks.FLINT_RESOURCE_NODE.get(),
                (block) -> createSingleItemTable(Items.FLINT));
        this.add(ModBlocks.REDSTONE_RESOURCE_NODE.get(),
                (block) -> createSingleItemTable(Items.REDSTONE));
        this.add(ModBlocks.LAPIS_RESOURCE_NODE.get(),
                (block) -> createSingleItemTable(Items.LAPIS_LAZULI));
        this.add(ModBlocks.DIAMOND_RESOURCE_NODE.get(),
                (block) -> createSingleItemTable(Items.DIAMOND));
        this.add(ModBlocks.EMERALD_RESOURCE_NODE.get(),
                (block) -> createSingleItemTable(Items.EMERALD));
        this.add(ModBlocks.QUARTZ_RESOURCE_NODE.get(),
                (block) -> createSingleItemTable(Items.QUARTZ));
        this.add(ModBlocks.GLOWSTONE_RESOURCE_NODE.get(),
                (block) -> createSingleItemTable(Items.GLOWSTONE_DUST));
        this.add(ModBlocks.ANCIENT_DEBRIS_RESOURCE_NODE.get(),
                (block) -> createSingleItemTable(Items.NETHERITE_SCRAP));


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
