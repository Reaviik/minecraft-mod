package com.minersdream.block.screen.MinerMK1;

import com.minersdream.MinersDream;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MinerMK1Screen extends AbstractContainerScreen<MinerMK1Menu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MinersDream.MOD_ID, "textures/gui/miner_gui.png");

    public MinerMK1Screen(MinerMK1Menu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.imageWidth = 256-80;
        this.imageHeight = 256+18;
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;



        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

//        if(menu.isCrafting()) {
//            blit(pPoseStack, x + 102, y + 41, 176, 0, 8, menu.getScaledProgress());
//        }
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }
}
