package net.transferchest.mod.loader;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkDirection;
import net.transferchest.mod.api.Serializer;
import net.transferchest.mod.core.SerializableInventory;
import net.transferchest.mod.core.TransferChestHandler;
import net.transferchest.mod.initializer.*;
import net.transferchest.mod.network.packet.TransferChestWatchersS2CPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(TCLoader.MOD_ID)
public class TCLoader
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "transferchestforge";
    
    public TCLoader()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::commonSetup);
        
        TCBlocks.initialize();
        TCItems.initialize();
        TCEntities.initialize();
        TCContainers.initialize();
    
        MinecraftForge.EVENT_BUS.addListener(this::onStart);
        MinecraftForge.EVENT_BUS.addListener(this::onStop);
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    public void commonSetup(FMLCommonSetupEvent event)
    {
        int messageNumber = 0;
        TCNetwork.channel.messageBuilder(TransferChestWatchersS2CPacket.class, messageNumber++, NetworkDirection.PLAY_TO_CLIENT).encoder(TransferChestWatchersS2CPacket::write).decoder(TransferChestWatchersS2CPacket::new).consumer(TransferChestWatchersS2CPacket::handle).add();
    }
    
    @SubscribeEvent
    public void onStart(FMLServerStartingEvent startingEvent)
    {
        TransferChestHandler.initialize();
        SerializableInventory obj = Serializer.deserialize(startingEvent.getServer().getServerConfiguration().getWorldName());
        if(obj != null)
        {
            TransferChestHandler.buildInventory(obj);
        }
        LOGGER.info("DESERIALIZING");
        TransferChestHandler.printStatus();
    }
    
    @SubscribeEvent
    public void onStop(FMLServerStoppingEvent stoppingEvent)
    {
        LOGGER.info("SERIALIZING");
        TransferChestHandler.printStatus();
        SerializableInventory inventory = TransferChestHandler.getSerializableInventory();
        Serializer.serialize(inventory, stoppingEvent.getServer().getServerConfiguration().getWorldName());
    }
}
