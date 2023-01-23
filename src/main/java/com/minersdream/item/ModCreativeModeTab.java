package com.minersdream.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab MinersDream_TAB = new CreativeModeTab("minersdream"){
        @Override
        public ItemStack makeIcon() {
                return new ItemStack(ModItems.ETERIUM_INGOT.get());
        }
    };
}
