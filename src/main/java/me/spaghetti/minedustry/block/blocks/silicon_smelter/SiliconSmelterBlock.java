package me.spaghetti.minedustry.block.blocks.silicon_smelter;

import me.spaghetti.minedustry.block.ModBlockEntities;
import me.spaghetti.minedustry.block.blocks.MinedustryBlock;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SiliconSmelterBlock extends MinedustryBlock {
    public SiliconSmelterBlock(Settings settings) {
        super(settings, 2);

    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SiliconSmelterBlockEntity(pos, state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            BlockPos controlPos = getControlPos(pos, state);

            NamedScreenHandlerFactory screenHandlerFactory = ((SiliconSmelterBlockEntity) world.getBlockEntity(controlPos));

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.SILICON_SMELTER_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}
