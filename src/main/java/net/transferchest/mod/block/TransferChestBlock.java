package net.transferchest.mod.block;


import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.transferchest.mod.abstraction.AHorizontalFacingInventoryBlock;
import net.transferchest.mod.entity.TransferChestTileEntity;
import net.transferchest.mod.loader.TCLoader;

import javax.annotation.Nullable;
import java.util.Random;

public class TransferChestBlock extends AHorizontalFacingInventoryBlock
{
    public static final String ID = "transfer_chest";
    public static final ResourceLocation OPEN_SOUND = new ResourceLocation(TCLoader.MOD_ID, "transfer_chest_open");
    public static final ResourceLocation CLOSE_SOUND = new ResourceLocation(TCLoader.MOD_ID, "transfer_chest_close");
    public static final BooleanProperty OPENED;
    
    static
    {
        OPENED = BooleanProperty.create("opened");
    }
    
    public TransferChestBlock()
    {
        super(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(40F, 900F).sound(SoundType.STONE));
        this.setDefaultState((BlockState) ((BlockState) ((BlockState) this.getStateContainer().getBaseState()).with(OPENED, false)));
    }
    
    @Override protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(OPENED);
        super.fillStateContainer(builder);
    }
    
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return Block.makeCuboidShape(1F, 0F, 1F, 15F, 14F, 15F);
    }
    
    @Nullable @Override public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new TransferChestTileEntity();
    }
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }
    
    @Override public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        int j = rand.nextInt(2) * 2 - 1;
        int k = rand.nextInt(2) * 2 - 1;
        double d = (double) pos.getX() + 0.5D + 0.25D * (double) j;
        double e = (double) ((float) pos.getY() + rand.nextFloat());
        double f = (double) pos.getZ() + 0.5D + 0.25D * (double) k;
        double g = 0.45D * (double) (rand.nextFloat() * (float) j);
        double h = 0.45D * ((double) rand.nextFloat()) * 0.1D;
        double l = 0.45D * (double) (rand.nextFloat() * (float) k);
        worldIn.addParticle(ParticleTypes.DRAGON_BREATH, d, e, f, g, h, l);
    }
    
    // Copied from ChestBlock
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult)
    {
        if (worldIn.isRemote) return ActionResultType.SUCCESS;
        
        INamedContainerProvider namedContainerProvider = this.getContainer(state, worldIn, pos);
        if (namedContainerProvider != null)
        {
            if (!(player instanceof ServerPlayerEntity)) return ActionResultType.FAIL;
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
            NetworkHooks.openGui(serverPlayerEntity, namedContainerProvider, (packetBuffer)->
                  packetBuffer.writeBlockPos(pos));
        }
        return ActionResultType.SUCCESS;
    }
    
    @Nullable @Override public TileEntity createNewTileEntity(IBlockReader worldIn)
    {
        return null;
    }
}
