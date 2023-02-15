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
            Block.box(3, 0, 0, 13, 4, 4),
            Block.box(3, 4, 4, 13, 8, 8),
            Block.box(3, 8, 8, 13, 12, 12),
            Block.box(3, 12, 12, 13, 16, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(12, 0, 3, 16, 4, 13),
            Block.box(8, 4, 3, 12, 8, 13),
            Block.box(4, 8, 3, 8, 12, 13),
            Block.box(0, 12, 3, 4, 16, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(3, 0, 12, 13, 4, 16),
            Block.box(3, 4, 8, 13, 8, 12),
            Block.box(3, 8, 4, 13, 12, 8),
            Block.box(3, 12, 0, 13, 16, 4)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(0, 0, 3, 4, 4, 13),
            Block.box(4, 4, 3, 8, 8, 13),
            Block.box(8, 8, 3, 12, 12, 13),
            Block.box(12, 12, 3, 16, 16, 13)
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
                    ((ItemEntity) pEntity).setExtendedLifetime();
                    ((ItemEntity) pEntity).setPickUpDelay(10);

                    if(pState.getValue(FACING) == Direction.EAST) {
                        pEntity.setPos(pEntity.getX() - 0.25, pEntity.getY() + 0.25,pPos.getZ() + 0.5);



                    }else if(pState.getValue(FACING) == Direction.SOUTH) {
                        pEntity.setPos(pPos.getX() + 0.5, pEntity.getY() + 0.25,pEntity.getZ() - 0.25);


                    }else if(pState.getValue(FACING) == Direction.WEST) {
                        pEntity.setPos(pEntity.getX() + 0.25, pEntity.getY() + 0.25,pPos.getZ() + 0.5);


                    }else {
                        pEntity.setPos(pPos.getX()  + 0.5, pEntity.getY() + 0.25,pEntity.getZ() + 0.25);

                    }
                }
            }
            super.stepOn(pLevel, pPos, pState, pEntity);
        }
}
