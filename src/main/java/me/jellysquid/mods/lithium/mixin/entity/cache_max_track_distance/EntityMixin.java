package me.jellysquid.mods.lithium.mixin.entity.cache_max_track_distance;

import me.jellysquid.mods.lithium.common.entity.PassengerCachingEntity;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements PassengerCachingEntity {
    @Shadow
    private @Nullable Entity vehicle;
    @Unique
    private boolean isPassengerListDirty = true;

    @Override
    public boolean lithium$isPassengerListDirty() {
        return this.isPassengerListDirty;
    }

    @Override
    public void lithium$setPassengerListDirty(boolean dirty) {
        this.isPassengerListDirty = dirty;
    }

    @Override
    public void lithium$setPassengerListDirtyRecursive(boolean dirty) {
        this.isPassengerListDirty = true;
        if (this.vehicle != null) {
            ((PassengerCachingEntity) this.vehicle).lithium$setPassengerListDirtyRecursive(dirty);
        }
    }

    @Inject(method = {"addPassenger", "removePassenger"}, at = @At("TAIL"))
    private void lithium$markPassengerListDirty(Entity passenger, CallbackInfo ci) {
        lithium$setPassengerListDirtyRecursive(true);
    }

}
