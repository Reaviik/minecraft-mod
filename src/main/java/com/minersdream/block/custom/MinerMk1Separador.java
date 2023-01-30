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


public class MinerMk1Separador extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(-0.25686, 3.86653, 1.9362, 15.84314, 13.11653, 14.1112),
            Block.box(0.24314, 14.53074, 3.85042, 11.84314, 15.78074, 12.17542),
            Block.box(12.59314, 17.78074, 7.45042, 13.74314, 28.78074, 8.57542)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(1.8887999999999998, 3.86653, -0.25685999999999964, 14.0638, 13.11653, 15.84314),
            Block.box(3.824579999999999, 14.53074, 0.24314000000000036, 12.14958, 15.78074, 11.84314),
            Block.box(7.424580000000001, 17.78074, 12.59314, 8.549579999999999, 28.78074, 13.74314)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(0.15686, 3.86653, 1.8887999999999998, 16.25686, 13.11653, 14.0638),
            Block.box(4.15686, 14.53074, 3.824579999999999, 15.75686, 15.78074, 12.14958),
            Block.box(2.2568599999999996, 17.78074, 7.424580000000001, 3.40686, 28.78074, 8.549579999999999)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(1.9361999999999995, 3.86653, 0.15686, 14.1112, 13.11653, 16.25686),
            Block.box(3.8504199999999997, 14.53074, 4.15686, 12.17542, 15.78074, 15.75686),
            Block.box(7.450420000000001, 17.78074, 2.2568599999999996, 8.57542, 28.78074, 3.40686)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public MinerMk1Separador(Properties pProperties) {
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
