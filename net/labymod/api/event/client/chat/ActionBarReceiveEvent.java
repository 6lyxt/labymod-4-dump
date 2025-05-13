// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.chat;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.Component;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class ActionBarReceiveEvent extends DefaultCancellable implements Event
{
    private final Phase phase;
    private final boolean animatedMessage;
    private Component message;
    
    public ActionBarReceiveEvent(@NotNull final Phase phase, @Nullable final Component message, final boolean animatedMessage) {
        this.phase = phase;
        this.message = message;
        this.animatedMessage = animatedMessage;
    }
    
    @NotNull
    public Phase phase() {
        return this.phase;
    }
    
    @Nullable
    public Component getMessage() {
        return this.message;
    }
    
    public void setMessage(final Component message) {
        if (this.phase != Phase.PRE) {
            throw new IllegalStateException("Message cannot be modified after PRE phase");
        }
        this.message = message;
    }
    
    public boolean isAnimatedMessage() {
        return this.animatedMessage;
    }
    
    @Override
    public void setCancelled(final boolean cancelled) {
        if (this.phase != Phase.PRE) {
            throw new IllegalStateException("ActionBar cannot be cancelled after PRE phase");
        }
        super.setCancelled(cancelled);
    }
    
    @Deprecated(forRemoval = true, since = "4.1.0")
    @Nullable
    public Component message() {
        return this.getMessage();
    }
    
    @Deprecated(forRemoval = true, since = "4.1.0")
    public void message(@Nullable final Component message) {
        this.setMessage(message);
    }
}
