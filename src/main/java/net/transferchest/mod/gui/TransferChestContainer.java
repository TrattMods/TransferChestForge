package net.transferchest.mod.gui;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.SlotItemHandler;
import net.transferchest.mod.core.TransferChestHandler;
import net.transferchest.mod.core.TransferChestInventory;
import net.transferchest.mod.initializer.TCContainers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransferChestContainer extends Container
{
    private TransferChestInventory content;
    private BlockPos pos;
    private static final Logger LOGGER = LogManager.getLogger();
    private PlayerEntity owner;
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = TransferChestInventory.INVENTORY_SIZE;
    
    
    
    public TransferChestContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player)
    {
        super(TCContainers.TRANSFER_CHEST_CONTAINER.get(), windowId);
        this.owner = playerInventory.player;
        this.pos = pos;
        this.content = TransferChestHandler.getTransferChestInventory();
        for(int i = 0; i < TransferChestInventory.INVENTORY_SIZE; i++)
        {
            if(i < TransferChestInventory.INVENTORY_SIZE / 2)
            {
                addSlot(new SlotItemHandler(content.getContent(), i, i * 18 + 8, 29));
            }
            else
            {
                addSlot(new SlotItemHandler(content.getContent(), i, (i - (TransferChestInventory.INVENTORY_SIZE / 2)) * 18 + 8, 47));
            }
        }
        bindPlayerInventory(playerInventory, 84);
    }
    
    public String getOwnerName()
    {
        return owner.getName().getString();
    }
    
    @Override
    public boolean canInteractWith(PlayerEntity playerEntity)
    {
        return playerEntity.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) < 8.0 * 8.0;
    }
    

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int sourceSlotIndex)
    {
        Slot sourceSlot = inventorySlots.get(sourceSlotIndex);
        if(sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getStack();
        ItemStack copyOfSourceStack = sourceStack.copy();
        
        if(sourceSlotIndex >= VANILLA_FIRST_SLOT_INDEX && sourceSlotIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT)
        {
            if(!mergeItemStack(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false))
            {
                return ItemStack.EMPTY;
            }
        }
        else if(sourceSlotIndex >= TE_INVENTORY_FIRST_SLOT_INDEX && sourceSlotIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT)
        {
            if(!mergeItemStack(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false))
            {
                return ItemStack.EMPTY;
            }
        }
        else
        {
            LOGGER.warn("Invalid slotIndex:" + sourceSlotIndex);
            return ItemStack.EMPTY;
        }
        
        if(sourceStack.getCount() == 0)
        {
            sourceSlot.putStack(ItemStack.EMPTY);
        }
        else
        {
            sourceSlot.onSlotChanged();
        }
        
        sourceSlot.onTake(playerEntity, sourceStack);
        return copyOfSourceStack;
    }
    
    
    @Override
    public void onContainerClosed(PlayerEntity playerIn)
    {
        TransferChestHandler.closeGUI(playerIn.world, this);
        super.onContainerClosed(playerIn);
    }
    
    protected void bindPlayerInventory(PlayerInventory playerInventory, int yOffset)
    {
        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                addSlot(new Slot(playerInventory,
                      x + y * 9 + 9,
                      8 + x * 18, yOffset + y * 18));
            }
        }
        
        for(int x = 0; x < 9; x++)
        {
            addSlot(new Slot(playerInventory, x, 8 + x * 18, yOffset + 58));
        }
    }
}
