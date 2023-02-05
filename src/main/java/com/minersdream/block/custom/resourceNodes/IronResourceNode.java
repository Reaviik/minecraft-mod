package com.minersdream.block.custom.resourceNodes;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class IronResourceNode extends Block {
    public IronResourceNode(Properties properties) {
        super(properties);
    }


    static ItemStack getDrop() {
        return new ItemStack(Items.RAW_IRON);
    }

}
