// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.font;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.event.Event;

public class StringWidthEvent implements Event
{
    private final CharSequence text;
    private final Style style;
    private int width;
    
    public StringWidthEvent(@NotNull final CharSequence text, @Nullable final Style style, final int width) {
        this.text = text;
        this.style = style;
        this.width = width;
    }
    
    @NotNull
    public CharSequence getText() {
        return this.text;
    }
    
    @Nullable
    public Style getStyle() {
        return this.style;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
}
