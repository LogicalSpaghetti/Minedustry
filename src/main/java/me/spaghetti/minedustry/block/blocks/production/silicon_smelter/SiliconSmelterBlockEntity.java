package me.spaghetti.minedustry.block.blocks.production.silicon_smelter;

import me.spaghetti.minedustry.block.ModBlockEntities;
import me.spaghetti.minedustry.block.abstractions.CraftingBlockEntity;
import me.spaghetti.minedustry.block.interfaces.ImplementedInventory;
import me.spaghetti.minedustry.item.ModItems;
import me.spaghetti.minedustry.screen.silicon_smelter.SiliconSmelterScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class SiliconSmelterBlockEntity extends CraftingBlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {

    public SiliconSmelterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SILICON_SMELTER_BLOCK_ENTITY, pos, state,
                "display.minedustry.silicon-smelter", 10,
                new ItemStack[]{new ItemStack(ModItems.COAL), new ItemStack(ModItems.SAND, 2)},
                new ItemStack[]{new ItemStack(ModItems.SILICON, 1)});
        craftingTime = 13; // 20tps * 2/3s // slightly too fast

    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SiliconSmelterScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
}
