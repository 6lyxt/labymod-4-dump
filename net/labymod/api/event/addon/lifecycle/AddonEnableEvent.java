// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.addon.lifecycle;

import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.reference.ReferenceStorageAccessor;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.event.LabyEvent;
import net.labymod.api.event.Event;

@LabyEvent(background = true, classLoaderExclusive = true, allowAllExceptions = true)
public class AddonEnableEvent implements Event
{
    private final LoadedAddon addon;
    private final Object instance;
    private final ReferenceStorageAccessor referenceStorageAccessor;
    
    public AddonEnableEvent(@NotNull final LoadedAddon addon, @NotNull final Object instance, @Nullable final ReferenceStorageAccessor referenceStorageAccessor) {
        Objects.requireNonNull(addon, "Loaded addon cannot be null!");
        Objects.requireNonNull(instance, "Addon instance cannot be null!");
        this.addon = addon;
        this.instance = instance;
        this.referenceStorageAccessor = referenceStorageAccessor;
    }
    
    public Object getInstance() {
        return this.instance;
    }
    
    public ReferenceStorageAccessor getReferenceStorageAccessor() {
        return this.referenceStorageAccessor;
    }
    
    public LoadedAddon addon() {
        return this.addon;
    }
    
    public InstalledAddonInfo addonInfo() {
        return this.addon.info();
    }
}
