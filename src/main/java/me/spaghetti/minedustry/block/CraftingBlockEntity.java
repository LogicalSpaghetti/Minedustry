package me.spaghetti.minedustry.block;

import me.spaghetti.minedustry.block.block_util.helpers.MultiOutputHelper;
import me.spaghetti.minedustry.block.block_util.helpers.TransferringHelper;
import me.spaghetti.minedustry.block.blocks.MinedustryBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import static me.spaghetti.minedustry.block.blocks.MinedustryBlock.SIZE;

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
        tryOutput(world, pos, state);
    }

    protected void tryOutput(World world, BlockPos pos, BlockState state) {
        Vec3i[] offsetVectors = MultiOutputHelper.getRandomOffsets(state.get(SIZE));

        Inventory outputInventory;

        // loops through all output locations
        for (int i = 0; i < offsetVectors.length; i++) {

            // get the inventory at a given offset
            outputInventory = HopperBlockEntity.getInventoryAt(world, pos.add(offsetVectors[i]));

            // if the output isn't null and the first slot isn't null (weird way to check for an invalid/slot-less inventory)
            if (outputInventory != null && outputInventory.getStack(0) != null) {
                int[] validSlots = TransferringHelper.getValidSlots(outputInventory, state, MultiOutputHelper.getDirectionForOffset(2, offsetVectors[i]));
                if (validSlots.length != 0) {

                    boolean outputSuccessful = false;

                    // loops through all output slots trying to transfer an item, if a transfer is successful, then it breaks out of the loop;
                    for (int slot : outputSlotIndexes()) {
                        if (TransferringHelper.trySendForwards(this, outputInventory, validSlots, slot, MultiOutputHelper.getOutputDirection(state.get(SIZE), i))) {
                            //transferCooldowns[2] = TRANSFER_COOLDOWN;
                            outputSuccessful = true;
                            break; // stops trying to find a valid slot if it succeeded
                        }
                    }

                    // stops looking for an inventory if it found one and was able to transfer
                    if (outputSuccessful) {
                        break;
                    }
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
