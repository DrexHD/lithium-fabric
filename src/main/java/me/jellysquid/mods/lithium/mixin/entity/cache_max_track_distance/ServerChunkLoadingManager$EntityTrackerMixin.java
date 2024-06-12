package me.jellysquid.mods.lithium.mixin.entity.cache_max_track_distance;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.jellysquid.mods.lithium.common.entity.PassengerCachingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerChunkLoadingManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerChunkLoadingManager.EntityTracker.class)
public abstract class ServerChunkLoadingManager$EntityTrackerMixin {

    @Shadow
    @Final
    public Entity entity;

    @Unique
    private int cachedMaxTrackingDistance = -1;

    @WrapOperation(method = "updateTrackedStatus(Lnet/minecraft/server/network/ServerPlayerEntity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerChunkLoadingManager$EntityTracker;getMaxTrackDistance()I"
            )
    )
    private int cacheMaxTrackDistance(ServerChunkLoadingManager.EntityTracker instance, Operation<Integer> original) {
        if (((PassengerCachingEntity) this.entity).lithium$isPassengerListDirty() || cachedMaxTrackingDistance == -1) {
            this.cachedMaxTrackingDistance = original.call(instance);
            ((PassengerCachingEntity) this.entity).lithium$setPassengerListDirty(false);
        }
        return this.cachedMaxTrackingDistance;
    }

}
