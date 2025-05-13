// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.resources.texture;

import net.labymod.api.Textures;
import net.labymod.api.client.resources.texture.TextureAllocator;
import net.labymod.api.client.gfx.texture.TextureTarget;
import java.io.IOException;
import java.io.InputStream;
import net.labymod.core.client.resources.BufferedGameImage;
import javax.imageio.ImageIO;
import net.labymod.api.client.resources.ResourceLocation;
import java.awt.image.BufferedImage;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.GameImageTexture;

public class VersionedGameImageTexture extends bme implements GameImageTexture
{
    private final boolean asynchronous;
    private final GameImage gameImage;
    private BufferedImage bufferedImage;
    private boolean uploaded;
    
    private VersionedGameImageTexture(final ResourceLocation location, final GameImage gameImage) {
        super((jy)location.getMinecraftLocation());
        this.asynchronous = location.metadata().getBoolean("asynchronous", false);
        this.gameImage = gameImage;
        this.bufferedImage = this.gameImage.getImage();
    }
    
    public static GameImageTexture of(final ResourceLocation location) throws IOException {
        try (final InputStream stream = location.openStream()) {
            return of(location, new BufferedGameImage(ImageIO.read(stream)));
        }
    }
    
    public static GameImageTexture of(final ResourceLocation location, final GameImage image) {
        return new VersionedGameImageTexture(location, image);
    }
    
    public void a(final bni p_loadTexture_1_) {
        if (this.uploaded) {
            this.c();
            this.uploaded = false;
        }
        if (this.bufferedImage == null) {
            return;
        }
        if (this.asynchronous) {
            TextureAllocator.allocateAndUpload(TextureTarget.TEXTURE_2D, this.getTextureId(), this.gameImage, this::onUploaded);
        }
        else {
            bml.a(this.getTextureId(), this.bufferedImage, this.b, this.c);
            this.onUploaded();
        }
    }
    
    public GameImage getImage() {
        return this.gameImage;
    }
    
    public int b() {
        if (this.uploaded) {
            return super.b();
        }
        final bmj textureManager = ave.A().P();
        final jy emptyResourceLocation = Textures.EMPTY.getMinecraftLocation();
        bmk texture = textureManager.b(emptyResourceLocation);
        if (texture == null) {
            textureManager.a(emptyResourceLocation, texture = (bmk)new bme(emptyResourceLocation));
        }
        return texture.b();
    }
    
    public void c() {
        super.c();
        this.bufferedImage = this.gameImage.getImage();
    }
    
    public ResourceLocation getTextureResourceLocation() {
        return (ResourceLocation)this.f;
    }
    
    public int getTextureId() {
        return super.b();
    }
    
    private void onUploaded() {
        this.uploaded = true;
        this.bufferedImage = null;
    }
}
