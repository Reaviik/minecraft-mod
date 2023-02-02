package com.minersdream.block.custom;

import com.minersdream.block.entity.custom.ConveiorMove;
import com.minersdream.util.SendMessage;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;


public class HorizontalConveior extends Block {

        public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
        public HorizontalConveior(Properties properties) {
            super(properties);
        }
    public static final VoxelShape SHAPE_W = Block.box(2, 0, 0, 14, 8, 16);
    public static final VoxelShape SHAPE_N = Block.box(0, 0, 2, 16, 8, 14);

    private static final Logger LOGGER = LogUtils.getLogger();
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
        //IMPORTANT
    }
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
        switch (pState.getValue(FACING)) {
            case WEST:
                return SHAPE_W;
            case EAST:
                return SHAPE_W;
            case SOUTH:
                return SHAPE_N;
            default:
                return SHAPE_N;
        }
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    /* BLOCK ENTITY */

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
        public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
            if(!pLevel.isClientSide()){
                if(pEntity instanceof ItemEntity){
                    LOGGER.info(pState.getValue(FACING).toString());
                    pEntity.setXRot(0);
                    pEntity.setYRot(0);
                    ((ItemEntity) pEntity).setPickUpDelay(10);

                    if(pState.getValue(FACING) == Direction.EAST) {
                        pEntity.setPos(pPos.getX()+0.5, pEntity.getY(), pEntity.getZ());
                        pEntity.moveTo(pEntity.getX(), pEntity.getY(), pEntity.getZ() - 0.05);
                        SendMessage.send(pLevel, "Conveior East");

                    }else if(pState.getValue(FACING) == Direction.SOUTH) {
                        pEntity.setPos(pEntity.getX(), pEntity.getY(), pPos.getZ()+0.5);
                        pEntity.moveTo(pEntity.getX() + 0.05, pEntity.getY(), pEntity.getZ());
                        SendMessage.send(pLevel, "Conveior South");

                    }else if(pState.getValue(FACING) == Direction.WEST) {
                        pEntity.setPos(pPos.getX()+0.5, pEntity.getY(), pEntity.getZ());
                        pEntity.moveTo(pEntity.getX(), pEntity.getY(), pEntity.getZ() + 0.05);
                        SendMessage.send(pLevel, "Conveior West");

                    }else {
                        pEntity.setPos(pEntity.getX(), pEntity.getY(), pPos.getZ()+0.5);
                        pEntity.moveTo(pEntity.getX() - 0.05, pEntity.getY(), pEntity.getZ());
                        SendMessage.send(pLevel, "Conveior North");
                    }
                }
            }
            super.stepOn(pLevel, pPos, pState, pEntity);
        }
}
