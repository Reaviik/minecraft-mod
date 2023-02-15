package com.minersdream.block.entity.custom.furnace;

import com.minersdream.block.ModBlocks;
import com.minersdream.block.custom.furnace.FurnaceEjetor;
import com.minersdream.block.entity.ModBlockEntities;
import com.minersdream.block.screen.furnace.FurnaceSmelterMenu;
import com.minersdream.recipe.FurnaceSmelterRecipe;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.util.Optional;

public class FurnaceEjectorBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 180;
    public FurnaceEjectorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FURNACE_EJECTOR_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                switch (pIndex) {
                    case 0:
                        return FurnaceEjectorBlockEntity.this.progress;
                    case 1:
                        return FurnaceEjectorBlockEntity.this.maxProgress;
                    default:
                        return 0;
                }
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0:
                        FurnaceEjectorBlockEntity.this.progress = pValue;
                        break;
                    case 1:
                        FurnaceEjectorBlockEntity.this.maxProgress = pValue;
                        break;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }
    @Override
    public Component getDisplayName() {
        return new TextComponent("ejector");
    }
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FurnaceSmelterMenu(pContainerId, pPlayerInventory, this, this.data);
    }
    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }
    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }
    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("furnace_ejector.progress", progress);
        super.saveAdditional(tag);
    }
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }
    private static final Logger LOGGER = LogUtils.getLogger();

    //Tick Manager
    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, FurnaceEjectorBlockEntity pBlockEntity) {
            pBlockEntity.progress++;
            setChanged(pLevel, pPos, pState);
            LOGGER.info(""+pBlockEntity.progress);
            if(pBlockEntity.progress > pBlockEntity.maxProgress) {
                FurnaceEjetor.ejectItem(pLevel, pState, pPos);
                pBlockEntity.resetProgress();
        } else {
            setChanged(pLevel, pPos, pState);
        }
    }
    //Reseta o progresso
    private void resetProgress() {
        this.progress = 0;
    }

}
