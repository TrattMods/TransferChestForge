package net.transferchest.mod.network.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;


public abstract class CustomS2CPacket
{
    
    public CustomS2CPacket()
    {
    }
    
    public abstract void sendTo(PlayerEntity player);
    
    protected abstract void onReceive(Minecraft ctx);
    
    public abstract void write(PacketBuffer buf);
}
