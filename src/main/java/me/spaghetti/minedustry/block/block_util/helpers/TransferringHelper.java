package me.spaghetti.minedustry.block.block_util.helpers;

import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;

public class TransferringHelper {
    public static int[] getValidSlots(Inventory destination, Direction side) {
        if (destination instanceof SidedInventory) {
            return ((SidedInventory) destination).getAvailableSlots(side);
        } else {
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
    public static boolean tryExternalTransfer(Inventory source, Inventory dest, int[] destSlots, int sourceSlotIndex) {
        boolean transactionOccurred = false;
        // go through trying to find the first slot of a similar type or that is empty
        for (int currentSlot : destSlots) {
            ItemStack currentDest = dest.getStack(currentSlot);

            // if it finds an empty slot, transfer the stack, make sure it gets marked, and break;
            if (currentDest.isEmpty()) {
                ItemStack movedItem = source.getStack(sourceSlotIndex).copy();
                movedItem.setCount(1);
                dest.setStack(currentSlot, movedItem);
                transactionOccurred = true;
                break;
            }

            // if it finds a similar slot, add one item
            if (ItemStack.canCombine(currentDest, source.getStack(sourceSlotIndex)) && currentDest.getCount() < currentDest.getMaxCount()) {
                currentDest.increment(1);
                dest.setStack(currentSlot, currentDest);
                transactionOccurred = true;
                break;
            }
        }

        // if we iterate through every slot and still have items remaining, return false
        if (transactionOccurred) {
            source.getStack(sourceSlotIndex).decrement(1);
            source.markDirty();
            dest.markDirty();
        }

        return transactionOccurred;
    }
}
