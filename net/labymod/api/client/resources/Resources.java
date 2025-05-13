// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources;

import net.labymod.api.client.resources.texture.GameImageTexture;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface Resources
{
    ResourceLocationFactory resourceLocationFactory();
    
    TextureRepository textureRepository();
    
    GameImageTexture.Factory gameImageTextureFactory();
}
