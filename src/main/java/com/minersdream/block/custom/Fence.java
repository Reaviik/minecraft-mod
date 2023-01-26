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
            Block.box(1.5, 5.301511374238574, 2.3063513742385737, 2.5, 7.301511374238574, 14.306351374238552),
            Block.box(1.5, 0.3015200000000011, 2.306352748477142, 2.5, 2.301520000000001, 14.306352748477131),
            Block.box(0.75, 7, 0.75, 3.25, 8, 3.25),
            Block.box(1, -8, 1, 3, 7.75, 3),
            Block.box(0.75, 15, 12.75, 3.25, 16, 15.25),
            Block.box(1, 0, 13, 3, 15.75, 15)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(1.6936486257614476, 5.301511374238574, 1.5, 13.693648625761426, 7.301511374238574, 2.5),
            Block.box(1.6936472515228687, 0.3015200000000011, 1.5, 13.693647251522858, 2.301520000000001, 2.5),
            Block.box(12.75, 7, 0.75, 15.25, 8, 3.25),
            Block.box(13, -8, 1, 15, 7.75, 3),
            Block.box(0.75, 15, 0.75, 3.25, 16, 3.25),
            Block.box(1, 0, 1, 3, 15.75, 3)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(13.5, 5.301511374238574, 1.6936486257614476, 14.5, 7.301511374238574, 13.693648625761426),
            Block.box(13.5, 0.3015200000000011, 1.6936472515228687, 14.5, 2.301520000000001, 13.693647251522858),
            Block.box(12.75, 7, 12.75, 15.25, 8, 15.25),
            Block.box(13, -8, 13, 15, 7.75, 15),
            Block.box(12.75, 15, 0.75, 15.25, 16, 3.25),
            Block.box(13, 0, 1, 15, 15.75, 3)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(2.3063513742385737, 5.301511374238574, 13.5, 14.306351374238552, 7.301511374238574, 14.5),
            Block.box(2.306352748477142, 0.3015200000000011, 13.5, 14.306352748477131, 2.301520000000001, 14.5),
            Block.box(0.75, 7, 12.75, 3.25, 8, 15.25),
            Block.box(1, -8, 13, 3, 7.75, 15),
            Block.box(12.75, 15, 12.75, 15.25, 16, 15.25),
            Block.box(13, 0, 13, 15, 15.75, 15)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();



    public Fence(Properties proprieties) {
        super(proprieties);
    }

    //Collision Box//

    // APAGAR PQ SIM NAO VAI SER MAIS UTILIZADO PAU NO TEU CU public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 4);


    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
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