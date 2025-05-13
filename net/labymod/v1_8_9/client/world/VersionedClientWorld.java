// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.world;

import net.labymod.api.util.math.MathHelper;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.chunk.Chunk;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.Laby;
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
    
    public static ResourceLocation getDimensionFromId(final int dimensionId) {
        final String name = switch (dimensionId) {
            case -1 -> "the_nether";
            case 0 -> "overworld";
            case 1 -> "the_end";
            default -> null;
        };
        if (name == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        return Laby.references().resourceLocationFactory().createMinecraft(name);
    }
    
    @Override
    public ResourceLocation dimension() {
        final bdb level = ave.A().f;
        if (level == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        return getDimensionFromId(level.t.q());
    }
    
    @NotNull
    @Override
    public ResourceLocation biome() {
        return this.getCurrentBiome();
    }
    
    @Override
    public long getDayTime() {
        final bdb level = ave.A().f;
        if (level == null) {
            return 0L;
        }
        return level.L();
    }
    
    @Override
    public boolean hasSolidBlockAt(final int x, final int y, final int z) {
        final bdb level = ave.A().f;
        return level != null && level.p(new cj(x, y, z)).c().t().a();
    }
    
    private ResourceLocation getCurrentBiome() {
        final pk entity = ave.A().ac();
        if (entity == null) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final cj blockPos = new cj(entity.s, entity.t, entity.u);
        final bdb clientWorld = ave.A().f;
        if (clientWorld == null || !clientWorld.e(blockPos)) {
            return VersionedClientWorld.LABYMOD_UNKNOWN;
        }
        final amy chunk = clientWorld.f(blockPos);
        String biomeName = chunk.a(blockPos, clientWorld.v()).ah;
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
        final bdb client = ave.A().f;
        if (client == null) {
            return null;
        }
        return (Chunk)client.a(chunkX, chunkZ);
    }
    
    @Override
    public BlockState getBlockState(final int x, final int y, final int z) {
        final bdb client = ave.A().f;
        if (client == null) {
            return null;
        }
        return new VersionedBlockState(client.p(new cj(x, y, z)), x, y, z);
    }
    
    @Override
    public int getPackedLight(final FloatVector3 position) {
        final cj blockPos = new cj((double)position.getX(), (double)position.getY(), (double)position.getZ());
        final bdb level = ave.A().f;
        if (level == null) {
            return 0;
        }
        return MinecraftUtil.getPackedLight(level, blockPos);
    }
    
    @Override
    public List<AxisAlignedBoundingBox> getBlockCollisions(final AxisAlignedBoundingBox boundingBox) {
        final bew player = ave.A().h;
        final bdb level = ave.A().f;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        final List<aug> voxelShapes = level.a((aug)VersionedClientWorld.GAME_MATH_MAPPER.toAABB(boundingBox));
        final List<AxisAlignedBoundingBox> collisions = new ArrayList<AxisAlignedBoundingBox>();
        for (final aug voxelShape : voxelShapes) {
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
        final bdb level = ave.A().f;
        return (level == null) ? 0 : level.U();
    }
    
    static {
        GAME_MATH_MAPPER = MathHelper.mapper();
        LABYMOD_UNKNOWN = ResourceLocation.create("labymod", "unknown");
    }
}
