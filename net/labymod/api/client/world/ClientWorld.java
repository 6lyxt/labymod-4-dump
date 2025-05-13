// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world;

import java.util.Set;
import java.util.Iterator;
import java.util.function.Predicate;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.chunk.Chunk;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.Entity;
import java.util.Optional;
import java.util.UUID;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ClientWorld
{
    Optional<Entity> getEntity(final UUID p0);
    
    Optional<Player> getPlayer(final UUID p0);
    
    Optional<Player> getPlayer(final String p0);
    
    int getPlayerCount();
    
    @NotNull
    List<Player> getPlayers();
    
    @NotNull
    List<Entity> getEntities();
    
    @NotNull
    String getReadableBiomeName();
    
    ResourceLocation dimension();
    
    @NotNull
    ResourceLocation biome();
    
    long getDayTime();
    
    @NotNull
    BossBarRegistry bossBarRegistry();
    
    boolean hasSolidBlockAt(final int p0, final int p1, final int p2);
    
    default boolean isBlockInBetween(final FloatVector3 from, final FloatVector3 to) {
        for (int distance = MathHelper.ceil(from.distance(to)), i = 0; i < distance; ++i) {
            final float x = from.getX() + (to.getX() - from.getX()) * i / distance;
            final float y = from.getY() + (to.getY() - from.getY()) * i / distance;
            final float z = from.getZ() + (to.getZ() - from.getZ()) * i / distance;
            if (this.hasSolidBlockAt(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z))) {
                return true;
            }
        }
        return false;
    }
    
    default boolean isBlockInBetween(final DoubleVector3 from, final DoubleVector3 to) {
        for (int distance = MathHelper.ceil(from.distance(to)), i = 0; i < distance; ++i) {
            final double x = from.getX() + (to.getX() - from.getX()) * i / distance;
            final double y = from.getY() + (to.getY() - from.getY()) * i / distance;
            final double z = from.getZ() + (to.getZ() - from.getZ()) * i / distance;
            if (this.hasSolidBlockAt(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z))) {
                return true;
            }
        }
        return false;
    }
    
    @Nullable
    default FloatVector3 findBlockPositionInBetween(final FloatVector3 from, final FloatVector3 to) {
        final int distance = MathHelper.ceil(from.distance(to));
        FloatVector3 blockPosition = null;
        for (int index = 0; index < distance; ++index) {
            final float x = from.getX() + (to.getX() - from.getX()) * index / distance;
            final float y = from.getY() + (to.getY() - from.getY()) * index / distance;
            final float z = from.getZ() + (to.getZ() - from.getZ()) * index / distance;
            if (this.hasSolidBlockAt(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z))) {
                blockPosition = new FloatVector3(x, y, z);
                break;
            }
        }
        return blockPosition;
    }
    
    Chunk getChunk(final int p0, final int p1);
    
    default BlockState getBlockState(final FloatVector3 position) {
        return this.getBlockState(MathHelper.floor(position.getX()), MathHelper.floor(position.getY()), MathHelper.floor(position.getZ()));
    }
    
    default BlockState getBlockState(final DoubleVector3 position) {
        return this.getBlockState(MathHelper.floor(position.getX()), MathHelper.floor(position.getY()), MathHelper.floor(position.getZ()));
    }
    
    default BlockState getBlockState(final IntVector3 position) {
        return this.getBlockState(position.getX(), position.getY(), position.getZ());
    }
    
    BlockState getBlockState(final int p0, final int p1, final int p2);
    
    default int getPackedLight(final float x, final float y, final float z) {
        return this.getPackedLight(new FloatVector3(x, y, z));
    }
    
    default int getPackedLight(final double x, final double y, final double z) {
        return this.getPackedLight((float)x, (float)y, (float)z);
    }
    
    default int getPackedLight(final DoubleVector3 position) {
        return this.getPackedLight(position.getX(), position.getY(), position.getZ());
    }
    
    int getPackedLight(final FloatVector3 p0);
    
    List<AxisAlignedBoundingBox> getBlockCollisions(final AxisAlignedBoundingBox p0);
    
    default <T extends Entity> T getNearestEntity(final List<? extends T> entities, final FloatVector3 eyePosition) {
        return this.getNearestEntity(entities, entity -> false, eyePosition);
    }
    
    default <T extends Entity> T getNearestEntity(final List<? extends T> entities, final DoubleVector3 position) {
        return this.getNearestEntity(entities, entity -> false, position);
    }
    
    @Nullable
    default <T extends Entity> T getNearestEntity(final List<? extends T> entities, final Predicate<T> filter, final FloatVector3 eyePosition) {
        float minDistance = -1.0f;
        T nearestEntity = null;
        for (final T entity : entities) {
            if (filter.test(entity)) {
                continue;
            }
            final float distance = (float)entity.getDistanceSquared(eyePosition);
            if (minDistance != -1.0f && distance >= minDistance) {
                continue;
            }
            minDistance = distance;
            nearestEntity = entity;
        }
        return nearestEntity;
    }
    
    @Nullable
    default <T extends Entity> T getNearestEntity(final List<? extends T> entities, final Predicate<T> filter, final DoubleVector3 position) {
        float minDistance = -1.0f;
        T nearestEntity = null;
        for (final T entity : entities) {
            if (filter.test(entity)) {
                continue;
            }
            final float distance = (float)entity.getDistanceSquared(position);
            if (minDistance != -1.0f && distance >= minDistance) {
                continue;
            }
            minDistance = distance;
            nearestEntity = entity;
        }
        return nearestEntity;
    }
    
    default int getHighestBlockYAt(final int x, final int z) {
        int y;
        for (y = 255; y > 0 && !this.hasSolidBlockAt(x, y, z); --y) {}
        return y;
    }
    
    default int getTopBlockYOf(final int x, int y, final int z) {
        if (this.hasSolidBlockAt(x, y, z)) {
            for (int i = 0; i < 255 && this.hasSolidBlockAt(x, y + 1, z); ++y, ++i) {}
        }
        else {
            while (y > 0) {
                if (this.hasSolidBlockAt(x, y, z)) {
                    break;
                }
                --y;
            }
        }
        return y;
    }
    
    int getMinBuildHeight();
    
    int getMaxBuildHeight();
    
    Set<Chunk> getChunks();
}
