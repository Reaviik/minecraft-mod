package com.minersdream.item.custom;

import com.minersdream.util.ITags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import static net.minecraft.sounds.SoundSource.BLOCKS;


public class LocateRod extends Item {
    public LocateRod(Properties pProperties) {
        super(pProperties);
    }
    int distance = 32;

        public static boolean verifyTags(ItemStack item) {
            return item.is(ITags.Items.RESOURCE_NODES);
        }
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getLevel().isClientSide()) {
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            LevelAccessor pLevel = pContext.getLevel();
            //Loop tridimensional, transdimensional, anal reverso de polaridade oposta
        for (int x = positionClicked.getX() - distance; x <= positionClicked.getX() + distance; x++) {
                for (int y = positionClicked.getY() - distance; y <= positionClicked.getY() + distance; y++) {
                    for (int z = positionClicked.getZ() - distance; z <= positionClicked.getZ() + distance; z++) {
                        Block blockBelow = pContext.getLevel().getBlockState(new BlockPos(x, y, z)).getBlock();
                        if (verifyTags(new ItemStack(blockBelow))) {
                            outputValuableCoordinates(new BlockPos(x, y, z), player, blockBelow, pLevel);
                            break;
                        }
                    }
                }
            }
        }
        //da 1 de dano no item Weeeee
        //pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
               // (player) -> player.broadcastBreakEvent(player.getUsedItemHand()));

        return super.useOn(pContext);
    }

    private void outputValuableCoordinates(BlockPos blockPos, Player player, Block blockBelow, LevelAccessor pLevel) {
        //distância euclidiana
        double distance = Math.sqrt(Math.pow(player.getX() - blockPos.getX(), 2) +
                Math.pow(player.getY() - blockPos.getY(), 2) +
                Math.pow(player.getZ() - blockPos.getZ(), 2));

        int distanceRounded = (int) Math.round(distance);
        float inverseDistanceRounded = 3f - (float) distanceRounded / 32f;
        player.sendMessage(new TextComponent( "" + (float) Math.round(inverseDistanceRounded * 10 )/10), player.getUUID());
        pLevel.playSound(player, new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()), new SoundEvent(new ResourceLocation("minecraft:block.amethyst_block.hit")), BLOCKS, inverseDistanceRounded, 1);
        pLevel.levelEvent(2001, new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()), Block.getId(Blocks.AMETHYST_BLOCK.defaultBlockState()));

    }
}