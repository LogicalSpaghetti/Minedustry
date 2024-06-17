package me.spaghetti.minedustry.block.abstractions;

import me.spaghetti.minedustry.block.energy.steam_generator.SteamGeneratorBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public abstract class NewBlock<T extends NewBlockEntity> extends BlockWithEntity implements BlockEntityProvider {

    private T blockEntity;

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return blockEntity;
    }

    protected NewBlock(Settings settings, T blockEntity) {
        super(settings);
        this.blockEntity = blockEntity;
    }
}
