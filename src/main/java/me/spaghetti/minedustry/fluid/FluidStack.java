package me.spaghetti.minedustry.fluid;

import net.minecraft.fluid.Fluid;
import org.jetbrains.annotations.Nullable;

public class FluidStack {
    public static final FluidStack empty = new FluidStack(null);

    private Fluid fluid;
    private float volume;

    private FluidStack(@Nullable Void void_) {
        this.fluid = null;
    }

    public Fluid getFluid() {
        return fluid;
    }

    public void setFluid(Fluid fluid) {
        this.fluid = fluid;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
