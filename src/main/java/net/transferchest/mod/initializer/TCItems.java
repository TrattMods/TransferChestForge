package net.transferchest.mod.initializer;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.transferchest.mod.block.TransferChestBlock;
import net.transferchest.mod.loader.TCLoader;

public class TCItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TCLoader.MOD_ID);
    
    public static final RegistryObject<BlockItem> TRANSFER_CHEST_ITEM = ITEMS.register(TransferChestBlock.ID,
          () -> new BlockItem(TCBlocks.TRANSFER_CHEST_BLOCK.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    
    public static void initialize()
    {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
