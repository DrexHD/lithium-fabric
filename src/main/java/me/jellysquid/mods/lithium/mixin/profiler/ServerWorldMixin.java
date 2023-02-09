package me.jellysquid.mods.lithium.mixin.profiler;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {

    protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DynamicRegistryManager dynamicRegistryManager, RegistryEntry<DimensionType> registryEntry, Supplier<Profiler> supplier, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, dynamicRegistryManager, registryEntry, supplier, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @Shadow
    @NotNull
    public abstract MinecraftServer getServer();

    @Override
    public Profiler getProfiler() {
        return this.getServer().getProfiler();
    }
}
