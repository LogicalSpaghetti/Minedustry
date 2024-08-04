package me.spaghetti.minedustry.block.blocks.production.beam_node;

import me.spaghetti.minedustry.Minedustry;
import me.spaghetti.minedustry.block.ModBlockEntities;
import me.spaghetti.minedustry.block.abstractions.MinedustryBlockEntity;
import me.spaghetti.minedustry.block.interfaces.PowerThingy;
import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;

public class BeamNodeBlockEntity extends MinedustryBlockEntity implements PowerThingy {

    //the max offset of a block, i.e. 10 means there can be a gap of 9
    final int maxBeamLength = 10;

    private BlockPos[] connections = new BlockPos[4];

    public BeamNodeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BEAM_NODE_BLOCK_ENTITY, pos, state, 0);
    }

    @Override
    public void serverTick(World world, BlockPos pos, BlockState state) {
        checkForConnections(world, pos);
    }

    @Override
    public void clientTick(World world, BlockPos pos, BlockState state) {
        renderConnections(world);
    }

    private void checkForConnections(World world, BlockPos pos) {
        connections = new BlockPos[4];
        List<Property.Value<Direction>> dirs = Properties.HORIZONTAL_FACING.stream().toList();
        for (int i = 0; i < dirs.size(); i++) {
            for (int offset = 1; offset <= maxBeamLength; offset++) {
                if (world.getBlockEntity(pos.add(dirs.get(i).value().getVector().multiply(offset))) instanceof BeamNodeBlockEntity) {
                    connections[i] = pos.add(dirs.get(i).value().getVector().multiply(offset));
                    break;
                }
            }
        }
    }

    private void renderConnections(World world) {
        for (BlockPos connection : connections) {
            if (connection != null) {
                Minedustry.LOGGER.info("\tparticle added at: {} {} {}", connection.getX(), (connection.getY() + 1), connection.getZ());
                world.addParticle(ParticleTypes.ELECTRIC_SPARK,
                        connection.getX(), connection.getY() + 1, connection.getZ(),
                        0, 0, 0);
            }
        }
    }
}
