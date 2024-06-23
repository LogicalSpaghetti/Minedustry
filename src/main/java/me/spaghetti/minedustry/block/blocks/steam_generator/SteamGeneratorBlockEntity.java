package me.spaghetti.minedustry.block.blocks.steam_generator;

import me.spaghetti.minedustry.block.ModBlockEntities;
import me.spaghetti.minedustry.block.blocks.MinedustryBlockEntity;
import me.spaghetti.minedustry.screen.steam_generator.SteamGeneratorScreenHandler;
import me.spaghetti.minedustry.util.FluidStorage;
import me.spaghetti.minedustry.util.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
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

public class SteamGeneratorBlockEntity extends MinedustryBlockEntity {
    private static final int FUEL_SLOT_INDEX = 0;
    private static final int BUCKET_SLOT_INDEX = 1;



    private static final int[] ALL_SLOTS = new int[]{0, 1};

    private int progress = 0;
    private int maxProgress = 30; // 20tps * 1.5s
    public static final Fluid FIRST_FLUID = Fluids.WATER;
    public static final int mB_PER_CRAFT = 6000;

    public final FluidStorage fluidStorage = new FluidStorage() {
        @Override
        public void changeSucceeded() {
            markDirty();
        }

        @Override
        public long getCapacity() {
            return 10 * mBPerBucket;
        }
    };



    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> SteamGeneratorBlockEntity.this.progress;
                case 1 -> SteamGeneratorBlockEntity.this.maxProgress;
                case 2 -> (int) SteamGeneratorBlockEntity.this.fluidStorage.amount;
                case 3 -> (int) SteamGeneratorBlockEntity.this.fluidStorage.getCapacity();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> SteamGeneratorBlockEntity.this.progress = value;
                case 1 -> SteamGeneratorBlockEntity.this.maxProgress = value;
                case 2 -> SteamGeneratorBlockEntity.this.fluidStorage.amount = value;
                case 3 -> {}
            }
        }

        @Override
        public int size() {
            return 4;
        }
    };

    public SteamGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STEAM_GENERATOR_BLOCK_ENTITY, pos, state, 2);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("steam_generator.progress", progress);
        nbt.putLong("steam_generator.fluid", fluidStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("steam_generator.progress");
        fluidStorage.amount = nbt.getLong("steam_generator.fluid");
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.getPos());
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("display.minedustry.steam-generator");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SteamGeneratorScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public void serverCommandTick(World world, BlockPos pos, BlockState state) {
        checkBucket();
        updateCraft();
    }

    @Override
    public boolean isValidPowerConnection() {
        return true;
    }

    @Override
    public boolean isItemOutput() {
        return false;
    }

    private void checkBucket() {
        if (inventory.get(1).isOf(FIRST_FLUID.getBucketItem())) {
            if (fluidStorage.tryInsertBucket()) {
                setStack(BUCKET_SLOT_INDEX, new ItemStack(Items.BUCKET));
            }
        }
    }

    private void updateCraft() {
        // if the block has enough resources
        if (hasNecessaryMaterials()) {
            progressCrafting();
        } else {
            progress = 0;
        }
    }

    private void progressCrafting() {
        progress++;
        markDirty();

        if (progress >= maxProgress) {
            removeStack(FUEL_SLOT_INDEX, 1);
            fluidStorage.tryExtract(mB_PER_CRAFT);

            progress = 0;
        }
    }

    private boolean hasNecessaryMaterials() {
        boolean hasInput =
                getStack(FUEL_SLOT_INDEX).getItem().getDefaultStack().isIn(ModTags.Items.STEAM_GENERATOR_FUEL);
        boolean hasEnoughResources = getStack(FUEL_SLOT_INDEX).getCount() >= 1 && fluidStorage.amount >= mB_PER_CRAFT;
        return hasInput && hasEnoughResources;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return ALL_SLOTS;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        if (slot == FUEL_SLOT_INDEX) {
            return stack.isIn(ModTags.Items.STEAM_GENERATOR_FUEL);
        }
        if (slot == BUCKET_SLOT_INDEX) {
            return stack.isOf(Items.WATER_BUCKET);
        }
        return super.canInsert(slot, stack, side);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        if (slot == BUCKET_SLOT_INDEX) {
            return !stack.isOf(FIRST_FLUID.getBucketItem());
        }
        return super.canExtract(slot, stack, side);
    }

    @Override
    public boolean isValid(int slot, ItemStack input) {
        if (slot == FUEL_SLOT_INDEX) {
            ItemStack itemStack = this.inventory.get(FUEL_SLOT_INDEX);
            return input.isIn(ModTags.Items.STEAM_GENERATOR_FUEL)
                    && (itemStack.isOf(input.getItem()) || itemStack.isEmpty());
        }

        // something went wrong
        return false;
    }
}
