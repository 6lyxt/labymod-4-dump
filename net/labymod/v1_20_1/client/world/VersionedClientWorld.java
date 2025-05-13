// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.world;

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
        final few level = enn.N().s;
        if (level == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final acq location = level.ac().a();
        return this.resourceLocationFactory.create(location.b(), location.a());
    }
    
    @NotNull
    @Override
    public ResourceLocation biome() {
        return this.getCurrentBiome();
    }
    
    @Override
    public long getDayTime() {
        final few level = enn.N().s;
        if (level == null) {
            return 0L;
        }
        return level.ah();
    }
    
    @Override
    public boolean hasSolidBlockAt(final int x, final int y, final int z) {
        final few level = enn.N().s;
        return level != null && level.a_(new gu(x, y, z)).e();
    }
    
    private ResourceLocation getCurrentBiome() {
        final few level = enn.N().s;
        final bfj cameraEntity = enn.N().al();
        if (level == null || cameraEntity == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final gu blockPos = cameraEntity.di();
        if (blockPos.v() < level.C_() || blockPos.v() >= level.aj()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final Optional<acp<cnk>> biomeHolder = level.s(blockPos).e();
        if (biomeHolder.isEmpty()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final acq biomeResourceLocation = biomeHolder.get().a();
        if (biomeResourceLocation == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }
    
    @Override
    public Chunk getChunk(final int chunkX, final int chunkZ) {
        final cmm level = (cmm)enn.N().s;
        if (level == null) {
            return null;
        }
        return (Chunk)level.d(chunkX, chunkZ);
    }
    
    @Override
    public BlockState getBlockState(final int x, final int y, final int z) {
        final cmm level = (cmm)enn.N().s;
        if (level == null) {
            return null;
        }
        final BlockState blockState = (BlockState)level.a_(new gu(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }
    
    @Override
    public int getPackedLight(final FloatVector3 position) {
        final gu blockPos = gu.a((double)position.getX(), (double)position.getY(), (double)position.getZ());
        final few level = enn.N().s;
        return fjw.a(this.getBrightness((cmm)level, cmv.b, blockPos), this.getBrightness((cmm)level, cmv.a, blockPos));
    }
    
    @Override
    public List<AxisAlignedBoundingBox> getBlockCollisions(final AxisAlignedBoundingBox boundingBox) {
        final fiy player = enn.N().t;
        final few level = enn.N().s;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        final Iterable<efb> voxelShapes = level.d((bfj)player, (eed)VersionedClientWorld.GAME_MATH_MAPPER.toAABB(boundingBox));
        final List<AxisAlignedBoundingBox> collisions = new ArrayList<AxisAlignedBoundingBox>();
        for (final efb voxelShape : voxelShapes) {
            for (final eed aabb : voxelShape.d()) {
                collisions.add(VersionedClientWorld.GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }
    
    @Override
    public int getMinBuildHeight() {
        final few level = enn.N().s;
        return (level == null) ? 0 : level.C_();
    }
    
    @Override
    public int getMaxBuildHeight() {
        final few level = enn.N().s;
        return (level == null) ? 0 : level.aj();
    }
    
    private int getBrightness(final cmm level, final cmv layer, final gu blockPos) {
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
