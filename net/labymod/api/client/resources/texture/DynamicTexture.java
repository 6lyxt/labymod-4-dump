// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.texture;

import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.logging.Logging;

public class DynamicTexture extends LabyTexture
{
    private static final Logging LOGGER;
    @Nullable
    private GameImage image;
    
    public DynamicTexture(final ResourceLocation textureLocation, final GameImage image) {
        super(textureLocation);
        this.image = image;
        TextureAllocator.allocateTexture(this, image.getWidth(), image.getHeight());
    }
    
    public DynamicTexture(final ResourceLocation textureLocation, final int width, final int height) {
        super(textureLocation);
        this.image = GameImage.IMAGE_PROVIDER.createImage(width, height);
        TextureAllocator.allocateTexture(this, width, height);
    }
    
    public void upload() {
        if (this.image == null) {
            DynamicTexture.LOGGER.error("Trying to upload closed texture {}", this.getTextureId());
            return;
        }
        TextureAllocator.upload(this, this.image);
    }
    
    public void setImageAndUpload(final GameImage image) {
        this.setImage(image);
        this.upload();
    }
    
    public void setImage(final GameImage image) {
        boolean changedSize = this.image == null;
        if (this.image != null) {
            final int width = this.image.getWidth();
            final int height = this.image.getHeight();
            if (width != image.getWidth() || height != image.getHeight()) {
                changedSize = true;
            }
            this.image.close();
        }
        this.image = image;
        if (changedSize) {
            TextureAllocator.allocateTexture(this, image.getWidth(), image.getHeight());
        }
    }
    
    @Nullable
    public GameImage getImage() {
        return this.image;
    }
    
    @Override
    public void close() {
        if (this.image != null) {
            this.image.close();
            this.release();
            this.image = null;
        }
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
