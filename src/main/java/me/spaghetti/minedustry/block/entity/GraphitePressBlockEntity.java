package me.spaghetti.minedustry.block.entity;

import me.spaghetti.minedustry.block.enums.TwoByTwoCorner;
import me.spaghetti.minedustry.item.ModItems;
import me.spaghetti.minedustry.screen.GraphitePressScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
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
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static me.spaghetti.minedustry.block.custom.GraphitePressBlock.CORNER;
import static me.spaghetti.minedustry.block.custom.GraphitePressBlock.getMasterPos;

public class GraphitePressBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;

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

    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient()) {
            return;
        }
        if (state.get(CORNER) != TwoByTwoCorner.NORTH_WEST) {
            BlockEntity blockEntity = world.getBlockEntity(getMasterPos(pos, state));
            if (blockEntity instanceof GraphitePressBlockEntity) {
                inventory = ((GraphitePressBlockEntity) blockEntity).inventory;
            }
        } else {
            updateCraft(world, pos, state);

            // todo
            //tryTransfer(world);
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
                this.resetProgress();
            }
        } else {
            this.resetProgress();
            markDirty(world, pos, state);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void craftItem() {
        this.removeStack(INPUT_SLOT, 1);
        ItemStack result = new ItemStack(ModItems.GRAPHITE);

        this.setStack(OUTPUT_SLOT, new ItemStack(result.getItem(), getStack(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() {
        this.progress++;
    }

    private boolean hasRecipe() {
        ItemStack result = new ItemStack(ModItems.GRAPHITE);
        boolean hasInput = getStack(INPUT_SLOT).getItem() == ModItems.COAL || getStack(INPUT_SLOT).getItem() == Items.COAL;

        return hasInput && canInsertAmountIntoOutputSlot(result) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.getStack(OUTPUT_SLOT).getItem() == item || this.getStack(OUTPUT_SLOT).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
        return this.getStack(OUTPUT_SLOT).getCount() + result.getCount() <= getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }


}
