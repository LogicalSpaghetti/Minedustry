package me.spaghetti.minedustry.block.energy;

import me.spaghetti.minedustry.Minedustry;
import me.spaghetti.minedustry.block.ModBlockEntities;
import me.spaghetti.minedustry.block.abstractions.MinedustryBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class PowerNodeBlockEntity extends MinedustryBlockEntity {

    final int maxConnections = 10;
    BlockPos[] connections = new BlockPos[maxConnections];

    public PowerNodeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.POWER_NODE_BLOCK_ENTITY, pos, state, 0);
        //connections[0] = new BlockPos(pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
    }

    @Override
    public void commandTick(World world, BlockPos pos, BlockState state) {
    }

    @Override
    public void clientTick(World world, BlockPos pos, BlockState state) {
        createBeams(world, pos, state);
    }

    private void createBeams(World world, BlockPos pos, BlockState state) {
        for (BlockPos connection : connections) {
            if (connection != null) createBeam(world, pos, state, connection);
        }
    }

    private void createBeam(World world, BlockPos pos, BlockState state, BlockPos target) {
        Minedustry.LOGGER.info("Beam created");
        BlockPos targetOffset = target.subtract(pos);
        double x = pos.getX() + 1.5 - getSize(state);
        double y = pos.getY() + 1.1;
        double z = pos.getZ() + 1.5 - getSize(state);
        world.addParticle(ParticleTypes.ELECTRIC_SPARK, x, y, z,
                targetOffset.getX(), targetOffset.getX(), targetOffset.getX());
    }

    @Override
    public boolean isValidPowerConnection() {
        return true;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("display.minedustry.power-node");
    }

    public void addConnection(BlockPos connection, int index) {
        connections[index] = connection;
    }

    public void removeConnection(int index) {
        connections[index] = null;
    }
}
