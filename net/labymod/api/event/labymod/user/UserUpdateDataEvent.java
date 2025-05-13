// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.user;

import net.labymod.api.user.GameUser;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Cancellable;

public class UserUpdateDataEvent extends AbstractGameUserEvent implements Cancellable
{
    private final Phase phase;
    private boolean cancelled;
    
    public UserUpdateDataEvent(final Phase phase, final GameUser user) {
        super(user);
        this.phase = phase;
    }
    
    public Phase phase() {
        return this.phase;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancelled) {
        if (this.phase == Phase.POST) {
            throw new IllegalStateException("Cannot cancel POST phase");
        }
        this.cancelled = cancelled;
    }
}
