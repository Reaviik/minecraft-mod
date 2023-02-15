package com.minersdream.block.custom.furnace;

import com.minersdream.block.ModBlocks;
import com.minersdream.block.entity.custom.miners.MinerMK1BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;


@Mod.EventBusSubscriber
public class Furnace extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public Furnace(Properties proprieties) {
        super(proprieties);
    }

    //Collision Box//
    public static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 14, 15);


    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
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
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }



    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MinerMK1BlockEntity(pPos, pState);
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event)   {
        BlockPos pPos = event.getPos();
        BlockState pState = event.getState();
        LevelAccessor world = event.getWorld();

        if (hasAir(world, new BlockPos(pPos.getX(), pPos.getY() + 1, pPos.getZ()))) {
            execute(event, world, event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), pPos, pState);
        }
    }

    public static boolean hasAir(LevelAccessor world, BlockPos pPos) {
        return world.getBlockState(pPos).getBlock() == Blocks.AIR;
    }

    public static void setMultiblockBlock(LevelAccessor world, double x, double y, double z, Block pBlock, Rotation pRotation, int pFlag){
        if (hasAir(world, new BlockPos(x, y, z))) {
            world.setBlock(new BlockPos(x, y, z), pBlock.defaultBlockState().rotate(world,
                    new BlockPos(x, y, z), pRotation), pFlag);
            world.levelEvent(2001, new BlockPos(x, y, z), Block.getId(Blocks.CHORUS_FLOWER.defaultBlockState()));
        }
    }

    private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, BlockPos pPos, BlockState pState) {
        if ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == ModBlocks.FURNACE.get()) {
            if (pState.getValue(FACING) == Direction.EAST) {
                //
                setMultiblockBlock(world, x, y, z - 1, ModBlocks.FURNACE_INPUT.get(), Rotation.CLOCKWISE_90, 3);
                setMultiblockBlock(world, x, y, z + 1, ModBlocks.FURNACE_EJETOR.get(), Rotation.CLOCKWISE_90, 3);
                setMultiblockBlock(world, x, y + 1, z, ModBlocks.FURNACE_TUBES.get(), Rotation.CLOCKWISE_90, 3);
                setMultiblockBlock(world, x, y + 1, z + 1, ModBlocks.FURNACE_AQUECEDOR_PARTE1.get(), Rotation.CLOCKWISE_90, 3);
                setMultiblockBlock(world, x, y + 2, z + 1, ModBlocks.FURNACE_AQUECEDOR_PARTE2.get(), Rotation.CLOCKWISE_90, 3);


                world.setBlock(new BlockPos(x, y, z), ModBlocks.FURNACE_SMELTER.get().defaultBlockState().rotate(world,
                        new BlockPos(x, y, z), Rotation.CLOCKWISE_90), 3);
                world.levelEvent(2001, new BlockPos(x, y, z), Block.getId(Blocks.CHORUS_FLOWER.defaultBlockState()));
            }else if (pState.getValue(FACING) == Direction.SOUTH) {
                //
                setMultiblockBlock(world, x + 1, y, z, ModBlocks.FURNACE_INPUT.get(), Rotation.CLOCKWISE_180, 3);
                setMultiblockBlock(world, x - 1, y, z, ModBlocks.FURNACE_EJETOR.get(), Rotation.CLOCKWISE_180, 3);
                setMultiblockBlock(world, x , y + 1, z, ModBlocks.FURNACE_TUBES.get(), Rotation.CLOCKWISE_180, 3);
                setMultiblockBlock(world, x - 1, y + 1, z, ModBlocks.FURNACE_AQUECEDOR_PARTE1.get(), Rotation.CLOCKWISE_180, 3);
                setMultiblockBlock(world, x - 1, y + 2, z, ModBlocks.FURNACE_AQUECEDOR_PARTE2.get(), Rotation.CLOCKWISE_180, 3);


                world.setBlock(new BlockPos(x, y, z), ModBlocks.FURNACE_SMELTER.get().defaultBlockState().rotate(world,
                        new BlockPos(x, y, z), Rotation.CLOCKWISE_180), 3);
                world.levelEvent(2001, new BlockPos(x, y, z), Block.getId(Blocks.CHORUS_FLOWER.defaultBlockState()));
            }else if (pState.getValue(FACING) == Direction.WEST) {
                //
                setMultiblockBlock(world, x, y, z + 1, ModBlocks.FURNACE_INPUT.get(), Rotation.COUNTERCLOCKWISE_90, 3);
                setMultiblockBlock(world, x, y, z - 1, ModBlocks.FURNACE_EJETOR.get(), Rotation.COUNTERCLOCKWISE_90, 3);
                setMultiblockBlock(world, x, y + 1, z, ModBlocks.FURNACE_TUBES.get(), Rotation.COUNTERCLOCKWISE_90, 3);
                setMultiblockBlock(world, x, y + 1, z - 1, ModBlocks.FURNACE_AQUECEDOR_PARTE1.get(), Rotation.COUNTERCLOCKWISE_90, 3);
                setMultiblockBlock(world, x, y + 2, z - 1, ModBlocks.FURNACE_AQUECEDOR_PARTE2.get(), Rotation.COUNTERCLOCKWISE_90, 3);


                world.setBlock(new BlockPos(x, y, z), ModBlocks.FURNACE_SMELTER.get().defaultBlockState().rotate(world,
                        new BlockPos(x, y, z), Rotation.COUNTERCLOCKWISE_90), 3);
                world.levelEvent(2001, new BlockPos(x, y, z), Block.getId(Blocks.CHORUS_FLOWER.defaultBlockState()));
            }else{
                setMultiblockBlock(world, x - 1, y, z, ModBlocks.FURNACE_INPUT.get(), Rotation.NONE, 3);
                setMultiblockBlock(world, x + 1, y, z, ModBlocks.FURNACE_EJETOR.get(), Rotation.NONE, 3);
                setMultiblockBlock(world, x, y + 1, z, ModBlocks.FURNACE_TUBES.get(), Rotation.NONE, 3);
                setMultiblockBlock(world, x + 1, y + 1, z, ModBlocks.FURNACE_AQUECEDOR_PARTE1.get(), Rotation.NONE, 3);
                setMultiblockBlock(world, x + 1, y + 2, z, ModBlocks.FURNACE_AQUECEDOR_PARTE2.get(), Rotation.NONE, 3);


                world.setBlock(new BlockPos(x, y, z), ModBlocks.FURNACE_SMELTER.get().defaultBlockState().rotate(world,
                        new BlockPos(x, y, z), Rotation.NONE), 3);
                world.levelEvent(2001, new BlockPos(x, y, z), Block.getId(Blocks.CHORUS_FLOWER.defaultBlockState()));
            }
        }
    }
}