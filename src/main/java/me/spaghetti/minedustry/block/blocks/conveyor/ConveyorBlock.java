package me.spaghetti.minedustry.block.blocks.conveyor;

import me.spaghetti.minedustry.block.ModBlockEntities;
import me.spaghetti.minedustry.block.block_util.properties.ConveyorShape;
import me.spaghetti.minedustry.util.ModProperties;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class ConveyorBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final EnumProperty<ConveyorShape> CONVEYOR_SHAPE = ModProperties.CONVEYOR_SHAPE;

    public static final BooleanProperty BACK_CONNECTION = BooleanProperty.of("back");
    public static final BooleanProperty LEFT_CONNECTION = BooleanProperty.of("left");
    public static final BooleanProperty RIGHT_CONNECTION = BooleanProperty.of("right");

    // todo: decide on whether to have this.
    //  down would require no side connections, and up would require at least no front connection
/*
    public static final BooleanProperty UP_CONNECTION = BooleanProperty.of("up_connection");
    public static final BooleanProperty DOWN_CONNECTION = BooleanProperty.of("down_connection");
*/

    protected static final VoxelShape OUTLINE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);

    final BlockState defaultBlockState = this.getDefaultState()
            .with(CONVEYOR_SHAPE, ConveyorShape.STRAIGHT)
            .with(FACING, Direction.NORTH)
            .with(BACK_CONNECTION, false)
            .with(LEFT_CONNECTION, false)
            .with(RIGHT_CONNECTION, false);

    public ConveyorBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ConveyorBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CONVEYOR_SHAPE, FACING, BACK_CONNECTION, LEFT_CONNECTION, RIGHT_CONNECTION);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        super.onSteppedOn(world, pos, state, entity);
        if (entity instanceof PlayerEntity) {
            if (entity.isSneaking()) {
                return;
            }
        }
        Vector3f vec = state.get(FACING).getUnitVector();
        Vec3d vec2 = new Vec3d(vec.x, vec.y, vec.z);
        vec2 = vec2.multiply(0.14d);
        entity.setVelocity(vec2);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getPlacementState(ctx.getWorld(),
                this.defaultBlockState.with(FACING, ctx.getHorizontalPlayerFacing()), ctx.getBlockPos());
    }

    // todo: replace the block entity stuff with straight block/state references
    private BlockState getPlacementState(BlockView world, BlockState state, BlockPos pos) {
        Direction[] offsets = {
                Direction.NORTH,
                Direction.EAST,
                Direction.SOUTH,
                Direction.WEST
        };
        BlockEntity[] directionalEntities = {
                world.getBlockEntity(pos.offset(offsets[0])),
                world.getBlockEntity(pos.offset(offsets[1])),
                world.getBlockEntity(pos.offset(offsets[2])),
                world.getBlockEntity(pos.offset(offsets[3]))
        };
        boolean[] connections = {false, false, false, false};

        for(int i = 0; i < 4; i++) {
            if (directionalEntities[i] == null || directionalEntities[i].getCachedState() == null) {

                continue;
            }

            // makes sure the block is a conveyor
            if (!(directionalEntities[i] instanceof ConveyorBlockEntity)) {
                continue;
            }

            // if the block is facing towards us
            if (directionalEntities[i].getCachedState().get(Properties.HORIZONTAL_FACING) == offsets[i].getOpposite()) {
                connections[i] = true;
            }
        }

        boolean[] rotatedConnections = connections.clone();
        int rotationCount = getRotationsForDirection(state.get(FACING));
        for (int i = 0; i < 4; i++) {
            rotatedConnections[i] = connections[(i + rotationCount) % 4];
        }

        boolean right = false;
        boolean back = false;
        boolean left = false;

        // finally set the states
        if (rotatedConnections[1]) {
            right = true;
        }
        if (rotatedConnections[2]) {
            back = true;
        }
        if (rotatedConnections[3]) {
            left = true;
        }

        ConveyorShape shape = getConveyorShape(right, left, back);

        return state
                .with(CONVEYOR_SHAPE, shape)
                .with(LEFT_CONNECTION, left)
                .with(RIGHT_CONNECTION, right)
                .with(BACK_CONNECTION, back);
    }

    private static @NotNull ConveyorShape getConveyorShape(boolean right, boolean left, boolean back) {
        boolean omni = right && left && back;
        boolean rightLeft = right && left && !back;
        boolean leftStraight = left && back && !right;
        boolean rightStraight = right && back && !left;

        ConveyorShape shape = ConveyorShape.STRAIGHT;

        if (omni) {
            shape = ConveyorShape.OMNI;
        } else if (rightLeft) {
            shape = ConveyorShape.RIGHT_LEFT;
        } else if (leftStraight) {
            shape = ConveyorShape.LEFT_SIDE_STRAIGHT;
        } else if (rightStraight) {
            shape = ConveyorShape.RIGHT_SIDE_STRAIGHT;
        } else if (right) {
            shape = ConveyorShape.RIGHT_CURVE;
        } else if (left) {
            shape = ConveyorShape.LEFT_CURVE;
        }
        return shape;
    }

    // todo, change the configuration when the blocks going into it changes
    // also see neighborUpdate
    // todo: I cooked, and it likely won't work
    @Override
    @SuppressWarnings("deprecation")
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return getPlacementState(world, state, pos);
    }

    private int getRotationsForDirection(Direction direction) {
        if (direction == Direction.NORTH) {
            return 0;
        }
        if (direction == Direction.EAST) {
            return 1;
        }
        if (direction == Direction.SOUTH) {
            return 2;
        }
        return 3;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.CONVEYOR_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
    }
}
