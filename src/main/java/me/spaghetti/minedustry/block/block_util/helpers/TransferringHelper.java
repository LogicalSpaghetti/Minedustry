package me.spaghetti.minedustry.block.block_util.helpers;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;

public class TransferringHelper {
    public static int[] getValidSlots(Inventory destination, BlockState state, Direction side) {
        if (destination instanceof SidedInventory) {
            return ((SidedInventory) destination).getAvailableSlots(side);
        } else {
            // this solution is terrible
            int size = destination.size();
            int[] returnArray = new int[size];
            for (int i = 0; i < returnArray.length; i++) {
                returnArray[i] = i;
            }
            return returnArray;
        }
    }

    /***
     * returns whether the transfer was successful
    */
    public static boolean trySendForwards(Inventory from, Inventory to, int[] slots, int outputSlotIndex, Direction outputDirection) {
        boolean transactionOccured = false;
        // go through trying to find the first slot of a similar type or that is empty
        for (int currentSlot : slots) {
            ItemStack currentDest = to.getStack(currentSlot);

            // if it finds an empty slot, transfer the stack, make sure it gets marked, and break;
            if (currentDest.isEmpty() && ((SidedInventory)to).canInsert(currentSlot, from.getStack(outputSlotIndex), outputDirection)) {
                ItemStack movedItem = from.getStack(outputSlotIndex).copy();
                movedItem.setCount(1);
                to.setStack(currentSlot, movedItem);
                transactionOccured = true;
                break;
            }

            // if it finds a similar slot, add one item
            if (ItemStack.canCombine(currentDest, from.getStack(outputSlotIndex)) && currentDest.getCount() < currentDest.getMaxCount()) {
                currentDest.increment(1);
                to.setStack(currentSlot, currentDest);
                transactionOccured = true;
                break;
            }
        }

        // if we iterate through every slot and still have items remaining, return false
        if (transactionOccured) {
            from.getStack(outputSlotIndex).decrement(1);
            from.markDirty();
            to.markDirty();
        }

        return transactionOccured;
    }
}
