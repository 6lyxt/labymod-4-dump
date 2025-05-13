// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources;

import net.labymod.api.metadata.Metadata;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.client.resources.CompletableResourceLocation;
import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.client.resources.ResourceLocation;

public abstract class AbstractResourceLocation implements ResourceLocation
{
    private final ResourceLocation delegate;
    
    public AbstractResourceLocation(final String namespace, final String path) {
        this.delegate = ResourceLocation.create(namespace, path);
    }
    
    @Override
    public String getNamespace() {
        return this.getDelegate().getNamespace();
    }
    
    @Override
    public String getPath() {
        return this.getDelegate().getPath();
    }
    
    @Override
    public <T> T getMinecraftLocation() {
        return this.getDelegate().getMinecraftLocation();
    }
    
    @Override
    public InputStream openStream() throws IOException {
        return this.getDelegate().openStream();
    }
    
    @Override
    public boolean exists() {
        return this.getDelegate().exists();
    }
    
    @ApiStatus.Experimental
    @ApiStatus.Internal
    @Override
    public CompletableResourceLocation toCompletableResourceLocation() {
        return this.getDelegate().toCompletableResourceLocation();
    }
    
    @Override
    public void metadata(final Metadata metadata) {
        this.getDelegate().metadata(metadata);
    }
    
    @Override
    public Metadata metadata() {
        return this.getDelegate().metadata();
    }
    
    public ResourceLocation getDelegate() {
        return this.delegate;
    }
}
