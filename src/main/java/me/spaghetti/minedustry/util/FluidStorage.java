package me.spaghetti.minedustry.util;

public abstract class FluidStorage {
    public final int mBPerBucket = 1000;
    public long amount = 0; // mB

    public abstract void changeSucceeded();

    public abstract long getCapacity();

    public long getRemainingCapacity() {
        return getCapacity() - amount;
    }

    public boolean tryExtract(long mBExtractAmount) {
        if (amount >= mBExtractAmount) {
            amount -= mBExtractAmount;
            changeSucceeded();
            return true;
        }
        return false;
    }

    public boolean tryExtractBucket() {
        return tryExtract(mBPerBucket);
    }

    public boolean tryInsert(long mBInsertAmount) {
        if (getRemainingCapacity() >= mBInsertAmount) {
            amount += mBInsertAmount;
            changeSucceeded();
            return true;
        }
        return false;
    }

    public boolean tryInsertBucket() {
        return tryInsert(mBPerBucket);
    }
}
