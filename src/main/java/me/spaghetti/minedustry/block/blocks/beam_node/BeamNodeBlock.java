package me.spaghetti.minedustry.block.blocks.beam_node;

import me.spaghetti.minedustry.block.ModBlockEntities;
import me.spaghetti.minedustry.block.blocks.MinedustryBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BeamNodeBlock extends MinedustryBlock {
    public BeamNodeBlock(Settings settings) {
        super(settings, 1);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BeamNodeBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.BEAM_NODE_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}
