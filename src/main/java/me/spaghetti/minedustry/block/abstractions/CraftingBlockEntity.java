package me.spaghetti.minedustry.block.abstractions;

import me.spaghetti.minedustry.block.helpers.SlotRandomizer;
import me.spaghetti.minedustry.block.helpers.Transferring;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public abstract class CraftingBlockEntity extends MinedustryBlockEntity {

    public abstract int[] inputSlotIndexes();
    public abstract int[] outputSlotIndexes();
    public abstract int[] allSlotIndexes();

    public abstract int timeToCraft();

    public int progress = 0;

    public CraftingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize) {
        super(type, pos, state, inventorySize);
    }

    @Override
    public void serverCommandTick(World world, BlockPos pos, BlockState state) {
        updateCraft(world, pos, state);
        tryTransfer(world, pos, state);
    }

    protected void tryTransfer(World world, BlockPos pos, BlockState state) {
        Vec3i[] offsetVectors = SlotRandomizer.getRandomOffsets(2);

        Inventory outputInventory;

        for (Vec3i offsetVector : offsetVectors) {
            outputInventory = HopperBlockEntity.getInventoryAt(world, pos.add(offsetVector));
            if (outputInventory != null && outputInventory.getStack(0) != null) {
                int[] validSlots = Transferring.getValidSlots(outputInventory, state, SlotRandomizer.getDirectionForOffset(2, offsetVector));
                if (validSlots.length != 0) {

                    // loops through all output slots trying to transfer an item, if a transfer is successful, then it breaks out of the loop;
                    for (int slot : outputSlotIndexes()) {
                        if (Transferring.trySendForwards(this, outputInventory, validSlots, slot)) {
                            //transferCooldowns[2] = TRANSFER_COOLDOWN;
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

    protected void updateCraft(World world, BlockPos pos, BlockState state) {
        if (this.hasRecipe()) {
            this.progress++;

            if (hasCraftingFinished()) {
                this.craftItem();
                this.progress = 0;
            }

            markDirty(world, pos, state);
        } else {
            this.lowerProgress();
        }
    }

    private void lowerProgress() {
        if (this.progress > 0)
            this.progress--;
    }

    public abstract void craftItem();

    private boolean hasCraftingFinished() {
        return progress >= timeToCraft();
    }

    public abstract boolean hasRecipe();

    public abstract boolean canInsertIntoOutput(ItemStack result);

    // returns which slots a hopper is allowed to interact with given its side
    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.DOWN) {
            return outputSlotIndexes();
        }
        if (side == Direction.UP) {
            return inputSlotIndexes();
        }
        return allSlotIndexes();
    }
}
