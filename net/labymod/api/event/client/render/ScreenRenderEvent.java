// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render;

import net.labymod.api.event.Phase;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.matrix.Stack;

public class ScreenRenderEvent extends RenderEvent
{
    private final float tickDelta;
    
    public ScreenRenderEvent(@NotNull final Stack stack, @NotNull final Phase phase, final float tickDelta) {
        super(stack, phase);
        this.tickDelta = tickDelta;
    }
    
    public float getTickDelta() {
        return this.tickDelta;
    }
}
