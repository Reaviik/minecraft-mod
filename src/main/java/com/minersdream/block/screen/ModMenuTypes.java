package com.minersdream.block.screen;

import com.minersdream.MinersDream;
import com.minersdream.block.screen.BlockTeste.BlockTesteMenu;
import com.minersdream.block.screen.MinerMK1.MinerMK1Menu;
import com.minersdream.block.screen.furnace.FurnaceSmelterMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, MinersDream.MOD_ID);

    public static final RegistryObject<MenuType<BlockTesteMenu>> BLOCK_TESTE_MENU =
            registerMenuType(BlockTesteMenu::new, "block_teste_menu");

    public static final RegistryObject<MenuType<MinerMK1Menu>> MINER_MK1_MENU =
            registerMenuType(MinerMK1Menu::new, "miner_mk1_menu");
    public static final RegistryObject<MenuType<FurnaceSmelterMenu>> FURNACE_SMELTER_MENU =
            registerMenuType(FurnaceSmelterMenu::new, "furnace_smelter_menu");




    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                 String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
