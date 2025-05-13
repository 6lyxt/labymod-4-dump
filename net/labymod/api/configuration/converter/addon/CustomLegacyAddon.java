// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.converter.addon;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.configuration.converter.LegacyAddonConverter;

public class CustomLegacyAddon implements LegacyAddon
{
    private final LegacyAddonConverter.Version version;
    private final String name;
    private final String namespace;
    
    public CustomLegacyAddon(@NotNull final LegacyAddonConverter.Version version, @NotNull final String name, @Nullable final String namespace) {
        this.version = version;
        this.name = name;
        this.namespace = namespace;
    }
    
    @NotNull
    @Override
    public LegacyAddonConverter.Version getVersion() {
        return this.version;
    }
    
    @NotNull
    @Override
    public String getName() {
        return this.name;
    }
    
    @Nullable
    @Override
    public String getNamespace() {
        return this.namespace;
    }
}
