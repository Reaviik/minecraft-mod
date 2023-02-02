package com.minersdream.util;

import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.server.ServerLifecycleHooks;

public class SendMessage {
        public static void send(LevelAccessor world, String message) {
            if (!world.isClientSide()) {
                MinecraftServer _mcserv = ServerLifecycleHooks.getCurrentServer();
                if (_mcserv != null)
                    _mcserv.getPlayerList().broadcastMessage(new TextComponent(message), ChatType.SYSTEM, Util.NIL_UUID);
            }
        }
    }

