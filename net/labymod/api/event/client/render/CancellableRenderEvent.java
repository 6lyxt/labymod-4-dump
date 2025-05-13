// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render;

import net.labymod.api.event.Phase;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Cancellable;

public abstract class CancellableRenderEvent extends RenderEvent implements Cancellable
{
    private boolean cancelled;
    
    protected CancellableRenderEvent(@NotNull final Stack stack, @NotNull final Phase phase) {
        super(stack, phase);
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancelled) {
        if (this.phase() == Phase.POST) {
            throw new IllegalStateException("Cannot cancel event in post phase!");
        }
        this.cancelled = cancelled;
    }
}
