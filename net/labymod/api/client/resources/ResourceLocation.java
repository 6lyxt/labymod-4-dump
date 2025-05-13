// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources;

import org.jetbrains.annotations.ApiStatus;
import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.Laby;
import java.util.function.Function;
import net.labymod.api.metadata.MetadataExtension;

public interface ResourceLocation extends MetadataExtension
{
    public static final Function<ResourceLocation, CompletableResourceLocation> MEMOIZED_LOCATION = Laby.references().functionMemoizeStorage().memoize(location -> new CompletableResourceLocation(location, true));
    
    default ResourceLocation create(final String namespace, final String path) {
        return Laby.references().resourceLocationFactory().create(namespace, path);
    }
    
    default ResourceLocation parse(final String value) {
        return Laby.references().resourceLocationFactory().parse(value);
    }
    
     <T> T getMinecraftLocation();
    
    String getNamespace();
    
    String getPath();
    
    InputStream openStream() throws IOException;
    
    boolean exists();
    
    @ApiStatus.Experimental
    @ApiStatus.Internal
    default CompletableResourceLocation toCompletableResourceLocation() {
        return ResourceLocation.MEMOIZED_LOCATION.apply(this);
    }
}
