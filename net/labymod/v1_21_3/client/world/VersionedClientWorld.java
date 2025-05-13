// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.world;

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
        final gfk level = fmg.Q().s;
        if (level == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final alz location = level.ah().a();
        return this.resourceLocationFactory.create(location.b(), location.a());
    }
    
    @NotNull
    @Override
    public ResourceLocation biome() {
        return this.getCurrentBiome();
    }
    
    @Override
    public long getDayTime() {
        final gfk level = fmg.Q().s;
        if (level == null) {
            return 0L;
        }
        return level.ak();
    }
    
    @Override
    public boolean hasSolidBlockAt(final int x, final int y, final int z) {
        final gfk level = fmg.Q().s;
        return level != null && level.a_(new jh(x, y, z)).e();
    }
    
    private ResourceLocation getCurrentBiome() {
        final gfk level = fmg.Q().s;
        final bvk cameraEntity = fmg.Q().ao();
        if (level == null || cameraEntity == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final jh blockPos = cameraEntity.dw();
        if (blockPos.v() < level.L_() || blockPos.v() >= level.am()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final Optional<aly<dik>> biomeHolder = level.t(blockPos).e();
        if (biomeHolder.isEmpty()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final alz biomeResourceLocation = biomeHolder.get().a();
        if (biomeResourceLocation == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }
    
    @Override
    public Chunk getChunk(final int chunkX, final int chunkZ) {
        final dhi level = (dhi)fmg.Q().s;
        if (level == null) {
            return null;
        }
        return (Chunk)level.d(chunkX, chunkZ);
    }
    
    @Override
    public BlockState getBlockState(final int x, final int y, final int z) {
        final dhi level = (dhi)fmg.Q().s;
        if (level == null) {
            return null;
        }
        final BlockState blockState = (BlockState)level.a_(new jh(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }
    
    @Override
    public int getPackedLight(final FloatVector3 position) {
        final jh blockPos = jh.a((double)position.getX(), (double)position.getY(), (double)position.getZ());
        final gfk level = fmg.Q().s;
        return glj.a(this.getBrightness((dhi)level, dhr.b, blockPos), this.getBrightness((dhi)level, dhr.a, blockPos));
    }
    
    @Override
    public List<AxisAlignedBoundingBox> getBlockCollisions(final AxisAlignedBoundingBox boundingBox) {
        final gkh player = fmg.Q().t;
        final gfk level = fmg.Q().s;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        final Iterable<fcs> voxelShapes = level.e((bvk)player, (fbt)VersionedClientWorld.GAME_MATH_MAPPER.toAABB(boundingBox));
        final List<AxisAlignedBoundingBox> collisions = new ArrayList<AxisAlignedBoundingBox>();
        for (final fcs voxelShape : voxelShapes) {
            for (final fbt aabb : voxelShape.e()) {
                collisions.add(VersionedClientWorld.GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }
    
    @Override
    public int getMinBuildHeight() {
        final gfk level = fmg.Q().s;
        return (level == null) ? 0 : level.L_();
    }
    
    @Override
    public int getMaxBuildHeight() {
        final gfk level = fmg.Q().s;
        return (level == null) ? 0 : level.am();
    }
    
    private int getBrightness(final dhi level, final dhr layer, final jh blockPos) {
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
