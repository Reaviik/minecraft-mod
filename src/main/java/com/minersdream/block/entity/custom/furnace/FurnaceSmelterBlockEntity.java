package com.minersdream.block.entity.custom.furnace;

import com.minersdream.block.ModBlocks;
import com.minersdream.block.entity.ModBlockEntities;
import com.minersdream.block.entity.custom.miners.MinerMK1BlockEntity;
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

public class FurnaceSmelterBlockEntity extends BlockEntity implements MenuProvider {
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
    public FurnaceSmelterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FURNACE_SMELTER_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                switch (pIndex) {
                    case 0:
                        return FurnaceSmelterBlockEntity.this.progress;
                    case 1:
                        return FurnaceSmelterBlockEntity.this.maxProgress;
                    default:
                        return 0;
                }
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0:
                        FurnaceSmelterBlockEntity.this.progress = pValue;
                        break;
                    case 1:
                        FurnaceSmelterBlockEntity.this.maxProgress = pValue;
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
        return new TextComponent("Smelter");
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
        tag.putInt("furnace_smelter.progress", progress);
        super.saveAdditional(tag);
    }
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }
    private static final Logger LOGGER = LogUtils.getLogger();
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(0).getItem() == output.getItem() || inventory.getItem(0).isEmpty();
    }
    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(0).getMaxStackSize() > inventory.getItem(0).getCount();
    }
    private static boolean hasRecipe(FurnaceSmelterBlockEntity entity) {
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<FurnaceSmelterRecipe> match = level.getRecipeManager()
                .getRecipeFor(FurnaceSmelterRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem());
    }
    private static int hasUpgrades(@NotNull FurnaceSmelterBlockEntity entity) {
        //Reseta o MaxProgress do bloco
        int overclock = 0;
        entity.maxProgress = 180;
        //Verifica todos os slots de 1 a 3
        for (int i = 1; i <= 3; i++) {
            if (entity.itemHandler.getStackInSlot(i).getItem() == ModBlocks.OVERCLOCK.get().asItem()) {
                entity.maxProgress -= entity.maxProgress * 0.5;
                overclock++;
            }
        }
        return overclock;
    }
    private static void craftItem(FurnaceSmelterBlockEntity entity) {
        hasUpgrades(entity);
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<FurnaceSmelterRecipe> match = level.getRecipeManager()
                .getRecipeFor(FurnaceSmelterRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            entity.itemHandler.extractItem(4,1, false);
            entity.itemHandler.setStackInSlot(0, new ItemStack(match.get().getResultItem().getItem(),
                    entity.itemHandler.getStackInSlot(0).getCount() + match.get().getResultItem().getCount()));

            entity.resetProgress();
        }
    }
    //Tick Manager
    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, FurnaceSmelterBlockEntity pBlockEntity) {
        if(hasRecipe(pBlockEntity)) {
            pBlockEntity.progress++;
            setChanged(pLevel, pPos, pState);
            if(pBlockEntity.progress > pBlockEntity.maxProgress) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }
    //Reseta o progresso
    private void resetProgress() {
        this.progress = 0;
    }

}
