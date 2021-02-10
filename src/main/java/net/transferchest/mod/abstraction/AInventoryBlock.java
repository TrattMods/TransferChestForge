package net.transferchest.mod.abstraction;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AInventoryBlock extends ContainerBlock
{
    public AInventoryBlock(AbstractBlock.Properties settings)
    {
        super(settings);
    }
    
    @Override
    public boolean hasComparatorInputOverride(BlockState state)
    {
        return false;
    }
    
    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos)
    {
        return 0;
    }
    
    
    public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.MODEL;
    }
}