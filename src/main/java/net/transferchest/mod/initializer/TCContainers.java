package net.transferchest.mod.initializer;


import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.transferchest.mod.block.TransferChestBlock;
import net.transferchest.mod.gui.TransferChestContainer;
import net.transferchest.mod.loader.TCLoader;

public final class TCContainers
{
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, TCLoader.MOD_ID);
    
    public static final RegistryObject<ContainerType<TransferChestContainer>> TRANSFER_CHEST_CONTAINER = CONTAINERS.register(TransferChestBlock.ID,
          () -> IForgeContainerType.create((windowId, inv, data) ->
          {
              BlockPos pos = data.readBlockPos();
              World world = inv.player.getEntityWorld();
              return new TransferChestContainer(windowId, world, pos, inv, inv.player);
          }));
    
    public static void initialize()
    {
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
