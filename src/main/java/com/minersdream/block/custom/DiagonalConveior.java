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
            Block.box(2, 6, -2, 14, 8, 0),
            Block.box(2, 20, 12, 14, 22, 14),
            Block.box(2, 22, 14, 14, 24, 16),
            Block.box(2, 18, 10, 14, 20, 12),
            Block.box(2, 16, 8, 14, 18, 10),
            Block.box(2, 14, 6, 14, 16, 8),
            Block.box(2, 8, 0, 14, 10, 2),
            Block.box(2, 10, 2, 14, 12, 4),
            Block.box(2, 12, 4, 14, 14, 6)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(16, 6, 2, 18, 8, 14),
            Block.box(2, 20, 2, 4, 22, 14),
            Block.box(0, 22, 2, 2, 24, 14),
            Block.box(4, 18, 2, 6, 20, 14),
            Block.box(6, 16, 2, 8, 18, 14),
            Block.box(8, 14, 2, 10, 16, 14),
            Block.box(14, 8, 2, 16, 10, 14),
            Block.box(12, 10, 2, 14, 12, 14),
            Block.box(10, 12, 2, 12, 14, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(2, 6, 16, 14, 8, 18),
            Block.box(2, 20, 2, 14, 22, 4),
            Block.box(2, 22, 0, 14, 24, 2),
            Block.box(2, 18, 4, 14, 20, 6),
            Block.box(2, 16, 6, 14, 18, 8),
            Block.box(2, 14, 8, 14, 16, 10),
            Block.box(2, 8, 14, 14, 10, 16),
            Block.box(2, 10, 12, 14, 12, 14),
            Block.box(2, 12, 10, 14, 14, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(-2, 6, 2, 0, 8, 14),
            Block.box(12, 20, 2, 14, 22, 14),
            Block.box(14, 22, 2, 16, 24, 14),
            Block.box(10, 18, 2, 12, 20, 14),
            Block.box(8, 16, 2, 10, 18, 14),
            Block.box(6, 14, 2, 8, 16, 14),
            Block.box(0, 8, 2, 2, 10, 14),
            Block.box(2, 10, 2, 4, 12, 14),
            Block.box(4, 12, 2, 6, 14, 14)
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
                        //pEntity.push(0D,0.24D,0D);
                        if(eX < pX-0.5) {
                            //pEntity.setPos(pX - 0.1, pY + 1.3,pZ + 0.5);
                            pEntity.push(-0.3D,0.5D,0D);
                        }else{
                            pEntity.push(-0.12D,0.21D,0D);
                        }
                        //pEntity.setPos(eX - 0.24, eY + 0.4, pZ+ 0.5);

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
