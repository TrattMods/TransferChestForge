package net.transferchest.mod.initializer;


import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.transferchest.mod.block.TransferChestBlock;
import net.transferchest.mod.loader.TCLoader;

public class TCBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TCLoader.MOD_ID);
    
    public static final RegistryObject<Block> TRANSFER_CHEST_BLOCK = BLOCKS.register(TransferChestBlock.ID, TransferChestBlock::new);
    
    public static void initialize()
    {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
