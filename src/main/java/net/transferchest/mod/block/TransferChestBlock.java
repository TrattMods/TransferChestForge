package net.transferchest.mod.block;


import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.transferchest.mod.abstraction.AHorizontalFacingInventoryBlock;
import net.transferchest.mod.core.TransferChestHandler;
import net.transferchest.mod.entity.TransferChestTileEntity;
import net.transferchest.mod.gui.TransferChestContainer;
import net.transferchest.mod.loader.TCLoader;

import javax.annotation.Nullable;

public class TransferChestBlock extends AHorizontalFacingInventoryBlock {
    public static final String ID = "transfer_chest";
    public static final ResourceLocation OPEN_SOUND = new ResourceLocation(TCLoader.MOD_ID, "transfer_chest_open");
    public static final ResourceLocation CLOSE_SOUND = new ResourceLocation(TCLoader.MOD_ID, "transfer_chest_close");
    public static final BooleanProperty OPENED;

    static {
        OPENED = BooleanProperty.create("opened");
    }

    public TransferChestBlock() {
        super(BlockBehaviour.Properties.of().setRequiresTool().hardnessAndResistance(40F, 900F).sound(SoundType.STONE));
        this.registerDefaultState(this.getStateDefinition().any().setValue(OPENED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OPENED);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Block.box(1F, 0F, 1F, 15F, 14F, 15F);
    }

    @Override
    public void animateTick(BlockState p_220827_, Level level, BlockPos pos, RandomSource rand) {
        int j = rand.nextInt(2) * 2 - 1;
        int k = rand.nextInt(2) * 2 - 1;
        double d = (double) pos.getX() + 0.5D + 0.25D * (double) j;
        double e = (double) ((float) pos.getY() + rand.nextFloat());
        double f = (double) pos.getZ() + 0.5D + 0.25D * (double) k;
        double g = 0.45D * (double) (rand.nextFloat() * (float) j);
        double h = 0.45D * ((double) rand.nextFloat()) * 0.1D;
        double l = 0.45D * (double) (rand.nextFloat() * (float) k);
        level.addParticle(ParticleTypes.DRAGON_BREATH, d, e, f, g, h, l);
    }

    @Override
    public InteractionResult use(BlockState p_60503_, Level worldIn, BlockPos pos, Player player, InteractionHand p_60507_, BlockHitResult p_60508_) {
        if (worldIn.isRemote) return InteractionResult.SUCCESS;

        TileEntity tileEntity = worldIn.getBlockEntity(pos);
        if (!(tileEntity instanceof INamedContainerProvider)) return InteractionResult.FAIL;

        NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, (packetBuffer) -> packetBuffer.writeBlockPos(pos));

        TransferChestHandler.openGUI(worldIn, (TransferChestContainer) player.open);
        return InteractionResult.SUCCESS;
    }

        @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (worldIn.isRemote) return ActionResultType.SUCCESS;

        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (!(tileEntity instanceof INamedContainerProvider)) return ActionResultType.FAIL;

        NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, (packetBuffer) -> packetBuffer.writeBlockPos(pos));

        TransferChestHandler.openGUI(worldIn, (TransferChestContainer) player.openContainer);
        return ActionResultType.SUCCESS;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TransferChestTileEntity();
    }
}
