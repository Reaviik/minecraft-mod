package com.minersdream.block.entity;

import ca.weblite.objc.Proxy;
import com.minersdream.MinersDream;
import com.minersdream.block.ModBlocks;
import com.minersdream.block.entity.custom.BlockTesteEntity;
import com.minersdream.block.entity.custom.furnace.FurnaceEjectorBlockEntity;
import com.minersdream.block.entity.custom.furnace.FurnaceSmelterBlockEntity;
import com.minersdream.block.entity.custom.miners.MinerMK1BlockEntity;
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

    public static final RegistryObject<BlockEntityType<MinerMK1BlockEntity>> MINER_MK1_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("miner_mk1_block_entity", () ->
                    BlockEntityType.Builder.of(MinerMK1BlockEntity::new,
                            ModBlocks.MINER_MK1_MOTOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<FurnaceSmelterBlockEntity>> FURNACE_SMELTER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("furnace_smelter_block_entity", () ->
                    BlockEntityType.Builder.of(FurnaceSmelterBlockEntity::new,
                            ModBlocks.FURNACE_SMELTER.get()).build(null));
    public static final RegistryObject<BlockEntityType<FurnaceEjectorBlockEntity>> FURNACE_EJECTOR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("furnace_ejector_block_entity", () ->
                    BlockEntityType.Builder.of(FurnaceEjectorBlockEntity::new,
                            ModBlocks.FURNACE_SMELTER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
