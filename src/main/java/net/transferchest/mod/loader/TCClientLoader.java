package net.transferchest.mod.loader;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.transferchest.mod.gui.Screen.TransferChestBlockScreen;
import net.transferchest.mod.initializer.TCContainers;

@Mod.EventBusSubscriber(modid = TCLoader.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCClientLoader
{
    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event)
    {
        ScreenManager.registerFactory(TCContainers.TRANSFER_CHEST_CONTAINER.get(), TransferChestBlockScreen::new);
    }
}
