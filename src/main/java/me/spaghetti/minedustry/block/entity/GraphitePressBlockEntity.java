package me.spaghetti.minedustry.block.entity;

import me.spaghetti.minedustry.block.enums.TwoByTwoCorner;
import me.spaghetti.minedustry.item.ModItems;
import me.spaghetti.minedustry.screen.GraphitePressScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static me.spaghetti.minedustry.block.custom.GraphitePressBlock.CORNER;
import static me.spaghetti.minedustry.block.custom.GraphitePressBlock.getMasterPos;

public class GraphitePressBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private static final int INPUT_SLOT_INDEX = 0;
    private static final int OUTPUT_SLOT_INDEX = 1;

    private static final int[] IN_SLOTS = new int[]{0};
    private static final int[] OUT_SLOTS = new int[]{1};
    private static final int[] ALL_SLOTS = new int[]{0, 1};

    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 30; // 20tps * 1.5s

    public GraphitePressBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GRAPHITE_PRESS_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> GraphitePressBlockEntity.this.progress;
                    case 1 -> GraphitePressBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> GraphitePressBlockEntity.this.progress = value;
                    case 1 -> GraphitePressBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
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
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.getPos());
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("display.minedustry.graphite_press");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new GraphitePressScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public void serverTick(World world, BlockPos pos, BlockState state) {
        if(world.isClient()) {
            return;
        }
        if (state.get(CORNER) == TwoByTwoCorner.NORTH_WEST) {
            updateCraft(world, pos, state);

            // todo
            //tryTransfer(world);
        } else {
            BlockEntity blockEntity = world.getBlockEntity(getMasterPos(pos, state));
            if (blockEntity instanceof GraphitePressBlockEntity) {
                inventory = ((GraphitePressBlockEntity) blockEntity).inventory;
            }
        }
    }

    private void updateCraft(World world, BlockPos pos, BlockState state) {
        if(isOutputSlotEmptyOrReceivable()) {
            if(this.hasRecipe()) {
                this.increaseCraftProgress();
                markDirty(world, pos, state);

                if(hasCraftingFinished()) {
                    this.craftItem();
                    this.resetProgress();
                }
            } else {
                this.lowerProgress();
            }
        } else {
            this.resetProgress();
            markDirty(world, pos, state);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void lowerProgress() {
        if (this.progress > 0)
            this.progress--;
    }

    private void craftItem() {
        this.removeStack(INPUT_SLOT_INDEX, 2);
        ItemStack result = new ItemStack(ModItems.GRAPHITE);

        this.setStack(OUTPUT_SLOT_INDEX, new ItemStack(result.getItem(), getStack(OUTPUT_SLOT_INDEX).getCount() + result.getCount()));
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() {
        this.progress++;
    }

    private boolean hasRecipe() {
        ItemStack result = new ItemStack(ModItems.GRAPHITE);
        boolean hasInput =
                getStack(INPUT_SLOT_INDEX).getItem() == ModItems.COAL ||
                getStack(INPUT_SLOT_INDEX).getItem() == Items.COAL;
        hasInput = hasInput && getStack(INPUT_SLOT_INDEX).getCount() > 1;
        return hasInput && canInsertAmountIntoOutputSlot(result) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.getStack(OUTPUT_SLOT_INDEX).getItem() == item || this.getStack(OUTPUT_SLOT_INDEX).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
        return this.getStack(OUTPUT_SLOT_INDEX).getCount() + result.getCount() <= getStack(OUTPUT_SLOT_INDEX).getMaxCount();
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.getStack(OUTPUT_SLOT_INDEX).isEmpty() || this.getStack(OUTPUT_SLOT_INDEX).getCount() < this.getStack(OUTPUT_SLOT_INDEX).getMaxCount();
    }

    // returns which slots a hopper is allowed to interact with given its side
    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.DOWN) {
            return OUT_SLOTS;
        }
        if (side == Direction.UP) {
            return IN_SLOTS;
        }
        return ALL_SLOTS;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        return ImplementedInventory.super.canInsert(slot, stack, side);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        return ImplementedInventory.super.canExtract(slot, stack, side);
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
