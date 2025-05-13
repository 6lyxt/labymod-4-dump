// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.font;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class StringRenderEvent extends DefaultCancellable implements Event
{
    private final Stack stack;
    private final String text;
    private final float x;
    private final float y;
    private int color;
    private boolean shadow;
    
    public StringRenderEvent(@NotNull final Stack stack, @NotNull final String text, final float x, final float y, final int color, final boolean shadow) {
        this.stack = stack;
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.shadow = shadow;
    }
    
    @NotNull
    public Stack stack() {
        return this.stack;
    }
    
    @NotNull
    public String getText() {
        return this.text;
    }
    
    public float getX() {
        return this.x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public void setColor(final int color) {
        this.color = color;
    }
    
    public boolean isShadow() {
        return this.shadow;
    }
    
    public void setShadow(final boolean shadow) {
        this.shadow = shadow;
    }
}
