// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.addon.lifecycle;

import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.event.LabyEvent;
import net.labymod.api.event.Event;

@LabyEvent(background = true, classLoaderExclusive = true, allowAllExceptions = true)
public class AddonPostEnableEvent implements Event
{
    private final LoadedAddon addon;
    
    public AddonPostEnableEvent(@NotNull final LoadedAddon addon) {
        Objects.requireNonNull(addon, "Loaded addon cannot be null!");
        this.addon = addon;
    }
    
    public LoadedAddon addon() {
        return this.addon;
    }
    
    public InstalledAddonInfo addonInfo() {
        return this.addon.info();
    }
}
