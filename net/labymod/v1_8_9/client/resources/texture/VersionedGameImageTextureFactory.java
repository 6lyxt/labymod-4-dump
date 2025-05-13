// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.resources.texture;

import net.labymod.api.client.resources.texture.GameImage;
import java.io.IOException;
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
        try {
            return VersionedGameImageTexture.of(location);
        }
        catch (final IOException exception) {
            throw new IllegalStateException(String.valueOf(location) + " could not be found.");
        }
    }
    
    @Override
    public GameImageTexture create(final ResourceLocation location, final GameImage image) {
        return VersionedGameImageTexture.of(location, image);
    }
}
