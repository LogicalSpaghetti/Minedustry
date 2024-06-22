package me.spaghetti.minedustry.block.blocks.graphite_press;

import me.spaghetti.minedustry.block.block_util.abstractions.CraftingBlockEntity;
import me.spaghetti.minedustry.block.ModBlockEntities;
import me.spaghetti.minedustry.item.ModItems;
import me.spaghetti.minedustry.screen.graphite_press.GraphitePressScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class GraphitePressBlockEntity extends CraftingBlockEntity {
    private static final int INPUT_SLOT_INDEX = 0;
    private static final int OUTPUT_SLOT_INDEX = 1;

    @Override
    public int[] inputSlotIndexes() {
        return new int[]{0};
    }

    @Override
    public int[] outputSlotIndexes() {
        return new int[]{1};
    }

    @Override
    public int[] allSlotIndexes() {
        return new int[]{0, 1};
    }

    @Override
    public int timeToCraft() {
        return 30; // 20tps * 1.5s
    }

    public final PropertyDelegate propertyDelegate;

    public GraphitePressBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GRAPHITE_PRESS_BLOCK_ENTITY, pos, state, 2);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> GraphitePressBlockEntity.this.progress;
                    case 1 -> GraphitePressBlockEntity.this.timeToCraft();
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                if (index == 0) {
                    GraphitePressBlockEntity.this.progress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("graphite_press.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        nbt.putInt("graphite_press.progress", progress);
    }


    @Override
    public Text getDisplayName() {
        return Text.translatable("display.minedustry.graphite-press");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new GraphitePressScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public boolean isValidPowerConnection() {
        return false;
    }

    public void craftItem() {
        this.removeStack(INPUT_SLOT_INDEX, 2);
        ItemStack result = new ItemStack(ModItems.GRAPHITE);

        this.setStack(OUTPUT_SLOT_INDEX, new ItemStack(result.getItem(), getStack(OUTPUT_SLOT_INDEX).getCount() + result.getCount()));
    }

    @Override
    public boolean hasRecipe() {
        ItemStack result = new ItemStack(ModItems.GRAPHITE);
        boolean hasInput =
                getStack(INPUT_SLOT_INDEX).getItem() == ModItems.COAL ||
                getStack(INPUT_SLOT_INDEX).getItem() == Items.COAL;
        hasInput = hasInput && getStack(INPUT_SLOT_INDEX).getCount() > 1;
        return hasInput && canInsertIntoOutput(result);
    }

    @Override
    public boolean canInsertIntoOutput(ItemStack result) {
        boolean validItem = this.getStack(OUTPUT_SLOT_INDEX).getItem() == result.getItem() || this.getStack(OUTPUT_SLOT_INDEX).isEmpty();
        boolean validSize = this.getStack(OUTPUT_SLOT_INDEX).getCount() + result.getCount() <= getStack(OUTPUT_SLOT_INDEX).getMaxCount();
        return validItem && validSize;
    }

    @Override
    public boolean isValid(int slot, ItemStack input) {
        if (slot == OUTPUT_SLOT_INDEX) {
            return false;
        }

        if (slot == INPUT_SLOT_INDEX) {
            ItemStack itemStack = this.inventory.get(INPUT_SLOT_INDEX);
            return (input.isOf(Items.COAL) || input.isOf(ModItems.COAL))
                    && (itemStack.isOf(input.getItem()) || itemStack.isEmpty());
        }

        // something went wrong
        return false;
    }
}
