package com.minersdream.block.screen.slot;

import com.minersdream.block.ModBlocks;
import com.minersdream.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ModUpgradeSlot extends SlotItemHandler {
    public ModUpgradeSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return (ModBlocks.OVERCLOCK.get().asItem() == stack.getItem());
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
