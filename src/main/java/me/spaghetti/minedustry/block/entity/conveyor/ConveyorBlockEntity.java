package me.spaghetti.minedustry.block.entity.conveyor;

import me.spaghetti.minedustry.block.helpers.ImplementedInventory;
import me.spaghetti.minedustry.block.entity.ModBlockEntities;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static me.spaghetti.minedustry.block.entity.conveyor.ConveyorBlock.FACING;

public class ConveyorBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    private static final int INPUT_SLOT_INDEX = 0;
    private static final int MIDDLE_SLOT_INDEX = 1;
    private static final int OUTPUT_SLOT_INDEX = 2;


    private static final int[] FIRST_SLOTS = new int[]{0};
    private static final int[] MIDDLE_SLOTS = new int[]{1};
    private static final int[] LAST_SLOTS = new int[]{2};
    private static final int[] REVERSED_INVENTORY = new int[]{2, 1, 0};

    final int TRANSFER_COOLDOWN = 8;
    int[] transferCooldowns = {TRANSFER_COOLDOWN, TRANSFER_COOLDOWN, TRANSFER_COOLDOWN};

    public ConveyorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CONVEYOR_BLOCK_ENTITY, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.getPos());
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("display.minedustry.conveyor");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return null;
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        /*if (world.isClient) {
        }*/

        if (canSendOut(world, pos, state))
            trySendForwards(world, pos, state);
        trySlotTransfer(1);
        trySlotTransfer(0);
    }

    private void trySlotTransfer(int from) {
        int to = from + 1;
        if (transferCooldowns[from] <= 0 && !this.getStack(from).isEmpty() && this.getStack(to).isEmpty()) {
            this.setStack(to, this.getStack(from));
            this.setStack(from, ItemStack.EMPTY);

            transferCooldowns[from] = TRANSFER_COOLDOWN;
            transferCooldowns[to] = TRANSFER_COOLDOWN;
        } else if (transferCooldowns[from] > 0 && !this.getStack(from).isEmpty()){
            transferCooldowns[from]--;
        }
    }

    private boolean canSendOut(World world, BlockPos pos, BlockState state) {
        return !this.getStack(OUTPUT_SLOT_INDEX).isEmpty();
    }

    private void trySendForwards(World world, BlockPos pos, BlockState state) {
        if (transferCooldowns[2] > 0) {
            transferCooldowns[2]--;
            return;
        }
        // get the inventory in front and the slots we are allowed to interact with if it's a sidedBlockEntity
        Inventory destination = getOutputInventory(world, pos, state);
        if (destination == null) {
            return;
        }
        if (destination.getStack(0) == null) {
            return;
        }
        int[] validSlots = getValidSlots(destination, state);
        if (validSlots.length == 0) {
            return;
        }
        // if it's empty or doesn't exist, return false
        // if it does have slots, set up for the second method and call it
        trySendForwards(destination, validSlots);
    }

    private void trySendForwards(Inventory to, int[] slots) {
        boolean transactionOccured = false;
        // go through trying to find the first slot of a similar type or that is empty
        for (int currentSlot : slots) {
            ItemStack currentDest = to.getStack(currentSlot);

            // if it finds an empty slot, transfer the stack, make sure it gets marked, and break;
            if (currentDest.isEmpty()) {
                ItemStack movedItem = this.getStack(OUTPUT_SLOT_INDEX).copy();
                movedItem.setCount(1);
                to.setStack(currentSlot, movedItem);
                transactionOccured = true;
                break;
            }

            // if it finds a similar slot, add one item
            if (ItemStack.canCombine(currentDest, this.getStack(OUTPUT_SLOT_INDEX)) && currentDest.getCount() < currentDest.getMaxCount()) {
                currentDest.increment(1);
                to.setStack(currentSlot, currentDest);
                transactionOccured = true;
                break;
            }
        }

        // if we iterate through every slot and still have items remaining, return false
        if (transactionOccured) {
            this.getStack(OUTPUT_SLOT_INDEX).decrement(1);
            transferCooldowns[2] = TRANSFER_COOLDOWN;
            this.markDirty();
            to.markDirty();
        }
    }

    private int[] getValidSlots(Inventory destination, BlockState state) {
        Direction side = state.get(FACING);
        if (destination instanceof SidedInventory) {
            return ((SidedInventory) destination).getAvailableSlots(side);
        } else {
            // this solution is terrible
            int size = destination.size();
            int[] returnArray = new int[size];
            for (int i = 0; i < returnArray.length; i++) {
                returnArray[i] = i;
            }
            return returnArray;
        }
    }

    @Nullable
    private static Inventory getOutputInventory(World world, BlockPos pos, BlockState state) {
        Direction direction = state.get(FACING);
        return HopperBlockEntity.getInventoryAt(world, pos.offset(direction));
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.DOWN) {
            return REVERSED_INVENTORY;
        }
        return FIRST_SLOTS;
    }

    private static Direction getFacing(Direction side, Direction facing) {
        Direction relativeDirection = side.getOpposite().getOpposite();
        if (relativeDirection != Direction.DOWN && relativeDirection != Direction.UP){
            int times = 0;
            if (facing == Direction.EAST) {
                times = 3;
            } else if (facing == Direction.SOUTH) {
                times = 2;
            } else if (facing == Direction.WEST) {
                times = 1;
            }

            for (int i = 0; i < times; i++) {
                relativeDirection = relativeDirection.rotateYClockwise();
            }
        }
        return relativeDirection;
    }

    public ItemStack getFirstRenderStack() {
        return this.getStack(INPUT_SLOT_INDEX);
    }

    public ItemStack getSecondRenderStack() {
        return this.getStack(MIDDLE_SLOT_INDEX);
    }

    public ItemStack getThirdRenderStack() {
        return this.getStack(OUTPUT_SLOT_INDEX);
    }

    public int[] getProgress() {
        return transferCooldowns;
    }

    public Direction getFacing() {
        return this.getCachedState().get(FACING);
    }

    @Override
    public void markDirty() {
        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        super.markDirty();
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    public Direction getBeltFacing() {
        return this.getCachedState().get(FACING);
    }
}
