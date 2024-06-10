package me.spaghetti.minedustry.block.abstractions;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public abstract class GenericCrafter extends MinedustryBlockEntity {

    public GenericCrafter(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
