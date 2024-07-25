package me.spaghetti.minedustry.block.blocks.production.graphite_press;

import me.spaghetti.minedustry.block.abstractions.CraftingBlockEntity;
import me.spaghetti.minedustry.block.ModBlockEntities;
import me.spaghetti.minedustry.item.ModItems;
import me.spaghetti.minedustry.screen.graphite_press.GraphitePressScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class GraphitePressBlockEntity extends CraftingBlockEntity {

    public GraphitePressBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GRAPHITE_PRESS_BLOCK_ENTITY,
                pos, state,
                "display.minedustry.graphite-press", 10,
                new ItemStack[]{new ItemStack(ModItems.COAL, 2)},
                new ItemStack[]{new ItemStack(ModItems.GRAPHITE)});
        craftingTime = 30; // 20tps * 1.5s
        isValidPowerConnection = false;
        consumesPower = false;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new GraphitePressScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
}
