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


public class MinerMk1Vacum extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(-0.68024, 0.4725799999999998, 2.86434, 7.3197600000000005, 8.47258, 13.11434),
            Block.box(7.3197600000000005, 2.47258, 2.86434, 15.31976, 10.47258, 13.11434),
            Block.box(2.01776, 2.00711, -0.48566, 4.39276, 16.00711, 1.83934),
            Block.box(2.01776, 2.00711, 14.11434, 4.39276, 16.00711, 16.43934)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(2.8856599999999997, 0.4725799999999998, -0.6802399999999995, 13.13566, 8.47258, 7.3197600000000005),
            Block.box(2.8856599999999997, 2.47258, 7.3197600000000005, 13.13566, 10.47258, 15.31976),
            Block.box(14.16066, 2.00711, 2.01776, 16.48566, 16.00711, 4.39276),
            Block.box(-0.4393400000000014, 2.00711, 2.01776, 1.8856599999999997, 16.00711, 4.39276)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(8.68024, 0.4725799999999998, 2.8856599999999997, 16.680239999999998, 8.47258, 13.13566),
            Block.box(0.6802399999999995, 2.47258, 2.8856599999999997, 8.68024, 10.47258, 13.13566),
            Block.box(11.607240000000001, 2.00711, 14.16066, 13.982240000000001, 16.00711, 16.48566),
            Block.box(11.607240000000001, 2.00711, -0.4393400000000014, 13.982240000000001, 16.00711, 1.8856599999999997)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(2.8643400000000003, 0.4725799999999998, 8.68024, 13.11434, 8.47258, 16.680239999999998),
            Block.box(2.8643400000000003, 2.47258, 0.6802399999999995, 13.11434, 10.47258, 8.68024),
            Block.box(-0.4856599999999993, 2.00711, 11.607240000000001, 1.83934, 16.00711, 13.982240000000001),
            Block.box(14.11434, 2.00711, 11.607240000000001, 16.43934, 16.00711, 13.982240000000001)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public MinerMk1Vacum(Properties pProperties) {
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
