// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.pack;

import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.reference.annotation.Referenceable;
import java.util.function.BiConsumer;
import java.util.Set;
import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.client.resources.ResourceLocation;

public interface ResourcePack
{
    String getName();
    
    InputStream getClientResource(final ResourceLocation p0) throws IOException;
    
    InputStream getDataResource(final ResourceLocation p0) throws IOException;
    
    default InputStream getResource(final boolean isServerData, final ResourceLocation location) throws IOException {
        return isServerData ? this.getDataResource(location) : this.getClientResource(location);
    }
    
    boolean hasClientResource(final ResourceLocation p0);
    
    boolean hasDataResource(final ResourceLocation p0);
    
    void listResources(final boolean p0, final String p1, final String p2, final ResourceOutput p3);
    
    default boolean hasResource(final boolean isServerData, final ResourceLocation location) {
        return isServerData ? this.hasDataResource(location) : this.hasClientResource(location);
    }
    
    Set<String> getClientNamespaces();
    
    Set<String> getDataNamespaces();
    
    @FunctionalInterface
    public interface InputStreamSupplier
    {
        InputStream get() throws IOException;
        
        default void silentClose() {
            try (final InputStream inputStream = this.get()) {}
            catch (final IOException ex) {}
        }
    }
    
    @FunctionalInterface
    public interface ResourceOutput extends BiConsumer<ResourceLocation, InputStreamSupplier>
    {
    }
    
    @Referenceable
    public interface Factory
    {
        ResourcePack create(final String p0);
        
        ResourcePack create(final String p0, final LoadedAddon p1);
        
        ResourcePack createClientPack(final String p0, final String... p1);
        
        ResourcePack createDataPack(final String p0, final String... p1);
        
        ResourcePack createClientAndDataPack(final String p0, final Set<String> p1, final Set<String> p2);
    }
}
