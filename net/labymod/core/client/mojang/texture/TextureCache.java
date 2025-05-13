// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.mojang.texture;

import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.mojang.texture.SkinPolicy;
import net.labymod.api.client.resources.texture.TextureDetails;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.resources.texture.Texture;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import java.util.concurrent.ConcurrentHashMap;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.client.resources.CompletableResourceLocation;
import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.UUID;
import net.labymod.api.mojang.texture.MojangTextureType;
import java.util.function.BiFunction;

public final class TextureCache
{
    private static final BiFunction<MojangTextureType, UUID, ResourceLocation> LOCATION_FACTORY;
    private final MojangTextureType type;
    private final Map<UUID, CompletableResourceLocation> cache;
    private final TextureRepository textureRepository;
    private final TextureLookup textureLookup;
    
    public TextureCache(final MojangTextureType type, final TextureLookup textureLookup) {
        this.type = type;
        this.cache = new ConcurrentHashMap<UUID, CompletableResourceLocation>();
        this.textureLookup = textureLookup;
        this.textureRepository = Laby.references().textureRepository();
    }
    
    public void refreshTexture(final UUID profileId, final String url, final Consumer<Texture> loadCallback) {
        ThreadSafe.ensureRenderThread();
        this.cache.remove(profileId);
        if (url == null) {
            loadCallback.accept(null);
        }
        else {
            this.getOrLoad(url, profileId, loadCallback);
        }
    }
    
    public CompletableResourceLocation getOrLoad(final UUID profileId, final Consumer<Texture> loadCallback) {
        return this.getOrLoad(String.format("https://laby.net/api/v3/user/%s/skin.png?strategy=skip", profileId), profileId, loadCallback);
    }
    
    public CompletableResourceLocation getOrLoad(final String url, final UUID profileId, final Consumer<Texture> loadCallback) {
        return this.cache.computeIfAbsent(profileId, uuid -> {
            final TextureDetails.Builder builder = TextureDetails.builder();
            if (this.type == MojangTextureType.SKIN) {
                builder.withImageProcessor(image -> SkinPolicy.applyPolicy(image, true));
            }
            return this.textureRepository.getOrRegisterTexture(builder.withLocation(TextureCache.LOCATION_FACTORY.apply(this.type, profileId)).withFallbackLocation(this.textureLookup.lookup(profileId, this.type)).withUrl(url).withRegisterStrategy(TextureDetails.RegisterStrategy.REGISTER_AND_RELEASE).withFinishHandler(loadCallback).build());
        });
    }
    
    public MojangTextureType getType() {
        return this.type;
    }
    
    @Override
    public String toString() {
        return "TextureCache[" + this.type.toString();
    }
    
    static {
        LOCATION_FACTORY = ((type, profileId) -> ResourceLocation.create("labymod", type.getLocationPrefix() + "/" + profileId.toString()));
    }
}
