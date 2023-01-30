package com.minersdream.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;


public class MinerMk1Broca extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(7, 5.5, 8, 7, 15.5, 8),
            Block.box(7.1, 3.5, 6.700000000000001, 9.1, 5.5, 8.700000000000001),
            Block.box(8.1, 1.5, 7.700000000000001, 8.1, 3.5, 7.700000000000001),
            Block.box(2, 0, 2, 14, 0.25, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(8, 5.5, 7, 8, 15.5, 7),
            Block.box(7.299999999999999, 3.5, 7.1, 9.299999999999999, 5.5, 9.1),
            Block.box(8.299999999999999, 1.5, 8.1, 8.299999999999999, 3.5, 8.1),
            Block.box(2, 0, 2, 14, 0.25, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
                    Block.box(9, 5.5, 8, 9, 15.5, 8),
                    Block.box(6.9, 3.5, 7.299999999999999, 8.9, 5.5, 9.299999999999999),
                    Block.box(7.9, 1.5, 8.299999999999999, 7.9, 3.5, 8.299999999999999),
                    Block.box(2, 0, 2, 14, 0.25, 14)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(8, 5.5, 9, 8, 15.5, 9),
            Block.box(6.700000000000001, 3.5, 6.9, 8.700000000000001, 5.5, 8.9),
            Block.box(7.700000000000001, 1.5, 7.9, 7.700000000000001, 3.5, 7.9),
            Block.box(2, 0, 2, 14, 0.25, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public MinerMk1Broca(Properties pProperties) {
        super(pProperties);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
        switch (pState.getValue(FACING)) {
            case NORTH:
                return SHAPE_N;
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

}
