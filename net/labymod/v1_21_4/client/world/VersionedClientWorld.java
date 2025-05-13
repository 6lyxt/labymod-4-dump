// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.world;

import net.labymod.api.util.math.MathHelper;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.chunk.Chunk;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import javax.inject.Inject;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.math.GameMathMapper;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.world.DefaultClientWorld;

@Singleton
@Implements(ClientWorld.class)
public class VersionedClientWorld extends DefaultClientWorld
{
    private final ResourceLocationFactory resourceLocationFactory;
    private static final GameMathMapper GAME_MATH_MAPPER;
    private static final ResourceLocation LABYMOD_UNKNOWN;
    
    @Inject
    public VersionedClientWorld(final ResourceLocationFactory resourceLocationFactory) {
        this.resourceLocationFactory = resourceLocationFactory;
    }
    
    @Override
    public ResourceLocation dimension() {
        final gga level = flk.Q().s;
        if (level == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final akv location = level.ai().a();
        return this.resourceLocationFactory.create(location.b(), location.a());
    }
    
    @NotNull
    @Override
    public ResourceLocation biome() {
        return this.getCurrentBiome();
    }
    
    @Override
    public long getDayTime() {
        final gga level = flk.Q().s;
        if (level == null) {
            return 0L;
        }
        return level.al();
    }
    
    @Override
    public boolean hasSolidBlockAt(final int x, final int y, final int z) {
        final gga level = flk.Q().s;
        return level != null && level.a_(new ji(x, y, z)).e();
    }
    
    private ResourceLocation getCurrentBiome() {
        final gga level = flk.Q().s;
        final bum cameraEntity = flk.Q().ao();
        if (level == null || cameraEntity == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final ji blockPos = cameraEntity.dv();
        if (blockPos.v() < level.L_() || blockPos.v() >= level.an()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final Optional<aku<dhl>> biomeHolder = level.t(blockPos).e();
        if (biomeHolder.isEmpty()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final akv biomeResourceLocation = biomeHolder.get().a();
        if (biomeResourceLocation == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }
    
    @Override
    public Chunk getChunk(final int chunkX, final int chunkZ) {
        final dgj level = (dgj)flk.Q().s;
        if (level == null) {
            return null;
        }
        return (Chunk)level.d(chunkX, chunkZ);
    }
    
    @Override
    public BlockState getBlockState(final int x, final int y, final int z) {
        final dgj level = (dgj)flk.Q().s;
        if (level == null) {
            return null;
        }
        final BlockState blockState = (BlockState)level.a_(new ji(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }
    
    @Override
    public int getPackedLight(final FloatVector3 position) {
        final ji blockPos = ji.a((double)position.getX(), (double)position.getY(), (double)position.getZ());
        final gga level = flk.Q().s;
        return glx.a(this.getBrightness((dgj)level, dgs.b, blockPos), this.getBrightness((dgj)level, dgs.a, blockPos));
    }
    
    @Override
    public List<AxisAlignedBoundingBox> getBlockCollisions(final AxisAlignedBoundingBox boundingBox) {
        final gkx player = flk.Q().t;
        final gga level = flk.Q().s;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        final Iterable<fbv> voxelShapes = level.e((bum)player, (faw)VersionedClientWorld.GAME_MATH_MAPPER.toAABB(boundingBox));
        final List<AxisAlignedBoundingBox> collisions = new ArrayList<AxisAlignedBoundingBox>();
        for (final fbv voxelShape : voxelShapes) {
            for (final faw aabb : voxelShape.e()) {
                collisions.add(VersionedClientWorld.GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }
    
    @Override
    public int getMinBuildHeight() {
        final gga level = flk.Q().s;
        return (level == null) ? 0 : level.L_();
    }
    
    @Override
    public int getMaxBuildHeight() {
        final gga level = flk.Q().s;
        return (level == null) ? 0 : level.an();
    }
    
    private int getBrightness(final dgj level, final dgs layer, final ji blockPos) {
        if (level == null) {
            return 15;
        }
        return level.a(layer, blockPos);
    }
    
    static {
        GAME_MATH_MAPPER = MathHelper.mapper();
        LABYMOD_UNKNOWN = ResourceLocation.create("labymod", "unknown");
    }
}
