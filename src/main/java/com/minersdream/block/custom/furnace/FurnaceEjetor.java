package com.minersdream.block.custom.furnace;

import com.minersdream.block.entity.ModBlockEntities;
import com.minersdream.block.entity.custom.furnace.FurnaceEjectorBlockEntity;
import com.minersdream.util.ITags;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public class FurnaceEjetor extends BaseEntityBlock {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;


    public FurnaceEjetor(Properties pProperties) {
        super(pProperties);
    }

    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
        return SHAPE;
    }
    public static boolean hasItemToOutput(Level pLevel, BlockPos pPos){
        BlockEntity smelter = pLevel.getBlockEntity(pPos);
        AtomicBoolean item = new AtomicBoolean(false);
        //smelter
        if(smelter != null) {
            smelter.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
                //se a quantidade de items no slot é menor que a capacidade do slot
                if ((!capability.getStackInSlot(0).isEmpty())) {
                    //se o item é igual o item a ser inserido e se a quantidade de espaço no slot é menor que a quandidade de items a ser inserido  ou o islot esta vazio
                    item.set(true);
                }
            });
        }
        return item.get();
    }
    public static boolean verifyConveiorTags(@NotNull ItemStack item) {
        return item.is(ITags.Items.CONVEYOR_BELT);
    }
    public static final boolean hasInventory(@NotNull LevelAccessor world, BlockPos pPos) {
        pPos = new BlockPos(pPos.getX(), pPos.getY(), pPos.getZ());
        BlockEntity tileEntity = world.getBlockEntity(pPos);
        if (tileEntity != null && tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).isPresent()){
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).isPresent();
            return true;
        }
        return false;
    }
    public static @NotNull AtomicInteger hasFreeSpaceInInventory(@NotNull LevelAccessor world, BlockState pState, BlockPos pPos, ItemStack _setstack){
        BlockEntity chest = world.getBlockEntity(new BlockPos(getChestPos(pState, pPos)));
        AtomicInteger index = new AtomicInteger(-1);
        chest.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
            if (capability instanceof IItemHandlerModifiable) {
                for(int i = 0; i < capability.getSlots(); i++){
                    if(capability.getStackInSlot(i).getCount() < capability.getStackInSlot(i).getMaxStackSize() || capability.getStackInSlot(i) == _setstack) {
                        index.set(i);
                        break;
                    }
                }
            }});
        return index;
    }
    //TODO >> COMO CARALHO FAZ ESSA FUNÇÂO SER EXECUTADA
    public static void ejectItem(Level pLevel, BlockState pState, BlockPos pPos){
        LOGGER.info("Eject on");
        if(hasItemToOutput(pLevel, pPos)){
            LOGGER.info("has items");
            BlockEntity chest = pLevel.getBlockEntity(new BlockPos(getChestPos(pState, pPos)));
            BlockEntity smelter = pLevel.getBlockEntity(new BlockPos(getSmeltPos(pState, pPos)));
            AtomicReference<ItemStack> _setstack = null;
            smelter.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
                _setstack.set(capability.getStackInSlot(0));
            });
            if(_setstack != null && hasInventory(pLevel, pPos) && hasFreeSpaceInInventory(pLevel, pState, getChestPos(pState, pPos), _setstack.get()).get() >= 0){
                LOGGER.info("todo");
                int slot = hasFreeSpaceInInventory(pLevel, pState, getChestPos(pState, pPos), _setstack.get()).get();
                chest.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(chestCapability -> {
                    smelter.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
                        capability.extractItem(0,1,false);
                        chestCapability.insertItem(slot, _setstack.get(), false);
                    });
                });
            }
        }
    }
    public static @Nullable BlockPos getChestPos(@NotNull BlockState pState, BlockPos pPos){
        if(pState.getValue(FACING) == Direction.NORTH){
            return new BlockPos(pPos.getX()+1, pPos.getY(), pPos.getZ());
        }
        else if(pState.getValue(FACING) == Direction.EAST){
            return new BlockPos(pPos.getX(), pPos.getY(), pPos.getZ()+1);

        }
        else if(pState.getValue(FACING) == Direction.SOUTH){
            return new BlockPos(pPos.getX()-1, pPos.getY(), pPos.getZ());

        }
        else if(pState.getValue(FACING) == Direction.WEST){
            return new BlockPos(pPos.getX(), pPos.getY(), pPos.getZ()-1);

        }
        return null;
    }
    public static @Nullable BlockPos getSmeltPos(@NotNull BlockState pState, BlockPos pPos){
        if(pState.getValue(FACING) == Direction.NORTH){
            return new BlockPos(pPos.getX()-1, pPos.getY(), pPos.getZ());
        }
        else if(pState.getValue(FACING) == Direction.EAST){
            return new BlockPos(pPos.getX(), pPos.getY(), pPos.getZ()-1);

        }
        else if(pState.getValue(FACING) == Direction.SOUTH){
            return new BlockPos(pPos.getX()+1, pPos.getY(), pPos.getZ());

        }
        else if(pState.getValue(FACING) == Direction.WEST){
            return new BlockPos(pPos.getX(), pPos.getY(), pPos.getZ()+1);

        }
        return null;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext){
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }
    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation){
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }
    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror){
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(FACING);
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FurnaceEjectorBlockEntity(pPos, pState);
    }
    //Pega o tick atual do bloco
    @Override
    public <T extends BlockEntity> BlockEntityTicker getTicker(Level plevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.FURNACE_EJECTOR_BLOCK_ENTITY.get(), FurnaceEjectorBlockEntity::tick);
    }
}
