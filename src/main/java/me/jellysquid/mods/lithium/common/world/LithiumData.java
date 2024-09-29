package me.jellysquid.mods.lithium.common.world;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import me.jellysquid.mods.lithium.common.entity.block_tracking.ChunkSectionChangeCallback;
import me.jellysquid.mods.lithium.common.entity.block_tracking.SectionedBlockChangeTracker;
import me.jellysquid.mods.lithium.common.entity.movement_tracker.SectionedEntityMovementTracker;
import me.jellysquid.mods.lithium.common.util.deduplication.LithiumInterner;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.GameEventDispatcher;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;

public interface LithiumData {

    record Data(
            // Map of chunk position -> y section -> game event dispatcher
            // This should be faster than the chunk lookup, since there are usually a lot more chunks than
            // chunk with game event dispatchers (we only initialize them when non-empty set of listeners)
            // All Int2ObjectMap objects are also stored in a field of the corresponding WorldChunk.
            Long2ReferenceOpenHashMap<Int2ObjectMap<GameEventDispatcher>> gameEventDispatchersByChunk,

            // Cached ominous banner, must not be mutated.
            ItemStack ominousBanner,

            // Set of active mob navigations (active = have a path)
            ReferenceOpenHashSet<EntityNavigation> activeNavigations,

            // Block change tracker deduplication
            LithiumInterner<SectionedBlockChangeTracker> blockChangeTrackers,

            // Entity movement tracker deduplication
            LithiumInterner<SectionedEntityMovementTracker<?, ?>> entityMovementTrackers,

            // Block ChunkSection listeners
            Long2ReferenceOpenHashMap<ChunkSectionChangeCallback> chunkSectionChangeCallbacks,

            // Poi registry entries
            RegistryEntry<PointOfInterestType> netherPortalEntry,
            RegistryEntry<PointOfInterestType> homeEntry
    ) {
        public Data(World world) {
            this(
                    new Long2ReferenceOpenHashMap<>(),
                    world.getRegistryManager().getOptional(RegistryKeys.BANNER_PATTERN).map(Raid::createOminousBanner).orElse(null),
                    new ReferenceOpenHashSet<>(),
                    new LithiumInterner<>(),
                    new LithiumInterner<>(),
                    new Long2ReferenceOpenHashMap<>(),
                    world.getRegistryManager().getOrThrow(RegistryKeys.POINT_OF_INTEREST_TYPE).getOrThrow(PointOfInterestTypes.NETHER_PORTAL),
                    world.getRegistryManager().getOrThrow(RegistryKeys.POINT_OF_INTEREST_TYPE).getOrThrow(PointOfInterestTypes.HOME)
            );
        }
    }

    LithiumData.Data lithium$getData();
}
