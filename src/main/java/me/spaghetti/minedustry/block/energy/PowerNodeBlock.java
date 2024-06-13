package me.spaghetti.minedustry.block.energy;

import me.spaghetti.minedustry.block.abstractions.MinedustryBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class PowerNodeBlock extends MinedustryBlock {
    protected PowerNodeBlock(Settings settings) {
        super(settings, 1);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }
}
