package com.minersdream.block.custom.conveyor;

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


public class ConveyorRIGHT extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public ConveyorRIGHT(Properties properties) {
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
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if(!pLevel.isClientSide()){
            if(pEntity instanceof ItemEntity){
                pEntity.setXRot(0);
                pEntity.setYRot(0);
                LOGGER.info(pState.getValue(FACING).toString());
                ((ItemEntity) pEntity).setExtendedLifetime();
                ((ItemEntity) pEntity).setPickUpDelay(10);

                if(pState.getValue(FACING) == Direction.EAST) {
                    if(pEntity.getX() > pPos.getX() + 0.5){
                        pEntity.setPos(pEntity.getX(), pEntity.getY(), pPos.getZ() + 0.5);
                        pEntity.push(- 0.01D, 0f, 0F);
                    }else {
                        pEntity.setPos(pPos.getX() + 0.5, pEntity.getY(), pEntity.getZ());
                        pEntity.push(0F, 0f, - 0.01D);
                    }

                }else if(pState.getValue(FACING) == Direction.SOUTH) {
                    if(pEntity.getZ() > pPos.getZ() + 0.5){
                        pEntity.setPos(pPos.getX() + 0.5, pEntity.getY(), pEntity.getZ());
                        pEntity.push(0f, 0f, - 0.01D);
                    }else {
                        pEntity.setPos(pEntity.getX(), pEntity.getY(), pPos.getZ() + 0.5);
                        pEntity.push(+ 0.01D, 0f, 0f);
                    }

                }else if(pState.getValue(FACING) == Direction.WEST) {
                    if(pEntity.getX() < pPos.getX() + 0.5){
                        pEntity.setPos(pEntity.getX(), pEntity.getY(), pPos.getZ() + 0.5);
                        pEntity.push(+ 0.01D, 0f, 0f);
                    }else {
                        pEntity.setPos(pPos.getX() + 0.5, pEntity.getY(), pEntity.getZ());
                        pEntity.push(0f, 0f, + 0.01D);
                    }

                }else {
                    if(pEntity.getZ() < pPos.getZ() + 0.5){
                        pEntity.setPos(pPos.getX() + 0.5 , pEntity.getY(), pEntity.getZ());
                        pEntity.push(0f, 0f, + 0.01D);
                    }else {
                        pEntity.setPos(pEntity.getX(), pEntity.getY(), pPos.getZ() + 0.5);
                        pEntity.push(- 0.01D, 0f, 0f);
                    }
                }
            }
        }
        super.stepOn(pLevel, pPos, pState, pEntity);
    }
}