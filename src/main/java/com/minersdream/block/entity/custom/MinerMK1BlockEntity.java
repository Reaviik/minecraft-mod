package com.minersdream.block.entity.custom;

import com.minersdream.MinersDream;
import com.minersdream.block.ModBlocks;
import com.minersdream.block.custom.MinerMK1;
import com.minersdream.block.entity.ModBlockEntities;
import com.minersdream.block.screen.MinerMK1.MinerMK1Menu;
import com.minersdream.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class MinerMK1BlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    //private int maxProgress = 72;
    //modificado
    private int maxProgress = 1;


    public MinerMK1BlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MINER_MK1_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                switch (pIndex) {
                    case 0: return MinerMK1BlockEntity.this.progress;
                    case 1: return MinerMK1BlockEntity.this.maxProgress;
                    default: return 0;
                }
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0: MinerMK1BlockEntity.this.progress = pValue; break;
                    case 1: MinerMK1BlockEntity.this.maxProgress = pValue; break;
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
        return new TextComponent(" ");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new MinerMK1Menu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    };

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
        tag.putInt("miner_mk1.progress", progress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    // pState / pPos > place?
    public static void execute(LevelAccessor world, double x, double y, double z, MinerMK1BlockEntity entity, Block resource) {

        BlockEntity _ent = world.getBlockEntity(new BlockPos(x, y, z));
        if (_ent != null) {
            // base 16 items
            final int _slotid = 0;
            hasUpgrades(entity);
            final int slotCount = entity.itemHandler.getStackInSlot(0).getCount();
            final ItemStack _setstack = new ItemStack(Items.RAW_IRON); // Todo tAGs : ores
            if(world instanceof Level _lvl_isPow ? _lvl_isPow.hasNeighborSignal(new BlockPos(x, y, z)) : false){
                if (entity.itemHandler.getStackInSlot(0).getCount() == 0 || entity.itemHandler.getStackInSlot(0).getCount() < entity.itemHandler.getSlotLimit(0) && entity.itemHandler.getStackInSlot(0).getItem() == Items.RAW_IRON) {
                    _setstack.setCount(slotCount + 1);
                    _ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
                        if (capability instanceof IItemHandlerModifiable)
                            ((IItemHandlerModifiable) capability).setStackInSlot(_slotid, _setstack);
                        world.levelEvent(2001, new BlockPos(x, y, z), Block.getId(Blocks.IRON_ORE.defaultBlockState()));
                    });
                    //place block in world
                    //world.levelEvent(2001, new BlockPos(x, y, z), Block.getId(ModBlocks.IRON_RESOURCE_NODE.get().defaultBlockState()));
                } else {

                   if (world instanceof Level _level && !_level.isClientSide()) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, y+1, z, new ItemStack(Items.RAW_IRON));
                        entityToSpawn.setPickUpDelay(1);
                        _level.addFreshEntity(entityToSpawn);
                       world.levelEvent(2001, new BlockPos(x, y, z), Block.getId(Blocks.IRON_ORE.defaultBlockState()));
                    }
                }
            }
        }
        entity.resetProgress();
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, MinerMK1BlockEntity pBlockEntity) {
        Block resource = pLevel.getBlockState(new BlockPos(pPos.getX(), pPos.getY()-1, pPos.getZ())).getBlock();


        if (resource != Blocks.AIR && resource == ModBlocks.IRON_RESOURCE_NODE.get()) { // TODO TAGs : Clusters
            pBlockEntity.progress++;
            setChanged(pLevel, pPos, pState);
            if(pBlockEntity.progress > pBlockEntity.maxProgress) {
                execute(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), pBlockEntity, resource);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private static void hasUpgrades(MinerMK1BlockEntity entity) {
        int upgrades = 0;
        //modificado
        entity.maxProgress = 120;
        for (int i = 1; i <= 3; i++) {
            if (entity.itemHandler.getStackInSlot(i).getItem() == ModBlocks.OVERCLOCK.get().asItem()) {
                upgrades++;
                //modificado
            }
        }
        entity.maxProgress = entity.maxProgress - upgrades * 30;
        //return true;
    }
    private void resetProgress() {
        this.progress = 0;
    }

}
