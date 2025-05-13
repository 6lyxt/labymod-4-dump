// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources;

import net.labymod.api.Laby;
import net.labymod.api.metadata.Metadata;
import java.io.IOException;
import java.io.InputStream;

public interface ThemeResourceLocation extends ResourceLocation
{
    public static final ResourceLocationFactory FACTORY = Laby.references().resourceLocationFactory();
    
    ResourceLocation resource();
    
    default <T> T getMinecraftLocation() {
        return this.resource().getMinecraftLocation();
    }
    
    default String getNamespace() {
        return this.resource().getNamespace();
    }
    
    default String getPath() {
        return this.resource().getPath();
    }
    
    default InputStream openStream() throws IOException {
        return this.resource().openStream();
    }
    
    default boolean exists() {
        return this.resource().exists();
    }
    
    default void metadata(final Metadata metadata) {
        this.resource().metadata(metadata);
    }
    
    default Metadata metadata() {
        return this.resource().metadata();
    }
}
