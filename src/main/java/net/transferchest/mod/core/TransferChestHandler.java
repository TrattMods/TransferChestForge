package net.transferchest.mod.core;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.transferchest.mod.gui.TransferChestContainer;
import net.transferchest.mod.loader.TCLoader;
import net.transferchest.mod.network.NetworkHandler;
import net.transferchest.mod.network.packet.TransferChestWatchersS2CPacket;

import java.util.ArrayList;

public final class TransferChestHandler {
    private static TransferChestInventory inventory;
    private static ArrayList<TransferChestContainer> openGUIs;

    public static void initialize() {
        inventory = new TransferChestInventory();
        openGUIs = new ArrayList<>();
    }

    public static TransferChestInventory getTransferChestInventory() {
        return inventory;
    }


    public static SerializableInventory getSerializableInventory() {
        return new SerializableInventory(inventory);
    }

    public static void openGUI(Level world, TransferChestContainer handler) {
        openGUIs.add(handler);
        notifyClients(world);
    }

    public static void closeGUI(Level world, TransferChestContainer handler) {
        openGUIs.remove(handler);
        notifyClients(world);
    }

    private static void notifyClients(Level world) {

        String[] names = new String[openGUIs.size()];
        for (int i = 0; i < openGUIs.size(); i++) {
            names[i] = openGUIs.get(i).getOwnerName();
            System.out.println(names[i]);
        }
        NetworkHandler.sendToAll(new TransferChestWatchersS2CPacket(names), world.getServer().getPlayerList());
    }


    public static void printStatus() {
        TCLoader.LOGGER.info(inventory.toString());
    }

    public static void buildInventory(SerializableInventory serializableInventory) {
        int length = serializableInventory.size();
        for (int i = 0; i < length; i++) {
            ItemStackWrapper current = serializableInventory.getInventory()[i];
            inventory.setStackInSlot(i, new ItemStack(Item.byId(current.getID()), current.getCount()));
        }
    }
}
