package net.transferchest.mod.core;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.io.Serializable;

public class SerializableInventory implements Serializable
{
    private ItemStackWrapper[] inventory;
    
    public int size()
    {
        return inventory.length;
    }
    
    public SerializableInventory(TransferChestInventory stacks)
    {
        int length = stacks.getContent().getSlots();
        inventory = new ItemStackWrapper[length];
        for(int i = 0; i < length; i++)
        {
            ItemStack stack = stacks.getContent().getStackInSlot(i);
            inventory[i] = new ItemStackWrapper(Item.getIdFromItem(stack.getItem()), stack.getCount());
        }
    }
    
    public ItemStackWrapper[] getInventory()
    {
        return inventory;
    }
}
