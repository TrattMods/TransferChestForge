package net.transferchest.mod.core;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.items.ItemStackHandler;
import net.transferchest.mod.initializer.TCItems;

import javax.annotation.Nonnull;

public class TransferChestInventory extends ItemStackHandler
{
    public static final int INVENTORY_SIZE = 10;
    
    public TransferChestInventory()
    {
        super(INVENTORY_SIZE);
    }
    
    @Override
    public String toString()
    {
        String ret = "Inventory: ";
        for(int i = 0; i < getSlots(); i++)
        {
            ret += getStackInSlot(i).toString();
            if(i < getSlots() - 1)
            {
                ret += ", ";
            }
        }
        return ret;
    }
    
    @Override public boolean isItemValid(int slot, @Nonnull ItemStack stack)
    {
        return !stack.getItem().equals(TCItems.TRANSFER_CHEST_ITEM) && !stack.equals(Items.SHULKER_BOX) && !stack.equals(Items.ENDER_CHEST);
    }
    
}
