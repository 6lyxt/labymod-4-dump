// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world;

import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.Minecraft;
import java.util.HashSet;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.world.chunk.Chunk;
import java.util.Set;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.util.ThreadSafe;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.ArrayList;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.Entity;
import java.util.List;
import net.labymod.api.client.world.BossBarRegistry;
import net.labymod.api.client.world.ClientWorld;

public abstract class DefaultClientWorld implements ClientWorld
{
    private final BossBarRegistry bossBarRegistry;
    private final List<Entity> entities;
    private final List<Player> players;
    private BiomeCache biomeCache;
    
    protected DefaultClientWorld() {
        this.bossBarRegistry = Laby.references().bossBarRegistry();
        this.entities = new ArrayList<Entity>();
        this.players = new ArrayList<Player>();
        this.biomeCache = new BiomeCache(ResourceLocation.create("labymod", "unknown"));
        this.buildPrettyName(this.biome());
    }
    
    @NotNull
    @Override
    public String getReadableBiomeName() {
        final ResourceLocation currentBiome = this.biome();
        if (this.biomeCache.sameBiome(currentBiome)) {
            return this.biomeCache.getPrettyName();
        }
        return this.buildPrettyName(currentBiome);
    }
    
    private String buildPrettyName(final ResourceLocation currentBiome) {
        String biomePath = currentBiome.getPath();
        final String[] split = biomePath.split("_");
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < split.length; ++i) {
            String biome = split[i];
            biome = biome.substring(0, 1).toUpperCase(Locale.US) + biome.substring(1);
            if (i == split.length - 1) {
                builder.append(biome);
                break;
            }
            builder.append(biome).append(" ");
        }
        biomePath = builder.toString();
        (this.biomeCache = new BiomeCache(currentBiome)).setPrettyName(biomePath);
        return biomePath;
    }
    
    @ApiStatus.Internal
    public void resetEntityList() {
        if (ThreadSafe.isNotOnRenderThread()) {
            return;
        }
        this.entities.clear();
        this.players.clear();
    }
    
    @ApiStatus.Internal
    public void addEntity(@NotNull final Entity entity) {
        if (ThreadSafe.isNotOnRenderThread()) {
            return;
        }
        this.entities.add(entity);
        if (entity instanceof final Player player) {
            this.players.add(player);
        }
    }
    
    @ApiStatus.Internal
    public void removeEntity(@NotNull final Entity entity) {
        if (ThreadSafe.isNotOnRenderThread()) {
            return;
        }
        this.entities.remove(entity);
        if (entity instanceof Player) {
            this.players.remove(entity);
        }
    }
    
    @Override
    public Optional<Entity> getEntity(final UUID uniqueId) {
        for (final Entity entity : this.entities) {
            if (entity.getUniqueId().equals(uniqueId)) {
                return Optional.of(entity);
            }
        }
        return Optional.empty();
    }
    
    @NotNull
    @Override
    public List<Player> getPlayers() {
        return Collections.unmodifiableList((List<? extends Player>)this.players);
    }
    
    @Override
    public Optional<Player> getPlayer(final String username) {
        for (final Player player : this.players) {
            if (player.getName().equalsIgnoreCase(username)) {
                return Optional.of(player);
            }
        }
        return Optional.empty();
    }
    
    @Override
    public Optional<Player> getPlayer(final UUID uniqueId) {
        for (final Player player : this.players) {
            if (player.getUniqueId().equals(uniqueId)) {
                return Optional.of(player);
            }
        }
        return Optional.empty();
    }
    
    @Override
    public Set<Chunk> getChunks() {
        final Minecraft minecraft = Laby.labyAPI().minecraft();
        final int renderDistance = minecraft.options().getRenderDistance();
        final int chunkDistance = renderDistance * 2 + 1;
        final MinecraftCamera camera = minecraft.getCamera();
        if (camera == null) {
            return Collections.emptySet();
        }
        final DoubleVector3 position = camera.position();
        final int x = MathHelper.floor(position.getX() / 16.0);
        final int z = MathHelper.floor(position.getZ() / 16.0);
        final Set<Chunk> chunks = new HashSet<Chunk>(chunkDistance * chunkDistance);
        for (int offsetX = -renderDistance; offsetX <= renderDistance; ++offsetX) {
            for (int offsetZ = -renderDistance; offsetZ <= renderDistance; ++offsetZ) {
                final Chunk chunk = this.getChunk(x + offsetX, z + offsetZ);
                if (chunk != null) {
                    chunks.add(chunk);
                }
            }
        }
        return chunks;
    }
    
    @Override
    public int getPlayerCount() {
        return this.players.size();
    }
    
    @NotNull
    @Override
    public List<Entity> getEntities() {
        return Collections.unmodifiableList((List<? extends Entity>)this.entities);
    }
    
    @NotNull
    @Override
    public BossBarRegistry bossBarRegistry() {
        return this.bossBarRegistry;
    }
    
    static final class BiomeCache
    {
        private final ResourceLocation resourceLocation;
        private String prettyName;
        
        public BiomeCache(final ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }
        
        public ResourceLocation getResourceLocation() {
            return this.resourceLocation;
        }
        
        public String getPrettyName() {
            return this.prettyName;
        }
        
        public void setPrettyName(final String prettyName) {
            this.prettyName = prettyName;
        }
        
        public boolean sameBiome(@NotNull final ResourceLocation resourceLocation) {
            return this.resourceLocation.getPath().equals(resourceLocation.getPath());
        }
    }
}
