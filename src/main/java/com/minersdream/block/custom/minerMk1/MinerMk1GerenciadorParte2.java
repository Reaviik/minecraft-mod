package com.minersdream.block.custom.minerMk1;

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


public class MinerMk1GerenciadorParte2 extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(-4.82016, 13.99289, 1.97742, 7.17984, 18.99289, 13.82742),
            Block.box(-9.82016, -0.00711, 3.97742, 12.17984, 14.99289, 11.82742),
            Block.box(-9.54645, 0.00711, 0.60355, -7.54645, 14.00711, 2.60355),
            Block.box(7.24838, 13.99289, 12.20789, 9.248380000000001, 22.99289, 14.20789)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(2.17258, 13.99289, -4.82016, 14.02258, 18.99289, 7.17984),
            Block.box(4.17258, -0.00711, -9.820160000000001, 12.02258, 14.99289, 12.17984),
            Block.box(13.39645, 0.00711, -9.54645, 15.39645, 14.00711, -7.54645),
            Block.box(1.7921099999999992, 13.99289, 7.24838, 3.792109999999999, 22.99289, 9.248380000000001)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(8.82016, 13.99289, 2.17258, 20.82016, 18.99289, 14.02258),
            Block.box(3.8201599999999996, -0.00711, 4.17258, 25.82016, 14.99289, 12.02258),
            Block.box(23.54645, 0.00711, 13.39645, 25.54645, 14.00711, 15.39645),
            Block.box(6.751619999999999, 13.99289, 1.7921099999999992, 8.751619999999999, 22.99289, 3.792109999999999)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(1.9774200000000004, 13.99289, 8.82016, 13.82742, 18.99289, 20.82016),
            Block.box(3.9774200000000004, -0.00711, 3.8201599999999996, 11.82742, 14.99289, 25.82016),
            Block.box(0.6035500000000003, 0.00711, 23.54645, 2.6035500000000003, 14.00711, 25.54645),
            Block.box(12.20789, 13.99289, 6.751619999999999, 14.20789, 22.99289, 8.751619999999999)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public MinerMk1GerenciadorParte2(Properties pProperties) {
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
