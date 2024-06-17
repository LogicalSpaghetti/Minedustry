package me.spaghetti.minedustry.block.abstractions;

import me.spaghetti.minedustry.block.helpers.ImplementedInventory;
import me.spaghetti.minedustry.block.helpers.enums.Relationship;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static me.spaghetti.minedustry.block.abstractions.MinedustryBlock.RELATIONSHIP;
import static me.spaghetti.minedustry.block.abstractions.MinedustryBlock.getControlPos;

// todo: power must be handled here because of shield walls
public abstract class MinedustryBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    public DefaultedList<ItemStack> inventory;

    public MinedustryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize) {
        super(type, pos, state);
        inventory = DefaultedList.ofSize(inventorySize, ItemStack.EMPTY);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.getPos());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return null;
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            clientTick(world, pos, state);
            return;
        }
        if (state.get(RELATIONSHIP) == Relationship.COMMAND) {
            commandTick(world, pos, state);
        } else {
            childTick(world, pos, state);
        }
    }

    public abstract void commandTick(World world, BlockPos pos, BlockState state);

    // rendering
    public void clientTick(World world, BlockPos pos, BlockState state) {

    }

    public void childTick(World world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = world.getBlockEntity(getControlPos(pos, state));
        if (blockEntity instanceof MinedustryBlockEntity) {
            inventory = ((MinedustryBlockEntity) blockEntity).inventory;
        }
    }

    public abstract boolean isValidPowerConnection();

    public int getSize(BlockState state) {
        if (state.getBlock() instanceof MinedustryBlock) {
            return ((MinedustryBlock) state.getBlock()).SIZE;
        }
        return 0;
    }
}
