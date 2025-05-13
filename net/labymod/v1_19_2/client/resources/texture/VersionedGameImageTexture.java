// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.client.resources.texture;

import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.resources.texture.GameImageTexture;

public class VersionedGameImageTexture extends fka implements GameImageTexture
{
    private static final Logging LOGGER;
    private final ResourceLocation resourceLocation;
    private dzq image;
    private GameImage gameImage;
    
    public VersionedGameImageTexture(final ResourceLocation location) {
        super((abb)location.getMinecraftLocation());
        this.resourceLocation = location;
    }
    
    public VersionedGameImageTexture(final ResourceLocation location, final GameImage gameImage) {
        super((abb)location.getMinecraftLocation());
        this.resourceLocation = location;
        this.gameImage = gameImage;
    }
    
    private void prepareImage() {
        if (this.gameImage != null && this.image == null) {
            this.image = ((NativeGameImage)this.gameImage).image();
            this.uploadImage();
            return;
        }
        if (this.image == null) {
            try {
                this.image = NativeImageUtils.read(this.resourceLocation.openStream());
            }
            catch (final IOException exception) {
                return;
            }
            this.gameImage = new NativeGameImage(this.image);
        }
        this.uploadImage();
    }
    
    private void uploadImage() {
        if (RenderSystem.isOnRenderThreadOrInit()) {
            this.upload();
        }
        else {
            RenderSystem.recordRenderCall(this::upload);
        }
    }
    
    private void upload() {
        try {
            this._upload();
        }
        catch (final Throwable throwable) {
            VersionedGameImageTexture.LOGGER.error("Failed to upload image {}", this.resourceLocation, throwable);
            throw throwable;
        }
    }
    
    private void _upload() {
        if (this.image == null) {
            return;
        }
        if (this.gameImage.isFreed()) {
            return;
        }
        TextureUtil.prepareImage(this.b(), 0, this.image.a(), this.image.b());
        this.image.a(0, 0, 0, false);
    }
    
    public void a(final aim resourceManager) {
        this.prepareImage();
    }
    
    public GameImage getImage() {
        return this.gameImage;
    }
    
    public ResourceLocation getTextureResourceLocation() {
        return this.resourceLocation;
    }
    
    public int getTextureId() {
        return this.b();
    }
    
    public void close() {
        super.close();
        this.gameImage.close();
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
