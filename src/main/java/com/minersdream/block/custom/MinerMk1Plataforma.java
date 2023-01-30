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


public class MinerMk1Plataforma extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(-5.1322399999999995, 11.99289, -1.2356599999999993, 28.11776, 11.99289, 17.11434),
            Block.box(2.6177600000000005, -0.007110000000000838, 3.0143400000000007, 12.86776, 11.99289, 12.864339999999999),
            Block.box(18.01776, -0.00711, 14.11434, 20.39276, 10.99289, 16.43934),
            Block.box(-4.38224, -0.00711, 14.11434, -2.00724, 10.99289, 16.43934),
            Block.box(-9.54645, 0.00711, 0.60355, -7.54645, 16.00711, 2.60355),
            Block.box(-4.38224, -0.00711, -0.48566, -2.00724, 10.99289, 1.83934),
            Block.box(18.01776, -0.00711, -0.48566, 20.39276, 10.99289, 1.83934)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(-1.1143399999999986, 11.99289, -5.1322399999999995, 17.23566, 11.99289, 28.11776),
            Block.box(3.1356600000000014, -0.007110000000000838, 2.6177600000000005, 12.98566, 11.99289, 12.86776),
            Block.box(-0.4393400000000014, -0.00711, 18.01776, 1.8856599999999997, 10.99289, 20.39276),
            Block.box(-0.4393400000000014, -0.00711, -4.3822399999999995, 1.8856599999999997, 10.99289, -2.0072399999999995),
            Block.box(13.39645, 0.00711, -9.54645, 15.39645, 16.00711, -7.54645),
            Block.box(14.16066, -0.00711, -4.3822399999999995, 16.48566, 10.99289, -2.0072399999999995),
            Block.box(14.16066, -0.00711, 18.01776, 16.48566, 10.99289, 20.39276)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(-12.11776, 11.99289, -1.1143399999999986, 21.13224, 11.99289, 17.23566),
            Block.box(3.1322399999999995, -0.007110000000000838, 3.1356600000000014, 13.38224, 11.99289, 12.98566),
            Block.box(-4.392759999999999, -0.00711, -0.4393400000000014, -2.017759999999999, 10.99289, 1.8856599999999997),
            Block.box(18.00724, -0.00711, -0.4393400000000014, 20.38224, 10.99289, 1.8856599999999997),
            Block.box(23.54645, 0.00711, 13.39645, 25.54645, 16.00711, 15.39645),
            Block.box(18.00724, -0.00711, 14.16066, 20.38224, 10.99289, 16.48566),
            Block.box(-4.392759999999999, -0.00711, 14.16066, -2.017759999999999, 10.99289, 16.48566)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(-1.2356599999999993, 11.99289, -12.11776, 17.11434, 11.99289, 21.13224),
            Block.box(3.0143400000000007, -0.007110000000000838, 3.1322399999999995, 12.864339999999999, 11.99289, 13.38224),
            Block.box(14.11434, -0.00711, -4.392759999999999, 16.43934, 10.99289, -2.017759999999999),
            Block.box(14.11434, -0.00711, 18.00724, 16.43934, 10.99289, 20.38224),
            Block.box(0.6035500000000003, 0.00711, 23.54645, 2.6035500000000003, 16.00711, 25.54645),
            Block.box(-0.4856599999999993, -0.00711, 18.00724, 1.83934, 10.99289, 20.38224),
            Block.box(-0.4856599999999993, -0.00711, -4.392759999999999, 1.83934, 10.99289, -2.017759999999999)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public MinerMk1Plataforma(Properties pProperties) {
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
