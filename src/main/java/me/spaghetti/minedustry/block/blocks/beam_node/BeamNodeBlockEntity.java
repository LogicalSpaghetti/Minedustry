package me.spaghetti.minedustry.block.blocks.beam_node;

import me.spaghetti.minedustry.Minedustry;
import me.spaghetti.minedustry.block.ModBlockEntities;
import me.spaghetti.minedustry.block.block_util.abstractions.MinedustryBlockEntity;
import me.spaghetti.minedustry.block.block_util.block_interfaces.PowerThingy;
import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class BeamNodeBlockEntity extends MinedustryBlockEntity implements PowerThingy {

    //the max offset of a block, i.e. 10 means there can be a gap of 9
    final int maxBeamLength = 10;

    private BlockPos[] connections = new BlockPos[4];

    public BeamNodeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BEAM_NODE_BLOCK_ENTITY, pos, state, 0);
    }

    @Override
    public void serverCommandTick(World world, BlockPos pos, BlockState state) {
        checkForConnections(world, pos, state);
        Minedustry.LOGGER.info("Server tick finished: " + Arrays.toString(connections));
    }

    @Override
    public void clientTick(World world, BlockPos pos, BlockState state) {
        Minedustry.LOGGER.info("Client starting: " + Arrays.toString(connections));
        renderConnections(world, pos, state);
    }

    private void checkForConnections(World world, BlockPos pos, BlockState state) {
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

    private void renderConnections(World world, BlockPos pos, BlockState state) {
        for (BlockPos connection : connections) {
            if (connection != null) {
                Minedustry.LOGGER.info("\tparticle added at: {} {} {}", connection.getX(), (connection.getY() + 1), connection.getZ());
                world.addParticle(ParticleTypes.ELECTRIC_SPARK,
                        connection.getX(), connection.getY() + 1, connection.getZ(),
                        0, 0, 0);
            }
        }
    }
    //todo: Ctrl+W expands code selection

    @Override
    public boolean isValidPowerConnection() {
        return true;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("display.minedustry.beam-node");
    }
}
