package com.minersdream.block.custom;

import com.minersdream.MinersDream;
import com.minersdream.block.ModBlocks;
import com.minersdream.block.entity.ModBlockEntities;
import com.minersdream.block.entity.custom.MinerMK1BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;


@Mod.EventBusSubscriber
public class MinerMK1  extends BaseEntityBlock{ // APAGA A LUZ APAGA TUDO QUE ISSO AMORRR SEI COMO RESOLVER SEUS PROBLEMAS, LIGUE JÁ PARA HAHAHA ERROU
    // https://www.youtube.com/watch?v=7zjJZrM3q_8 CHAAAMAAA
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    //Replace final .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public MinerMK1(Properties proprieties) {
        super(proprieties);
    }

    //Collision Box//
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);


    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    //Facing//
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

    /* BLOCK ENTITY */

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof MinerMK1BlockEntity) {
                ((MinerMK1BlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof MinerMK1BlockEntity) {
                NetworkHooks.openGui(((ServerPlayer)pPlayer), (MinerMK1BlockEntity) entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MinerMK1BlockEntity(pPos, pState);
    }
        @SubscribeEvent
        public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
            execute(event, event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
        }

        public static void execute(LevelAccessor world, double x, double y, double z) {
            execute(null, world, x, y, z);
        }

        private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z) {
        //CAPETA DE JAVA, VAI TOMA NO CU, Tu tambem, mas com carinho.
          /*if ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == ModBlocks.MINER_MK1.get()) {
                    switch (world.getBlockState(new BlockPos(x, y, z)).getValue(FACING)) {
                        case EAST:
                            world.setBlock(new BlockPos(x, y, z), ModBlocks.MINER_MK1_BACK.get().rotate(pState, world, pPos, Rotation.CLOCKWISE_90), 0);
                        case SOUTH:
                            world.setBlock(new BlockPos(x, y, z), ModBlocks.MINER_MK1_BACK.get().rotate(pState, world, pPos, Rotation.CLOCKWISE_180), 0);
                        case WEST:
                            world.setBlock(new BlockPos(x, y, z), ModBlocks.MINER_MK1_BACK.get().rotate(pState, world, pPos, Rotation.COUNTERCLOCKWISE_90), 0);
                        default:
                            world.setBlock(new BlockPos(x, y, z), ModBlocks.MINER_MK1_BACK.get().rotate(pState, world, pPos, Rotation.NONE), 0);
                    }
                }
            */
            }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level plevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.MINER_MK1_BLOCK_ENTITY.get(),
                MinerMK1BlockEntity::tick);
    }
}