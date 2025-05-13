// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.resources.texture;

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

public class VersionedGameImageTexture extends cdm implements GameImageTexture
{
    private final boolean asynchronous;
    private final GameImage gameImage;
    private BufferedImage bufferedImage;
    private boolean uploaded;
    
    private VersionedGameImageTexture(final ResourceLocation location, final GameImage gameImage) {
        super((nf)location.getMinecraftLocation());
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
    
    public void a(final cep p_loadTexture_1_) {
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
            cdt.a(this.getTextureId(), this.bufferedImage, this.b, this.c);
            this.onUploaded();
        }
    }
    
    public GameImage getImage() {
        return this.gameImage;
    }
    
    public ResourceLocation getTextureResourceLocation() {
        return (ResourceLocation)this.f;
    }
    
    public int getTextureId() {
        return this.b();
    }
    
    public void c() {
        super.c();
        this.bufferedImage = this.gameImage.getImage();
    }
    
    private void onUploaded() {
        this.uploaded = true;
        this.bufferedImage = null;
    }
}
