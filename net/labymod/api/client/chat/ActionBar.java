// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.Component;

public class ActionBar
{
    private final Runnable removeHandler;
    private Component message;
    private int tick;
    
    public ActionBar(final Runnable removeHandler, final Component message) {
        this.removeHandler = removeHandler;
        this.message = message;
    }
    
    public void remove() {
        this.removeHandler.run();
    }
    
    @NotNull
    public Component message() {
        return this.message;
    }
    
    public void setMessage(@NotNull final Component message) {
        this.message = message;
    }
    
    @ApiStatus.Internal
    public int getTick() {
        return this.tick;
    }
    
    @ApiStatus.Internal
    public void setTick(final int tick) {
        this.tick = tick;
    }
}
