package com.minersdream.item.custom;

import com.minersdream.util.ITags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;


public class LocateRod extends Item {
    public LocateRod(Properties pProperties) {
        super(pProperties);
    }


    public static boolean verifyTags(ItemStack item) {
        return item.is(ITags.Items.RESOURCE_NODES);
    }
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(pContext.getLevel().isClientSide()) {
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            LevelAccessor pLevel = pContext.getLevel();
            boolean foundBlock = false;
            //Loop tridimensional, transdimensional, anal reverso de polaridade oposta
            for(int x = positionClicked.getX() - 32; x <= positionClicked.getX() + 32; x++) {
                for(int y = positionClicked.getY() - 32; y <= positionClicked.getY() + 32; y++) {
                    for(int z = positionClicked.getZ() - 32; z <= positionClicked.getZ() + 32; z++) {
                        Block blockBelow = pContext.getLevel().getBlockState(new BlockPos(x, y, z)).getBlock();
                        if(verifyTags(new ItemStack(blockBelow, 1))) {
                            outputValuableCoordinates(new BlockPos(x, y, z), player, blockBelow, pLevel);
                            foundBlock = true;
                            break;
                        }
                    }
                }
            }

            if(!foundBlock) {
                player.sendMessage(new TranslatableComponent("item.minersdream:locate_rod.notfound"),
                        player.getUUID());
            }
        }
        //da 1 de dano no item Weeeee
        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
                (player) -> player.broadcastBreakEvent(player.getUsedItemHand()));

        return super.useOn(pContext);
    }


    private void outputValuableCoordinates(BlockPos blockPos, Player player, Block blockBelow, LevelAccessor pLevel) {
        //distância euclidiana
        double distance = Math.sqrt(Math.pow(player.getX() - blockPos.getX(), 2) +
                Math.pow(player.getY() - blockPos.getY(), 2) +
                Math.pow(player.getZ() - blockPos.getZ(), 2));

        int distanceRounded = (int) Math.round(distance);
        player.sendMessage(new TextComponent( "" + distanceRounded), player.getUUID());
        pLevel.levelEvent(2001, new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()), Block.getId(Blocks.AMETHYST_BLOCK.defaultBlockState()));

        //oldMessage Bi The Ibagems Abilton
        //player.sendMessage(new TextComponent("Found " + blockBelow.asItem().getRegistryName().toString() + " at " +
                //"(" + blockPos.getX() + ", " + blockPos.getY() + "," + blockPos.getZ() + ")"), player.getUUID());
    }
}