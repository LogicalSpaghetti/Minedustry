package me.spaghetti.minedustry.block.energy;

import me.spaghetti.minedustry.block.ModBlockEntities;
import me.spaghetti.minedustry.block.abstractions.MinedustryBlock;
import net.minecraft.block.BlockState;
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

public class PowerNodeBlock extends MinedustryBlock {
    public PowerNodeBlock(Settings settings) {
        super(settings, 1);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PowerNodeBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.POWER_NODE_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockPos controlPos = getControlPos(pos, state);
        if (!world.isClient) {

            NamedScreenHandlerFactory screenHandlerFactory = ((PowerNodeBlockEntity) world.getBlockEntity(controlPos));

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        ((PowerNodeBlockEntity) world.getBlockEntity(controlPos)).addConnection(pos.add(1, 1,1), 0);

        return ActionResult.SUCCESS;
    }
}
