package me.spaghetti.minedustry.block.production.arc_furnace;

import me.spaghetti.minedustry.block.ModBlockEntities;
import me.spaghetti.minedustry.block.abstractions.CraftingBlockEntity;
import me.spaghetti.minedustry.item.ModItems;
import me.spaghetti.minedustry.screen.arc_furnace.SiliconArcFurnaceScreenHandler;
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
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class SiliconArcFurnaceBlockEntity extends CraftingBlockEntity {
    private static final int SAND_INPUT_SLOT_INDEX = 0;
    private static final int GRAPHITE_INPUT_SLOT_INDEX = 1;
    private static final int OUTPUT_SLOT_INDEX = 2;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;

    public SiliconArcFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SILICON_ARC_FURNACE_BLOCK_ENTITY, pos, state, 3);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> SiliconArcFurnaceBlockEntity.this.progress;
                    case 1 -> SiliconArcFurnaceBlockEntity.this.timeToCraft();
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                if (index == 0) {
                    SiliconArcFurnaceBlockEntity.this.progress = value;
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
        nbt.putInt("silicon_arc_furnace.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        nbt.putInt("silicon_arc_furnace.progress", progress);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("display.minedustry.silicon_arc_furnace");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SiliconArcFurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public int[] inputSlotIndexes() {
        return new int[]{0, 1};
    }

    @Override
    public int[] outputSlotIndexes() {
        return new int[]{2};
    }

    @Override
    public int[] allSlotIndexes() {
        return new int[]{0, 1, 2};
    }

    @Override
    public int timeToCraft() {
        return 16; // 20tps * 0.83333s, should be 16.666 seconds, but needs to be rounded down because the original runs at 60 fps
    }

    public void craftItem() {
        this.removeStack(GRAPHITE_INPUT_SLOT_INDEX, 1);
        this.removeStack(SAND_INPUT_SLOT_INDEX, 2);
        ItemStack result = new ItemStack(ModItems.SILICON);
        result.setCount(4);

        this.setStack(OUTPUT_SLOT_INDEX, new ItemStack(result.getItem(), getStack(OUTPUT_SLOT_INDEX).getCount() + result.getCount()));
    }

    public boolean hasRecipe() {
        ItemStack result = new ItemStack(ModItems.SILICON, 4);

        boolean hasInput =
                getStack(GRAPHITE_INPUT_SLOT_INDEX).getItem() == ModItems.GRAPHITE &&
                (getStack(SAND_INPUT_SLOT_INDEX).getItem() == ModItems.SAND ||
                getStack(SAND_INPUT_SLOT_INDEX).getItem() == Items.SAND);
        boolean hasEnough = getStack(GRAPHITE_INPUT_SLOT_INDEX).getCount() >= 1 &&
                getStack(SAND_INPUT_SLOT_INDEX).getCount() >= 4;
        return hasInput && hasEnough && canInsertIntoOutput(result);
    }

    @Override
    public boolean canInsertIntoOutput(ItemStack result) {
        boolean validItem = this.getStack(OUTPUT_SLOT_INDEX).getItem() == result.getItem() || this.getStack(OUTPUT_SLOT_INDEX).isEmpty();
        boolean validSize = this.getStack(OUTPUT_SLOT_INDEX).getCount() + result.getCount() <= getStack(OUTPUT_SLOT_INDEX).getMaxCount();
        return validItem && validSize;
    }


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

    @Override
    public boolean isValid(int slot, ItemStack input) {
        if (slot == OUTPUT_SLOT_INDEX) {
            return false;
        }

        if (slot == GRAPHITE_INPUT_SLOT_INDEX) {
            ItemStack itemStack = this.inventory.get(GRAPHITE_INPUT_SLOT_INDEX);
            return (input.isOf(ModItems.GRAPHITE))
                    && (itemStack.isOf(input.getItem()) || itemStack.isEmpty());
        }

        if (slot == SAND_INPUT_SLOT_INDEX) {
            ItemStack itemStack = this.inventory.get(SAND_INPUT_SLOT_INDEX);
            return (input.isOf(Items.SAND) || input.isOf(ModItems.SAND))
                    && (itemStack.isOf(input.getItem()) || itemStack.isEmpty());
        }

        // something went wrong
        return false;
    }
}
