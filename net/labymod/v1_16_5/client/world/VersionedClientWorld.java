// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.world;

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
        final dwt level = djz.C().r;
        if (level == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final vk location = level.Y().a();
        return this.resourceLocationFactory.create(location.b(), location.a());
    }
    
    @NotNull
    @Override
    public ResourceLocation biome() {
        return this.getCurrentBiome();
    }
    
    @Override
    public long getDayTime() {
        final dwt level = djz.C().r;
        if (level == null) {
            return 0L;
        }
        return level.ac();
    }
    
    @Override
    public boolean hasSolidBlockAt(final int x, final int y, final int z) {
        final dwt level = djz.C().r;
        return level != null && level.d_(new fx(x, y, z)).c().b();
    }
    
    private ResourceLocation getCurrentBiome() {
        final dwt level = djz.C().r;
        final aqa cameraEntity = djz.C().aa();
        if (level == null || cameraEntity == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final fx blockPos = cameraEntity.cB();
        if (blockPos.v() < 0 || blockPos.v() >= level.L()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final Optional<? extends gm<bsv>> optionalRegistry = level.r().a(gm.ay);
        if (!optionalRegistry.isPresent()) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final vk biomeResourceLocation = ((gm)optionalRegistry.get()).b((Object)level.v(blockPos));
        if (biomeResourceLocation == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }
    
    @Override
    public Chunk getChunk(final int chunkX, final int chunkZ) {
        final brx level = (brx)djz.C().r;
        if (level == null) {
            return null;
        }
        return (Chunk)level.d(chunkX, chunkZ);
    }
    
    @Override
    public BlockState getBlockState(final int x, final int y, final int z) {
        final brx level = (brx)djz.C().r;
        if (level == null) {
            return null;
        }
        final BlockState blockState = (BlockState)level.d_(new fx(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }
    
    @Override
    public List<AxisAlignedBoundingBox> getBlockCollisions(final AxisAlignedBoundingBox boundingBox) {
        final dzm player = djz.C().s;
        final dwt level = djz.C().r;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        final Stream<ddh> voxelShapes = level.b((aqa)player, (dci)VersionedClientWorld.GAME_MATH_MAPPER.toAABB(boundingBox));
        final List<AxisAlignedBoundingBox> collisions = new ArrayList<AxisAlignedBoundingBox>();
        voxelShapes.forEach(voxelShape -> {
            final List<dci> aabbs = voxelShape.d();
            aabbs.iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final dci aabb = iterator.next();
                collisions.add(VersionedClientWorld.GAME_MATH_MAPPER.fromAABB(aabb));
            }
            return;
        });
        return collisions;
    }
    
    @Override
    public int getPackedLight(final FloatVector3 position) {
        final fx blockPos = new fx((double)position.getX(), (double)position.getY(), (double)position.getZ());
        final dwt level = djz.C().r;
        return eaf.a(this.getBrightness((brx)level, bsf.b, blockPos), this.getBrightness((brx)level, bsf.a, blockPos));
    }
    
    @Override
    public int getMinBuildHeight() {
        return 0;
    }
    
    @Override
    public int getMaxBuildHeight() {
        final dwt level = djz.C().r;
        return (level == null) ? 0 : level.L();
    }
    
    private int getBrightness(final brx level, final bsf layer, final fx blockPos) {
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
