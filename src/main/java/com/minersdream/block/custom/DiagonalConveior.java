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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;

import java.util.stream.Stream;


public class DiagonalConveior extends Block {

        public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
        public DiagonalConveior(Properties properties) {
            super(properties);
        }
    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(2, -4, 0, 14, -3, 1),
            Block.box(2, -3, 1, 14, -2, 2),
            Block.box(2, -2, 2, 14, -1, 3),
            Block.box(2, 1, 5, 14, 2, 6),
            Block.box(2, 0, 4, 14, 1, 5),
            Block.box(2, -1, 3, 14, 0, 4),
            Block.box(2, 2, 6, 14, 3, 7),
            Block.box(2, 6, 10, 14, 7, 11),
            Block.box(2, 5, 9, 14, 6, 10),
            Block.box(2, 4, 8, 14, 5, 9),
            Block.box(2, 3, 7, 14, 4, 8),
            Block.box(2, 9, 13, 14, 10, 14),
            Block.box(2, 8, 12, 14, 9, 13),
            Block.box(2, 7, 11, 14, 8, 12),
            Block.box(2, 10, 14, 14, 11, 15),
            Block.box(2, 12, 16, 14, 13, 17),
            Block.box(2, 11, 15, 14, 12, 16),
            Block.box(2, 8, 12, 14, 9, 13),
            Block.box(2, 7, 11, 14, 8, 12),
            Block.box(2, 10, 14, 14, 11, 15)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(15, -4, 2, 16, -3, 14),
            Block.box(14, -3, 2, 15, -2, 14),
            Block.box(13, -2, 2, 14, -1, 14),
            Block.box(10, 1, 2, 11, 2, 14),
            Block.box(11, 0, 2, 12, 1, 14),
            Block.box(12, -1, 2, 13, 0, 14),
            Block.box(9, 2, 2, 10, 3, 14),
            Block.box(5, 6, 2, 6, 7, 14),
            Block.box(6, 5, 2, 7, 6, 14),
            Block.box(7, 4, 2, 8, 5, 14),
            Block.box(8, 3, 2, 9, 4, 14),
            Block.box(2, 9, 2, 3, 10, 14),
            Block.box(3, 8, 2, 4, 9, 14),
            Block.box(4, 7, 2, 5, 8, 14),
            Block.box(1, 10, 2, 2, 11, 14),
            Block.box(-1, 12, 2, 0, 13, 14),
            Block.box(0, 11, 2, 1, 12, 14),
            Block.box(3, 8, 2, 4, 9, 14),
            Block.box(4, 7, 2, 5, 8, 14),
            Block.box(1, 10, 2, 2, 11, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(2, -4, 15, 14, -3, 16),
            Block.box(2, -3, 14, 14, -2, 15),
            Block.box(2, -2, 13, 14, -1, 14),
            Block.box(2, 1, 10, 14, 2, 11),
            Block.box(2, 0, 11, 14, 1, 12),
            Block.box(2, -1, 12, 14, 0, 13),
            Block.box(2, 2, 9, 14, 3, 10),
            Block.box(2, 6, 5, 14, 7, 6),
            Block.box(2, 5, 6, 14, 6, 7),
            Block.box(2, 4, 7, 14, 5, 8),
            Block.box(2, 3, 8, 14, 4, 9),
            Block.box(2, 9, 2, 14, 10, 3),
            Block.box(2, 8, 3, 14, 9, 4),
            Block.box(2, 7, 4, 14, 8, 5),
            Block.box(2, 10, 1, 14, 11, 2),
            Block.box(2, 12, -1, 14, 13, 0),
            Block.box(2, 11, 0, 14, 12, 1),
            Block.box(2, 8, 3, 14, 9, 4),
            Block.box(2, 7, 4, 14, 8, 5),
            Block.box(2, 10, 1, 14, 11, 2)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(0, -4, 2, 1, -3, 14),
            Block.box(1, -3, 2, 2, -2, 14),
            Block.box(2, -2, 2, 3, -1, 14),
            Block.box(5, 1, 2, 6, 2, 14),
            Block.box(4, 0, 2, 5, 1, 14),
            Block.box(3, -1, 2, 4, 0, 14),
            Block.box(6, 2, 2, 7, 3, 14),
            Block.box(10, 6, 2, 11, 7, 14),
            Block.box(9, 5, 2, 10, 6, 14),
            Block.box(8, 4, 2, 9, 5, 14),
            Block.box(7, 3, 2, 8, 4, 14),
            Block.box(13, 9, 2, 14, 10, 14),
            Block.box(12, 8, 2, 13, 9, 14),
            Block.box(11, 7, 2, 12, 8, 14),
            Block.box(14, 10, 2, 15, 11, 14),
            Block.box(16, 12, 2, 17, 13, 14),
            Block.box(15, 11, 2, 16, 12, 14),
            Block.box(12, 8, 2, 13, 9, 14),
            Block.box(11, 7, 2, 12, 8, 14),
            Block.box(14, 10, 2, 15, 11, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final Logger LOGGER = LogUtils.getLogger();
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
        //IMPORTANT
    }
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
                    double eX = pEntity.getX();
                    double eY = pEntity.getY();
                    double eZ = pEntity.getZ();
                    double pX = pPos.getX();
                    double pY = pPos.getY();
                    double pZ = pPos.getZ();
                    ((ItemEntity) pEntity).setPickUpDelay(10);

                    if(pState.getValue(FACING) == Direction.EAST) {
                        pEntity.push(- 0.02D, 0f, 0f);
                        pEntity.push(- 0f, 0.2D, 0f);
                        SendMessage.send(pLevel, "Conveior East");


                    /*}else if(pState.getValue(FACING) == Direction.SOUTH) {
                        pEntity.setPos(pEntity.getX()+0.5, pEntity.getY()+ 0.2, pPos.getZ());
                        pEntity.moveTo(pEntity.getX(), pEntity.getY(), pEntity.getZ() + 0.05);
                        SendMessage.send(pLevel, "Conveior South");

                    }else if(pState.getValue(FACING) == Direction.WEST) {
                        pEntity.setPos(pPos.getX(), pEntity.getY()+ 0.2, pEntity.getZ()+0.5);
                        pEntity.moveTo(pEntity.getX() + 0.05, pEntity.getY(), pEntity.getZ());
                        SendMessage.send(pLevel, "Conveior West");

                    }else {
                        pEntity.setPos(pEntity.getX()+0.5, pEntity.getY(), pPos.getZ());
                        pEntity.moveTo(pEntity.getX(), pEntity.getY()+ 0.2, pEntity.getZ() - 0.05);
                        SendMessage.send(pLevel, "Conveior North");

                     */
                    }
                }
            }
            super.stepOn(pLevel, pPos, pState, pEntity);
        }
}
