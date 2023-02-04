package com.minersdream;

import com.minersdream.block.ModBlocks;
import com.minersdream.block.entity.ModBlockEntities;
import com.minersdream.block.screen.BlockTeste.BlockTesteScreen;
import com.minersdream.block.screen.MinerMK1.MinerMK1Screen;
import com.minersdream.block.screen.ModMenuTypes;
import com.minersdream.recipe.ModRecipes;
import com.mojang.logging.LogUtils;
import com.minersdream.item.ModItems;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("minersdream")
public class MinersDream {

    // Directly reference a slf4j logger
    public static final String MOD_ID = "minersdream";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MinersDream() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }
    public void clientSetup(final FMLClientSetupEvent event){
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLOCK_TESTE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.FENCE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MINER_ZERO.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.OVERCLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ASNIUM_LAMP.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MINER_MK1.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MINER_MK1_SEPARADOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MINER_MK1_VACUM.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MINER_MK1_BROCA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MINER_MK1_BACK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MINER_MK1_MOTOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MINER_MK1_PLATAFORMA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MINER_MK1_GERENCIADOR_PARTE1.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MINER_MK1_GERENCIADOR_PARTE2.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.IRON_RESOURCE_NODE.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.HORIZONTAL_CONVEIOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DIAGONAL_CONVEIOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CONER_CONVEIOR.get(), RenderType.translucent());

        MenuScreens.register(ModMenuTypes.BLOCK_TESTE_MENU.get(), BlockTesteScreen::new);
        MenuScreens.register(ModMenuTypes.MINER_MK1_MENU.get(), MinerMK1Screen::new);
    }
    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("My first mod " + MinersDream.MOD_ID);
        //LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // Some example code to dispatch IMC to another mod
        //InterModComms.sendTo("MD_mods", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // Some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("Server Starting With " + MinersDream.MOD_ID);
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
        {
            // Register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
