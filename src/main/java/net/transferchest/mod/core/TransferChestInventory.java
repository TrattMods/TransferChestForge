package net.transferchest.mod.core;

import net.minecraftforge.items.ItemStackHandler;

public class TransferChestInventory
{
    public static final int INVENTORY_SIZE = 10;
    protected final ItemStackHandler content;
    
    public TransferChestInventory()
    {
        content = new ItemStackHandler(INVENTORY_SIZE);
    }
    
    public ItemStackHandler getContent()
    {
        return content;
    }
    
    @Override
    public String toString()
    {
        String ret = "Inventory: ";
        //for(int i = 0; i < getSizeInventory(); i++)
        //{
        //    ret += getStackInSlot(i).toString();
        //    if(i < getSizeInventory() - 1)
        //    {
        //        ret += ", ";
        //    }
        //}
        return ret;
    }
}
