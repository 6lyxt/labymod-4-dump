// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component.builder;

import net.labymod.api.client.component.ComponentService;
import java.util.Objects;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.format.TextColor;

public abstract class StyleableBuilder<T, B extends StyleableBuilder<T, B>>
{
    protected TextColor textColor;
    protected Integer shadowColor;
    protected Boolean bold;
    protected Boolean italic;
    protected Boolean underlined;
    protected Boolean strikethrough;
    protected Boolean obfuscated;
    protected ClickEvent clickEvent;
    protected HoverEvent<?> hoverEvent;
    protected String insertion;
    protected ResourceLocation fontLocation;
    
    protected StyleableBuilder() {
    }
    
    protected StyleableBuilder(final Style style) {
        this.applyStyle(style);
    }
    
    public B color(final TextColor color) {
        this.textColor = color;
        return (B)this;
    }
    
    public B colorIfAbsent(final TextColor color) {
        if (this.textColor == null) {
            this.textColor = color;
        }
        return (B)this;
    }
    
    public B shadowColor(final Integer color) {
        this.shadowColor = color;
        return (B)this;
    }
    
    public B shadowColorIfAbsent(final Integer color) {
        if (this.shadowColor == null) {
            this.shadowColor = color;
        }
        return (B)this;
    }
    
    public B decorate(final TextDecoration decoration) {
        return this.setDecoration(decoration, true);
    }
    
    public B decorate(final TextDecoration... decorations) {
        for (final TextDecoration decoration : decorations) {
            this.decorate(decoration);
        }
        return (B)this;
    }
    
    public B undecorate(final TextDecoration decoration) {
        return this.setDecoration(decoration, false);
    }
    
    public B undecorate(final TextDecoration... decorations) {
        for (final TextDecoration decoration : decorations) {
            this.undecorate(decoration);
        }
        return (B)this;
    }
    
    public B unsetDecoration(final TextDecoration decoration) {
        return this.setDecoration(decoration, null);
    }
    
    public B unsetDecoration(final TextDecoration... decorations) {
        for (final TextDecoration decoration : decorations) {
            this.unsetDecoration(decoration);
        }
        return (B)this;
    }
    
    public B clickEvent(final ClickEvent clickEvent) {
        this.clickEvent = clickEvent;
        return (B)this;
    }
    
    public B hoverEvent(final HoverEvent<?> hoverEvent) {
        this.hoverEvent = hoverEvent;
        return (B)this;
    }
    
    public B insertion(final String insertion) {
        this.insertion = insertion;
        return (B)this;
    }
    
    public B font(final ResourceLocation fontLocation) {
        this.fontLocation = fontLocation;
        return (B)this;
    }
    
    @Deprecated
    public B decoration(final TextDecoration decoration, final TextDecoration.State state) {
        Objects.requireNonNull(state, "State cannot be null");
        switch (state) {
            case TRUE: {
                return this.decorate(decoration);
            }
            case FALSE: {
                return this.undecorate(decoration);
            }
            default: {
                return this.unsetDecoration(decoration);
            }
        }
    }
    
    public boolean hasColor() {
        return this.textColor != null;
    }
    
    public boolean hasDecoration(final TextDecoration decoration) {
        return this.getDecorationState(decoration) != Boolean.TRUE;
    }
    
    public boolean isDecorationSet(final TextDecoration decoration) {
        return this.getDecorationState(decoration) != null;
    }
    
    public boolean hasClickEvent() {
        return this.clickEvent != null;
    }
    
    public boolean hasHoverEvent() {
        return this.hoverEvent != null;
    }
    
    public boolean hasInsertion() {
        return this.insertion != null;
    }
    
    public boolean hasFont() {
        return this.fontLocation != null;
    }
    
    public boolean isEmpty() {
        return this.textColor == null && this.shadowColor == null && this.bold == null && this.italic == null && this.underlined == null && this.strikethrough == null && this.obfuscated == null && this.clickEvent == null && this.hoverEvent == null && this.insertion == null;
    }
    
    protected Style buildStyle() {
        return ComponentService.buildStyle(this.textColor, this.shadowColor, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.fontLocation);
    }
    
    protected B applyStyle(final Style style) {
        this.textColor = style.getColor();
        this.bold = this.getDecorationState(style, TextDecoration.BOLD);
        this.italic = this.getDecorationState(style, TextDecoration.ITALIC);
        this.underlined = this.getDecorationState(style, TextDecoration.UNDERLINED);
        this.strikethrough = this.getDecorationState(style, TextDecoration.STRIKETHROUGH);
        this.obfuscated = this.getDecorationState(style, TextDecoration.OBFUSCATED);
        this.clickEvent = style.getClickEvent();
        this.hoverEvent = style.getHoverEvent();
        this.insertion = style.getInsertion();
        this.fontLocation = style.getFont();
        return (B)this;
    }
    
    public abstract T build();
    
    private Boolean getDecorationState(final TextDecoration decoration) {
        switch (decoration) {
            case BOLD: {
                return this.bold;
            }
            case ITALIC: {
                return this.italic;
            }
            case UNDERLINED: {
                return this.underlined;
            }
            case STRIKETHROUGH: {
                return this.strikethrough;
            }
            case OBFUSCATED: {
                return this.obfuscated;
            }
            default: {
                return null;
            }
        }
    }
    
    private Boolean getDecorationState(final Style style, final TextDecoration decoration) {
        return style.isDecorationSet(decoration) ? Boolean.valueOf(style.hasDecoration(decoration)) : null;
    }
    
    private B setDecoration(final TextDecoration decoration, final Boolean value) {
        switch (decoration) {
            case BOLD: {
                this.bold = value;
                break;
            }
            case ITALIC: {
                this.italic = value;
                break;
            }
            case UNDERLINED: {
                this.underlined = value;
                break;
            }
            case STRIKETHROUGH: {
                this.strikethrough = value;
                break;
            }
            case OBFUSCATED: {
                this.obfuscated = value;
                break;
            }
        }
        return (B)this;
    }
}
