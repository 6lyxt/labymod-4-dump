// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.client.world;

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
        final euv level = efu.I().s;
        if (level == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final abb location = level.ab().a();
        return this.resourceLocationFactory.create(location.b(), location.a());
    }
    
    @NotNull
    @Override
    public ResourceLocation biome() {
        return this.getCurrentBiome();
    }
    
    @Override
    public long getDayTime() {
        final euv level = efu.I().s;
        if (level == null) {
            return 0L;
        }
        return level.af();
    }
    
    @Override
    public boolean hasSolidBlockAt(final int x, final int y, final int z) {
        final euv level = efu.I().s;
        return level != null && level.a_(new gt(x, y, z)).d().b();
    }
    
    private ResourceLocation getCurrentBiome() {
        final euv level = efu.I().s;
        final bbn cameraEntity = efu.I().ae();
        if (level == null || cameraEntity == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final gt blockPos = cameraEntity.da();
        if (blockPos.v() < level.u_() || blockPos.v() >= level.ah()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final Optional<? extends hm<cht>> optionalRegistry = level.s().c(hm.aR);
        if (optionalRegistry.isEmpty()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final abb biomeResourceLocation = ((hm)optionalRegistry.get()).b((Object)level.w(blockPos).a());
        if (biomeResourceLocation == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }
    
    @Override
    public Chunk getChunk(final int chunkX, final int chunkZ) {
        final cgx level = (cgx)efu.I().s;
        if (level == null) {
            return null;
        }
        return (Chunk)level.d(chunkX, chunkZ);
    }
    
    @Override
    public BlockState getBlockState(final int x, final int y, final int z) {
        final cgx level = (cgx)efu.I().s;
        if (level == null) {
            return null;
        }
        final BlockState blockState = (BlockState)level.a_(new gt(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }
    
    @Override
    public List<AxisAlignedBoundingBox> getBlockCollisions(final AxisAlignedBoundingBox boundingBox) {
        final eyw player = efu.I().t;
        final euv level = efu.I().s;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        final Iterable<dxj> voxelShapes = level.d((bbn)player, (dwl)VersionedClientWorld.GAME_MATH_MAPPER.toAABB(boundingBox));
        final List<AxisAlignedBoundingBox> collisions = new ArrayList<AxisAlignedBoundingBox>();
        for (final dxj voxelShape : voxelShapes) {
            for (final dwl aabb : voxelShape.d()) {
                collisions.add(VersionedClientWorld.GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }
    
    @Override
    public int getPackedLight(final FloatVector3 position) {
        final gt blockPos = new gt((double)position.getX(), (double)position.getY(), (double)position.getZ());
        final euv level = efu.I().s;
        return ezr.a(this.getBrightness((cgx)level, chg.b, blockPos), this.getBrightness((cgx)level, chg.a, blockPos));
    }
    
    @Override
    public int getMinBuildHeight() {
        final euv level = efu.I().s;
        return (level == null) ? 0 : level.u_();
    }
    
    @Override
    public int getMaxBuildHeight() {
        final euv level = efu.I().s;
        return (level == null) ? 0 : level.ah();
    }
    
    private int getBrightness(final cgx level, final chg layer, final gt blockPos) {
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
