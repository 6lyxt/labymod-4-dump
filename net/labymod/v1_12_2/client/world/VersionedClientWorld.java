// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.world;

import net.labymod.api.util.math.MathHelper;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.chunk.Chunk;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import javax.inject.Inject;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.math.GameMathMapper;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.world.DefaultClientWorld;

@Singleton
@Implements(ClientWorld.class)
public class VersionedClientWorld extends DefaultClientWorld
{
    private static final GameMathMapper GAME_MATH_MAPPER;
    private static final ResourceLocation LABYMOD_UNKNOWN;
    private final ResourceLocationFactory resourceLocationFactory;
    
    @Inject
    public VersionedClientWorld(final ResourceLocationFactory resourceLocationFactory) {
        this.resourceLocationFactory = resourceLocationFactory;
    }
    
    @Override
    public ResourceLocation dimension() {
        final bsb level = bib.z().f;
        if (level == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.createMinecraft(level.s.q().b());
    }
    
    @NotNull
    @Override
    public ResourceLocation biome() {
        return this.getCurrentBiome();
    }
    
    @Override
    public long getDayTime() {
        final bsb level = bib.z().f;
        if (level == null) {
            return 0L;
        }
        return level.S();
    }
    
    @Override
    public boolean hasSolidBlockAt(final int x, final int y, final int z) {
        final bsb level = bib.z().f;
        if (level == null) {
            return false;
        }
        final awt blockState = level.o(new et(x, y, z));
        return blockState.u().q(blockState).a();
    }
    
    private ResourceLocation getCurrentBiome() {
        final vg entity = bib.z().aa();
        if (entity == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final et blockPos = new et(entity.p, entity.q, entity.r);
        final bsb clientWorld = bib.z().f;
        if (clientWorld == null || !clientWorld.e(blockPos)) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final axw chunk = clientWorld.f(blockPos);
        String biomeName = chunk.a(blockPos, clientWorld.C()).l();
        biomeName = biomeName.replace(" ", "");
        final String[] split = biomeName.split("(?=\\p{Upper})");
        final StringBuilder biomeBuilder = new StringBuilder();
        for (int i = 0; i < split.length; ++i) {
            final String biome = split[i].toLowerCase(Locale.US);
            if (i == split.length - 1) {
                biomeBuilder.append(biome);
                break;
            }
            biomeBuilder.append(biome).append("_");
        }
        return this.resourceLocationFactory.create("minecraft", biomeBuilder.toString());
    }
    
    @Override
    public Chunk getChunk(final int chunkX, final int chunkZ) {
        final bsb client = bib.z().f;
        if (client == null) {
            return null;
        }
        return (Chunk)client.a(chunkX, chunkZ);
    }
    
    @Override
    public BlockState getBlockState(final int x, final int y, final int z) {
        final bsb client = bib.z().f;
        if (client == null) {
            return null;
        }
        return new VersionedBlockState(client.o(new et(x, y, z)), x, y, z);
    }
    
    @Override
    public int getPackedLight(final FloatVector3 position) {
        final et blockPos = new et((double)position.getX(), (double)position.getY(), (double)position.getZ());
        final bsb level = bib.z().f;
        if (level == null) {
            return 0;
        }
        return MinecraftUtil.getPackedLight(level, blockPos);
    }
    
    @Override
    public List<AxisAlignedBoundingBox> getBlockCollisions(final AxisAlignedBoundingBox boundingBox) {
        final bud player = bib.z().h;
        final bsb level = bib.z().f;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        final List<bhb> voxelShapes = level.a((vg)player, (bhb)VersionedClientWorld.GAME_MATH_MAPPER.toAABB(boundingBox));
        final List<AxisAlignedBoundingBox> collisions = new ArrayList<AxisAlignedBoundingBox>();
        for (final bhb voxelShape : voxelShapes) {
            collisions.add(VersionedClientWorld.GAME_MATH_MAPPER.fromAABB(voxelShape));
        }
        return collisions;
    }
    
    @Override
    public int getMinBuildHeight() {
        return 0;
    }
    
    @Override
    public int getMaxBuildHeight() {
        final bsb level = bib.z().f;
        return (level == null) ? 0 : level.aa();
    }
    
    static {
        GAME_MATH_MAPPER = MathHelper.mapper();
        LABYMOD_UNKNOWN = ResourceLocation.create("labymod", "unknown");
    }
}
