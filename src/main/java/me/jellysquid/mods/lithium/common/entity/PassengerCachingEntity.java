package me.jellysquid.mods.lithium.common.entity;

public interface PassengerCachingEntity {

    boolean lithium$isPassengerListDirty();

    void lithium$setPassengerListDirty(boolean dirty);

    void lithium$setPassengerListDirtyRecursive(boolean dirty);

}
