package net.transferchest.mod.network;


import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.management.PlayerList;
import net.transferchest.mod.network.packet.TransferChestWatchersS2CPacket;

import java.util.List;

public class NetworkHandler
{
    public static void sendToAll(TransferChestWatchersS2CPacket packet, PlayerList list)
    {
        List<ServerPlayerEntity> targets = list.getPlayers();
        for(ServerPlayerEntity target : targets)
        {
            packet.sendTo(target);
        }
    }
}
