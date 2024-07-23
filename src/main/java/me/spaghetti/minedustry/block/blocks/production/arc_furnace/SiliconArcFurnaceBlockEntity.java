package me.spaghetti.minedustry.block.blocks.production.arc_furnace;

import me.spaghetti.minedustry.block.ModBlockEntities;
import me.spaghetti.minedustry.block.block_util.abstractions.CraftingBlockEntity;
import me.spaghetti.minedustry.item.ModItems;
import me.spaghetti.minedustry.screen.arc_furnace.SiliconArcFurnaceScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class SiliconArcFurnaceBlockEntity extends CraftingBlockEntity {

    public SiliconArcFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SILICON_ARC_FURNACE_BLOCK_ENTITY, pos, state,
                "display.minedustry.silicon-arc-furnace", 30,
                new ItemStack[]{new ItemStack(ModItems.GRAPHITE), new ItemStack(ModItems.SAND, 4)},
                new ItemStack[]{new ItemStack(ModItems.SILICON, 4)});
        craftingTime = 16; // 20tps * 0.83333s, rounded down
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SiliconArcFurnaceScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }
}
