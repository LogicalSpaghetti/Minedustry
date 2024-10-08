package me.spaghetti.minedustry.block.abstractions;

import me.spaghetti.minedustry.block.helpers.MultiBlockHelper;
import me.spaghetti.minedustry.block.properties.EPlanet;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Range;

/**
 * An abstract class extended by all Minedustry blocks.
 * <p>
 *     Handles the breaking, placing, and state of multi-blocks
*/

public abstract class MinedustryMultiBlock extends BlockWithEntity implements BlockEntityProvider {

    public static final IntProperty X_OFFSET = IntProperty.of("x_offset", 0, 8);
    public static final IntProperty Y_OFFSET = IntProperty.of("y_offset", 0, 8);
    public static final IntProperty Z_OFFSET = IntProperty.of("z_offset", 0, 8);

    public static final EnumProperty<EPlanet> PLANET = EnumProperty.of("planet", EPlanet.class);

    public final int SIZE;

    public MinedustryMultiBlock(Settings settings,@Range(from=2,to=Integer.MAX_VALUE) int multiSize) {
        super(settings);

        this.SIZE = multiSize;
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(X_OFFSET, 0)
                .with(Y_OFFSET, 0)
                .with(Z_OFFSET, 0));
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return MultiBlockHelper.canPlaceAtLocations(world, MultiBlockHelper.getWorldLocations(pos, SIZE));
    }

    public static BlockPos getControlPos(BlockPos pos, BlockState state) {
        int x = state.get(X_OFFSET);
        int y = state.get(Y_OFFSET);
        int z = state.get(Z_OFFSET);
        Vec3i offset = new Vec3i(x, y, z);
        return pos.subtract(offset);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) return;

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MinedustryBlockEntity) {
            if (world instanceof ServerWorld) {
                ItemScatterer.spawn(world, pos, ((MinedustryBlockEntity) blockEntity).getItems());
            }
            world.updateComparators(pos, this);
        }

        BlockPos controlPos = getControlPos(pos, state);

        BlockPos[] positions = MultiBlockHelper.getWorldLocations(controlPos, SIZE);


        for (int i = 0; i < positions.length; i++) {
            world.breakBlock(positions[i], false);
            if(i > 0) world.addBlockBreakParticles(positions[i], state);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0f;
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        if (isStateVisible(state)) {
            return BlockRenderType.MODEL;
        }
        return BlockRenderType.INVISIBLE;
    }

    public boolean isStateVisible(BlockState state) {
        return switch(SIZE) {
            case 1, 2 -> true;
            case 3 -> state.get(X_OFFSET) == 1 && state.get(Y_OFFSET) == 1 && state.get(Z_OFFSET) == 1;
            case 4 -> (state.get(X_OFFSET)%2 == 0 && state.get(Y_OFFSET)%2 == 0 && state.get(Z_OFFSET)%2 == 0);
            case 5 -> (state.get(X_OFFSET)%2 == 1 && state.get(Y_OFFSET)%2 == 1 && state.get(Z_OFFSET)%2 == 1);
            case 6 -> (state.get(X_OFFSET)%3 == 1 && state.get(Y_OFFSET)%3 == 1 && state.get(Z_OFFSET)%3 == 1);
            default -> false;
        };
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        // north-west will be the primary block
        BlockPos[] offsets = MultiBlockHelper.getOffsets(SIZE);
        for (int i = 1; i < offsets.length; i++) {
            world.setBlockState(pos.add(offsets[i]), state
                            .with(X_OFFSET, offsets[i].getX())
                            .with(Y_OFFSET, offsets[i].getY())
                            .with(Z_OFFSET, offsets[i].getZ()),
                    Block.NOTIFY_ALL);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(X_OFFSET);
        builder.add(Y_OFFSET);
        builder.add(Z_OFFSET);
    }
}
