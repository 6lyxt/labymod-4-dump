// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.world;

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
        final fns level = evi.O().r;
        if (level == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final ahg location = level.ae().a();
        return this.resourceLocationFactory.create(location.b(), location.a());
    }
    
    @NotNull
    @Override
    public ResourceLocation biome() {
        return this.getCurrentBiome();
    }
    
    @Override
    public long getDayTime() {
        final fns level = evi.O().r;
        if (level == null) {
            return 0L;
        }
        return level.aj();
    }
    
    @Override
    public boolean hasSolidBlockAt(final int x, final int y, final int z) {
        final fns level = evi.O().r;
        return level != null && level.a_(new hx(x, y, z)).e();
    }
    
    private ResourceLocation getCurrentBiome() {
        final fns level = evi.O().r;
        final blv cameraEntity = evi.O().am();
        if (level == null || cameraEntity == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final hx blockPos = cameraEntity.dm();
        if (blockPos.v() < level.J_() || blockPos.v() >= level.al()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final Optional<ahf<cuo>> biomeHolder = level.t(blockPos).e();
        if (biomeHolder.isEmpty()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final ahg biomeResourceLocation = biomeHolder.get().a();
        if (biomeResourceLocation == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }
    
    @Override
    public Chunk getChunk(final int chunkX, final int chunkZ) {
        final ctp level = (ctp)evi.O().r;
        if (level == null) {
            return null;
        }
        return (Chunk)level.d(chunkX, chunkZ);
    }
    
    @Override
    public BlockState getBlockState(final int x, final int y, final int z) {
        final ctp level = (ctp)evi.O().r;
        if (level == null) {
            return null;
        }
        final BlockState blockState = (BlockState)level.a_(new hx(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }
    
    @Override
    public int getPackedLight(final FloatVector3 position) {
        final hx blockPos = hx.a((double)position.getX(), (double)position.getY(), (double)position.getZ());
        final fns level = evi.O().r;
        return ftg.a(this.getBrightness((ctp)level, cty.b, blockPos), this.getBrightness((ctp)level, cty.a, blockPos));
    }
    
    @Override
    public List<AxisAlignedBoundingBox> getBlockCollisions(final AxisAlignedBoundingBox boundingBox) {
        final fsj player = evi.O().s;
        final fns level = evi.O().r;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        final Iterable<emm> voxelShapes = level.e((blv)player, (elo)VersionedClientWorld.GAME_MATH_MAPPER.toAABB(boundingBox));
        final List<AxisAlignedBoundingBox> collisions = new ArrayList<AxisAlignedBoundingBox>();
        for (final emm voxelShape : voxelShapes) {
            for (final elo aabb : voxelShape.e()) {
                collisions.add(VersionedClientWorld.GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }
    
    @Override
    public int getMinBuildHeight() {
        final fns level = evi.O().r;
        return (level == null) ? 0 : level.J_();
    }
    
    @Override
    public int getMaxBuildHeight() {
        final fns level = evi.O().r;
        return (level == null) ? 0 : level.al();
    }
    
    private int getBrightness(final ctp level, final cty layer, final hx blockPos) {
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
