package com.minersdream.block.custom;

import com.minersdream.block.ModBlocks;
import com.minersdream.item.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

public class NodesHandler extends Item{

    public static final Logger LOGGER = LogUtils.getLogger();

    public NodesHandler(Properties pProperties) {
        super(pProperties);
    }

    public static Item getParallelItem(Block pBlock){
        // TODO Retornar sound effect
        if(pBlock == ModBlocks.IRON_RESOURCE_NODE.get()) {
            return ModItems.RAW_IRON.get();
        }else if(pBlock == ModBlocks.COPPER_RESOURCE_NODE.get()){
            return ModItems.RAW_COPPER.get();
        }else if(pBlock == ModBlocks.GOLD_RESOURCE_NODE.get()){
            return ModItems.RAW_GOLD.get();
        }else if(pBlock == ModBlocks.COAL_RESOURCE_NODE.get()){
            return Items.COAL;
        }else if(pBlock == ModBlocks.REDSTONE_RESOURCE_NODE.get()){
            return Items.REDSTONE;
        }else if(pBlock == ModBlocks.LAPIS_RESOURCE_NODE.get()){
            return Items.LAPIS_LAZULI;
        }else if(pBlock == ModBlocks.DIAMOND_RESOURCE_NODE.get()){
            return Items.DIAMOND;
        }else if(pBlock == ModBlocks.EMERALD_RESOURCE_NODE.get()){
            return Items.EMERALD;
        }else if(pBlock == ModBlocks.GLOWSTONE_RESOURCE_NODE.get()){
            return Items.GLOWSTONE_DUST;
        }else if(pBlock == ModBlocks.QUARTZ_RESOURCE_NODE.get()){
            return Items.QUARTZ;
        }else if(pBlock == ModBlocks.ANCIENT_DEBRIS_RESOURCE_NODE.get()){
            return Items.NETHERITE_SCRAP;
        }else if(pBlock == ModBlocks.FLINT_RESOURCE_NODE.get()){
            return Items.FLINT;
        }
        return null;
    }
    public static ItemStack tagToItem(String key) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(key));
        if (item != null) {
            LOGGER.info(new ItemStack(item).toString());
            return new ItemStack(item);
        }
        return null;
    }
}
