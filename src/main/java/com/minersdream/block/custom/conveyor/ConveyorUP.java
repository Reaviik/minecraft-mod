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
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;

import java.util.stream.Stream;


public class ConveyorUP extends Block {

        public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
        public ConveyorUP(Properties properties) {
            super(properties);
        }
    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(3, 0, 0, 13, 1, 1),
            Block.box(3, 1, 1, 13, 2, 2),
            Block.box(3, 2, 2, 13, 3, 3),
            Block.box(3, 3, 3, 13, 4, 4),
            Block.box(3, 6, 6, 13, 7, 7),
            Block.box(3, 7, 7, 13, 8, 8),
            Block.box(3, 4, 4, 13, 5, 5),
            Block.box(3, 5, 5, 13, 6, 6),
            Block.box(3, 14, 14, 13, 15, 15),
            Block.box(3, 15, 15, 13, 16, 16),
            Block.box(3, 12, 12, 13, 13, 13),
            Block.box(3, 13, 13, 13, 14, 14),
            Block.box(3, 8, 8, 13, 9, 9),
            Block.box(3, 9, 9, 13, 10, 10),
            Block.box(3, 10, 10, 13, 11, 11),
            Block.box(3, 11, 11, 13, 12, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(15, 0, 3, 16, 1, 13),
            Block.box(14, 1, 3, 15, 2, 13),
            Block.box(13, 2, 3, 14, 3, 13),
            Block.box(12, 3, 3, 13, 4, 13),
            Block.box(9, 6, 3, 10, 7, 13),
            Block.box(8, 7, 3, 9, 8, 13),
            Block.box(11, 4, 3, 12, 5, 13),
            Block.box(10, 5, 3, 11, 6, 13),
            Block.box(1, 14, 3, 2, 15, 13),
            Block.box(0, 15, 3, 1, 16, 13),
            Block.box(3, 12, 3, 4, 13, 13),
            Block.box(2, 13, 3, 3, 14, 13),
            Block.box(7, 8, 3, 8, 9, 13),
            Block.box(6, 9, 3, 7, 10, 13),
            Block.box(5, 10, 3, 6, 11, 13),
            Block.box(4, 11, 3, 5, 12, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(3, 0, 15, 13, 1, 16),
            Block.box(3, 1, 14, 13, 2, 15),
            Block.box(3, 2, 13, 13, 3, 14),
            Block.box(3, 3, 12, 13, 4, 13),
            Block.box(3, 6, 9, 13, 7, 10),
            Block.box(3, 7, 8, 13, 8, 9),
            Block.box(3, 4, 11, 13, 5, 12),
            Block.box(3, 5, 10, 13, 6, 11),
            Block.box(3, 14, 1, 13, 15, 2),
            Block.box(3, 15, 0, 13, 16, 1),
            Block.box(3, 12, 3, 13, 13, 4),
            Block.box(3, 13, 2, 13, 14, 3),
            Block.box(3, 8, 7, 13, 9, 8),
            Block.box(3, 9, 6, 13, 10, 7),
            Block.box(3, 10, 5, 13, 11, 6),
            Block.box(3, 11, 4, 13, 12, 5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(0, 0, 3, 1, 1, 13),
            Block.box(1, 1, 3, 2, 2, 13),
            Block.box(2, 2, 3, 3, 3, 13),
            Block.box(3, 3, 3, 4, 4, 13),
            Block.box(6, 6, 3, 7, 7, 13),
            Block.box(7, 7, 3, 8, 8, 13),
            Block.box(4, 4, 3, 5, 5, 13),
            Block.box(5, 5, 3, 6, 6, 13),
            Block.box(14, 14, 3, 15, 15, 13),
            Block.box(15, 15, 3, 16, 16, 13),
            Block.box(12, 12, 3, 13, 13, 13),
            Block.box(13, 13, 3, 14, 14, 13),
            Block.box(8, 8, 3, 9, 9, 13),
            Block.box(9, 9, 3, 10, 10, 13),
            Block.box(10, 10, 3, 11, 11, 13),
            Block.box(11, 11, 3, 12, 12, 13)
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
