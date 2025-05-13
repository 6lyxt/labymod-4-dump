// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.platform.launcher;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.models.version.Version;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface LauncherService
{
    @Nullable
    String getLauncher();
    
    @Nullable
    String getLauncherVersion();
    
    @Nullable
    UUID getGameSessionId();
    
    @Nullable
    String getModPackId();
    
    @Nullable
    String getModPackName();
    
    @Nullable
    Version getModPackVersion();
    
    boolean isUsingLabyModLauncher();
    
    boolean isUsingModLoader();
    
    String getModLoader();
    
    void restart();
    
    boolean isConnectedToLauncher();
    
    @NotNull
    default String getLauncherOrDefault(@NotNull final String defaultValue) {
        Objects.requireNonNull(defaultValue, "defaultValue");
        final String launcher = this.getLauncher();
        return (launcher == null) ? defaultValue : launcher;
    }
    
    default boolean hasGameSessionId() {
        return this.getGameSessionId() != null;
    }
    
    default boolean isUsingModPack() {
        return this.getModPackId() != null;
    }
}
