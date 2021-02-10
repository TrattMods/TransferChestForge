package net.transferchest.mod.initializer;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.transferchest.mod.loader.TCLoader;

public class TCNetwork
{
    public static final String CHANNEL = "main";
    private static final String PROTOCOL_VERSION = "1.1";
    public static SimpleChannel channel = NetworkRegistry.ChannelBuilder
          .named(new ResourceLocation(TCLoader.MOD_ID, CHANNEL))
          .clientAcceptedVersions((v) -> PROTOCOL_VERSION.equals(v) || NetworkRegistry.ABSENT.equals(v) || NetworkRegistry.ACCEPTVANILLA.equals(v))
          .serverAcceptedVersions((v) -> PROTOCOL_VERSION.equals(v) || NetworkRegistry.ABSENT.equals(v) || NetworkRegistry.ACCEPTVANILLA.equals(v))
          .networkProtocolVersion(() -> PROTOCOL_VERSION)
          .simpleChannel();
}
