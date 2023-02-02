/*
package com.minersdream.block.entity.custom;

import com.minersdream.block.ModBlocks;
import com.minersdream.block.custom.MinerMk1Broca;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import javax.annotation.Nullable;

@EventBusSubscriber
public class BrocaBreak extends MinerMk1Broca {
    public BrocaBreak(Properties pProperties) {
        super(pProperties);
    }
    public static boolean hasMinerBlock(LevelAccessor world, BlockPos pPos) {
        if (world.getBlockState(pPos).getBlock() != Blocks.AIR) {
            if(world.getBlockState(pPos).getBlock() == ModBlocks.MINER_MK1_BACK.get() ||
                world.getBlockState(pPos).getBlock() == ModBlocks.MINER_MK1_VACUM.get() ||
                world.getBlockState(pPos).getBlock() == ModBlocks.MINER_MK1_SEPARADOR.get()||
                world.getBlockState(pPos).getBlock() == ModBlocks.MINER_MK1_MOTOR.get() ||
                world.getBlockState(pPos).getBlock() == ModBlocks.MINER_MK1_PLATAFORMA.get() ||
                world.getBlockState(pPos).getBlock() == ModBlocks.MINER_MK1_GERENCIADOR_PARTE1.get() ||
                world.getBlockState(pPos).getBlock() == ModBlocks.MINER_MK1_GERENCIADOR_PARTE2.get()) {
                return true;
            }
        }
        return false;
    }
    public static void breack(LevelAccessor world, double x, double y, double z){
        if (hasMinerBlock(world, new BlockPos(x, y, z))) {
            world.destroyBlock(new BlockPos(x, y, z), false);
        }
    }
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        execute(event, event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getPos(), event.getState());
    }

    public static void execute(LevelAccessor world, double x, double y, double z, BlockPos pPos, BlockState pState) {
        execute(null, world, x, y, z, pPos, pState);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, BlockPos pPos, BlockState pState) {
            if (pState.getValue(FACING) == Direction.EAST) {
                breack(world, x, y, z - 1);
                breack(world, x, y, z + 1);
                breack(world, x, y, z + 2);
                breack(world, x, y + 1, z);
                breack(world, x, y + 2, z);
                breack(world, x, y + 3, z);
                breack(world, x, y + 4, z);
            }
            if (pState.getValue(FACING) == Direction.SOUTH) {
                breack(world, x + 1, y, z);
                breack(world, x - 1, y, z);
                breack(world, x - 2, y, z);
                breack(world, x, y + 1, z);
                breack(world, x, y + 2, z);
                breack(world, x, y + 3, z);
                breack(world, x, y + 4, z);
            }
            if (pState.getValue(FACING) == Direction.WEST) {
                breack(world, x, y, z + 1);
                breack(world, x, y, z - 1);
                breack(world, x, y, z - 2);
                breack(world, x, y + 1, z);
                breack(world, x, y + 2, z);
                breack(world, x, y + 3, z);
                breack(world, x, y + 4, z);
            }else {
                breack(world, x - 1, y, z);
                breack(world, x + 1, y, z);
                breack(world, x + 2, y, z);
                breack(world, x, y + 1, z);
                breack(world, x, y + 2, z);
                breack(world, x, y + 3, z);
                breack(world, x, y + 4, z);
            }
        }
    }
    */