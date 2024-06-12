package me.spaghetti.minedustry.block.abstractions;

import me.spaghetti.minedustry.block.helpers.MultiBlock;
import me.spaghetti.minedustry.block.helpers.enums.Relationship;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public abstract class MinedustryBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final IntProperty X_OFFSET = IntProperty.of("x_offset", 0, 8);
    public static final IntProperty Y_OFFSET = IntProperty.of("y_offset", 0, 8);
    public static final IntProperty Z_OFFSET = IntProperty.of("z_offset", 0, 8);

    public static final EnumProperty<Relationship> RELATIONSHIP = EnumProperty.of("relationship", Relationship.class);

    public final int SIZE;

    protected MinedustryBlock(Settings settings, int size) {
        super(settings);
        this.SIZE = size;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return MultiBlock.canPlaceAtLocations(world, MultiBlock.getLocations(pos, SIZE));
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
        if (state.isOf(newState.getBlock())) {
            return;
        }

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MinedustryBlockEntity) {
            if (world instanceof ServerWorld) {
                ItemScatterer.spawn(world, pos, ((MinedustryBlockEntity) blockEntity).getItems());
            }
            world.updateComparators(pos, this);
        }

        BlockPos controlPos = getControlPos(pos, state);

        BlockPos[] positions = MultiBlock.getLocations(controlPos, SIZE);

        for (BlockPos position : positions) {
            world.breakBlock(position, false);
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
}
