// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.spray.model;

import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.Constants;
import java.util.Locale;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.BufferedImage;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.Textures;
import net.labymod.api.event.Subscribe;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.client.resources.ReleaseTextureEvent;
import javax.inject.Inject;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.resources.texture.TextureRepository;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.labymod.api.client.resources.CompletableResourceLocation;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public final class SprayAssetProvider
{
    private final Short2ObjectMap<CompletableResourceLocation> diffuseCache;
    private final Int2ObjectMap<CompletableResourceLocation> wearCache;
    private final TextureRepository textureRepository;
    private final GameImageProvider gameImageProvider;
    
    @Inject
    public SprayAssetProvider(final EventBus eventBus, final TextureRepository textureRepository, final GameImageProvider gameImageProvider) {
        this.diffuseCache = (Short2ObjectMap<CompletableResourceLocation>)new Short2ObjectOpenHashMap();
        this.wearCache = (Int2ObjectMap<CompletableResourceLocation>)new Int2ObjectOpenHashMap();
        this.textureRepository = textureRepository;
        this.gameImageProvider = gameImageProvider;
        eventBus.registerListener(this);
    }
    
    @Subscribe
    public void onTextureReleased(final ReleaseTextureEvent event) {
        final ResourceLocation releasedTextureLocation = event.location();
        for (final Short2ObjectMap.Entry<CompletableResourceLocation> entry : this.diffuseCache.short2ObjectEntrySet()) {
            final CompletableResourceLocation location = (CompletableResourceLocation)entry.getValue();
            final ResourceLocation completedLocation = location.getCompleted();
            if (completedLocation.equals(releasedTextureLocation)) {
                this.diffuseCache.remove(entry.getShortKey());
                break;
            }
        }
        for (final Int2ObjectMap.Entry<CompletableResourceLocation> entry2 : this.wearCache.int2ObjectEntrySet()) {
            final CompletableResourceLocation location = (CompletableResourceLocation)entry2.getValue();
            final ResourceLocation completedLocation = location.getCompleted();
            if (completedLocation.equals(releasedTextureLocation)) {
                this.wearCache.remove(entry2.getIntKey());
                break;
            }
        }
    }
    
    public CompletableResourceLocation getTexture(final Spray spray, final TextureType textureType) {
        final short sprayId = (short)spray.getId();
        final boolean diffuseTexture = textureType == TextureType.DIFFUSE;
        CompletableResourceLocation location;
        if (diffuseTexture) {
            location = (CompletableResourceLocation)this.diffuseCache.get(sprayId);
        }
        else {
            location = (CompletableResourceLocation)this.wearCache.get(sprayId * 17 + textureType.ordinal());
        }
        if (location == null) {
            final ResourceLocation resourceLocation = ResourceLocation.create("labymod", "textures/spray/" + sprayId + "_" + textureType.getName() + ".png");
            if (diffuseTexture) {
                location = this.textureRepository.getOrRegisterTexture(resourceLocation, Textures.SPRAY_LOADING, this.getUrl(spray, textureType), this::scaleImage, texture -> {});
            }
            else {
                location = this.textureRepository.getOrRegisterTexture(resourceLocation, ResourceLocation.create("labymod", "textures/spray/default_wear_" + ((textureType == TextureType.WEAR_FADE_IN) ? "fadein" : "fadeout") + ".png"), this.getUrl(spray, textureType), texture -> {});
            }
            if (diffuseTexture) {
                this.diffuseCache.put(sprayId, (Object)location);
            }
            else {
                this.wearCache.put(sprayId * 17 + textureType.ordinal(), (Object)location);
            }
        }
        return location;
    }
    
    private GameImage scaleImage(final GameImage image) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        final int offset = width / 2;
        final BufferedImage bufferedImage = image.getImage();
        final Image scaledImage = bufferedImage.getScaledInstance(width - offset, height - offset, 4);
        final BufferedImage newImage = new BufferedImage(width, height, 6);
        final Graphics2D graphics = newImage.createGraphics();
        final int halfOffset = offset / 2;
        graphics.drawImage(scaledImage, halfOffset, halfOffset, null);
        graphics.dispose();
        return this.gameImageProvider.getImage(newImage);
    }
    
    private String getUrl(final Spray spray, final TextureType textureType) {
        return String.format(Locale.ROOT, Constants.LegacyUrls.CUSTOM_TEXTURES, "sticker", "cut/" + String.valueOf(spray.getUuid()) + textureType.getPathSuffix() + ".webp");
    }
    
    public enum TextureType
    {
        DIFFUSE(""), 
        WEAR_FADE_IN("_wear_fadein"), 
        WEAR_FADE_OUT("_wear_fadeout");
        
        private final String name;
        private final String pathSuffix;
        
        private TextureType(final String pathSuffix) {
            this.pathSuffix = pathSuffix;
            this.name = this.name().toLowerCase(Locale.ROOT);
        }
        
        public String getName() {
            return this.name;
        }
        
        public String getPathSuffix() {
            return this.pathSuffix;
        }
    }
}
