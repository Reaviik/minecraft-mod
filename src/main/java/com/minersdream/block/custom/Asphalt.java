package com.minersdream.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class Asphalt extends Block {
        public Asphalt(Properties properties) {
            super(properties);
        }
        @Override
        public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
            if(!pLevel.isClientSide()){
                if(pEntity instanceof LivingEntity){
                    LivingEntity livingEntity = ((LivingEntity)pEntity);
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 1, false, false, false));
                }
            }
            super.stepOn(pLevel, pPos, pState, pEntity);
        }
}
