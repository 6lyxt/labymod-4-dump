// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.texture.concurrent;

import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface RefreshableTextureFactory
{
    @Deprecated(forRemoval = true, since = "4.2.17")
    Object createVersionedTexture(final DefaultRefreshableTexture p0);
    
    Texture createTexture(final DefaultRefreshableTexture p0);
}
