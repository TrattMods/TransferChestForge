package net.transferchest.mod.initializer;

import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.transferchest.mod.block.TransferChestBlock;

public final class TCSounds
{
    public static SoundEvent TRANSFER_CHEST_OPEN_SOUNDEVENT = new SoundEvent(TransferChestBlock.OPEN_SOUND);
    public static SoundEvent TRANSFER_CHEST_CLOSE_SOUNDEVENT = new SoundEvent(TransferChestBlock.CLOSE_SOUND);
    
    
    public static void initialize()
    {
        Registry.register(Registry.SOUND_EVENT, TransferChestBlock.OPEN_SOUND, TRANSFER_CHEST_OPEN_SOUNDEVENT);
        Registry.register(Registry.SOUND_EVENT, TransferChestBlock.CLOSE_SOUND, TRANSFER_CHEST_CLOSE_SOUNDEVENT);
    }
}
