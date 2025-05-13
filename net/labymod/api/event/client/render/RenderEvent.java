// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.event.Phase;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Event;

public class RenderEvent implements Event
{
    private final Stack stack;
    private final Phase phase;
    
    public RenderEvent(@NotNull final Stack stack, @NotNull final Phase phase) {
        this.stack = stack;
        this.phase = phase;
    }
    
    @NotNull
    public Stack stack() {
        return this.stack;
    }
    
    @NotNull
    public Phase phase() {
        return this.phase;
    }
}
