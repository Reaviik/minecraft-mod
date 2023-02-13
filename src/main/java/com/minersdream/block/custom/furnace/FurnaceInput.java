package com.minersdream.block.custom.furnace;

import com.minersdream.block.ModBlocks;
import com.minersdream.block.entity.custom.miners.MinerMK1BlockEntity;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;


public class FurnaceInput extends BaseEntityBlock {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public FurnaceInput(Properties pProperties) {
        super(pProperties);
    }


    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(0, 0, 2, 4, 4, 14),
            Block.box(4, 0, 1, 16, 21, 15),
            Block.box(0, 16, 1, 4, 21, 15)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(2, 0, 0, 14, 4, 4),
            Block.box(1, 0, 4, 15, 21, 16),
            Block.box(1, 16, 0, 15, 21, 4)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(12, 0, 2, 16, 4, 14),
            Block.box(0, 0, 1, 12, 21, 15),
            Block.box(12, 16, 1, 16, 21, 15)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(2, 0, 12, 14, 4, 16),
            Block.box(1, 0, 0, 15, 21, 12),
            Block.box(1, 16, 12, 15, 21, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();


    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
        switch (pState.getValue(FACING)) {
            case EAST:
                return SHAPE_E;
            case SOUTH:
                return SHAPE_S;
            case WEST:
                return SHAPE_W;
            default:
                return SHAPE_N;
        }
    }
    //Facing//
    @SuppressWarnings("deprecated")
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
    //TODO >> continuar
    public void insertItem(Level pLevel, BlockPos pPos, BlockEntity pEntity, ItemStack _setStack, BlockPos hPos){
        BlockEntity chest = pLevel.getBlockEntity(pPos);
        BlockEntity input = pLevel.getBlockEntity(hPos);
        chest.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
                chest.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(container  -> {
                    if(capability.getStackInSlot(4) == _setStack && capability.getSlotLimit(4) < capability.getStackInSlot(4).getCount() && container.getStackInSlot(0).getCount() == 1) {
                        container.extractItem(0, 1, false);
                        capability.insertItem(0, _setStack, false);
                    }
                });
        });
    }
    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if(!pLevel.isClientSide()) {
            if (pEntity instanceof ItemEntity) {
                if(pState.getValue(FACING) == Direction.EAST) {

                }else if(pState.getValue(FACING) == Direction.SOUTH) {

                }else if(pState.getValue(FACING) == Direction.WEST) {

                }else {
                }
            }
        }
        super.stepOn(pLevel, pPos, pState, pEntity);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FurnaceBlockEntity(pPos, pState);
    }
}
