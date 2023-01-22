package com.rani.mod.item;

import com.rani.mod.MinersDream;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MinersDream.MOD_ID);

    public static final RegistryObject<Item> ETERIUM = ITEMS.register("eterium",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MinersDream_TAB)));
    public static final RegistryObject<Item> RAW_ETERIUM = ITEMS.register("raw_eterium",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MinersDream_TAB)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
