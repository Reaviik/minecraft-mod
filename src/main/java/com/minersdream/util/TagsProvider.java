package com.minersdream.util;

import com.minersdream.MinersDream;
import com.minersdream.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import static net.minecraftforge.common.Tags.Blocks.ORES;

public class TagsProvider {
    public static class Blocks extends BlockTagsProvider {
        public Blocks(DataGenerator generatorIn, String modId, ExistingFileHelper existingFileHelper) {
            super(generatorIn, modId, existingFileHelper);
        }

        @Override
        protected void addTags() {
            // Remove non-dry ice if Forge handles them in the future
            tag(ITags.Blocks.ASNIUM_ORE).add(ModBlocks.ASNIUM_ORE.get()).add(ModBlocks.DEEPSLATE_ASNIUM_ORE.get());
            tag(ORES).addTag(ITags.Blocks.ASNIUM_ORE);

            tag(ITags.Blocks.RESOURCE_NODES)
                    .add(ModBlocks.IRON_RESOURCE_NODE.get())
                    .add(ModBlocks.REDSTONE_RESOURCE_NODE.get())
                    .add(ModBlocks.COAL_RESOURCE_NODE.get())
                    .add(ModBlocks.FLINT_RESOURCE_NODE.get())
                    .add(ModBlocks.LAPIS_RESOURCE_NODE.get())
                    .add(ModBlocks.GOLD_RESOURCE_NODE.get())
                    .add(ModBlocks.COPPER_RESOURCE_NODE.get())
                    .add(ModBlocks.DIAMOND_RESOURCE_NODE.get())
                    .add(ModBlocks.EMERALD_RESOURCE_NODE.get())
                    .add(ModBlocks.QUARTZ_RESOURCE_NODE.get())
                    .add(ModBlocks.GLOWSTONE_RESOURCE_NODE.get())
                    .add(ModBlocks.ANCIENT_DEBRIS_RESOURCE_NODE.get());
            tag(ITags.Blocks.MINER_MK1)
                    .add(ModBlocks.MINER_MK1_BACK.get())
                    .add(ModBlocks.MINER_MK1_BROCA.get())
                    .add(ModBlocks.MINER_MK1_VACUM.get())
                    .add(ModBlocks.MINER_MK1_SEPARADOR.get())
                    .add(ModBlocks.MINER_MK1_MOTOR.get())
                    .add(ModBlocks.MINER_MK1_PLATAFORMA.get())
                    .add(ModBlocks.MINER_MK1_GERENCIADOR_PARTE1.get())
                    .add(ModBlocks.MINER_MK1_GERENCIADOR_PARTE2.get());
            tag(ITags.Blocks.CONVEYOR_BELT)
                    .add(ModBlocks.HORIZONTAL_CONVEIOR.get())
                    .add(ModBlocks.FENCE_VERTICAL.get())
                    .add(ModBlocks.CONER_CONVEIOR.get());

//            tag(ITags.Blocks.ICES).addTag(ITags.Blocks.ICES_ICE).addTag(ITags.Blocks.ICES_PACKED).addTag(ITags.Blocks.ICES_BLUE);
//            tag(ITags.Blocks.ICES_ICE).add(net.minecraft.world.level.block.Blocks.ICE);
//            tag(ITags.Blocks.ICES_PACKED).add(net.minecraft.world.level.block.Blocks.PACKED_ICE);
//            tag(ITags.Blocks.ICES_BLUE).add(net.minecraft.world.level.block.Blocks.BLUE_ICE);
//            tag(ITags.Blocks.ICES).addTag(ITags.Blocks.ICES_DRY);
//            tag(ITags.Blocks.ICES_DRY).add(Blcks.DRY_ICE.get());
//
//            tag(ITags.Blocks.URANINITE_ORE).add(Blcks.URANINITE_ORE.get()).add(Blcks.URANINITE_ORE_POOR.get()).add(Blcks.URANINITE_ORE_DENSE.get());
//            tag(ITags.Blocks.URANINITE_ORE).add(Blcks.DEEPSLATE_URANINITE_ORE.get()).add(Blcks.DEEPSLATE_URANINITE_ORE_POOR.get()).add(Blcks.DEEPSLATE_URANINITE_ORE_DENSE.get());
//            tag(ORES).addTag(ITags.Blocks.URANINITE_ORE);
//
//            tag(STORAGE_BLOCKS).add(Blcks.URANINITE.get());
//            tag(ITags.Blocks.URANINITE_BLOCK).add(Blcks.URANINITE.get());

            tag(BlockTags.MINEABLE_WITH_PICKAXE).addTag(ITags.Blocks.ASNIUM_ORE);

            // All of our blocks are mineable with a pickaxe
//            for (var block : Registry.BLOCK) {
//                if (Registry.BLOCK.getKey(block).getNamespace().equals(MinersDream.MOD_ID)) {
//                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
//                }
//            }

            // However the uraninite ores require at least an iron pickaxe
            tag(BlockTags.NEEDS_IRON_TOOL).addTag(ITags.Blocks.ASNIUM_ORE);
        }
    }

    public static class Items extends ItemTagsProvider {
        public Items(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, String modId, ExistingFileHelper existingFileHelper) {
            super(dataGenerator, blockTagProvider, modId, existingFileHelper);
        }

