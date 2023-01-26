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


public class MinerZero extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(3, 8, 0, 13, 14, 1), Block.box(1, 4, 1, 15, 15, 15),
            Block.box(5, 2, 5, 11, 4, 11), Block.box(6, 1, 6, 10, 2, 10),
            Block.box(7, 0, 7, 9, 1, 9), Block.box(1, 15, 0, 15, 16, 1),
            Block.box(15, 0, 0, 16, 16, 1), Block.box(0, 0, 0, 1, 16, 1),
            Block.box(15, 0, 15, 16, 16, 16), Block.box(0, 0, 15, 1, 16, 16),
            Block.box(1, 15, 15, 15, 16, 16), Block.box(0, 15, 1, 1, 16, 15),
            Block.box(15, 15, 1, 16, 16, 15)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(15, 8, 3, 16, 14, 13), Block.box(1, 4, 1, 15, 15, 15),
            Block.box(5, 2, 5, 11, 4, 11), Block.box(6, 1, 6, 10, 2, 10),
            Block.box(7, 0, 7, 9, 1, 9), Block.box(15, 15, 1, 16, 16, 15),
            Block.box(15, 0, 15, 16, 16, 16), Block.box(15, 0, 0, 16, 16, 1),
            Block.box(0, 0, 15, 1, 16, 16), Block.box(0, 0, 0, 1, 16, 1),
            Block.box(0, 15, 1, 1, 16, 15), Block.box(1, 15, 0, 15, 16, 1),
            Block.box(1, 15, 15, 15, 16, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(3, 8, 15, 13, 14, 16), Block.box(1, 4, 1, 15, 15, 15),
            Block.box(5, 2, 5, 11, 4, 11), Block.box(6, 1, 6, 10, 2, 10),
            Block.box(7, 0, 7, 9, 1, 9), Block.box(1, 15, 15, 15, 16, 16),
            Block.box(0, 0, 15, 1, 16, 16), Block.box(15, 0, 15, 16, 16, 16),
            Block.box(0, 0, 0, 1, 16, 1), Block.box(15, 0, 0, 16, 16, 1),
            Block.box(1, 15, 0, 15, 16, 1), Block.box(15, 15, 1, 16, 16, 15),
            Block.box(0, 15, 1, 1, 16, 15)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(0, 8, 3, 1, 14, 13), Block.box(1, 4, 1, 15, 15, 15),
            Block.box(5, 2, 5, 11, 4, 11), Block.box(6, 1, 6, 10, 2, 10),
            Block.box(7, 0, 7, 9, 1, 9), Block.box(0, 15, 1, 1, 16, 15),
            Block.box(0, 0, 0, 1, 16, 1), Block.box(0, 0, 15, 1, 16, 16),
            Block.box(15, 0, 0, 16, 16, 1), Block.box(15, 0, 15, 16, 16, 16),
            Block.box(15, 15, 1, 16, 16, 15), Block.box(1, 15, 15, 15, 16, 16),
            Block.box(1, 15, 0, 15, 16, 1)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public MinerZero(Properties pProperties) {
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

    /* BLOCK ENTITY */

//    @Override
//    public RenderShape getRenderShape(BlockState pState) {
//        return RenderShape.MODEL;
//    }
//
//    @Override
//    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
//        if (pState.getBlock() != pNewState.getBlock()) {
//            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
//            if (blockEntity instanceof MinerZeroBlockEntity) {
//                ((MinerZeroBlockEntity) blockEntity).drops();
//            }
//        }
//        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
//    }
//
//    @Override
//    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
//        if (!pLevel.isClientSide()) {
//            BlockEntity entity = pLevel.getBlockEntity(pPos);
//            if (entity instanceof BlockTesteEntity) {
//                NetworkHooks.openGui(((ServerPlayer)pPlayer), (BlockTesteEntity)entity, pPos);
//            } else {
//                throw new IllegalStateException("Our Container provider is missing!");
//            }
//        }
//
//        return InteractionResult.sidedSuccess(pLevel.isClientSide());
//    }
//
//    @Nullable
//    @Override
//    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
//        return new MinerZeroBlockEntity(pPos, pState);
//    }
//
//    @Nullable
//    @Override
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level plevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
//        return createTickerHelper(pBlockEntityType, ModBlockEntities.MINER_ZERO_BLOCK_ENTITY.get(),
//                MinerZeroBlockEntity::tick);
//    }
}
