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


public class MinerMk1Motor extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(-4.38224, 0.00711, -0.48566, -2.00724, 16.00711, 1.83934),
            Block.box(-4.38224, 0.00711, 14.11434, -2.00724, 16.00711, 16.43934),
            Block.box(18.11776, 0.00711, 14.11434, 20.49276, 16.00711, 16.43934),
            Block.box(18.11776, 0.00711, -0.48566, 20.49276, 16.00711, 1.83934),
            Block.box(-0.56993, 2.5, 4.8257, 2.45507, 13.5, 11.0257),
            Block.box(14.25507, 3.5, 4.8257, 16.25507, 10.5, 11.0257),
            Block.box(4, 0.5, 4, 12, 15.5, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(14.16066, 0.00711, -4.3822399999999995, 16.48566, 16.00711, -2.0072399999999995),
            Block.box(-0.4393400000000014, 0.00711, -4.3822399999999995, 1.8856599999999997, 16.00711, -2.0072399999999995),
            Block.box(-0.4393400000000014, 0.00711, 18.11776, 1.8856599999999997, 16.00711, 20.49276),
            Block.box(14.16066, 0.00711, 18.11776, 16.48566, 16.00711, 20.49276),
            Block.box(4.9742999999999995, 2.5, -0.5699299999999994, 11.174299999999999, 13.5, 2.45507),
            Block.box(4.9742999999999995, 3.5, 14.25507, 11.174299999999999, 10.5, 16.25507),
            Block.box(4, 0.5, 4, 12, 15.5, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(18.00724, 0.00711, 14.16066, 20.38224, 16.00711, 16.48566),
            Block.box(18.00724, 0.00711, -0.4393400000000014, 20.38224, 16.00711, 1.8856599999999997),
            Block.box(-4.4927600000000005, 0.00711, -0.4393400000000014, -2.1177600000000005, 16.00711, 1.8856599999999997),
            Block.box(-4.4927600000000005, 0.00711, 14.16066, -2.1177600000000005, 16.00711, 16.48566),
            Block.box(13.54493, 2.5, 4.9742999999999995, 16.56993, 13.5, 11.174299999999999),
            Block.box(-0.2550699999999999, 3.5, 4.9742999999999995, 1.74493, 10.5, 11.174299999999999),
            Block.box(4, 0.5, 4, 12, 15.5, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(-0.4856599999999993, 0.00711, 18.00724, 1.83934, 16.00711, 20.38224),
            Block.box(14.11434, 0.00711, 18.00724, 16.43934, 16.00711, 20.38224),
            Block.box(14.11434, 0.00711, -4.4927600000000005, 16.43934, 16.00711, -2.1177600000000005),
            Block.box(-0.4856599999999993, 0.00711, -4.4927600000000005, 1.83934, 16.00711, -2.1177600000000005),
            Block.box(4.825700000000001, 2.5, 13.54493, 11.0257, 13.5, 16.56993),
            Block.box(4.825700000000001, 3.5, -0.2550699999999999, 11.0257, 10.5, 1.74493),
            Block.box(4, 0.5, 4, 12, 15.5, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public MinerMk1Motor(Properties pProperties) {
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
