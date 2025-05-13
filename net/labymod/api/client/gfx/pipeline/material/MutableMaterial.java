// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.material;

import net.labymod.api.client.resources.ResourceLocation;

public interface MutableMaterial extends Material
{
    void setTextureLocation(final ResourceLocation p0);
    
    void setColor(final MaterialColor p0);
    
    void setGlowing(final boolean p0);
}
