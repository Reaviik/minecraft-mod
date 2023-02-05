package com.minersdream.block.custom.resourceNodes;

import com.minersdream.block.ModBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

public class NodesHandler {

    public static Item NodesHandler(Block pBlock){

        if(pBlock == ModBlocks.IRON_RESOURCE_NODE.get()){
            return Items.RAW_IRON;
        }
        return null;
    }
}
