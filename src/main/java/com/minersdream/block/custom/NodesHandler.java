package com.minersdream.block.custom;

import com.minersdream.block.ModBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

public class NodesHandler {

    public static Item NodesHandler(Block pBlock){

        if(pBlock == ModBlocks.IRON_RESOURCE_NODE.get()){
            return Items.RAW_IRON;
        }else if(pBlock == ModBlocks.COPPER_RESOURCE_NODE.get()){
            return Items.RAW_COPPER;
        }else if(pBlock == ModBlocks.GOLD_RESOURCE_NODE.get()){
            return Items.RAW_GOLD;
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
            return Items.GLOWSTONE;
        }else if(pBlock == ModBlocks.QUARTZ_RESOURCE_NODE.get()){
            return Items.QUARTZ;
        }else if(pBlock == ModBlocks.ANCIENT_DEBRIS_RESOURCE_NODE.get()){
            return Items.NETHERITE_SCRAP;
        }
        return null;
    }
}
