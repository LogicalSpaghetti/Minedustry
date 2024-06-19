package me.spaghetti.minedustry.fluid;

import me.spaghetti.minedustry.sounds.ModSounds;
import me.spaghetti.minedustry.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class CryoFluid extends FlowableFluid {
        @Override
        public Fluid getFlowing() {
            return ModFluids.FLOWING_CRYOFLUID;
        }

        @Override
        public Fluid getStill() {
            return ModFluids.CRYOFLUID;
        }

        @Override
        public Item getBucketItem() {
            return ModFluids.CRYOFLUID_BUCKET;
        }

        @Override
        public void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random) {
            if (!state.isStill() && !state.get(FALLING)) {
                if (random.nextInt(64) == 0) {
                    world.playSound((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, ModSounds.cryo_ambient, SoundCategory.BLOCKS, random.nextFloat() * 0.25f + 0.75f, random.nextFloat() + 0.5f, false);
                }
            } else if (random.nextInt(10) == 0) {
                world.addParticle(ParticleTypes.SNOWFLAKE, (double)pos.getX() + random.nextDouble(), (double)pos.getY() + random.nextDouble(), (double)pos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
            }
        }

        @Override
        @Nullable
        public ParticleEffect getParticle() {
            return ParticleTypes.SNOWFLAKE;
        }

        @Override
        protected boolean isInfinite(World world) {
            return world.getGameRules().getBoolean(GameRules.WATER_SOURCE_CONVERSION);
        }

        @Override
        protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
            BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
            Block.dropStacks(state, world, pos, blockEntity);
        }

        @Override
        public int getFlowSpeed(WorldView world) {
            return 4;
        }

        @Override
        public BlockState toBlockState(FluidState state) {
            return ModFluids.CRYOFLUID_BLOCK.getDefaultState().with(FluidBlock.LEVEL, CryoFluid.getBlockStateLevel(state));
        }

        @Override
        public boolean matchesType(Fluid fluid) {
            return fluid == ModFluids.CRYOFLUID || fluid == ModFluids.FLOWING_CRYOFLUID;
        }

        @Override
        public int getLevelDecreasePerBlock(WorldView world) {
            return 1;
        }

        @Override
        public int getTickRate(WorldView world) {
            return 5;
        }

        @Override
        public boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
            return direction == Direction.DOWN && !fluid.isIn(ModTags.Fluids.CRYOFLUID);
        }

        @Override
        protected float getBlastResistance() {
            return 100.0f;
        }

        @Override
        public Optional<SoundEvent> getBucketFillSound() {
            return Optional.of(ModSounds.cryo_ambient);
        }

        public static class Flowing
                extends CryoFluid {
            @Override
            protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
                super.appendProperties(builder);
                builder.add(LEVEL);
            }

            @Override
            public int getLevel(FluidState state) {
                return state.get(LEVEL);
            }

            @Override
            public boolean isStill(FluidState state) {
                return false;
            }
        }

        public static class Still
                extends CryoFluid {
            @Override
            public int getLevel(FluidState state) {
                return 8;
            }

            @Override
            public boolean isStill(FluidState state) {
                return true;
            }
        }
}
