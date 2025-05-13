// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font;

import net.labymod.api.client.render.font.text.glyph.GlyphConsumer;

public class VisualCharSequence
{
    private final GlyphSink sink;
    private Object formattedCharSequence;
    
    public VisualCharSequence(final GlyphSink sink) {
        this.sink = sink;
    }
    
    public void accept(final GlyphConsumer consumer) {
        this.sink.accept(consumer);
    }
    
    public void setFormattedCharSequence(final Object formattedCharSequence) {
        this.formattedCharSequence = formattedCharSequence;
    }
    
    public <T> T castTo(final Class<T> type) {
        return type.cast(this.formattedCharSequence);
    }
    
    public interface GlyphSink
    {
        boolean accept(final GlyphConsumer p0);
    }
}
