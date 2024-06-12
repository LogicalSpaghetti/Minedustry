package me.spaghetti.minedustry.block.production.graphite_press;

import me.spaghetti.minedustry.block.ModBlockEntities;
import me.spaghetti.minedustry.block.abstractions.MinedustryBlock;
import me.spaghetti.minedustry.block.helpers.MultiBlock;
import me.spaghetti.minedustry.block.helpers.enums.Relationship;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

//todo: inventory doesn't drop, even when destroying the controller
public class GraphitePressBlock extends MinedustryBlock {

    public GraphitePressBlock(Settings settings) {
        super(settings, 2);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(RELATIONSHIP, Relationship.COMMAND)
                .with(X_OFFSET, 0)
                .with(Y_OFFSET, 0)
                .with(Z_OFFSET, 0));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GraphitePressBlockEntity(pos, state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            BlockPos controlPos = getControlPos(pos, state);

            NamedScreenHandlerFactory screenHandlerFactory = ((GraphitePressBlockEntity) world.getBlockEntity(controlPos));

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, ModBlockEntities.GRAPHITE_PRESS_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        // north-west will be the primary block
        BlockPos[] offsets = MultiBlock.getOffsets(SIZE);
        for (int i = 1; i < offsets.length; i++) {
            world.setBlockState(pos.add(offsets[i]), state
                            .with(RELATIONSHIP, Relationship.CHILD)
                            .with(X_OFFSET, offsets[i].getX())
                            .with(Y_OFFSET, offsets[i].getY())
                            .with(Z_OFFSET, offsets[i].getZ()),
                    Block.NOTIFY_ALL);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(RELATIONSHIP);
        builder.add(X_OFFSET);
        builder.add(Y_OFFSET);
        builder.add(Z_OFFSET);
    }
}
