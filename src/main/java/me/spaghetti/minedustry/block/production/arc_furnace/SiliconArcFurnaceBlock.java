package me.spaghetti.minedustry.block.production.arc_furnace;

import me.spaghetti.minedustry.block.entity.ModBlockEntities;
import me.spaghetti.minedustry.block.helpers.enums.TwoByTwoCorner;
import net.minecraft.block.*;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

//todo: inventory doesn't drop, even when destroying the controller
public class SiliconArcFurnaceBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final EnumProperty<TwoByTwoCorner> CORNER = EnumProperty.of("corner", TwoByTwoCorner.class);
    public SiliconArcFurnaceBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(CORNER, TwoByTwoCorner.NORTH_WEST));

    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SiliconArcFurnaceBlockEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        BlockPos controlPos = getMasterPos(pos, state);
        world.breakBlock(controlPos, false);
        world.breakBlock(controlPos.south(), false);
        world.breakBlock(controlPos.east(), false);
        world.breakBlock(controlPos.south().east(), false);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof AbstractFurnaceBlockEntity) {
            if (world instanceof ServerWorld) {
                ItemScatterer.spawn(world, pos, (AbstractFurnaceBlockEntity)blockEntity);
                ((AbstractFurnaceBlockEntity)blockEntity).getRecipesUsedAndDropExperience((ServerWorld)world, Vec3d.ofCenter(pos));
            }
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            BlockPos controlPos = getMasterPos(pos, state);

            NamedScreenHandlerFactory screenHandlerFactory = ((SiliconArcFurnaceBlockEntity) world.getBlockEntity(controlPos));

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, ModBlockEntities.SILICON_ARC_FURNACE_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        // north-west will be the primary block
        world.setBlockState(pos.east(), state.with(CORNER, TwoByTwoCorner.NORTH_EAST), Block.NOTIFY_ALL);
        world.setBlockState(pos.south(), state.with(CORNER, TwoByTwoCorner.SOUTH_WEST), Block.NOTIFY_ALL);
        world.setBlockState(pos.east().south(), state.with(CORNER, TwoByTwoCorner.SOUTH_EAST), Block.NOTIFY_ALL);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CORNER);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (world.getBlockState(pos.east()).isReplaceable() &&
                world.getBlockState(pos.south()).isReplaceable() &&
                world.getBlockState(pos.east().south()).isReplaceable()) {

            return super.canPlaceAt(state, world, pos);
        }
        return false;
    }

    public static BlockPos getMasterPos(BlockPos pos, BlockState state) {
        BlockPos controlPos = pos;
        if (state.get(CORNER) == TwoByTwoCorner.NORTH_EAST || state.get(CORNER) == TwoByTwoCorner.SOUTH_EAST) {
            controlPos = controlPos.west();
        }
        if (state.get(CORNER) == TwoByTwoCorner.SOUTH_EAST || state.get(CORNER) == TwoByTwoCorner.SOUTH_WEST) {
            controlPos = controlPos.north();
        }
        return controlPos;
    }
}
