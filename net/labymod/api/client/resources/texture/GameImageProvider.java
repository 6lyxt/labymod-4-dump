// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.texture;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface GameImageProvider
{
    default GameImage getImage(final ResourceLocation location) throws IOException {
        return this.getImage(location.openStream());
    }
    
    GameImage getImage(final InputStream p0) throws IOException;
    
    GameImage createImage(final int p0, final int p1);
    
    GameImage loadImage(final Texture p0);
    
    GameImage getImage(final BufferedImage p0);
    
    GameImage getImage(final BufferedImage p0, final BufferedImageProcessor p1);
}
