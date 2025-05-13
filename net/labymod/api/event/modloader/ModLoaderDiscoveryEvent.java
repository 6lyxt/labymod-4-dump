// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.modloader;

import java.util.HashSet;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;
import java.util.Collection;
import net.labymod.api.modloader.ModLoader;
import net.labymod.api.event.Event;

public class ModLoaderDiscoveryEvent implements Event
{
    private final ModLoader modLoader;
    private final Collection<Path> additionalDiscoveries;
    
    public ModLoaderDiscoveryEvent(@NotNull final ModLoader modLoader) {
        this.modLoader = modLoader;
        this.additionalDiscoveries = new HashSet<Path>();
    }
    
    @NotNull
    public ModLoader modLoader() {
        return this.modLoader;
    }
    
    public void addAdditionalDiscovery(@NotNull final Path path) {
        this.additionalDiscoveries.add(path);
    }
    
    @NotNull
    public Collection<Path> getAdditionalDiscoveries() {
        return this.additionalDiscoveries;
    }
}
