package me.spaghetti.minedustry.block.blocks.conveyor.conveyor;

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
    private static final int OUTPUT_SLOT_INDEX = 2;


    private static final int[] FIRST_SLOTS = new int[]{0};
    private static final int[] REVERSED_INVENTORY = new int[]{2, 1, 0};

    private final int TRANSFER_TIME = 8;
    private final int[] transferProgress = {TRANSFER_TIME, TRANSFER_TIME, TRANSFER_TIME};

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
        // todo: fix visual de-sync
        if (world.isClient) {
            return;
        }
        handleItems(world, pos, state);

    }

    private void handleItems(World world, BlockPos pos, BlockState state) {
        if (readySlot(2))
            trySendForwards(world, pos, state);
        if (readySlot(1))
            trySlotTransfer(1, 2);
        if (readySlot(0))
            trySlotTransfer(0, 1);
    }

    private boolean readySlot(int slot) {
        if (slotOpen(slot))
            return false;
        if (transferProgress[slot] < 1) {
            return true;
        } else {
            transferProgress[slot]--;
            return false;
        }
    }

    private boolean slotOpen(int slot) {
        return this.getStack(slot).isEmpty();
    }

    // attempts to move an item from one slot to the next within the belt
    private void trySlotTransfer(int fromIndex, int toIndex) {
        if (slotOpen(toIndex)) {
            this.setStack(toIndex, this.getStack(fromIndex));
            this.setStack(fromIndex, ItemStack.EMPTY);

            resetProgress(fromIndex);
            markDirty();
        }
    }

    private void resetProgress(int slot) {
        transferProgress[slot] = TRANSFER_TIME;
    }

    private void trySendForwards(World world, BlockPos pos, BlockState state) {
        // get the inventory in front and the slots we are allowed to interact with if it's a sidedBlockEntity
        Inventory dest = getOutputInventory(world, pos, state);
        if (dest == null || dest.getStack(0) == null) return;

        int[] destSlots = TransferringHelper.getValidSlots(dest, state.get(FACING).getOpposite());
        if (destSlots.length == 0) {
            return;
        }

        if (TransferringHelper.tryExternalTransfer(this, dest, destSlots, OUTPUT_SLOT_INDEX)) {
            transferProgress[OUTPUT_SLOT_INDEX] = TRANSFER_TIME;
            markDirty();
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
        nbt.putIntArray("cooldowns", transferProgress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        int[] cds = nbt.getIntArray("cooldowns");
        transferProgress[0] = cds[0];
        transferProgress[1] = cds[1];
        transferProgress[2] = cds[2];
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

    public int[] getProgress() {
        return this.transferProgress;
    }

    @Override
    public void markDirty() {
        assert world != null;
        world.updateListeners(pos, getCachedState(), getCachedState(), ConveyorBlock.NOTIFY_ALL);
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
