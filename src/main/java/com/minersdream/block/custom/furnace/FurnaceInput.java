package com.minersdream.block.custom.furnace;

import com.minersdream.util.SendMessage;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.stream.Stream;

import static net.minecraft.world.entity.Entity.RemovalReason.KILLED;


public class FurnaceInput extends Block {
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

    public void insertItem(Level pLevel, BlockPos pPos, Entity pEntity, ItemStack _setStack){
        //posição da smelter
        BlockEntity smelter = pLevel.getBlockEntity(pPos);
        //smelter
        if(smelter != null) {
            smelter.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
                //se a quantidade de items no slot é menor que a capacidade do slot
                if ((capability.getStackInSlot(4).getCount() < capability.getSlotLimit(4))) {
                    //se o item é igual o item a ser inserido e se a quantidade de espaço no slot é menor que a quandidade de items a ser inserido  ou o islot esta vazio
                    if(capability.getStackInSlot(4).getItem() == _setStack.getItem() && (capability.getSlotLimit(4) - capability.getStackInSlot(4).getCount()) >= _setStack.getCount() || capability.getStackInSlot(4).isEmpty()) {

                        capability.insertItem(4, _setStack, false);
                        pEntity.remove(KILLED);
                    }
                }
            });
        }
    }
    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if(!pLevel.isClientSide()) {
            if (pEntity instanceof ItemEntity) {
                if(pState.getValue(FACING) == Direction.EAST) {
                    insertItem(
                            pLevel,
                            new BlockPos(pPos.getX(),pPos.getY(),pPos.getZ()+1),
                            pEntity,
                            ((ItemEntity) pEntity).getItem());
                        SendMessage.send(pLevel, "East");
                }else if(pState.getValue(FACING) == Direction.SOUTH) {
                    insertItem(
                            pLevel,
                            new BlockPos(pPos.getX()-1,pPos.getY(),pPos.getZ()),
                            pEntity,
                            ((ItemEntity) pEntity).getItem());
                        SendMessage.send(pLevel, "South");

                }else if(pState.getValue(FACING) == Direction.WEST) {
                    insertItem(
                            pLevel,
                            new BlockPos(pPos.getX(),pPos.getY(),pPos.getZ()-1),
                            pEntity,
                            ((ItemEntity) pEntity).getItem());
                        SendMessage.send(pLevel, "West");

                }else {
                    insertItem(
                            pLevel,
                            new BlockPos(pPos.getX()+1,pPos.getY(),pPos.getZ()),
                            pEntity,
                            ((ItemEntity) pEntity).getItem());
                        SendMessage.send(pLevel, "North");
                }
            }
        }
        super.stepOn(pLevel, pPos, pState, pEntity);
    }
}
