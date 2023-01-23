package com.minersdream.block;

import com.minersdream.MinersDream;
import com.minersdream.item.ModCreativeModeTab;
import com.minersdream.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCK =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MinersDream.MOD_ID);

    public static final RegistryObject<Block> ETERIUM_BLOCK = registerBlock("eterium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(1f).requiresCorrectToolForDrops()),ModCreativeModeTab.MinersDream_TAB);

    public static final RegistryObject<Block> ETERIUM_ORE = registerBlock("eterium_ore",
            () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(0.5f).requiresCorrectToolForDrops()),ModCreativeModeTab.MinersDream_TAB);

    public static final RegistryObject<Block> DEEPSLATE_ETERIUM_ORE = registerBlock("deepslate_eterium_ore",
            () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(0.6f).requiresCorrectToolForDrops()),ModCreativeModeTab.MinersDream_TAB);


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> blocks, CreativeModeTab tab){
    RegistryObject<T> toReturn = BLOCK.register(name, blocks);
    registerBlockItem(name, toReturn, tab);
    return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),  new Item.Properties().tab(tab)));
    }
    public static void register(IEventBus eventBus){
        BLOCK.register(eventBus);
    }
}
