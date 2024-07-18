package me.spaghetti.minedustry.block.blocks.conveyor.conveyor;

import me.spaghetti.minedustry.Minedustry;
import me.spaghetti.minedustry.block.block_util.block_interfaces.ImplementedInventory;
import me.spaghetti.minedustry.block.ModBlockEntities;
import me.spaghetti.minedustry.block.block_util.helpers.TransferringHelper;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
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

import static me.spaghetti.minedustry.block.blocks.conveyor.conveyor.ConveyorBlock.FACING;

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
        // todo: figure out how the server is communicating the item when it's swapped, and do the same every time its position changes
        if (world.isClient) {
            return;
        }
        if (canSendOut(world, pos, state))
            trySendForwards(world, pos, state);
        trySlotTransfer(1);
        trySlotTransfer(0);
    }

    // attempts to move an item from one slot to the next within the belt
    private void trySlotTransfer(int fromSlotIndex) {
        int toSlotIndex = fromSlotIndex + 1;
        if (transferCooldowns[fromSlotIndex] <= 0 && !this.getStack(fromSlotIndex).isEmpty() && this.getStack(toSlotIndex).isEmpty()) {
            this.setStack(toSlotIndex, this.getStack(fromSlotIndex));
            this.setStack(fromSlotIndex, ItemStack.EMPTY);

            transferCooldowns[fromSlotIndex] = TRANSFER_COOLDOWN;
            transferCooldowns[toSlotIndex] = TRANSFER_COOLDOWN;
        } else if (transferCooldowns[fromSlotIndex] > 0 && !this.getStack(fromSlotIndex).isEmpty()){
            transferCooldowns[fromSlotIndex]--;
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
        int[] validSlots = TransferringHelper.getValidSlots(destination, state, state.get(FACING).getOpposite());
        if (validSlots.length == 0) {
            return;
        }
        // if it's empty or doesn't exist, return false
        // if it does have slots, set up for the second method and call it
        if (TransferringHelper.trySendForwards(this, destination, validSlots, OUTPUT_SLOT_INDEX)) {
            transferCooldowns[2] = TRANSFER_COOLDOWN;
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
        if (side == this.getCachedState().get(FACING)) {
            return new int[] {};
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

    public int[] getProgress() {
        return transferCooldowns;
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
