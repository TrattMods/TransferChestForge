package net.transferchest.mod.initializer;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.transferchest.mod.block.TransferChestBlock;
import net.transferchest.mod.entity.TransferChestTileEntity;
import net.transferchest.mod.loader.TCLoader;

public class TCEntities
{
    public static final DeferredRegister<TileEntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, TCLoader.MOD_ID);
    
    public static final RegistryObject<TileEntityType<TransferChestTileEntity>> TRANSFER_CHEST_TILE_ENTITY = ENTITIES.register(TransferChestBlock.ID,
          () -> TileEntityType.Builder.create(TransferChestTileEntity::new, TCBlocks.TRANSFER_CHEST_BLOCK.get()).build(null));
    
    public static void initialize()
    {
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
