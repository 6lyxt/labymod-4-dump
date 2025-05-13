// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.client.world;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import java.util.Iterator;
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
        final ems level = dyr.D().r;
        if (level == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final yt location = level.aa().a();
        return this.resourceLocationFactory.create(location.b(), location.a());
    }
    
    @NotNull
    @Override
    public ResourceLocation biome() {
        return this.getCurrentBiome();
    }
    
    @Override
    public long getDayTime() {
        final ems level = dyr.D().r;
        if (level == null) {
            return 0L;
        }
        return level.ae();
    }
    
    @Override
    public boolean hasSolidBlockAt(final int x, final int y, final int z) {
        final ems level = dyr.D().r;
        return level != null && level.a_(new gj(x, y, z)).c().b();
    }
    
    private ResourceLocation getCurrentBiome() {
        final ems level = dyr.D().r;
        final axk cameraEntity = dyr.D().Z();
        if (level == null || cameraEntity == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final gj blockPos = cameraEntity.cW();
        if (blockPos.v() < level.u_() || blockPos.v() >= level.ag()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final Optional<? extends hb<cbr>> optionalRegistry = level.s().c(hb.aP);
        if (optionalRegistry.isEmpty()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final yt biomeResourceLocation = ((hb)optionalRegistry.get()).b((Object)level.v(blockPos).a());
        if (biomeResourceLocation == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }
    
    @Override
    public Chunk getChunk(final int chunkX, final int chunkZ) {
        final cav level = (cav)dyr.D().r;
        if (level == null) {
            return null;
        }
        return (Chunk)level.d(chunkX, chunkZ);
    }
    
    @Override
    public BlockState getBlockState(final int x, final int y, final int z) {
        final cav level = (cav)dyr.D().r;
        if (level == null) {
            return null;
        }
        final BlockState blockState = (BlockState)level.a_(new gj(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }
    
    @Override
    public List<AxisAlignedBoundingBox> getBlockCollisions(final AxisAlignedBoundingBox boundingBox) {
        final epw player = dyr.D().s;
        final ems level = dyr.D().r;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        final Iterable<dqh> voxelShapes = level.d((axk)player, (dpj)VersionedClientWorld.GAME_MATH_MAPPER.toAABB(boundingBox));
        final List<AxisAlignedBoundingBox> collisions = new ArrayList<AxisAlignedBoundingBox>();
        for (final dqh voxelShape : voxelShapes) {
            for (final dpj aabb : voxelShape.d()) {
                collisions.add(VersionedClientWorld.GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }
    
    @Override
    public int getPackedLight(final FloatVector3 position) {
        final gj blockPos = new gj((double)position.getX(), (double)position.getY(), (double)position.getZ());
        final ems level = dyr.D().r;
        return eqr.a(this.getBrightness((cav)level, cbe.b, blockPos), this.getBrightness((cav)level, cbe.a, blockPos));
    }
    
    @Override
    public int getMinBuildHeight() {
        final ems level = dyr.D().r;
        return (level == null) ? 0 : level.u_();
    }
    
    @Override
    public int getMaxBuildHeight() {
        final ems level = dyr.D().r;
        return (level == null) ? 0 : level.ag();
    }
    
    private int getBrightness(final cav level, final cbe layer, final gj blockPos) {
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
