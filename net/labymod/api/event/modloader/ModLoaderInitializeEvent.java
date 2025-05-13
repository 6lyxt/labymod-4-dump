// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.modloader;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.event.Phase;
import net.labymod.api.modloader.ModLoader;
import net.labymod.api.event.Event;

public class ModLoaderInitializeEvent implements Event
{
    private final ModLoader modLoader;
    private final Phase phase;
    
    public ModLoaderInitializeEvent(@NotNull final ModLoader modLoader, @NotNull final Phase phase) {
        this.modLoader = modLoader;
        this.phase = phase;
    }
    
    @NotNull
    public ModLoader modLoader() {
        return this.modLoader;
    }
    
    @NotNull
    public Phase phase() {
        return this.phase;
    }
}
