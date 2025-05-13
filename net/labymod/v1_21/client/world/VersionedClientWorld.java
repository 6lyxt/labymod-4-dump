// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.client.world;

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
        final fzf level = fgo.Q().r;
        if (level == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final akr location = level.af().a();
        return this.resourceLocationFactory.create(location.b(), location.a());
    }
    
    @NotNull
    @Override
    public ResourceLocation biome() {
        return this.getCurrentBiome();
    }
    
    @Override
    public long getDayTime() {
        final fzf level = fgo.Q().r;
        if (level == null) {
            return 0L;
        }
        return level.ak();
    }
    
    @Override
    public boolean hasSolidBlockAt(final int x, final int y, final int z) {
        final fzf level = fgo.Q().r;
        return level != null && level.a_(new jd(x, y, z)).e();
    }
    
    private ResourceLocation getCurrentBiome() {
        final fzf level = fgo.Q().r;
        final bsr cameraEntity = fgo.Q().an();
        if (level == null || cameraEntity == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final jd blockPos = cameraEntity.do();
        if (blockPos.v() < level.I_() || blockPos.v() >= level.am()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final Optional<akq<ddw>> biomeHolder = level.t(blockPos).e();
        if (biomeHolder.isEmpty()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final akr biomeResourceLocation = biomeHolder.get().a();
        if (biomeResourceLocation == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }
    
    @Override
    public Chunk getChunk(final int chunkX, final int chunkZ) {
        final dcw level = (dcw)fgo.Q().r;
        if (level == null) {
            return null;
        }
        return (Chunk)level.d(chunkX, chunkZ);
    }
    
    @Override
    public BlockState getBlockState(final int x, final int y, final int z) {
        final dcw level = (dcw)fgo.Q().r;
        if (level == null) {
            return null;
        }
        final BlockState blockState = (BlockState)level.a_(new jd(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }
    
    @Override
    public int getPackedLight(final FloatVector3 position) {
        final jd blockPos = jd.a((double)position.getX(), (double)position.getY(), (double)position.getZ());
        final fzf level = fgo.Q().r;
        return gey.a(this.getBrightness((dcw)level, ddf.b, blockPos), this.getBrightness((dcw)level, ddf.a, blockPos));
    }
    
    @Override
    public List<AxisAlignedBoundingBox> getBlockCollisions(final AxisAlignedBoundingBox boundingBox) {
        final geb player = fgo.Q().s;
        final fzf level = fgo.Q().r;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        final Iterable<exv> voxelShapes = level.e((bsr)player, (ewx)VersionedClientWorld.GAME_MATH_MAPPER.toAABB(boundingBox));
        final List<AxisAlignedBoundingBox> collisions = new ArrayList<AxisAlignedBoundingBox>();
        for (final exv voxelShape : voxelShapes) {
            for (final ewx aabb : voxelShape.e()) {
                collisions.add(VersionedClientWorld.GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }
    
    @Override
    public int getMinBuildHeight() {
        final fzf level = fgo.Q().r;
        return (level == null) ? 0 : level.I_();
    }
    
    @Override
    public int getMaxBuildHeight() {
        final fzf level = fgo.Q().r;
        return (level == null) ? 0 : level.am();
    }
    
    private int getBrightness(final dcw level, final ddf layer, final jd blockPos) {
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
