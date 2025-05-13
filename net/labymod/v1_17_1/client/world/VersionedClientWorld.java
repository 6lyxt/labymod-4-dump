// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.client.world;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
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
        final eji level = dvp.C().r;
        if (level == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final ww location = level.aa().a();
        return this.resourceLocationFactory.create(location.b(), location.a());
    }
    
    @NotNull
    @Override
    public ResourceLocation biome() {
        return this.getCurrentBiome();
    }
    
    @Override
    public long getDayTime() {
        final eji level = dvp.C().r;
        if (level == null) {
            return 0L;
        }
        return level.ae();
    }
    
    @Override
    public boolean hasSolidBlockAt(final int x, final int y, final int z) {
        final eji level = dvp.C().r;
        return level != null && level.a_(new gg(x, y, z)).c().b();
    }
    
    private ResourceLocation getCurrentBiome() {
        final eji level = dvp.C().r;
        final atg cameraEntity = dvp.C().aa();
        if (level == null || cameraEntity == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final gg blockPos = cameraEntity.cR();
        if (blockPos.v() < level.s_() || blockPos.v() >= level.ag()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final Optional<? extends gw<bxp>> optionalRegistry = level.t().c(gw.aO);
        if (optionalRegistry.isEmpty()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final ww biomeResourceLocation = ((gw)optionalRegistry.get()).b((Object)level.w(blockPos));
        if (biomeResourceLocation == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }
    
    @Override
    public Chunk getChunk(final int chunkX, final int chunkZ) {
        final bwq level = (bwq)dvp.C().r;
        if (level == null) {
            return null;
        }
        return (Chunk)level.d(chunkX, chunkZ);
    }
    
    @Override
    public BlockState getBlockState(final int x, final int y, final int z) {
        final bwq level = (bwq)dvp.C().r;
        if (level == null) {
            return null;
        }
        final BlockState blockState = (BlockState)level.a_(new gg(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }
    
    @Override
    public List<AxisAlignedBoundingBox> getBlockCollisions(final AxisAlignedBoundingBox boundingBox) {
        final emm player = dvp.C().s;
        final eji level = dvp.C().r;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        final Stream<dnt> voxelShapes = level.b((atg)player, (dmv)VersionedClientWorld.GAME_MATH_MAPPER.toAABB(boundingBox));
        final List<AxisAlignedBoundingBox> collisions = new ArrayList<AxisAlignedBoundingBox>();
        voxelShapes.forEach(voxelShape -> {
            final List<dmv> aabbs = voxelShape.d();
            aabbs.iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final dmv aabb = iterator.next();
                collisions.add(VersionedClientWorld.GAME_MATH_MAPPER.fromAABB(aabb));
            }
            return;
        });
        return collisions;
    }
    
    @Override
    public int getPackedLight(final FloatVector3 position) {
        final gg blockPos = new gg((double)position.getX(), (double)position.getY(), (double)position.getZ());
        final eji level = dvp.C().r;
        return enh.a(this.getBrightness((bwq)level, bwz.b, blockPos), this.getBrightness((bwq)level, bwz.a, blockPos));
    }
    
    @Override
    public int getMinBuildHeight() {
        final eji level = dvp.C().r;
        return (level == null) ? 0 : level.s_();
    }
    
    @Override
    public int getMaxBuildHeight() {
        final eji level = dvp.C().r;
        return (level == null) ? 0 : level.ag();
    }
    
    private int getBrightness(final bwq level, final bwz layer, final gg blockPos) {
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
