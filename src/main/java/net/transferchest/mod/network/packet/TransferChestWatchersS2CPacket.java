package net.transferchest.mod.network.packet;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import net.transferchest.mod.gui.TransferChestContainer;
import net.transferchest.mod.initializer.TCNetwork;

import java.util.function.Supplier;

public class TransferChestWatchersS2CPacket
{
    private String[] names;
    private int namesLength;
    
    public TransferChestWatchersS2CPacket(PacketBuffer buffer)
    {
        read(buffer);
    }
    
    public TransferChestWatchersS2CPacket(String[] names)
    {
        this.names = names;
        this.namesLength = names.length;
    }
    
    
    public void sendTo(PlayerEntity player)
    {
        PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
        write(buf);
        TCNetwork.channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), this);
        //ServerPlayNetworking.send((ServerPlayerEntity) player, PACKET_ID, buf);
        //ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, PACKET_ID, buf);
    }
    
    public boolean handle(Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() ->
        {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            Container handler = player.openContainer;
            if(handler != null)
            {
                if(handler instanceof TransferChestContainer)
                {
                    TransferChestContainer tcHandler = (TransferChestContainer) handler;
                    //tcHandler.updatePanelEntries(names);
                }
            }
        });
        return true;
    }
    
    
    public void write(PacketBuffer buf)
    {
        buf.writeInt(namesLength);
        for(String name : names)
        {
            buf.writeString(name);
        }
    }
    
    public TransferChestWatchersS2CPacket read(PacketBuffer buf)
    {
        int length = buf.readInt();
        String[] names = new String[length];
        for(int i = 0; i < length; i++)
        {
            names[i] = buf.readString();
        }
        return new TransferChestWatchersS2CPacket(names);
    }
}
