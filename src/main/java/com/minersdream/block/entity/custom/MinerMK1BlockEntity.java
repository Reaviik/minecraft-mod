package com.minersdream.block.entity.custom;

import com.minersdream.block.entity.ModBlockEntities;
import com.minersdream.block.screen.MinerMK1.MinerMK1Menu;
import com.minersdream.item.ModItems;
import com.minersdream.recipe.BlockTesteRecipe;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
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
import java.util.Optional;
import java.util.Random;

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
    private int maxProgress = 72;

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
        return new MinerMK1Menu(pContainerId, pPlayerInventory, this);
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

    public static void execute(LevelAccessor world, double x, double y, double z, MinerMK1BlockEntity entity, Block resource) {

        BlockEntity _ent = world.getBlockEntity(new BlockPos(x, y, z));
        if (_ent != null) {
            final int _slotid = 0;
            final ItemStack _setstack = new ItemStack(resource);
            _setstack.setCount(16);
            _ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
                if (capability instanceof IItemHandlerModifiable)
                    ((IItemHandlerModifiable) capability).setStackInSlot(_slotid, _setstack);
            });
        }
        entity.resetProgress();
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, MinerMK1BlockEntity pBlockEntity) {
        Block resource = pLevel.getBlockState(new BlockPos(pPos.getX(), pPos.getY()-1, pPos.getZ())).getBlock();

        if (resource != Blocks.AIR && resource == Blocks.AMETHYST_CLUSTER) { // TODO TAGs : Clusters
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

    private static int hasUpgrades(MinerMK1BlockEntity entity) {
        int upgrades = 0;
        for (int i = 1; i <= 3; i++) {
            if (entity.itemHandler.getStackInSlot(i).getItem() == ModItems.ASNIUM_PICKAXE.get()) {
                upgrades++;
            }
        }
        return upgrades;
        //return true;
    }

    private static boolean hasToolsInToolSlot(MinerMK1BlockEntity entity) {
        return entity.itemHandler.getStackInSlot(2).getItem() == ModItems.ASNIUM_PICKAXE.get();
    }

    private static void craftItem(MinerMK1BlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<BlockTesteRecipe> match = level.getRecipeManager()
                .getRecipeFor(BlockTesteRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            entity.itemHandler.extractItem(0,1, false);
            entity.itemHandler.extractItem(1,1, false);
            entity.itemHandler.getStackInSlot(2).hurt(1, new Random(), null);

            entity.itemHandler.setStackInSlot(0, new ItemStack(Items.GLASS_BOTTLE));
            entity.itemHandler.setStackInSlot(3, new ItemStack(match.get().getResultItem().getItem(),
                    entity.itemHandler.getStackInSlot(3).getCount() + match.get().getResultItem().getCount()));

            entity.resetProgress();
        }
    }
    private void resetProgress() {
        this.progress = 0;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(3).getItem() == output.getItem() || inventory.getItem(3).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(3).getMaxStackSize() > inventory.getItem(3).getCount();
    }
}
