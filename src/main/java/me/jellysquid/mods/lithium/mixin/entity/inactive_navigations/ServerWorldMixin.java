package me.jellysquid.mods.lithium.mixin.entity.inactive_navigations;

import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import me.jellysquid.mods.lithium.common.world.ServerWorldExtended;
import net.minecraft.block.BlockState;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Spawner;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * This patch is supposed to reduce the cost of setblockstate calls that change the collision shape of a block.
 * In vanilla, changing the collision shape of a block will notify *ALL* EntityNavigations in the world.
 * As EntityNavigations only care about these changes when they actually have a currentPath, we skip the iteration
 * of many navigations. For that optimization we need to keep track of which navigations have a path and which do not.
 *
 * Another possible optimization for the future: If we can somehow find a maximum range that a navigation listens for,
 * we can partition the set by region/chunk/etc. to be able to only iterate over nearby EntityNavigations. In vanilla
 * however, that limit calculation includes the entity position, which can change by a lot very quickly in rare cases.
 * For this optimization we would need to add detection code for very far entity movements. Therefore we don't implement
 * this yet.
 */

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin implements ServerWorldExtended {

    @Shadow
    @Final
    private Set<MobEntity> mobSet;
    private ReferenceOpenHashSet<MobEntity> activelyMovingMobs;
    private boolean isIteratingActiveEntityNavigations;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey<World> registryKey, DimensionType dimensionType, WorldGenerationProgressListener worldGenerationProgressListener, ChunkGenerator chunkGenerator, boolean debugWorld, long l, List<Spawner> spawners, boolean shouldTickTime, CallbackInfo ci) {
        this.activelyMovingMobs = new ReferenceOpenHashSet<>();
    }

    /**
     * Optimization: Only update listeners that may care about the update. Listeners which have no path
     * never react to the update.
     * With thousands of non-pathfinding mobs in the world, this can be a relevant difference.
     */
    @Redirect(method = "updateListeners", at = @At(value = "INVOKE", target = "Ljava/util/Set;iterator()Ljava/util/Iterator;"))
    private Iterator<MobEntity> getActiveListeners(Set<MobEntity> set) {
        this.isIteratingActiveEntityNavigations = true;
        return this.activelyMovingMobs.iterator();
    }

    @Inject(method = "updateListeners", at = @At(value = "RETURN"))
    private void onIterationFinished(BlockPos pos, BlockState oldState, BlockState newState, int flags, CallbackInfo ci) {
        this.isIteratingActiveEntityNavigations = false;
    }

    @Override
    public void setNavigationActive(MobEntity mobEntity) {
        this.avoidConcurrentModification();
        this.activelyMovingMobs.add(mobEntity);
    }

    @Override
    public void setNavigationInactive(MobEntity mobEntity) {
        this.avoidConcurrentModification();
        this.activelyMovingMobs.remove(mobEntity);
    }

    private void avoidConcurrentModification() {
        if (this.isIteratingActiveEntityNavigations) {
            //work around concurrent modification problems. This breaks Iterator.remove(), but that is not used.
            this.activelyMovingMobs = this.activelyMovingMobs.clone();
            this.isIteratingActiveEntityNavigations = false;
        }
    }

    /**
     * Debug function
     * @return whether the activeEntityNavigation set is in the correct state
     */
    public boolean isConsistent() {
        int i = 0;
        for (MobEntity mobEntity : this.mobSet) {
            if ((mobEntity.getNavigation() == null || mobEntity.getNavigation().getCurrentPath() == null) == this.activelyMovingMobs.contains(mobEntity)) {
                return false;
            }
            if (mobEntity.getNavigation() != null && mobEntity.getNavigation().getCurrentPath() != null) {
                i++;
            }
        }
        return this.activelyMovingMobs.size() == i;
    }
}
