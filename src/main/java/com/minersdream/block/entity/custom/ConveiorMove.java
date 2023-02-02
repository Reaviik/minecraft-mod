package com.minersdream.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;

public class ConveiorMove {

    public static void moveItems(Entity pEntity, BlockPos pPos, Level pLevel, double px, double py, double pz, double mx, double my, double mz){

        pEntity.setXRot(0);
        pEntity.setYRot(0);
        ((ItemEntity) pEntity).setPickUpDelay(10);

        pEntity.setPos(px, py, pz);

        pEntity.moveTo(mx, my, mz);

    }
}
