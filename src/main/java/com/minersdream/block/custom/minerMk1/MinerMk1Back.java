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


public class MinerMk1Back extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_N = Stream.of(
                    Block.box(0, 1, 7, 6, 16, 16),
                    Block.box(0, 1, 0, 6, 9, 7),
                    Block.box(11.617763562373096, 2.007110000000001, -0.38566356237309485, 13.992763562373096, 17.007109999999997, 1.9393364376269053),
                    Block.box(11.617763562373096, 2.007110000000001, 14.114336437626907, 13.992763562373096, 17.007109999999997, 16.439336437626906),
                    Block.box(6.45355, 5.007110000000001, 0.6035500000000003, 8.45355, 17.007110000000008, 2.6035500000000003)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(-0.07053178118654557, 1, 0.12420465644035872, 8.929468218813454, 16, 6.124204656440359),
            Block.box(8.929468218813454, 1, 0.12420465644035872, 15.929468218813454, 9, 6.124204656440359),
            Block.box(13.990131781186548, 2.007110000000001, 11.741968218813454, 16.31513178118655, 17.007109999999997, 14.116968218813454),
            Block.box(-0.5098682188134518, 2.007110000000001, 11.741968218813454, 1.8151317811865475, 17.007109999999997, 14.116968218813454),
            Block.box(13.325918218813454, 5.007110000000001, 6.577754656440359, 15.325918218813454, 17.007110000000008, 8.577754656440359)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(9.805263562373096, 1, 0.053672875253813146, 15.805263562373096, 16, 9.053672875253813),
            Block.box(9.805263562373096, 1, 9.053672875253813, 15.805263562373096, 9, 16.053672875253813),
            Block.box(1.8125, 2.007110000000001, 14.114336437626907, 4.1875, 17.007109999999997, 16.43933643762691),
            Block.box(1.8125, 2.007110000000001, -0.38566356237309307, 4.1875, 17.007109999999997, 1.9393364376269062),
            Block.box(7.351713562373096, 5.007110000000001, 13.450122875253813, 9.351713562373096, 17.007110000000008, 15.450122875253813)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(6.875795343559641, 1, 9.929468218813454, 15.875795343559641, 16, 15.929468218813454),
            Block.box(-0.12420465644035872, 1, 9.929468218813454, 6.875795343559641, 9, 15.929468218813454),
            Block.box(-0.5098682188134553, 2.007110000000001, 1.9367046564403587, 1.8151317811865475, 17.007109999999997, 4.311704656440359),
            Block.box(13.990131781186548, 2.007110000000001, 1.9367046564403587, 16.315131781186547, 17.007109999999997, 4.311704656440359),
            Block.box(0.47934534355964153, 5.007110000000001, 7.4759182188134545, 2.4793453435596415, 17.007110000000008, 9.475918218813455)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public MinerMk1Back(Properties pProperties) {
        super(pProperties);
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
