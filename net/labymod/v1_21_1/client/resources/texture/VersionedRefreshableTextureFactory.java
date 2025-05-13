// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.client.resources.texture;

import net.labymod.api.client.resources.texture.Texture;
import net.labymod.core.client.resources.texture.concurrent.DefaultRefreshableTexture;
import net.labymod.api.models.Implements;
import net.labymod.core.client.resources.texture.concurrent.RefreshableTextureFactory;

@Implements(RefreshableTextureFactory.class)
public class VersionedRefreshableTextureFactory implements RefreshableTextureFactory
{
    @Override
    public Object createVersionedTexture(final DefaultRefreshableTexture texture) {
        return new VersionedRefreshableTexture(texture);
    }
    
    @Override
    public Texture createTexture(final DefaultRefreshableTexture texture) {
        return new VersionedRefreshableTexture(texture);
    }
}
