package me.spaghetti.minedustry.block.custom;

import me.spaghetti.minedustry.Minedustry;
import me.spaghetti.minedustry.block.entity.ConveyorBlockEntity;
import me.spaghetti.minedustry.block.enums.ConveyorShape;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class ConveyorBlock extends AbstractConveyor {
    public static final DirectionProperty FACING = DirectionProperty.of("facing", Direction.Type.HORIZONTAL);;
    public static final EnumProperty<ConveyorShape> SHAPE = EnumProperty.of("shape", ConveyorShape.class);

    protected static final VoxelShape OUTLINE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);


    protected ConveyorBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(SHAPE, ConveyorShape.STRAIGHT)
                .with(FACING, ctx.getHorizontalPlayerFacing());
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
        builder.add(SHAPE, FACING);
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
        vec2 = vec2.multiply(1);
        Minedustry.LOGGER.info("ConveyorBlock.onSteppedOn: {}", vec2);
        // doesn't work, changing the hitbox to be smaller makes it no longer detect stepping I believe
        entity.addVelocity(vec2);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }
}
