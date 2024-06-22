package me.spaghetti.minedustry.block.block_util.helpers;

import me.spaghetti.minedustry.Minedustry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class MultiBlockHelper {

    public static BlockPos[] getOffsets(int size) {

        BlockPos[] slaves = new BlockPos[size * size * size];

        int counter = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    slaves[counter] = new BlockPos(x, y, z);
                    counter++;
                }
            }
        }

        return slaves;
    }

    public static BlockPos[] getLocations(BlockPos controlPos, int size) {

        BlockPos[] slaves = new BlockPos[size * size * size];

        int counter = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    slaves[counter] = new BlockPos(x, y, z).add(controlPos);
                    counter++;
                }
            }
        }

        return slaves;
    }

    public static boolean canPlaceAtOffsets(World world, BlockPos[] offsets, BlockPos pos) {
        for (BlockPos location : offsets) {
            if (!world.getBlockState(location.add(pos)).isReplaceable()) {
                Minedustry.LOGGER.info(location.add(pos).toString());
                return false;
            }
        }
        return true;
    }

    public static boolean canPlaceAtLocations(WorldView world, BlockPos[] locations) {
        for (BlockPos location : locations) {
            if (!world.getBlockState(location).isReplaceable()) {
                Minedustry.LOGGER.info(location.toString());
                return false;
            }
        }
        return true;
    }
}
