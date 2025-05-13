// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Event;

public class GameRenderEvent implements Event
{
    private final Phase phase;
    private final Stack stack;
    private final float partialTicks;
    
    public GameRenderEvent(final Phase phase, final Stack stack, final float partialTicks) {
        this.phase = phase;
        this.stack = stack;
        this.partialTicks = partialTicks;
    }
    
    public Stack stack() {
        return this.stack;
    }
    
    public Phase phase() {
        return this.phase;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
}
