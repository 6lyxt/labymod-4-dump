// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.misc;

import java.io.File;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Cancellable;
import net.labymod.api.event.Event;

public class VanillaOptionsSaveEvent implements Event, Cancellable
{
    private final Phase phase;
    private final File optionsFile;
    private boolean cancelled;
    
    public VanillaOptionsSaveEvent(final Phase phase, final File optionsFile) {
        this.phase = phase;
        this.optionsFile = optionsFile;
    }
    
    public Phase getPhase() {
        return this.phase;
    }
    
    public File getFile() {
        return this.optionsFile;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
