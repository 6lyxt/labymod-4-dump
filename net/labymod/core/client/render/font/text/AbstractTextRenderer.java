// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.text;

import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.render.font.text.model.StringWidth;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.client.render.font.text.glyph.GlyphConsumer;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.core.client.render.font.text.pool.StringWidthPool;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.render.font.text.TextRenderer;

public abstract class AbstractTextRenderer implements TextRenderer
{
    protected static final float ALPHA_THRESHOLD = 0.02f;
    protected static final Style BOLD_STYLE;
    protected final StringWidthPool stringWidthPool;
    protected String text;
    protected RenderableComponent component;
    protected float x;
    protected float y;
    protected int color;
    protected int backgroundColor;
    protected boolean shadow;
    protected TextDrawMode textDrawMode;
    protected boolean capitalize;
    protected boolean centered;
    protected float scale;
    protected boolean useFloatingPointPosition;
    protected Style style;
    protected GlyphConsumer glyphConsumer;
    protected boolean disableWidthCaching;
    
    public AbstractTextRenderer() {
        this.stringWidthPool = new StringWidthPool();
        this.textDrawMode = TextDrawMode.NORMAL;
        this.scale = 1.0f;
        this.disableWidthCaching = true;
        this.dispose();
    }
    
    @Override
    public TextRenderer text(final String text) {
        this.text = text;
        return this;
    }
    
    @Override
    public TextRenderer text(final RenderableComponent text) {
        this.component = text;
        return this;
    }
    
    @Override
    public TextRenderer disableWidthCaching() {
        this.disableWidthCaching = true;
        return this;
    }
    
    @Override
    public TextRenderer pos(final float x, final float y) {
        this.x = x;
        this.y = y;
        return this;
    }
    
    @Override
    public TextRenderer color(int color, final boolean adjustColor) {
        color = (adjustColor ? ColorUtil.adjustedColor(color) : color);
        this.color = color;
        return this;
    }
    
    @Override
    public TextRenderer backgroundColor(final int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }
    
    @Override
    public TextRenderer shadow(final boolean shadow) {
        this.shadow = shadow;
        return this;
    }
    
    @Override
    public TextRenderer drawMode(final TextDrawMode drawMode) {
        this.textDrawMode = drawMode;
        return this;
    }
    
    @Override
    public TextRenderer capitalize(final boolean capitalize) {
        this.capitalize = capitalize;
        return this;
    }
    
    @Override
    public TextRenderer centered(final boolean centered) {
        this.centered = centered;
        return this;
    }
    
    @Override
    public TextRenderer scale(final float scale) {
        this.scale = scale;
        return this;
    }
    
    @Override
    public TextRenderer useFloatingPointPosition(final boolean useFloatingPointPosition) {
        this.useFloatingPointPosition = useFloatingPointPosition;
        return this;
    }
    
    @Override
    public TextRenderer style(final Style style) {
        this.style = style;
        return this;
    }
    
    @Override
    public TextRenderer glyphConsumer(final GlyphConsumer glyphConsumer) {
        this.glyphConsumer = glyphConsumer;
        return this;
    }
    
    @Override
    public float width(final String text, final Style style) {
        if (text == null || text.isEmpty()) {
            return 0.0f;
        }
        if (this.disableWidthCaching) {
            return -1.0f;
        }
        final StringWidth stringWidth = this.stringWidthPool.get(text.hashCode());
        return (stringWidth == null) ? -1.0f : ((style != null && style.hasDecoration(TextDecoration.BOLD)) ? stringWidth.getBoldWidth() : stringWidth.getWidth());
    }
    
    @Override
    public void dispose() {
        this.text = null;
        this.component = null;
        this.x = 0.0f;
        this.y = 0.0f;
        this.color = -1;
        this.backgroundColor = 0;
        this.scale = 1.0f;
        this.shadow = true;
        this.capitalize = false;
        this.centered = false;
        this.useFloatingPointPosition = false;
        this.style = Style.empty();
        this.glyphConsumer = null;
        this.textDrawMode = TextDrawMode.NORMAL;
    }
    
    @Override
    public void invalidate() {
        this.stringWidthPool.invalidate();
    }
    
    @Override
    public String trimStringToWidth(final String text, final float maxWidth) {
        return this.trimStringToWidth(text, maxWidth, StringStart.LEFT);
    }
    
    @Override
    public String trimStringToWidth(final String text, final boolean bold, final float maxWidth) {
        return this.trimStringToWidth(text, bold, maxWidth, StringStart.LEFT);
    }
    
    @Override
    public String trimStringToWidth(final String text, final float maxWidth, final StringStart start) {
        return this.trimStringToWidth(text, false, maxWidth, start);
    }
    
    @Override
    public String trimStringToWidth(final String text, final boolean bold, final float maxWidth, final StringStart start) {
        if (start == null) {
            throw new NullPointerException("start");
        }
        final boolean reverse = start == StringStart.RIGHT;
        final StringBuilder builder = new StringBuilder();
        float fullWidth = 0.0f;
        final int position = reverse ? (text.length() - 1) : 0;
        final int delta = reverse ? -1 : 1;
        boolean currentParagraph = false;
        boolean currentBold = false;
        for (int i = position; i >= 0 && i < text.length() && fullWidth < maxWidth; i += delta) {
            final char c = text.charAt(i);
            final float charWidth = this.width(c);
            if (currentParagraph) {
                currentParagraph = false;
                if (c != 'l' && c != 'L') {
                    if (c == 'r' || c == 'R') {
                        currentBold = false;
                    }
                }
                else {
                    currentBold = true;
                }
            }
            else if (charWidth < 0.0f) {
                currentParagraph = true;
            }
            else {
                fullWidth += charWidth;
                if (currentBold || bold) {
                    ++fullWidth;
                }
            }
            if (fullWidth > maxWidth) {
                break;
            }
            if (reverse) {
                builder.insert(0, c);
            }
            else {
                builder.append(c);
            }
        }
        return builder.toString();
    }
    
    protected boolean isAlphaAboveThreshold(final int value) {
        return ColorFormat.ARGB32.normalizedAlpha(value) > 0.02f;
    }
    
    static {
        BOLD_STYLE = Style.style(TextDecoration.BOLD);
    }
}
