// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event;

public abstract class DefaultCancellable implements Cancellable
{
    private boolean cancelled;
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
