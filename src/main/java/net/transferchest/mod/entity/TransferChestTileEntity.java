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
import net.transferchest.mod.block.TransferChestBlock;
import net.transferchest.mod.gui.TransferChestContainer;
import net.transferchest.mod.initializer.TCBlocks;
import net.transferchest.mod.initializer.TCEntities;
import net.transferchest.mod.initializer.TCSounds;

import javax.annotation.Nullable;

public class TransferChestTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider
{
    private int viewers;
    
    public TransferChestTileEntity()
    {
        super(TCEntities.TRANSFER_CHEST_TILE_ENTITY.get());
    }
    
    //public boolean canPlayerAccessInventory(PlayerEntity player)
    //{
    //    if(this.world.getTileEntity(this.pos) != this) return false;
    //    final double X_CENTRE_OFFSET = 0.5;
    //    final double Y_CENTRE_OFFSET = 0.5;
    //    final double Z_CENTRE_OFFSET = 0.5;
    //    final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
    //    return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
    //}
    
    @Override public ITextComponent getDisplayName()
    {
        return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
    }
    
    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        CompoundNBT nbtTagCompound = new CompoundNBT();
        write(nbtTagCompound);
        int tileEntityType = 42;  // arbitrary number; only used for vanilla TileEntities.  You can use it, or not, as you want.
        return new SUpdateTileEntityPacket(this.pos, tileEntityType, nbtTagCompound);
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
        BlockState blockState = world.getBlockState(pos);
        read(blockState, pkt.getNbtCompound());
    }
    
    @Override
    public CompoundNBT getUpdateTag()
    {
        CompoundNBT nbtTagCompound = new CompoundNBT();
        write(nbtTagCompound);
        return nbtTagCompound;
    }
    
    @Override
    public void handleUpdateTag(BlockState blockState, CompoundNBT tag)
    {
        this.read(blockState, tag);
    }
    
    
    private void setOpened(boolean opened)
    {
        if(!world.isRemote)
        {
            this.world.setBlockState(this.pos, (BlockState) this.world.getBlockState(this.pos).with(TransferChestBlock.OPENED, opened), 0B1011);
            SoundEvent event = opened ? TCSounds.TRANSFER_CHEST_OPEN_SOUNDEVENT : TCSounds.TRANSFER_CHEST_CLOSE_SOUNDEVENT;
            this.world.playSound(null, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, event, SoundCategory.BLOCKS, 0.45F, this.world.rand.nextFloat() * 0.2F + 1F);
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
        return new TransferChestContainer(windowID, world, pos, playerInventory, playerEntity);
    }
}
