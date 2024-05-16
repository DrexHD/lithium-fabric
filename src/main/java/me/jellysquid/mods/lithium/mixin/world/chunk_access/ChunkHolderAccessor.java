package me.jellysquid.mods.lithium.mixin.world.chunk_access;

import net.minecraft.server.world.ChunkHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.concurrent.CompletableFuture;

@Mixin(ChunkHolder.class)
public interface ChunkHolderAccessor {
    @Invoker
    void callCombineSavingFuture(CompletableFuture<?> completableFuture);
}
