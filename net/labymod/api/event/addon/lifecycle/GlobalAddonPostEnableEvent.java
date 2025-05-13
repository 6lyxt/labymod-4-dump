// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.addon.lifecycle;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.event.Event;

public class GlobalAddonPostEnableEvent implements Event
{
    private final InstalledAddonInfo addonInfo;
    
    public GlobalAddonPostEnableEvent(@NotNull final InstalledAddonInfo addonInfo) {
        Objects.requireNonNull(addonInfo, "Addon info must not be null");
        this.addonInfo = addonInfo;
    }
    
    public InstalledAddonInfo addonInfo() {
        return this.addonInfo;
    }
}
