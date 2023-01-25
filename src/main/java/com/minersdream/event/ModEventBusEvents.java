package com.minersdream.event;

import com.minersdream.MinersDream;
import com.minersdream.event.loot.BerylFromGrassAdditionModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = MinersDream.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>>
                                                               event) {
        event.getRegistry().registerAll(
                new BerylFromGrassAdditionModifier.Serializer().setRegistryName
                        (new ResourceLocation(MinersDream.MOD_ID, "beryl_from_grass"))
        );
    }
}
