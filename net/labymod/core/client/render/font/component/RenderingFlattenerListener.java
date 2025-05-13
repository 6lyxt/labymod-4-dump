// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.component;

import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.Laby;
import java.util.Iterator;
import net.labymod.api.util.StringUtil;
import net.labymod.api.util.CharSequences;
import java.util.Collections;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.render.font.text.StringIterator;
import java.util.Objects;
import net.labymod.api.client.component.IconComponent;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.ArrayDeque;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.render.font.RenderableComponent;
import java.util.List;
import net.labymod.api.client.component.Component;
import java.util.Deque;
import net.labymod.api.client.render.font.text.TextRenderer;
import java.util.function.Supplier;
import net.labymod.api.client.component.flattener.FlattenerListener;

public class RenderingFlattenerListener implements FlattenerListener
{
    private final Supplier<TextRenderer> textRenderer;
    private final float lineSpacing;
    private final boolean useChatOptions;
    private float x;
    private float lineHeight;
    private final Deque<Component> styleQueue;
    private final List<RenderableComponent> components;
    private Style lastStyle;
    
    public RenderingFlattenerListener(final TextRenderer textRenderer, final float lineSpacing, final boolean useChatOptions) {
        this(() -> textRenderer, lineSpacing, useChatOptions);
    }
    
    public RenderingFlattenerListener(final Supplier<TextRenderer> textRenderer, final float lineSpacing, final boolean useChatOptions) {
        this.styleQueue = new ArrayDeque<Component>();
        this.components = new ArrayList<RenderableComponent>();
        this.textRenderer = textRenderer;
        this.lineSpacing = lineSpacing;
        this.useChatOptions = useChatOptions;
        this.reset();
    }
    
    public List<RenderableComponent> getComponents() {
        return this.components;
    }
    
    @Override
    public void push(@NotNull final Component source) {
        if (source.style() == Style.EMPTY) {
            this.styleQueue.clear();
        }
        this.styleQueue.offerFirst(source);
    }
    
    @Override
    public void component(@NotNull final String text) {
        Style style = null;
        if (!this.styleQueue.isEmpty() && this.styleQueue.peek() instanceof IconComponent) {
            final IconComponent icon = this.styleQueue.pop();
            style = icon.style();
            this.pushComponent("", icon, style);
            this.x += icon.getWidth();
            this.lineHeight = Math.max(this.lineHeight, (float)icon.getHeight());
            return;
        }
        final Component peek = this.styleQueue.peek();
        final StringBuilder builder = new StringBuilder();
        StringIterator.iterateFormatted(text, (peek == null) ? null : peek.style(), false, (position, style, codePoint) -> {
            final char[] characters = Character.toChars(codePoint);
            if (this.lastStyle == null) {
                builder.append(characters);
                this.styleQueue.push(Component.text("", style));
                this.lastStyle = style;
                return true;
            }
            else if (Objects.equals(this.lastStyle, style)) {
                builder.append(characters);
                return true;
            }
            else {
                this.render(builder.toString(), null);
                builder.setLength();
                this.styleQueue.pop();
                this.styleQueue.push(Component.text("", style));
                this.lastStyle = style;
                builder.append(characters);
                return true;
            }
        });
        if (builder.length() != 0) {
            this.render(builder.toString(), null);
        }
        if (this.lastStyle != null) {
            this.styleQueue.pop();
        }
        this.lastStyle = null;
    }
    
    private RenderableComponent pushComponent(@NotNull final String text, @Nullable final IconComponent icon, @NotNull final Style style) {
        final RenderableComponent component = RenderableComponent.of(text, icon, this.applyStyleOptions(style), this.x, 0.0f, Collections.emptyList(), this.lineSpacing);
        this.components.add(component);
        return component;
    }
    
    private void render(@NotNull String text, @Nullable final IconComponent icon) {
        text = StringUtil.removeIllegalCharacters(CharSequences.toString(text));
        if (CharSequences.isEmpty(text) && icon == null) {
            return;
        }
        Style style = Style.empty();
        for (final Component queued : this.styleQueue) {
            style = style.merge(queued.style(), Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
        }
        final RenderableComponent component = this.pushComponent(text, icon, style);
        this.x += component.getSingleWidth();
    }
    
    private Style applyStyleOptions(final Style style) {
        if (!this.useChatOptions) {
            return style;
        }
        if (!Laby.labyAPI().minecraft().options().isChatColorsEnabled()) {
            return style.color(null);
        }
        return style;
    }
    
    @Override
    public void pop(@NotNull final Component source) {
        this.styleQueue.removeFirstOccurrence(source);
    }
    
    public void reset() {
        this.x = 0.0f;
        this.lineHeight = -1.0f;
        this.styleQueue.clear();
        this.components.clear();
    }
}