        @Override
        protected void addTags() {

            tag(ITags.Items.ASNIUM_ORE).add(ModBlocks.ASNIUM_ORE.get().asItem()).add(ModBlocks.DEEPSLATE_ASNIUM_ORE.get().asItem());
            tag(Tags.Items.ORES).addTag(ITags.Items.ASNIUM_ORE);

            tag(ITags.Items.RESOURCE_NODES)
                    .add(ModBlocks.IRON_RESOURCE_NODE.get().asItem())
                    .add(ModBlocks.REDSTONE_RESOURCE_NODE.get().asItem())
                    .add(ModBlocks.COAL_RESOURCE_NODE.get().asItem())
                    .add(ModBlocks.FLINT_RESOURCE_NODE.get().asItem())
                    .add(ModBlocks.LAPIS_RESOURCE_NODE.get().asItem())
                    .add(ModBlocks.GOLD_RESOURCE_NODE.get().asItem())
                    .add(ModBlocks.COPPER_RESOURCE_NODE.get().asItem())
                    .add(ModBlocks.DIAMOND_RESOURCE_NODE.get().asItem())
                    .add(ModBlocks.EMERALD_RESOURCE_NODE.get().asItem())
                    .add(ModBlocks.QUARTZ_RESOURCE_NODE.get().asItem())
                    .add(ModBlocks.GLOWSTONE_RESOURCE_NODE.get().asItem())
                    .add(ModBlocks.ANCIENT_DEBRIS_RESOURCE_NODE.get().asItem());
            tag(ITags.Items.MINER_MK1)
                    .add(ModBlocks.MINER_MK1_BACK.get().asItem())
                    .add(ModBlocks.MINER_MK1_BROCA.get().asItem())
                    .add(ModBlocks.MINER_MK1_VACUM.get().asItem())
                    .add(ModBlocks.MINER_MK1_SEPARADOR.get().asItem())
                    .add(ModBlocks.MINER_MK1_MOTOR.get().asItem())
                    .add(ModBlocks.MINER_MK1_PLATAFORMA.get().asItem())
                    .add(ModBlocks.MINER_MK1_GERENCIADOR_PARTE1.get().asItem())
                    .add(ModBlocks.MINER_MK1_GERENCIADOR_PARTE2.get().asItem());
            tag(ITags.Items.CONVEYOR_BELT)
                    .add(ModBlocks.HORIZONTAL_CONVEIOR.get().asItem())
                    .add(ModBlocks.FENCE_VERTICAL.get().asItem())
                    .add(ModBlocks.CONER_CONVEIOR.get().asItem());

            // Remove non-dry ice if Forge handles them in the future
//            tag(ITags.Items.ICES).addTag(ITags.Items.ICES_ICE).addTag(ITags.Items.ICES_PACKED).addTag(ITags.Items.ICES_BLUE);
//            tag(ITags.Items.ICES_ICE).add(net.minecraft.world.item.Items.ICE);
//            tag(ITags.Items.ICES_PACKED).add(net.minecraft.world.item.Items.PACKED_ICE);
//            tag(ITags.Items.ICES_BLUE).add(net.minecraft.world.item.Items.BLUE_ICE);
//            tag(ITags.Items.ICES).addTag(ITags.Items.ICES_DRY);
//            tag(ITags.Items.ICES_DRY).add(Blcks.DRY_ICE.get().asItem());
//
//            tag(ITags.Items.URANINITE_ORE).add(Blcks.URANINITE_ORE.get().asItem()).add(Blcks.URANINITE_ORE_POOR.get().asItem()).add(Blcks.URANINITE_ORE_DENSE.get().asItem());
//            tag(ITags.Items.URANINITE_ORE).add(Blcks.DEEPSLATE_URANINITE_ORE.get().asItem()).add(Blcks.DEEPSLATE_URANINITE_ORE_POOR.get().asItem()).add(Blcks.DEEPSLATE_URANINITE_ORE_DENSE.get().asItem());
//            tag(Tags.Items.ORES).addTag(ITags.Items.URANINITE_ORE);
//
//            tag(Tags.Items.STORAGE_BLOCKS).add(Blcks.URANINITE.get().asItem());
//            tag(ITags.Items.URANINITE_BLOCK).add(Blcks.URANINITE.get().asItem());
//
//            tag(ITags.Items.URANINITE_RAW).add(Itms.URANINITE_RAW.get());
//            tag(Tags.Items.RAW_MATERIALS).addTag(ITags.Items.URANINITE_RAW);
//
//            tag(Tags.Items.INGOTS).add(Itms.ENERGIZED_STEEL.get());
//            tag(Tags.Items.GEMS).add(Itms.BLAZING_CRYSTAL.get(), Itms.NIOTIC_CRYSTAL.get(), Itms.SPIRITED_CRYSTAL.get(), Itms.NITRO_CRYSTAL.get());
//
//            // Platform abstractions!
//            tag(ITags.ItemAbstractions.GLASS).addTag(Tags.Items.GLASS);
//            tag(ITags.ItemAbstractions.GLASS_PANES).addTag(Tags.Items.GLASS_PANES);
//            tag(ITags.ItemAbstractions.QUARTZ_BLOCKS).addTag(Tags.Items.STORAGE_BLOCKS_QUARTZ);
        }
    }
}