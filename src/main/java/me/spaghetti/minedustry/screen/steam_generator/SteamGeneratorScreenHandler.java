package me.spaghetti.minedustry.screen.steam_generator;

import me.spaghetti.minedustry.Minedustry;
import me.spaghetti.minedustry.block.energy.steam_generator.SteamGeneratorBlockEntity;
import me.spaghetti.minedustry.screen.AbstractModScreenHandler;
import me.spaghetti.minedustry.screen.ModScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.slot.Slot;

public class SteamGeneratorScreenHandler extends AbstractModScreenHandler {
    private PropertyDelegate propertyDelegate;
    private final SteamGeneratorBlockEntity blockEntity;

    public SteamGeneratorScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(4));
    }

    public SteamGeneratorScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.STEAM_GENERATOR_SCREEN_HANDLER, syncId, (Inventory) blockEntity);
        checkSize(inventory, 1);
        playerInventory.onOpen(playerInventory.player);
        this.propertyDelegate = propertyDelegate;
        this.blockEntity = (SteamGeneratorBlockEntity) blockEntity;

        this.addSlot(new Slot(inventory, 0, 56 + 24, 36));
        this.addSlot(new Slot(inventory, 1, 35, 58));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(this.propertyDelegate);
    }


    public int getScaledProgress(int height) {
        int volume = this.propertyDelegate.get(2);
        double maxVolume = this.propertyDelegate.get(3); // set to a double to prevent integer division rounding errors

        if (maxVolume == 0) {
            Minedustry.LOGGER.info("maxVolume is zero");
            return 0;
        }
        return (int) (height * (volume / maxVolume));
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
