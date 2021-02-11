package net.transferchest.mod.entity;


import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.IItemHandler;
import net.transferchest.mod.block.TransferChestBlock;
import net.transferchest.mod.core.TransferChestHandler;
import net.transferchest.mod.core.TransferChestInventory;
import net.transferchest.mod.gui.TransferChestContainer;
import net.transferchest.mod.initializer.TCBlocks;
import net.transferchest.mod.initializer.TCEntities;
import net.transferchest.mod.initializer.TCSounds;

import javax.annotation.Nullable;

public class TransferChestTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider
{
    private int viewers;
    private TransferChestInventory inventory;
    
    public TransferChestTileEntity()
    {
        super(TCEntities.TRANSFER_CHEST_TILE_ENTITY.get());
        inventory = new TransferChestInventory();
    }
    
    public void onClose(TransferChestContainer container)
    {
        if(!world.isRemote)
        {
            viewers--;
            if(viewers < 0) viewers = 0;
            TransferChestHandler.closeGUI(world, container);
        }
    }
    
    @Override public ITextComponent getDisplayName()
    {
        return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
    }
    
    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        return new SUpdateTileEntityPacket(this.pos, 0, getUpdateTag());
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
        handleUpdateTag(getBlockState(), pkt.getNbtCompound());
    }
    
    public IItemHandler inventory()
    {
        return inventory;
    }
    
    @Override
    public CompoundNBT getUpdateTag()
    {
        CompoundNBT nbtTagCompound = super.getUpdateTag();
        nbtTagCompound.put("Items", inventory.serializeNBT());
        return nbtTagCompound;
    }
    
    @Override
    public void handleUpdateTag(BlockState blockState, CompoundNBT tag)
    {
        inventory.deserializeNBT(tag.getCompound("Items"));
    }
    
    
    private void setOpened(boolean opened)
    {
        if(!world.isRemote)
        {
            this.world.setBlockState(this.pos, (BlockState) this.world.getBlockState(this.pos).with(TransferChestBlock.OPENED, opened), 0B1011);
            SoundEvent event = opened ? TCSounds.TRANSFER_CHEST_OPEN_SOUNDEVENT.get() : TCSounds.TRANSFER_CHEST_CLOSE_SOUNDEVENT.get();
            this.world.playSound(null, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, event, SoundCategory.BLOCKS, 0.45F, this.world.rand.nextFloat() * 0.2F + 1F);
        }
    }
    
    public void onOpen(TransferChestContainer container)
    {
        if(!world.isRemote)
        {
            viewers++;
            if(viewers > 0 && !isOpened())
            {
                setOpened(true);
            }
        }
    }
    
    private boolean isOpened()
    {
        if(this.world.getBlockState(pos).getBlock().equals(TCBlocks.TRANSFER_CHEST_BLOCK.get()))
        {
            return this.world.getBlockState(pos).get(TransferChestBlock.OPENED);
        }
        return false;
    }
    
    @Override
    public void tick()
    {
        if(!world.isRemote)
        {
            if(viewers <= 0 && isOpened())
            {
                setOpened(false);
            }
        }
    }
    
    @Nullable
    @Override
    public Container createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity playerEntity)
    {
        inventory = TransferChestHandler.getTransferChestInventory();
        return new TransferChestContainer(windowID, world, pos, playerEntity.inventory, this);
    }
}
