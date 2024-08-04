package me.spaghetti.minedustry.block.abstractions;

import me.spaghetti.minedustry.block.interfaces.ImplementedInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static me.spaghetti.minedustry.block.abstractions.MinedustryMultiBlock.*;
import static me.spaghetti.minedustry.block.helpers.MultiBlockHelper.isCommandPosition;

/**
 * An abstract class which establishes nearly every trait a Minedustry block can have
 * @see me.spaghetti.minedustry.block.abstractions.MinedustryMultiBlock
 */

public abstract class MinedustryBlockEntity extends BlockEntity implements ImplementedInventory {
    public DefaultedList<ItemStack> inventory;

    public boolean isCrafter = false;

    public int maxStackCount = 10;

    public boolean isValidPowerConnection = true;

    public boolean outputsPower = false;
    public boolean consumesPower = false;

    public int progress = 0;
    public int craftingTime; //todo: how do I deal with non-20tps compatible production times? i.e. the silicon smelter which needs a 13.3333... tick craft time

    int powerConsumption = 60; // per second

    public MinedustryBlockEntity(BlockEntityType<?> type, BlockPos pos,
                                 BlockState state, int inventorySize) {
        super(type, pos, state);
        inventory = DefaultedList.ofSize(inventorySize, ItemStack.EMPTY);
    }

    // todo: test
    @Override
    public DefaultedList<ItemStack> getItems() {
        if (getCachedState().get(X_OFFSET) == 0 && getCachedState().get(Y_OFFSET) == 0 && getCachedState().get(Z_OFFSET) == 0) {
            return inventory;
        } else {
            assert world != null;
            return ((MinedustryBlockEntity) world.getBlockEntity(getControlPos(getPos(), getCachedState()))).getItems();
        }
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            clientTick(world, pos, state);
        } else if (isCommandPosition(state)) {
            serverTick(world, pos, state);
        } else {
            childTick(world, pos, state);
        }
    }

    public void serverTick(World world, BlockPos pos, BlockState state) {}

    public void clientTick(World world, BlockPos pos, BlockState state) {}

    public void childTick(World world, BlockPos pos, BlockState state) {

    }

    @Override
    public int getMaxCountPerStack() {
        return maxStackCount;
    }
}
