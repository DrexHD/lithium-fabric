// Disabled because it (presumably) caused off by one-off issues in chunk height indexes, and I am to lazy to figure out why
@MixinConfigOption(description = "Reduces indirection by inlining world height access methods", enabled = false)
package me.jellysquid.mods.lithium.mixin.world.inline_height;

import net.caffeinemc.gradle.MixinConfigOption;