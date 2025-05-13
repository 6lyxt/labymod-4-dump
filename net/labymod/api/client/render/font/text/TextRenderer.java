// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font.text;

import net.labymod.api.client.render.font.text.glyph.GlyphProvider;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.font.text.glyph.GlyphConsumer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.font.VisualCharSequence;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.Disposable;

@Referenceable(named = true)
public interface TextRenderer extends Disposable
{
    default void prepare(final String namespace, final String font) {
    }
    
    default void unload() {
    }
    
    default float width(final char c) {
        return this.width(c, Style.EMPTY);
    }
    
    default float width(final char c, final Style style) {
        return this.width((int)c, style);
    }
    
    float width(final int p0, final Style p1);
    
    default float width(final String text) {
        return this.width(text, Style.EMPTY);
    }
    
    float width(final String p0, final Style p1);
    
    float width(final VisualCharSequence p0);
    
    float height();
    
    TextRenderer text(final String p0);
    
    TextRenderer text(final RenderableComponent p0);
    
    @Deprecated
    TextRenderer disableWidthCaching();
    
    TextRenderer pos(final float p0, final float p1);
    
    default TextRenderer color(final int color) {
        return this.color(color, true);
    }
    
    TextRenderer color(final int p0, final boolean p1);
    
    TextRenderer backgroundColor(final int p0);
    
    TextRenderer shadow(final boolean p0);
    
    TextRenderer drawMode(final TextDrawMode p0);
    
    TextRenderer capitalize(final boolean p0);
    
    TextRenderer centered(final boolean p0);
    
    TextRenderer scale(final float p0);
    
    TextRenderer useFloatingPointPosition(final boolean p0);
    
    default TextRenderer fontWeight(final float fontWeight) {
        return this;
    }
    
    TextRenderer glyphConsumer(final GlyphConsumer p0);
    
    default boolean usable() {
        return true;
    }
    
    TextRenderer style(final Style p0);
    
    @Nullable
    TextBuffer render(final Stack p0);
    
    @Nullable
    default TextBuffer renderBatch(final Stack stack) {
        return this.render(stack);
    }
    
    @Nullable
    default GlyphProvider glyphProvider() {
        return null;
    }
    
    default void beginBatch(final Stack stack) {
    }
    
    default void endBatch(final Stack stack) {
    }
    
    void invalidate();
    
    String trimStringToWidth(final String p0, final float p1);
    
    String trimStringToWidth(final String p0, final boolean p1, final float p2);
    
    String trimStringToWidth(final String p0, final float p1, final StringStart p2);
    
    String trimStringToWidth(final String p0, final boolean p1, final float p2, final StringStart p3);
    
    default boolean isVanilla() {
        return false;
    }
    
    public enum StringStart
    {
        LEFT, 
        RIGHT;
    }
}
