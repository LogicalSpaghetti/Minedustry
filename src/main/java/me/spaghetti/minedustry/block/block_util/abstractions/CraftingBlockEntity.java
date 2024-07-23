package me.spaghetti.minedustry.block.block_util.abstractions;

import me.spaghetti.minedustry.block.block_util.helpers.MultiOutputHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static me.spaghetti.minedustry.block.block_util.helpers.TransferringHelper.getValidSlots;
import static me.spaghetti.minedustry.block.block_util.helpers.TransferringHelper.tryExternalTransfer;

public abstract class CraftingBlockEntity extends MinedustryBlockEntity implements NamedScreenHandlerFactory {

    public int progress = 0;
    public int craftingTime; //todo: how do I deal with non-20tps compatible production times? i.e. the silicon smelter which needs a 13.3333... tick craft time

    public PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> progress;
                case 1 -> craftingTime;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> progress = value;
                case 1 -> craftingTime = value;
            }
        }

        @Override
        public int size() {
            return 2;
        }
    };

    final ItemStack[] inputStacks;
    final ItemStack[] outputStacks;

    String translationKey;

    int maxStackCount;

    public CraftingBlockEntity(
            BlockEntityType<?> type, BlockPos pos, BlockState state,
            String translationKey, int maxStackCount,
            ItemStack[] inputStacks, ItemStack[] outputStacks) {
        super(type, pos, state,
                inputStacks.length + outputStacks.length);
        this.inputStacks = inputStacks;
        this.outputStacks = outputStacks;
        this.translationKey = translationKey;
        this.maxStackCount = maxStackCount;
    }

    @Override
    public void serverCommandTick(World world, BlockPos pos, BlockState state) {
        updateCraft(world, pos, state);
        tryTransfer(world, pos);
    }

    protected void tryTransfer(World world, BlockPos pos) {
        Vec3i[] offsetVectors = MultiOutputHelper.getRandomOffsets(2);
        // loop through, trying all possible output locations until one succeeds
        for (Vec3i offsetVector : offsetVectors) {
            if (tryLocation(offsetVector, world, pos))
                break;
        }
    }

    private boolean tryLocation(Vec3i offset, World world, BlockPos pos) {
        Inventory dest = HopperBlockEntity.getInventoryAt(world, pos.add(offset));
        if (dest == null) return false;
        if (dest.getStack(0) == null) return false;
        int[] validSlots = getValidSlots(dest, MultiOutputHelper.getDirectionForOffset(2, offset));
        if (validSlots.length == 0) return false;

        // loops through all output slots trying to transfer an item, if a transfer is successful, then it breaks out of the loop;
        for (int slot = inputStacks.length; slot < inventory.size(); slot++) {
            if (tryExternalTransfer(this, dest, validSlots, slot))
                return true;
        }
        return false;
    }

    protected void updateCraft(World world, BlockPos pos, BlockState state) {
        if (this.hasRecipe() && this.roomInOutput()) {
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

    public void craftItem() {
        for (int slot = 0; slot < inputStacks.length; slot++) {
            this.removeStack(slot, inputStacks[slot].getCount());
        }
        for (int slot = inputStacks.length; slot < inventory.size(); slot++) {
            ItemStack itemStack = outputStacks[slot - inputStacks.length].copy();
            itemStack.increment(this.getStack(slot).getCount());
            this.setStack(slot, itemStack);
        }
    }

    private boolean hasCraftingFinished() {
        return progress >= craftingTime;
    }

    public boolean hasRecipe() {
        for (int slot = 0; slot < inputStacks.length; slot++) {
            if (this.getStack(slot).getCount() < inputStacks[slot].getCount()) return false;
            if (this.getStack(slot).getItem() != inputStacks[slot].getItem()) return false;
        }
        return true;
    }

    public boolean roomInOutput() {
        for (int slot = 0; slot < outputStacks.length; slot++) {
            if (this.getStack(slot + inputStacks.length).getCount() + outputStacks[slot].getCount() > this.getMaxCountPerStack())
                return false;
        }
        return true;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        return slot < inputStacks.length && stack.isOf(inputStacks[slot].getItem());
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        return slot >= inputStacks.length;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(translationKey);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("minedustry_crafting_block.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        nbt.putInt("minedustry_crafting_block.progress", progress);
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        if (slot < inputStacks.length) {
            return stack.getItem() == inputStacks[slot].getItem();
        }
        return false;
    }

    @Override
    public int getMaxCountPerStack() {
        return maxStackCount;
    }
}
