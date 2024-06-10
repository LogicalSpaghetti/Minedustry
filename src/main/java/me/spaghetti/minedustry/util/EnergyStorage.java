package me.spaghetti.minedustry.util;

// used by factories to store their energy values.
public abstract class EnergyStorage {
    public double energy = 0;

    public abstract void changeSucceeded();

    public abstract double getCapacity();

    public double getRemainingCapacity() {
        return getCapacity() - energy;
    }

    // returns the amount of energy that was extracted
    public double extract(int desiredAmount) {
        if (energy >= desiredAmount) {
            energy -= desiredAmount;
            changeSucceeded();
            return desiredAmount;
        } else if (energy > 0){
            energy = 0;
            changeSucceeded();
            return energy;
        }
        return 0;
    }

    // returns the amount of energy that was unable to be inserted
    public double insert(int insertAmount) {
        if (getRemainingCapacity() >= insertAmount) {
            energy += insertAmount;
            changeSucceeded();
            return 0;
        } else if (getCapacity() > 0) {
            double remainingCapacity = getRemainingCapacity();
            energy = getCapacity();
            changeSucceeded();
            return insertAmount - remainingCapacity;
        }
        return insertAmount;
    }
}
