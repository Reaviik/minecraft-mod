package com.minersdream.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
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

public class Fence  extends Block{ // APAGA A LUZ APAGA TUDO QUE ISSO AMORRR SEI COMO RESOLVER SEUS PROBLEMAS, LIGUE JÁ PARA HAHAHA ERROU
    // https://www.youtube.com/watch?v=7zjJZrM3q_8 CHAAAMAAA
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    //Replace final .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(0, 0, 0, 2, 15, 2),
            Block.box(-0.25, 15, -0.25, 2.25, 16, 2.25),
            Block.box(0, 0, 14, 2, 15, 16),
            Block.box(-0.25, 15, 13.75, 2.25, 16, 16.25),
            Block.box(0.5, 6.5, 2, 1.5, 14.5, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(14, 0, 0, 16, 15, 2),
            Block.box(13.75, 15, -0.25, 16.25, 16, 2.25),
            Block.box(0, 0, 0, 2, 15, 2),
            Block.box(-0.25, 15, -0.25, 2.25, 16, 2.25),
            Block.box(2, 6.5, 0.5, 14, 14.5, 1.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(14, 0, 14, 16, 15, 16),
            Block.box(13.75, 15, 13.75, 16.25, 16, 16.25),
            Block.box(14, 0, 0, 16, 15, 2),
            Block.box(13.75, 15, -0.25, 16.25, 16, 2.25),
            Block.box(14.5, 6.5, 2, 15.5, 14.5, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(0, 0, 14, 2, 15, 16),
            Block.box(-0.25, 15, 13.75, 2.25, 16, 16.25),
            Block.box(14, 0, 14, 16, 15, 16),
            Block.box(13.75, 15, 13.75, 16.25, 16, 16.25),
            Block.box(2, 6.5, 14.5, 14, 14.5, 15.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();



    public Fence(Properties proprieties) {
        super(proprieties);
    }

    //Collision Box//

    // APAGAR PQ SIM NAO VAI SER MAIS UTILIZADO PAU NO TEU CU public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 4);


    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
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
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
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

}