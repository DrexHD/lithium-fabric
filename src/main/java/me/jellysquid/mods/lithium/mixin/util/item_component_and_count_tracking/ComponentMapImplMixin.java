package me.jellysquid.mods.lithium.mixin.util.item_component_and_count_tracking;

import me.jellysquid.mods.lithium.common.util.change_tracking.ChangePublisher;
import me.jellysquid.mods.lithium.common.util.change_tracking.ChangeSubscriber;
import net.minecraft.component.MergedComponentMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MergedComponentMap.class)
public class ComponentMapImplMixin implements ChangePublisher<MergedComponentMap> {

    @Unique
    private ChangeSubscriber<MergedComponentMap> subscriber;

    @Override
    public void lithium$subscribe(ChangeSubscriber<MergedComponentMap> subscriber, int subscriberData) {
        if (subscriberData != 0) {
            throw new UnsupportedOperationException("ComponentMapImpl does not support subscriber data");
        }
        this.subscriber = ChangeSubscriber.combine(this.subscriber, 0, subscriber, 0);
    }

    @Override
    public int lithium$unsubscribe(ChangeSubscriber<MergedComponentMap> subscriber) {
        this.subscriber = ChangeSubscriber.without(this.subscriber, subscriber);
        return 0;
    }

    @Inject(
            method = "onWrite()V", at = @At("HEAD")
    )
    private void trackBeforeChange(CallbackInfo ci) {
        if (this.subscriber != null) {
            this.subscriber.lithium$notify((MergedComponentMap) (Object) this, 0);
        }
    }
}
