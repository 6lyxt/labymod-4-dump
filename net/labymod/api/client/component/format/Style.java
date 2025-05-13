// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component.format;

import net.labymod.api.client.component.builder.StyleableBuilder;
import java.util.Objects;
import java.util.Iterator;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.builder.Buildable;

public interface Style extends Buildable<Builder>
{
    public static final Style EMPTY = empty();
    
    default Style empty() {
        return ComponentService.emptyStyle();
    }
    
    @Deprecated
    default Style style() {
        return empty();
    }
    
    @Deprecated
    default Style style(final TextColor textColor) {
        return empty().color(textColor);
    }
    
    @Deprecated
    default Style style(final TextDecoration... decorations) {
        return empty().decorate(decorations);
    }
    
    @Deprecated
    default Style style(final TextColor textColor, final TextDecoration... decorations) {
        return empty().color(textColor).decorate(decorations);
    }
    
    default Builder builder() {
        return new Builder();
    }
    
    HoverEvent<?> getHoverEvent();
    
    Style hoverEvent(final HoverEvent<?> p0);
    
    ClickEvent getClickEvent();
    
    Style clickEvent(final ClickEvent p0);
    
    TextColor getColor();
    
    default Integer getShadowColor() {
        return null;
    }
    
    Style color(final TextColor p0);
    
    ResourceLocation getFont();
    
    Style font(final ResourceLocation p0);
    
    boolean hasDecoration(final TextDecoration p0);
    
    boolean isDecorationSet(final TextDecoration p0);
    
    Style decorate(final TextDecoration p0);
    
    Style undecorate(final TextDecoration p0);
    
    Style unsetDecoration(final TextDecoration p0);
    
    boolean isEmpty();
    
    Style insertion(final String p0);
    
    String getInsertion();
    
    default Style merge(final Style style, final Merge.Strategy strategy) {
        return this.merge(style, strategy, 62);
    }
    
    default Style merge(final Style style, final Merge.Strategy strategy, final int flags) {
        if (strategy == Merge.Strategy.NEVER || style == null || style.isEmpty()) {
            return this;
        }
        final Builder builder = this.toBuilder();
        if (Merge.contains(flags, 2)) {
            final TextColor color = style.getColor();
            if (color != null && (strategy == Merge.Strategy.ALWAYS || !builder.hasColor())) {
                builder.color(color);
            }
        }
        if (Merge.contains(flags, 4)) {
            for (final TextDecoration decoration : TextDecoration.getValues()) {
                if (!style.isDecorationSet(decoration)) {
                    continue;
                }
                final boolean value = style.hasDecoration(decoration);
                if (strategy != Merge.Strategy.ALWAYS && this.isDecorationSet(decoration)) {
                    continue;
                }
                if (value) {
                    builder.decorate(decoration);
                }
                else {
                    builder.undecorate(decoration);
                }
            }
        }
        if (Merge.contains(flags, 8)) {
            final ClickEvent clickEvent = style.getClickEvent();
            if (clickEvent != null && (strategy == Merge.Strategy.ALWAYS || !builder.hasClickEvent())) {
                builder.clickEvent(clickEvent);
            }
            final HoverEvent<?> hoverEvent = style.getHoverEvent();
            if (hoverEvent != null && (strategy == Merge.Strategy.ALWAYS || !builder.hasHoverEvent())) {
                builder.hoverEvent(hoverEvent);
            }
        }
        if (Merge.contains(flags, 16)) {
            final String insertion = style.getInsertion();
            if (insertion != null && (strategy == Merge.Strategy.ALWAYS || !builder.hasInsertion())) {
                builder.insertion(insertion);
            }
        }
        if (Merge.contains(flags, 32)) {
            final ResourceLocation font = style.getFont();
            if (font != null && (strategy == Merge.Strategy.ALWAYS || !builder.hasFont())) {
                builder.font(font);
            }
        }
        return builder.build();
    }
    
    default Style decorate(final TextDecoration... decorations) {
        Style style = this;
        for (final TextDecoration decoration : decorations) {
            style = style.decorate(decoration);
        }
        return style;
    }
    
    default Style undecorate(final TextDecoration... decorations) {
        Style style = this;
        for (final TextDecoration decoration : decorations) {
            style = style.undecorate(decoration);
        }
        return style;
    }
    
    default Style unsetDecorations(final TextDecoration... decorations) {
        Style style = this;
        for (final TextDecoration decoration : decorations) {
            style = style.unsetDecoration(decoration);
        }
        return style;
    }
    
    default Style colorIfAbsent(final TextColor textColor) {
        if (this.getColor() != null) {
            return this;
        }
        return this.color(textColor);
    }
    
    @Deprecated
    default Style decoration(final TextDecoration decoration, final boolean flag) {
        return flag ? this.decorate(decoration) : this.undecorate(decoration);
    }
    
    @Deprecated
    default Style decoration(final TextDecoration decoration, final TextDecoration.State state) {
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
    
    @Deprecated
    default TextColor color() {
        return this.getColor();
    }
    
    @Deprecated
    default Style build() {
        return this;
    }
    
    @Deprecated
    default String insertion() {
        return this.getInsertion();
    }
    
    default Builder toBuilder() {
        return new Builder(this);
    }
    
    public static class Builder extends StyleableBuilder<Style, Builder>
    {
        private Builder() {
        }
        
        private Builder(final Style style) {
            super(style);
        }
        
        @Override
        public Style build() {
            return this.buildStyle();
        }
    }
    
    public static class Merge
    {
        public static final int NONE = 0;
        public static final int COLOR = 2;
        public static final int DECORATIONS = 4;
        public static final int EVENTS = 8;
        public static final int INSERTION = 16;
        public static final int FONT = 32;
        public static final int ALL = 62;
        public static final int COLOR_AND_DECORATIONS = 6;
        
        public static boolean contains(final int flags, final int flag) {
            return (flags & flag) == flag;
        }
        
        public enum Strategy
        {
            ALWAYS, 
            NEVER, 
            IF_ABSENT_ON_TARGET;
        }
    }
}
