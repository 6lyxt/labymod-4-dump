// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.serverapi;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.serverapi.core.model.display.EconomyDisplay;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class EconomyUpdateEvent extends DefaultCancellable implements Event
{
    private EconomyDisplay economy;
    
    public EconomyUpdateEvent(@NotNull final EconomyDisplay economy) {
        Objects.requireNonNull(economy, "Economy cannot be null");
        this.economy = economy;
    }
    
    public EconomyDisplay economy() {
        return this.economy;
    }
    
    public void setEconomy(@NotNull final EconomyDisplay economy) {
        Objects.requireNonNull(economy, "Economy cannot be null");
        this.economy = economy;
    }
}
