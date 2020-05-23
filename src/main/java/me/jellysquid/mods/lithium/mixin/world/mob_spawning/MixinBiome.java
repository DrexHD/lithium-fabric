package me.jellysquid.mods.lithium.mixin.world.mob_spawning;

import com.google.common.collect.Maps;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(Biome.class)
public class MixinBiome {
    @Mutable
    @Shadow
    @Final
    private Map<SpawnGroup, List<Biome.SpawnEntry>> spawns;

    /**
     * Re-initialize the spawn category lists with a much faster backing collection type for enum keys. This provides
     * a modest speed-up for mob spawning as {@link Biome#getEntitySpawnList(SpawnGroup)} is a rather hot method.
     */
    @Inject(method = "<init>", at = @At("RETURN"))
    private void reinit(Biome.Settings settings, CallbackInfo ci) {
        Map<SpawnGroup, List<Biome.SpawnEntry>> spawns = Maps.newEnumMap(SpawnGroup.class);
        spawns.putAll(this.spawns);

        this.spawns = spawns;
    }
}
