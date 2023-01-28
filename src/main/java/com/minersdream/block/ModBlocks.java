package com.minersdream.block;

import com.minersdream.MinersDream;
import com.minersdream.block.custom.*;
import com.minersdream.item.ModCreativeModeTab;
import com.minersdream.item.ModItems;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import static org.openjdk.nashorn.api.tree.Parser.create;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MinersDream.MOD_ID);

    public static final RegistryObject<Block> ASNIUM_BLOCK = registerBlock("asnium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(2f).requiresCorrectToolForDrops()),ModCreativeModeTab.MinersDream_TAB);

    public static final RegistryObject<Block> ASNIUM_ORE = registerBlock("asnium_ore",
            () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(2f).requiresCorrectToolForDrops()),ModCreativeModeTab.MinersDream_TAB);

    public static final RegistryObject<Block> DEEPSLATE_ASNIUM_ORE = registerBlock("deepslate_asnium_ore",
            () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(2f).requiresCorrectToolForDrops()),ModCreativeModeTab.MinersDream_TAB);

    public static final RegistryObject<Block> ASPHALT = registerBlock("asphalt",
         () -> new SpeedyBlock(BlockBehaviour.Properties.of(Material.STONE)
                 .strength(1f).requiresCorrectToolForDrops()),ModCreativeModeTab.MinersDream_TAB, "tooltip.minersdream.asphalt");

    //Custom
    public static final RegistryObject<Block> BLOCK_TESTE = registerBlock("block_teste",
            () -> new BlockTeste(BlockBehaviour.Properties.of(Material.STONE).noOcclusion()
                    .strength(0.1f)),ModCreativeModeTab.MinersDream_TAB, "tooltip.minersdream.block_teste");

    public static final RegistryObject<Block> FENCE = registerBlock("fence",
            () -> new Fence(BlockBehaviour.Properties.of(Material.STONE).noOcclusion().dynamicShape()
                    .strength(0.1f)),ModCreativeModeTab.MinersDream_TAB, "tooltip.minersdream.fence");

    public static final RegistryObject<Block> FOUNDATION = registerBlock("foundation",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .strength(0.7f).requiresCorrectToolForDrops()),ModCreativeModeTab.MinersDream_TAB, "tooltip.minersdream.foundation");

    public static final RegistryObject<Block> MINER_ZERO = registerBlock("miner_zero",
            () -> new MinerZero(BlockBehaviour.Properties.of(Material.HEAVY_METAL).noOcclusion()
                    .strength(1.5f).requiresCorrectToolForDrops()),ModCreativeModeTab.MinersDream_TAB, "tooltip.minersdream.miner_zero");

    public static final RegistryObject<Block> MINER_MK1 = registerBlock("miner_mk1",
            () -> new MinerMK1(BlockBehaviour.Properties.of(Material.METAL).noOcclusion().dynamicShape()
                    .strength(1.5f)),ModCreativeModeTab.MinersDream_TAB, "tooltip.minersdream.miner_mk1");
    public static final RegistryObject<Block> OVERCLOCK = registerBlock("overclock",
            () -> new Overclock(BlockBehaviour.Properties.of(Material.GLASS).noOcclusion()
                    .strength(1.5f).lightLevel(LUZ -> {return 6;})),ModCreativeModeTab.MinersDream_TAB, "tooltip.minersdream.overclock");


    //Tooltip
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> blocks, CreativeModeTab tab, String tooltipKey){
        RegistryObject<T> toReturn = BLOCKS.register(name, blocks);
        registerBlockItem(name, toReturn, tab, tooltipKey);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab, String tooltipKey){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),  new Item.Properties().tab(tab)){
            @Override
            public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
                pTooltip.add(new TranslatableComponent("tooltip.minersdream.pressShift"));
                    if(Screen.hasShiftDown()) {
                        pTooltip.remove(1);
                        pTooltip.add(new TranslatableComponent(tooltipKey));
                }
            }
        });
    }
    //No Tooltip
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> blocks, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, blocks);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),  new Item.Properties().tab(tab)));
    }
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
