// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.converter.addon;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.configuration.converter.LegacyAddonConverter;
import java.nio.file.Path;

public class LoadableLegacyAddon implements LegacyAddon
{
    private final Path path;
    private final LegacyAddonConverter.Version version;
    private final String uuid;
    private final String name;
    private final String mainClass;
    
    public LoadableLegacyAddon(@NotNull final Path path, @NotNull final LegacyAddonConverter.Version version, @NotNull final String uuid, @NotNull final String name, @NotNull final String mainClass) {
        this.path = path;
        this.version = version;
        this.uuid = uuid;
        this.name = name;
        this.mainClass = mainClass;
    }
    
    @NotNull
    public Path getPath() {
        return this.path;
    }
    
    @NotNull
    @Override
    public LegacyAddonConverter.Version getVersion() {
        return this.version;
    }
    
    @NotNull
    public String getUuid() {
        return this.uuid;
    }
    
    @NotNull
    @Override
    public String getName() {
        return this.name;
    }
    
    @NotNull
    public String getMainClass() {
        return this.mainClass;
    }
    
    @Nullable
    @Override
    public String getNamespace() {
        return Laby.references().legacyConfigConverter().getModernAddonNamespace(this.uuid);
    }
}
