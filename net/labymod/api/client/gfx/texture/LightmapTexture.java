// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.texture;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface LightmapTexture
{
    void apply(final int p0);
    
    void clear(final int p0);
    
    int getTextureId();
}
