// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.client.resources.texture;

import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.resources.texture.GameImageTexture;

@Singleton
@Implements(GameImageTexture.Factory.class)
public class VersionedGameImageTextureFactory implements GameImageTexture.Factory
{
    @Override
    public GameImageTexture create(final ResourceLocation location) {
        return new VersionedGameImageTexture(location);
    }
    
    @Override
    public GameImageTexture create(final ResourceLocation location, final GameImage image) {
        return new VersionedGameImageTexture(location, image);
    }
}
