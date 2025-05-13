// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources;

import javax.inject.Inject;
import net.labymod.api.client.resources.texture.GameImageTexture;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.resources.Resources;

@Singleton
@Implements(Resources.class)
public class DefaultResources implements Resources
{
    private final ResourceLocationFactory resourceLocationFactory;
    private final TextureRepository textureRepository;
    private final GameImageTexture.Factory gameImageTextureFactory;
    
    @Inject
    public DefaultResources(final ResourceLocationFactory resourceLocationFactory, final TextureRepository textureRepository, final GameImageTexture.Factory gameImageTextureFactory) {
        this.resourceLocationFactory = resourceLocationFactory;
        this.textureRepository = textureRepository;
        this.gameImageTextureFactory = gameImageTextureFactory;
    }
    
    @Override
    public ResourceLocationFactory resourceLocationFactory() {
        return this.resourceLocationFactory;
    }
    
    @Override
    public TextureRepository textureRepository() {
        return this.textureRepository;
    }
    
    @Override
    public GameImageTexture.Factory gameImageTextureFactory() {
        return this.gameImageTextureFactory;
    }
}
