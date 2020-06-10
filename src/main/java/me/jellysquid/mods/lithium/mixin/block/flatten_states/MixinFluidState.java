package me.jellysquid.mods.lithium.mixin.block.flatten_states;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.property.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * The methods in {@link FluidState} involve a lot of indirection through the BlockState/Fluid classes and require
 * property lookups in order to compute the returned value. This shows up as a hot spot in some areas (namely fluid
 * ticking and world generation).
 * <p>
 * Since these are constant for any given fluid state, we can cache them nearby for improved performance and eliminate
 * the overhead.
 */
@Mixin(FluidState.class)
public abstract class MixinFluidState{
    private float height;
    private int level;
    private boolean isEmpty;
    private boolean isStill;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(Fluid fluid, ImmutableMap<Property<?>, Comparable<?>> immutableMap, MapCodec<FluidState> mapCodec, CallbackInfo ci) {
        this.isEmpty = fluid.isEmpty();

        this.level = fluid.getLevel((FluidState)(Object)this);
        this.height = fluid.getHeight((FluidState)(Object)this);
        this.isStill = fluid.isStill((FluidState)(Object)this);
    }

    /**
     * @author JellySquid
     * modified by 2No2Name during 1.16 pre2 port
     *
     * @reason trying to be faster
     */
    @Overwrite
    public boolean isStill() {
        return this.isStill;
    }

    /**
     * @author JellySquid
     * modified by 2No2Name during 1.16 pre2 port
     *
     * @reason trying to be faster
     */
    @Overwrite
    public boolean isEmpty() {
        return this.isEmpty;
    }

    /**
     * @author JellySquid
     * modified by 2No2Name during 1.16 pre2 port
     *
     * @reason trying to be faster
     */
    @Overwrite
    public float getHeight() {
        return this.height;
    }

    /**
     * @author JellySquid
     * modified by 2No2Name during 1.16 pre2 port
     *
     * @reason trying to be faster
     */
    @Overwrite
    public int getLevel() {
        return this.level;
    }
}
