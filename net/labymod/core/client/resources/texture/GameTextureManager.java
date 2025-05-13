// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.texture;

import net.labymod.api.client.resources.ResourceLocation;

public interface GameTextureManager
{
    boolean hasResource(final ResourceLocation p0);
    
    void registerAndRelease(final ResourceLocation p0, final Object p1);
}
