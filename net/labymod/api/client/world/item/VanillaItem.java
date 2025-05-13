// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.item;

import java.util.HashMap;
import net.labymod.api.loader.MinecraftVersion;
import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;

public final class VanillaItem
{
    private final ResourceLocation identifier;
    private final Map<String, MinecraftVersion> versions;
    private final boolean available;
    
    private VanillaItem(final ResourceLocation identifier, final MinecraftVersion[] versions) {
        this.versions = new HashMap<String, MinecraftVersion>();
        this.identifier = identifier;
        boolean available = false;
        for (final MinecraftVersion version : versions) {
            this.versions.put(version.toString(), version);
            available |= version.isCurrent();
        }
        this.available = available;
    }
    
    public static VanillaItem of(final String identifier, final MinecraftVersion[] versions) {
        final ResourceLocation itemIdentifier = ResourceLocation.parse(identifier);
        return new VanillaItem(itemIdentifier, versions);
    }
    
    public ResourceLocation identifier() {
        return this.identifier;
    }
    
    public Map<String, MinecraftVersion> getVersions() {
        return this.versions;
    }
    
    public boolean isAvailable() {
        return this.available;
    }
}
