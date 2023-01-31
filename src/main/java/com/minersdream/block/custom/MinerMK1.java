package com.minersdream.block.custom;

import com.minersdream.MinersDream;
import com.minersdream.block.ModBlocks;
import com.minersdream.block.entity.ModBlockEntities;
import com.minersdream.block.entity.custom.MinerMK1BlockEntity;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.state.properties.Property;
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
import org.slf4j.Logger;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;


@Mod.EventBusSubscriber
public class MinerMK1  extends BaseEntityBlock { // APAGA A LUZ APAGA TUDO QUE ISSO AMORRR SEI COMO RESOLVER SEUS PROBLEMAS, LIGUE JÁ PARA HAHAHA ERROU
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
        //IMPORTANT
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
                NetworkHooks.openGui(((ServerPlayer) pPlayer), (MinerMK1BlockEntity) entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MinerMK1BlockEntity(pPos, pState);
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        BlockPos pPos = event.getPos();
        BlockState pState = event.getState();

        execute(event, event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), pPos, pState);
    }

//        public static void execute(LevelAccessor world, double x, double y, double z) {
//            execute(null, world, x, y, z);
//        }
    public void setMinerMk1(LevelAccessor world, double x, double y, double z, Object block,Rotation rot, int flag){
        world.setBlock(new BlockPos(x , y, z), block.get().defaultBlockState().rotate( world,
                new BlockPos(x, y, z + 1);
   }

    private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, BlockPos pPos, BlockState pState) {
        //CAPETA DE JAVA, VAI TOMA NO CU, Tu tambem, mas com carinho.
        if ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == ModBlocks.MINER_MK1.get()) {
            LOGGER.info(String.valueOf(pState.getValue(FACING)));
            if (pState.getValue(FACING) == Direction.EAST) {
                world.setBlock(new BlockPos(x, y, z - 1), ModBlocks.MINER_MK1_BACK.get().defaultBlockState().rotate( world,
                        new BlockPos(x, y, z - 1), Rotation.CLOCKWISE_90), 3);
                LOGGER.info(String.valueOf(pState.getValue(FACING)));

            }else if (pState.getValue(FACING) == Direction.SOUTH) {
                    world.setBlock(new BlockPos(x + 1, y, z), ModBlocks.MINER_MK1_BACK.get().defaultBlockState().rotate( world,
                            new BlockPos(x + 1, y, z), Rotation.CLOCKWISE_180), 3);
                    LOGGER.info(String.valueOf(pState.getValue(FACING)));

            }else if (pState.getValue(FACING) == Direction.WEST) {
                    world.setBlock(new BlockPos(x, y, z + 1), ModBlocks.MINER_MK1_BACK.get().defaultBlockState().rotate( world,
                            new BlockPos(x, y, z + 1), Rotation.COUNTERCLOCKWISE_90), 3);
                    LOGGER.info(String.valueOf(pState.getValue(FACING)));
            }else{
                    world.setBlock(new BlockPos(x - 1, y, z), ModBlocks.MINER_MK1_BACK.get().defaultBlockState().rotate( world,
                            new BlockPos(x - 1, y, z), Rotation.NONE), 3);
                    LOGGER.info(String.valueOf(pState.getValue(FACING)));
                }
//                  default:
//                      world.setBlock(new BlockPos(x, y, z-1), ModBlocks.ASNIUM_BLOCK.get().rotate(ModBlocks.ASNIUM_BLOCK.get().defaultBlockState(), world,
//                              new BlockPos(x, y, z-1), Rotation.CLOCKWISE_180), 3);
            }
        }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level plevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.MINER_MK1_BLOCK_ENTITY.get(),
                MinerMK1BlockEntity::tick);
    }
}

//        public void placeStructure (LevelAccessor world, BlockState pState, BlockPos pPos){
//            switch (pState.getValue(FACING)) {
//                case EAST:
//                    world.setBlock(new BlockPos(pPos.getX(), pPos.getY(), pPos.getZ()), ModBlocks.MINER_MK1_BACK.get().rotate(pState, Rotation.CLOCKWISE_90), 3);
//                case SOUTH:
//                    world.setBlock(new BlockPos(pPos.getX(), pPos.getY(), pPos.getZ()), ModBlocks.MINER_MK1_BACK.get().rotate(pState, Rotation.CLOCKWISE_180), 3);
//                case WEST:
//                    world.setBlock(new BlockPos(pPos.getX(), pPos.getY(), pPos.getZ()), ModBlocks.MINER_MK1_BACK.get().rotate(pState, Rotation.COUNTERCLOCKWISE_90), 3);
//                default:
//                    world.setBlock(new BlockPos(pPos.getX(), pPos.getY(), pPos.getZ()), ModBlocks.MINER_MK1_BACK.get().rotate(pState, Rotation.NONE), 3);
//            }
//        }
//    }