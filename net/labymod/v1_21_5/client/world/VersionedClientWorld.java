// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.world;

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
        final glo level = fqq.Q().s;
        if (level == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final alr location = level.aj().a();
        return this.resourceLocationFactory.create(location.b(), location.a());
    }
    
    @NotNull
    @Override
    public ResourceLocation biome() {
        return this.getCurrentBiome();
    }
    
    @Override
    public long getDayTime() {
        final glo level = fqq.Q().s;
        if (level == null) {
            return 0L;
        }
        return level.am();
    }
    
    @Override
    public boolean hasSolidBlockAt(final int x, final int y, final int z) {
        final glo level = fqq.Q().s;
        return level != null && level.a_(new iw(x, y, z)).e();
    }
    
    private ResourceLocation getCurrentBiome() {
        final glo level = fqq.Q().s;
        final bxe cameraEntity = fqq.Q().ao();
        if (level == null || cameraEntity == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final iw blockPos = cameraEntity.dv();
        if (blockPos.v() < level.K_() || blockPos.v() >= level.ao()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final Optional<alq<dlm>> biomeHolder = level.u(blockPos).e();
        if (biomeHolder.isEmpty()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final alr biomeResourceLocation = biomeHolder.get().a();
        if (biomeResourceLocation == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }
    
    @Override
    public Chunk getChunk(final int chunkX, final int chunkZ) {
        final dkj level = (dkj)fqq.Q().s;
        if (level == null) {
            return null;
        }
        return (Chunk)level.d(chunkX, chunkZ);
    }
    
    @Override
    public BlockState getBlockState(final int x, final int y, final int z) {
        final dkj level = (dkj)fqq.Q().s;
        if (level == null) {
            return null;
        }
        final BlockState blockState = (BlockState)level.a_(new iw(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }
    
    @Override
    public int getPackedLight(final FloatVector3 position) {
        final iw blockPos = iw.a((double)position.getX(), (double)position.getY(), (double)position.getZ());
        final glo level = fqq.Q().s;
        return grk.a(this.getBrightness((dkj)level, dks.b, blockPos), this.getBrightness((dkj)level, dks.a, blockPos));
    }
    
    @Override
    public List<AxisAlignedBoundingBox> getBlockCollisions(final AxisAlignedBoundingBox boundingBox) {
        final gqm player = fqq.Q().t;
        final glo level = fqq.Q().s;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        final Iterable<fgw> voxelShapes = level.e((bxe)player, (ffx)VersionedClientWorld.GAME_MATH_MAPPER.toAABB(boundingBox));
        final List<AxisAlignedBoundingBox> collisions = new ArrayList<AxisAlignedBoundingBox>();
        for (final fgw voxelShape : voxelShapes) {
            for (final ffx aabb : voxelShape.e()) {
                collisions.add(VersionedClientWorld.GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }
    
    @Override
    public int getMinBuildHeight() {
        final glo level = fqq.Q().s;
        return (level == null) ? 0 : level.K_();
    }
    
    @Override
    public int getMaxBuildHeight() {
        final glo level = fqq.Q().s;
        return (level == null) ? 0 : level.ao();
    }
    
    private int getBrightness(final dkj level, final dks layer, final iw blockPos) {
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
