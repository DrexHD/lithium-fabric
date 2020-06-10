package me.jellysquid.mods.lithium.mixin.world.fire_checks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(World.class)
public abstract class MixinWorld {
    @Shadow
    public abstract Chunk getChunk(int chunkX, int chunkZ, ChunkStatus leastStatus, boolean create);

//todo: adapt this to the new version

//    /**
//     * This implementation avoids repeatedly fetching chunks from the world by hoisting it from the y-iteration step.
//     * Additionally, the check whether an area of chunks is loaded upfront is removed and moved down into the loop by
//     * checking if a chunk is null.
//     *
//     * @reason Use optimized function
//     * @author JellySquid
//     */
////    @Overwrite
//    public boolean doesAreaContainFireSource(Box box) {
//        int minX = MathHelper.floor(box.minX);
//        int maxX = MathHelper.floor(box.maxX);
//        int minY = MathHelper.floor(box.minY);
//        int maxY = MathHelper.floor(box.maxY);
//        int minZ = MathHelper.floor(box.minZ);
//        int maxZ = MathHelper.floor(box.maxZ);
//
//        for (int x = minX; x <= maxX; ++x) {
//            for (int z = minZ; z <= maxZ; ++z) {
//                Chunk chunk = this.getChunk(x >> 4, z >> 4, ChunkStatus.FULL, false);
//
//                if (chunk == null) {
//                    continue;
//                }
//
//                for (int y = minY; y <= maxY; ++y) {
//                    if (y < 0 || y >= 256) {
//                        continue;
//                    }
//
//                    ChunkSection section = chunk.getSectionArray()[y >> 4];
//
//                    if (section != null) {
//                        BlockState blockState = section.getBlockState(x & 15, y & 15, z & 15);
//
//                        if (blockState.isIn(BlockTags.FIRE) || blockState.getBlock() == Blocks.LAVA) {
//                            return true;
//                        }
//                    }
//                }
//            }
//        }
//
//        return false;
//    }
}
