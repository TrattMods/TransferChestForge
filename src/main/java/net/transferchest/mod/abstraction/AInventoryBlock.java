package net.transferchest.mod.abstraction;


import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AInventoryBlock extends BaseEntityBlock {
    public AInventoryBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }


    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
        //return super.getRenderShape(p_49232_);
    }
}