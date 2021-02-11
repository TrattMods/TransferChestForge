package net.transferchest.mod.initializer;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.transferchest.mod.block.TransferChestBlock;
import net.transferchest.mod.loader.TCLoader;

public final class TCSounds
{
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TCLoader.MOD_ID);
    public static final RegistryObject<SoundEvent> TRANSFER_CHEST_OPEN_SOUNDEVENT = SOUNDS.register(TransferChestBlock.OPEN_SOUND.getPath(),()->new SoundEvent(TransferChestBlock.OPEN_SOUND));
    public static final RegistryObject<SoundEvent> TRANSFER_CHEST_CLOSE_SOUNDEVENT = SOUNDS.register(TransferChestBlock.CLOSE_SOUND.getPath(),()->new SoundEvent(TransferChestBlock.CLOSE_SOUND));
    
    
    public static void initialize()
    {
        SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        //Registry.register(Registry.SOUND_EVENT, TransferChestBlock.OPEN_SOUND, TRANSFER_CHEST_OPEN_SOUNDEVENT);
        //Registry.register(Registry.SOUND_EVENT, TransferChestBlock.CLOSE_SOUND, TRANSFER_CHEST_CLOSE_SOUNDEVENT);
    }
}
