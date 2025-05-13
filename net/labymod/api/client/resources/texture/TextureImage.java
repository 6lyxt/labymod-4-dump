// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.texture;

import net.labymod.api.Laby;
import java.io.InputStream;
import java.io.IOException;
import net.labymod.api.client.resources.ResourceLocation;

public class TextureImage
{
    private static GameImageProvider gameImageProvider;
    private final GameImage gameImage;
    private final String type;
    
    public TextureImage(final ResourceLocation location) throws IOException {
        this(gameImageProvider().getImage(location.openStream()));
    }
    
    public TextureImage(final GameImage gameImage) {
        this(gameImage, "png");
    }
    
    public TextureImage(final InputStream inputStream, final String type) throws IOException {
        this(gameImageProvider().getImage(inputStream), type);
    }
    
    public TextureImage(final GameImage gameImage, final String type) {
        this.gameImage = gameImage;
        this.type = type;
    }
    
    public GameImage getGameImage() {
        return this.gameImage;
    }
    
    public String getFormat() {
        return this.type;
    }
    
    public void close() {
        if (this.gameImage != null) {
            this.gameImage.close();
        }
    }
    
    public static GameImageProvider gameImageProvider() {
        if (TextureImage.gameImageProvider == null) {
            TextureImage.gameImageProvider = Laby.references().gameImageProvider();
        }
        return TextureImage.gameImageProvider;
    }
}
