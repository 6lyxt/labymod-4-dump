// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.texture;

import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.client.resources.ResourceLocation;

public interface GameImageTexture extends Texture
{
    GameImage getImage();
    
    ResourceLocation getTextureResourceLocation();
    
    @Referenceable
    public interface Factory
    {
        GameImageTexture create(final ResourceLocation p0);
        
        GameImageTexture create(final ResourceLocation p0, final GameImage p1);
    }
}
