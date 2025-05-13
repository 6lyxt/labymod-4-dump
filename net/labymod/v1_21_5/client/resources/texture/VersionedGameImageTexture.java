// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.resources.texture;

import net.labymod.v1_21_5.client.util.MinecraftUtil;
import com.mojang.blaze3d.systems.GpuDevice;
import java.util.function.Supplier;
import com.mojang.blaze3d.textures.TextureFormat;
import java.util.Objects;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.resources.texture.GameImageTexture;

public class VersionedGameImageTexture extends hki implements GameImageTexture
{
    private static final Logging LOGGER;
    private final ResourceLocation resourceLocation;
    private fkf image;
    private GameImage gameImage;
    
    public VersionedGameImageTexture(final ResourceLocation location) {
        super((alr)location.getMinecraftLocation());
        this.resourceLocation = location;
    }
    
    public VersionedGameImageTexture(final ResourceLocation location, final GameImage gameImage) {
        super((alr)location.getMinecraftLocation());
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
        this.upload();
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
        final GpuDevice device;
        final GpuDevice gpuDevice = device = RenderSystem.getDevice();
        final alr b = this.b();
        Objects.requireNonNull(b);
        this.a = device.createTexture((Supplier)b::toString, TextureFormat.RGBA8, this.image.a(), this.image.b(), 1);
        gpuDevice.createCommandEncoder().writeToTexture(this.a, this.image);
    }
    
    public void a(final hkr contents) {
        this.prepareImage();
    }
    
    public hkr a(final avo $$0) throws IOException {
        return (this.gameImage == null) ? super.a($$0) : null;
    }
    
    public GameImage getImage() {
        return this.gameImage;
    }
    
    public ResourceLocation getTextureResourceLocation() {
        return this.resourceLocation;
    }
    
    public int getTextureId() {
        return MinecraftUtil.getGpuTextureId(this.a());
    }
    
    public void close() {
        super.close();
        this.gameImage.close();
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
