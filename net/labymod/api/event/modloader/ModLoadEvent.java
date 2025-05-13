// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.modloader;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.modloader.ModLoader;
import net.labymod.api.modloader.mod.ModInfo;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class ModLoadEvent extends DefaultCancellable implements Event
{
    private final ModInfo modInfo;
    private final ModLoader modLoader;
    
    public ModLoadEvent(@NotNull final ModInfo modInfo, @NotNull final ModLoader modLoader) {
        this.modInfo = modInfo;
        this.modLoader = modLoader;
    }
    
    @NotNull
    public ModInfo modInfo() {
        return this.modInfo;
    }
    
    @NotNull
    public ModLoader modLoader() {
        return this.modLoader;
    }
}
