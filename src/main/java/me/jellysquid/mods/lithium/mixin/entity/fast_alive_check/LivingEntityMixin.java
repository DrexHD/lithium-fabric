package me.jellysquid.mods.lithium.mixin.entity.fast_alive_check;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Unique
    private boolean strictlyPositiveHealth = true;

    @Inject(method = "setHealth", at = @At("RETURN"))
    private void updatePositiveHealth(float health, CallbackInfo ci) {
        strictlyPositiveHealth = health > 0.0f;
    }

    @Redirect(method = "isAlive", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getHealth()F"))
    private float usePositiveHealth(LivingEntity instance) {
        return strictlyPositiveHealth ? 1.0f : 0.0f;
    }

}
