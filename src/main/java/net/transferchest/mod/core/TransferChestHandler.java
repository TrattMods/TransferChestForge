package net.transferchest.mod.core;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.transferchest.mod.api.ModLogger;
import net.transferchest.mod.gui.TransferChestContainer;
import net.transferchest.mod.loader.TCLoader;
import net.transferchest.mod.network.NetworkHandler;
import net.transferchest.mod.network.packet.TransferChestWatchersS2CPacket;

import java.util.ArrayList;

public final class TransferChestHandler
{
    private static TransferChestInventory inventory;
    private static ArrayList<TransferChestContainer> openGUIs;
    
    public static void initialize()
    {
        inventory = new TransferChestInventory();
        openGUIs = new ArrayList<>();
    }
    
    public static TransferChestInventory getTransferChestInventory()
    {
        return inventory;
    }
    
    
    public static SerializableInventory getSerializableInventory()
    {
        return new SerializableInventory(inventory);
    }
    
    public static void openGUI(World world, TransferChestContainer handler)
    {
        openGUIs.add(handler);
        notifyClients(world);
    }
    
    public static void closeGUI(World world, TransferChestContainer handler)
    {
        openGUIs.remove(handler);
        notifyClients(world);
    }
    
    private static void notifyClients(World world)
    {
        String[] names = new String[openGUIs.size()];
        for(int i = 0; i < openGUIs.size(); i++)
        {
            names[i] = openGUIs.get(i).getOwnerName();
        }
        NetworkHandler.sendToAll(new TransferChestWatchersS2CPacket(names), world.getServer().getPlayerList());
    }
    
    
    public static void printStatus()
    {
        new ModLogger(TCLoader.MOD_ID).logInfo(inventory.toString());
    }
    
    public static void buildInventory(SerializableInventory serializableInventory)
    {
        int length = serializableInventory.size();
        for(int i = 0; i < length; i++)
        {
            ItemStackWrapper current = serializableInventory.getInventory()[i];
            inventory.getContent().setStackInSlot(i, new ItemStack(Item.getItemById(current.getID()), current.getCount()));
        }
    }
}
