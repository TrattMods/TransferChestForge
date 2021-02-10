package net.transferchest.mod.abstraction;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;


public abstract class AHorizontalFacingInventoryBlock extends AInventoryBlock
{
    public static final DirectionProperty FACING;
    
    static
    {
        FACING = HorizontalBlock.HORIZONTAL_FACING;
    }
    
    public AHorizontalFacingInventoryBlock(AbstractBlock.Properties settings)
    {
        super(settings);
        this.setDefaultState((BlockState) ((BlockState) this.getDefaultState()).with(FACING, Direction.NORTH));
    }
    
    @Nullable @Override public abstract TileEntity createNewTileEntity(IBlockReader worldIn);
    
    @Override protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
        super.fillStateContainer(builder);
    }
    
    @Nullable @Override public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return (BlockState) this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }
    
    @Override public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction)
    {
        return (BlockState) state.with(FACING, direction.rotate(state.get(FACING)));
    }
    
    @Override public BlockState mirror(BlockState state, Mirror mirrorIn)
    {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }
}
