package com.minersdream.item;

import com.minersdream.MinersDream;
import com.minersdream.item.custom.Coal;
import com.minersdream.item.custom.EteriumPickaxe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MinersDream.MOD_ID);


    //BASIC ITEMS
    public static final RegistryObject<Item> ETERIUM_INGOT = ITEMS.register("eterium_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MinersDream_TAB).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> RAW_ETERIUM = ITEMS.register("raw_eterium",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MinersDream_TAB)));

    // TOOLS
    public static final RegistryObject<Item> ETERIUM_PICKAXE = ITEMS.register("eterium_pickaxe",
            () -> new EteriumPickaxe(ModTiers.ETERIUM, 1, 1.0f,
                    new Item.Properties().tab(ModCreativeModeTab.MinersDream_TAB).rarity(Rarity.EPIC)));

    // FOODS
    public static final RegistryObject<Item> BERYL_NUT = ITEMS.register("beryl_nut",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MinersDream_TAB).food(ModFoods.BERYL_NUT).stacksTo(128)
                    .rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> PALEBERRY= ITEMS.register("paleberry",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MinersDream_TAB).food(ModFoods.BERYL_NUT).stacksTo(128)
                    .rarity(Rarity.UNCOMMON)));

    // FUEL
    public static final RegistryObject<Item> COAL = ITEMS.register("coal",
            () -> new Coal(new Item.Properties().tab(ModCreativeModeTab.MinersDream_TAB).rarity(Rarity.UNCOMMON)));



    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
