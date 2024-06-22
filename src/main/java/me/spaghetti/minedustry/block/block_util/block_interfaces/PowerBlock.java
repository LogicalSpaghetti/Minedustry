package me.spaghetti.minedustry.block.block_util.block_interfaces;

// power is stored in milliJoules
public interface PowerBlock {

    void changeSucceeded();

    int getCapacity();

    int getPower();

    void setPower(int power);

    private void addPower(int power) {
        setPower(getPower() + power);
    }

    // returns the amount of power that was extracted
    default int extractPower(int desiredAmount) {
        if (getPower() >= desiredAmount) {
            addPower(-desiredAmount);
            changeSucceeded();
            return desiredAmount;
        } else if (getPower() > 0){
            int returnPower = getPower();
            setPower(0);
            changeSucceeded();
            return returnPower;
        }
        return 0;
    }

    // returns the amount of power left over after inserting
    default int insertPower(int insertAmount) {
        if (getRemainingCapacity() >= insertAmount) {
            addPower(insertAmount);
            changeSucceeded();
            return 0;
        } else if (getRemainingCapacity() > 0) {
            int returnRemainingCapacity = getRemainingCapacity();
            setPower(getCapacity());
            changeSucceeded();
            return insertAmount - returnRemainingCapacity;
        }
        return insertAmount;
    }

    private int getRemainingCapacity() {
        return getCapacity() - getPower();
    }
}
