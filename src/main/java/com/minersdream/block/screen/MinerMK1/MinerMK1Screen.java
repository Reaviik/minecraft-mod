package com.minersdream.block.screen.MinerMK1;

import com.minersdream.MinersDream;
import com.minersdream.block.entity.custom.MinerMK1BlockEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.LevelAccessor;

public class MinerMK1Screen extends AbstractContainerScreen<MinerMK1Menu> {

    //O caminho até a textura da GUI é setado aqui
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MinersDream.MOD_ID, "textures/gui/miner_gui.png");
    //Define a Scream
    public MinerMK1Screen(MinerMK1Menu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }
    //Renderiza a GUI na posião x, y, com base no tanahdo da imagem e da tela
    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        //Posições definir o x 0 e y 0 da GUI
        this.imageHeight = 221;
        int x = (width - imageWidth) / 2;
        int y = (height - (imageHeight + 55)) / 2;
        //Renderiza a GUI
        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
        //Teoricamente renderiza uma barra de progreço se isCrafting for true
        //TODO >> Fazer essa barra funcionar
        if(menu.isCrafting()) {
            y = (height - imageHeight) / 2;
            blit(pPoseStack, x + 102, y + 41, 8, 0, 8, menu.getScaledProgress());
        }
    }

    //Ainda não sei, pergunta pro player
    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }
}
