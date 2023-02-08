package com.minersdream.block.screen.MinerMK1;

import com.minersdream.block.ModBlocks;
import com.minersdream.block.entity.custom.MinerMK1BlockEntity;
import com.minersdream.block.screen.ModMenuTypes;
import com.minersdream.block.screen.slot.ModResultSlot;
import com.minersdream.block.screen.slot.ModUpgradeSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;

public class MinerMK1Menu extends AbstractContainerMenu {
    private final MinerMK1BlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public MinerMK1Menu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }
    //Responsavel por setar a posição dos slots
    public MinerMK1Menu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.MINER_MK1_MENU.get(), pContainerId);
        checkContainerSize(inv, 4);
        blockEntity = ((MinerMK1BlockEntity) entity);
        this.level = inv.player.level;
        this.data = data;
        //Não sei
        addPlayerInventory(inv);
        addPlayerHotbar(inv);
        //Seta a posição dos slots
        ResourceLocation bc = new ResourceLocation("minersdream:textures/slot/output_slot.png");
        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            this.addSlot(new ModResultSlot(handler, 0, 94, -10));
                //Slot 0 slot de saida
                //Slots 1,2,3 slots de upgrade
            this.addSlot(new ModUpgradeSlot(handler, 1, 73, 36));
            this.addSlot(new ModUpgradeSlot(handler, 2, 107, 36));
            this.addSlot(new ModUpgradeSlot(handler, 3, 141, 36));
        });
    }
    //Matematica muito loca que diz quando o bloco esta craftando algo
    public boolean isCrafting() {
        return data.get(0) > 0;
    }
    //Não sei exatamente
    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 26;

        return maxProgress !=0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    // CRÉDITO VAI PARA: diesieben07 | https://github.com/diesieben07/SevenCommons
    // deve atribuir um número de slot a cada um dos slots usados pela GUI.
    // Para este contêiner, podemos ver tanto os slots do inventário de blocos quanto os slots do inventário do jogador e a barra de acesso.
    // Cada vez que adicionamos um Slot ao container, ele aumenta automaticamente o slotIndex, o que significa
    // 0 - 8 = slots de hotbar (que serão mapeados para os números de slot do InventoryPlayer 0 - 8)
    // 9 - 35 = espaços de inventário do jogador (que mapeiam para os números de espaço do InventoryPlayer 9 - 35)
    // 36 - 44 = slots TileInventory, que mapeiam para nossos números de slot TileEntity 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    //DEFINA ISSO PELO AMOR DE DEUS!
    private static final int TE_INVENTORY_SLOT_COUNT = 4;  // Numero de slots que o bloco tem!!!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Verifique se o slot clicado é um dos slots do contêiner
        //Não sei '-'
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            //Não sei '-'
            // Este é um slot de contêiner vanilla, então mescle a pilha no inventário de blocos
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            //Não sei '-'
            // Este é um slot TE, então mescle a pilha no inventário do jogador
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        //Praque isso serve?
        // Se o tamanho da pilha == 0 (toda a pilha foi movida), defina o conteúdo do slot como nulo
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            //I dont undestand Weee
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.MINER_MK1_MOTOR.get());
    }
    //Faz 0 sentido pra min, porque ta adicionando slota ao inventario do player? e o meu inventario? só o player ganha slot extra?
    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot((new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 85 + i * 18)));
            }
        }
    }
    //Desentendo completamente, mas denove, bah meu o player, adiciona no meu tambem
    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 143));
        }
    }


}
