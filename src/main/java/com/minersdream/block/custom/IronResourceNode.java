package com.minersdream.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class IronResourceNode extends Block {
        public IronResourceNode(Properties properties) {
            super(properties);
        }
        @Override
        public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
            super.stepOn(pLevel, pPos, pState, pEntity);
        }
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
        return SHAPE;
    }
}
