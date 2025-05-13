// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.screen.title;

import java.util.Objects;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Event;

public final class TitleScreenRenderEvent implements Event
{
    private final Phase phase;
    private final Stack stack;
    private final MutableMouse mouse;
    private final float partialTicks;
    
    public TitleScreenRenderEvent(final Phase phase, final Stack stack, final MutableMouse mouse, final float partialTicks) {
        this.phase = phase;
        this.stack = stack;
        this.mouse = mouse;
        this.partialTicks = partialTicks;
    }
    
    public Phase phase() {
        return this.phase;
    }
    
    public Stack stack() {
        return this.stack;
    }
    
    public MutableMouse mouse() {
        return this.mouse;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        final TitleScreenRenderEvent that = (TitleScreenRenderEvent)obj;
        return Objects.equals(this.stack, that.stack) && Objects.equals(this.mouse, that.mouse) && Float.floatToIntBits(this.partialTicks) == Float.floatToIntBits(that.partialTicks);
    }
    
    @Override
    public int hashCode() {
        int result = (this.stack != null) ? this.stack.hashCode() : 0;
        result = 31 * result + ((this.mouse != null) ? this.mouse.hashCode() : 0);
        result = 31 * result + ((this.partialTicks != 0.0f) ? Float.floatToIntBits(this.partialTicks) : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "TitleScreenPostRenderEvent[stack=" + String.valueOf(this.stack) + ", mouse=" + String.valueOf(this.mouse) + ", partialTicks=" + this.partialTicks;
    }
}
