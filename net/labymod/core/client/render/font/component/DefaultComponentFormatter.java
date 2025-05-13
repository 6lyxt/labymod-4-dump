// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.component;

import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.render.font.TextOverflowStrategy;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.font.ComponentFormatter;

public class DefaultComponentFormatter implements ComponentFormatter
{
    private final ComponentRenderer componentRenderer;
    private float lineSpacing;
    private TextOverflowStrategy overflowStrategy;
    private float maxWidth;
    private HorizontalAlignment alignment;
    private boolean noCache;
    private int maxLines;
    private boolean removeLeadingSpaces;
    private boolean useChatOptions;
    private boolean maxLinesClipText;
    
    public DefaultComponentFormatter(final ComponentRenderer componentRenderer) {
        this.componentRenderer = componentRenderer;
        this.reset();
    }
    
    @Override
    public ComponentFormatter lineSpacing(final float lineSpacing) {
        this.lineSpacing = lineSpacing;
        return this;
    }
    
    @Override
    public ComponentFormatter overflow(final TextOverflowStrategy overflowStrategy) {
        this.overflowStrategy = overflowStrategy;
        return this;
    }
    
    @Override
    public ComponentFormatter maxWidth(final float maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }
    
    @Override
    public ComponentFormatter alignment(final HorizontalAlignment alignment) {
        this.alignment = alignment;
        return this;
    }
    
    @Override
    public ComponentFormatter disableCache() {
        this.noCache = true;
        return this;
    }
    
    @Override
    public ComponentFormatter leadingSpaces(final boolean leadingSpaces) {
        this.removeLeadingSpaces = !leadingSpaces;
        return this;
    }
    
    @Override
    public ComponentFormatter maxLines(final int maxLines) {
        this.maxLines = maxLines;
        return this;
    }
    
    @Override
    public ComponentFormatter maxLinesClipText(final boolean maxLinesClipText) {
        this.maxLinesClipText = maxLinesClipText;
        return this;
    }
    
    @Override
    public ComponentFormatter useChatOptions(final boolean useChatOptions) {
        this.useChatOptions = useChatOptions;
        return this;
    }
    
    @Override
    public RenderableComponent format(final Component component) {
        final RenderableComponent renderableComponent = this.componentRenderer.formatComponent(component, this.maxWidth, this.alignment, this.lineSpacing, this.overflowStrategy, this.noCache, this.maxLines, this.removeLeadingSpaces, this.useChatOptions, this.maxLinesClipText);
        this.reset();
        return renderableComponent;
    }
    
    private void reset() {
        this.lineSpacing = 0.0f;
        this.overflowStrategy = TextOverflowStrategy.WRAP;
        this.maxWidth = -1.0f;
        this.alignment = HorizontalAlignment.LEFT;
        this.noCache = false;
        this.maxLines = 0;
        this.removeLeadingSpaces = true;
        this.useChatOptions = false;
    }
}
