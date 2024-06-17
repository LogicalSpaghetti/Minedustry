package me.spaghetti.minedustry.block.abstractions;

import me.spaghetti.minedustry.block.helpers.ImplementedInventory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public abstract class NewBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    public NewBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
