package com.minersdream.block.entity;

import com.minersdream.MinersDream;
import com.minersdream.block.ModBlocks;
import com.minersdream.block.entity.custom.BlockTesteEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MinersDream.MOD_ID);

    public static final RegistryObject<BlockEntityType<BlockTesteEntity>> BLOCK_TESTE_ENTITY =
            BLOCK_ENTITIES.register("block_teste_entity", () ->
                    BlockEntityType.Builder.of(BlockTesteEntity::new,
                            ModBlocks.BLOCK_TESTE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
