package me.spaghetti.minedustry.block.energy;

import me.spaghetti.minedustry.block.abstractions.MinedustryBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PowerNodeBlockEntity extends MinedustryBlockEntity {
    public PowerNodeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize) {
        super(type, pos, state, inventorySize);
    }

    @Override
    public void commandTick(World world, BlockPos pos, BlockState state) {

    }

    @Override
    public Text getDisplayName() {
        return null;
    }
}
