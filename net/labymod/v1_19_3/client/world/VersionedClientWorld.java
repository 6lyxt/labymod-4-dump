// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.world;

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
        final eyz level = ejf.N().s;
        if (level == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final acf location = level.ac().a();
        return this.resourceLocationFactory.create(location.b(), location.a());
    }
    
    @NotNull
    @Override
    public ResourceLocation biome() {
        return this.getCurrentBiome();
    }
    
    @Override
    public long getDayTime() {
        final eyz level = ejf.N().s;
        if (level == null) {
            return 0L;
        }
        return level.ag();
    }
    
    @Override
    public boolean hasSolidBlockAt(final int x, final int y, final int z) {
        final eyz level = ejf.N().s;
        return level != null && level.a_(new gp(x, y, z)).d().b();
    }
    
    private ResourceLocation getCurrentBiome() {
        final eyz level = ejf.N().s;
        final bdr cameraEntity = ejf.N().al();
        if (level == null || cameraEntity == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final gp blockPos = cameraEntity.df();
        if (blockPos.v() < level.v_() || blockPos.v() >= level.ai()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final Optional<ace<ckt>> biomeHolder = level.w(blockPos).e();
        if (biomeHolder.isEmpty()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final acf biomeResourceLocation = biomeHolder.get().a();
        if (biomeResourceLocation == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }
    
    @Override
    public Chunk getChunk(final int chunkX, final int chunkZ) {
        final cjw level = (cjw)ejf.N().s;
        if (level == null) {
            return null;
        }
        return (Chunk)level.d(chunkX, chunkZ);
    }
    
    @Override
    public BlockState getBlockState(final int x, final int y, final int z) {
        final cjw level = (cjw)ejf.N().s;
        if (level == null) {
            return null;
        }
        final BlockState blockState = (BlockState)level.a_(new gp(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }
    
    @Override
    public int getPackedLight(final FloatVector3 position) {
        final gp blockPos = new gp((double)position.getX(), (double)position.getY(), (double)position.getZ());
        final eyz level = ejf.N().s;
        return fdu.a(this.getBrightness((cjw)level, ckf.b, blockPos), this.getBrightness((cjw)level, ckf.a, blockPos));
    }
    
    @Override
    public List<AxisAlignedBoundingBox> getBlockCollisions(final AxisAlignedBoundingBox boundingBox) {
        final fcz player = ejf.N().t;
        final eyz level = ejf.N().s;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        final Iterable<eax> voxelShapes = level.d((bdr)player, (dzz)VersionedClientWorld.GAME_MATH_MAPPER.toAABB(boundingBox));
        final List<AxisAlignedBoundingBox> collisions = new ArrayList<AxisAlignedBoundingBox>();
        for (final eax voxelShape : voxelShapes) {
            for (final dzz aabb : voxelShape.d()) {
                collisions.add(VersionedClientWorld.GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }
    
    @Override
    public int getMinBuildHeight() {
        final eyz level = ejf.N().s;
        return (level == null) ? 0 : level.v_();
    }
    
    @Override
    public int getMaxBuildHeight() {
        final eyz level = ejf.N().s;
        return (level == null) ? 0 : level.ai();
    }
    
    private int getBrightness(final cjw level, final ckf layer, final gp blockPos) {
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
