package com.minersdream.block.custom;

import com.minersdream.util.SendMessage;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;


public class ConerConveior extends Block {

        public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
        public ConerConveior(Properties properties) {
            super(properties);
        }
    public static final VoxelShape SHAPE_W = Block.box(0, 0, 2, 14, 4, 16);
    //ok
    public static final VoxelShape SHAPE_E = Block.box(2, 0, 0, 16, 4, 14);
    public static final VoxelShape SHAPE_S = Block.box(2, 0, 2, 16, 4, 16);

    public static final VoxelShape SHAPE_N = Block.box(0, 0, 0, 14, 4, 14);

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
                return SHAPE_E;
            case SOUTH:
                return SHAPE_S;
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
                    ((ItemEntity) pEntity).setExtendedLifetime();
                    double eX = pEntity.getX();
                    double eY = pEntity.getY();
                    double eZ = pEntity.getZ();
                    double pX = pPos.getX();
                    double pY = pPos.getY();
                    double pZ = pPos.getZ();
                    ((ItemEntity) pEntity).setPickUpDelay(10);

                    if(pState.getValue(FACING) == Direction.EAST) {
                        SendMessage.send(pLevel, "Conveior East");
                        if(eZ < pZ + 0.5){
                            pEntity.push(0f, 0f, + 0.01D);
                        }else {
                           pEntity.push(+ 0.01D, 0f, 0f);
                       }

                    }else if(pState.getValue(FACING) == Direction.SOUTH) {
                        SendMessage.send(pLevel, "Conveior South");
                        if(eX < pX + 0.5){
                            pEntity.push(0f, 0f, + 0.01D);
                        }else {
                            pEntity.push(- 0.01D, 0f, 0f);
                        }

                    }else if(pState.getValue(FACING) == Direction.WEST) {
                        SendMessage.send(pLevel, "Conveior West");
                        if(eZ > pZ + 0.5){
                            pEntity.push(0f, 0f, - 0.01D);
                        }else {
                            pEntity.push(- 0.01D, 0f, 0f);
                        }

                    }else {
                        SendMessage.send(pLevel, "Conveior North");
                        if(eX > pX + 0.5){
                            pEntity.push(0f, 0f, - 0.01D);
                        }else {
                            pEntity.push(+ 0.01D, 0f, 0f);
                        }
                    }
                }
            }
            super.stepOn(pLevel, pPos, pState, pEntity);
        }
}