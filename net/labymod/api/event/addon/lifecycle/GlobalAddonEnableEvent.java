// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.addon.lifecycle;

import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.event.Event;

public class GlobalAddonEnableEvent implements Event
{
    private final LoadedAddon addon;
    private final Object instance;
    
    public GlobalAddonEnableEvent(@NotNull final LoadedAddon addon, @NotNull final Object instance) {
        Objects.requireNonNull(addon, "Loaded addon cannot be null!");
        Objects.requireNonNull(instance, "Addon instance cannot be null!");
        this.addon = addon;
        this.instance = instance;
    }
    
    public Object getInstance() {
        return this.instance;
    }
    
    public LoadedAddon addon() {
        return this.addon;
    }
    
    public InstalledAddonInfo addonInfo() {
        return this.addon.info();
    }
}
