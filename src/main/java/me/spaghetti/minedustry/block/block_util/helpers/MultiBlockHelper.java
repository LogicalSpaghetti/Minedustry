package me.spaghetti.minedustry.block.block_util.helpers;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import static me.spaghetti.minedustry.block.block_util.abstractions.MinedustryBlock.*;

public class MultiBlockHelper {

    public static BlockPos[] getOffsets(int size) {

        BlockPos[] offsets = new BlockPos[size * size * size];

        int counter = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    offsets[counter] = new BlockPos(x, y, z);
                    counter++;
                }
            }
        }

        return offsets;
    }

    public static BlockPos[] getWorldLocations(BlockPos controlPos, int size) {

        BlockPos[] offsets = new BlockPos[size * size * size];

        int counter = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    offsets[counter] = new BlockPos(x, y, z).add(controlPos);
                    counter++;
                }
            }
        }

        return offsets;
    }

    public static boolean canPlaceAtOffsets(World world, BlockPos[] offsets, BlockPos pos) {
        for (BlockPos location : offsets) {
            if (!world.getBlockState(location.add(pos)).isReplaceable()) {
                return false;
            }
        }
        return true;
    }

    public static boolean canPlaceAtLocations(WorldView world, BlockPos[] locations) {
        for (BlockPos location : locations) {
            if (!world.getBlockState(location).isReplaceable()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isCommandPosition(BlockState state) {
        return state.get(X_OFFSET) == 0 &&
                state.get(Y_OFFSET) == 0 &&
                state.get(Z_OFFSET) == 0;
    }
}
