// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font;

import org.jetbrains.annotations.ApiStatus;
import java.util.Objects;
import net.labymod.api.client.component.event.HoverEvent;
import java.util.Map;
import net.labymod.api.client.render.font.text.TextRenderer;
import java.util.ArrayList;
import java.util.HashMap;
import net.labymod.api.util.math.MathHelper;
import java.util.Iterator;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.component.Component;
import java.util.List;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.IconComponent;
import net.labymod.api.util.logging.Logging;

public class RenderableComponent
{
    private static final Logging LOGGER;
    private final String text;
    private final IconComponent icon;
    private final Style style;
    private float xOffset;
    private float yOffset;
    private float lineYOffset;
    private float innerY;
    private float singleWidth;
    private float width;
    private float height;
    private int lines;
    private boolean clipped;
    private final List<RenderableComponent> children;
    private final float lineSpacing;
    @Deprecated
    private boolean disableWidthCaching;
    private VisualCharSequence visualCharSequence;
    
    private RenderableComponent(final String text, final IconComponent icon, final Style style, final float xOffset, final float yOffset, final List<RenderableComponent> children, final float lineSpacing) {
        this.text = text;
        this.icon = icon;
        this.style = this.requireNonNullStyle(style);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.children = children;
        this.lineSpacing = lineSpacing;
        this.updateBounds();
    }
    
    private RenderableComponent(final String text, final IconComponent icon, final Style style, final float xOffset, final float yOffset, final float innerY, final float singleWidth, final float width, final float height, final int lines, final boolean clipped, final List<RenderableComponent> children, final float lineSpacing) {
        this.text = text;
        this.icon = icon;
        this.style = this.requireNonNullStyle(style);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.innerY = innerY;
        this.singleWidth = singleWidth;
        this.width = width;
        this.height = height;
        this.lines = lines;
        this.clipped = clipped;
        this.children = children;
        this.lineSpacing = lineSpacing;
    }
    
    public static RenderableComponent of(final String text, final IconComponent icon, final Style style, final float xOffset, final float yOffset, final List<RenderableComponent> children, final float lineSpacing) {
        return new RenderableComponent(text, icon, style, xOffset, yOffset, children, lineSpacing);
    }
    
    public static RenderableComponent merge(final List<RenderableComponent> components) {
        if (components.size() == 1) {
            return components.getFirst();
        }
        return of("", null, Style.EMPTY, 0.0f, 0.0f, components, 0.0f);
    }
    
    public static RenderableComponent realignedMerge(final List<RenderableComponent> components) {
        return componentRenderer().realignedMerge(components);
    }
    
    public static ComponentFormatter builder() {
        return componentRenderer().formatter();
    }
    
    public static RenderableComponent of(final Component component) {
        return builder().format(component);
    }
    
    public static RenderableComponent of(final Component component, final boolean cache) {
        return builder().cache(cache).format(component);
    }
    
    public static RenderableComponent of(final Component component, final HorizontalAlignment alignment) {
        return builder().alignment(alignment).format(component);
    }
    
    public static RenderableComponent ofWindow(final Component component, final HorizontalAlignment alignment) {
        return of(component, Laby.labyAPI().minecraft().minecraftWindow().absoluteBounds().getWidth(), alignment);
    }
    
    public static RenderableComponent of(final Component component, final float maxWidth, final HorizontalAlignment alignment) {
        return builder().maxWidth(maxWidth).alignment(alignment).format(component);
    }
    
    public static RenderableComponent of(final Component component, final float maxWidth, final HorizontalAlignment alignment, final TextOverflowStrategy overflowStrategy) {
        return builder().maxWidth(maxWidth).alignment(alignment).overflow(overflowStrategy).format(component);
    }
    
    public String getText() {
        return this.text;
    }
    
    public IconComponent getIcon() {
        return this.icon;
    }
    
    @NotNull
    public Style style() {
        return this.style;
    }
    
    public float getXOffset() {
        return this.xOffset;
    }
    
    public void setXOffset(final float xOffset) {
        this.xOffset = xOffset;
    }
    
    public void addXOffsetWithChildren(final float xOffset) {
        this.xOffset += xOffset;
        for (final RenderableComponent child : this.getChildren()) {
            child.addXOffsetWithChildren(xOffset);
        }
    }
    
    public void alignXOffsetToRenderContentFromRightBorder(final float endX) {
        this.xOffset = endX - this.getWidth();
        float alreadyRenderedWidth = 0.0f;
        for (int i = this.getChildren().size() - 1; i >= 0; --i) {
            final float newEndX = endX - alreadyRenderedWidth;
            final RenderableComponent child = this.getChildren().get(i);
            alreadyRenderedWidth += child.getWidth();
            child.alignXOffsetToRenderContentFromRightBorder(newEndX);
        }
    }
    
    public float getYOffset() {
        return this.yOffset + this.lineYOffset;
    }
    
    public void setYOffset(final float yOffset) {
        this.yOffset = yOffset;
    }
    
    public void setLineYOffset(final float offset) {
        this.setLineYOffset(offset, false);
    }
    
    public void setLineYOffset(final float offset, final boolean withChildren) {
        this.lineYOffset = offset;
        if (withChildren) {
            for (final RenderableComponent child : this.getChildren()) {
                child.setLineYOffset(offset, withChildren);
            }
        }
    }
    
    public void setYOffsetWithChildren(final float yOffset) {
        this.setYOffset(yOffset);
        for (final RenderableComponent child : this.getChildren()) {
            child.setYOffsetWithChildren(yOffset);
        }
    }
    
    public float getInnerY() {
        return this.innerY;
    }
    
    public float getSingleWidth() {
        return this.singleWidth;
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    public float getCeilHeight() {
        return (float)MathHelper.floor(this.getHeight());
    }
    
    public float getSingleHeight() {
        final IconComponent icon = this.getIcon();
        return (icon != null) ? ((float)icon.getHeight()) : Math.max(this.textRenderer().height(), 0.0f);
    }
    
    @Deprecated
    public RenderableComponent disableWidthCaching() {
        this.disableWidthCaching = true;
        return this;
    }
    
    public void updateBounds() {
        final TextRenderer renderer = this.textRenderer();
        if (this.disableWidthCaching) {
            renderer.disableWidthCaching();
        }
        this.singleWidth = renderer.width(this.getVisualCharSequence()) + ((this.icon != null) ? this.icon.getWidth() : 0);
        this.setWidth(this.getSingleWidth());
        float minY;
        final float yOffset = minY = this.getYOffset();
        float maxY = yOffset + this.getSingleHeight() + this.getLineSpacing();
        boolean hasIcon = this.getIcon() != null;
        for (final RenderableComponent child : this.getChildren()) {
            child.updateBounds();
            minY = Math.min(minY, child.getYOffset());
            maxY = Math.max(maxY, child.getYOffset() + child.getHeight());
            if (child.getIcon() != null) {
                hasIcon = true;
            }
        }
        if (hasIcon) {
            final Map<Float, List<RenderableComponent>> yOffsetMap = new HashMap<Float, List<RenderableComponent>>();
            for (final RenderableComponent child2 : this.getChildren()) {
                yOffsetMap.computeIfAbsent(Float.valueOf(child2.getYOffset()), k -> new ArrayList()).add(child2);
            }
            for (final List<RenderableComponent> line : yOffsetMap.values()) {
                boolean seen = false;
                double best = 0.0;
                for (final RenderableComponent value : line) {
                    final double valueHeight = value.getHeight();
                    if (!seen || Double.compare(valueHeight, best) > 0) {
                        seen = true;
                        best = valueHeight;
                    }
                }
                final float maxHeight = (float)(seen ? best : 0.0);
                for (final RenderableComponent component : line) {
                    if (component.getHeight() != maxHeight) {
                        final float innerY = maxHeight / 2.0f - component.getHeight() / 2.0f;
                        component.innerY = ((component.getHeight() % 2.0f == 0.0f) ? ((float)MathHelper.floor(innerY)) : ((float)MathHelper.ceil(innerY)));
                    }
                }
            }
        }
        final Map<Float, List<RenderableComponent>> childYOffsetMap = new HashMap<Float, List<RenderableComponent>>();
        for (final RenderableComponent child2 : this.getChildren()) {
            childYOffsetMap.computeIfAbsent(Float.valueOf(child2.getYOffset()), k -> new ArrayList()).add(child2);
        }
        boolean seen2 = false;
        double best2 = 0.0;
        for (final List<RenderableComponent> components : childYOffsetMap.values()) {
            double sum = 0.0;
            for (final RenderableComponent value2 : components) {
                final double valueWidth = value2.getWidth();
                sum += valueWidth;
            }
            if (!seen2 || Double.compare(sum, best2) > 0) {
                seen2 = true;
                best2 = sum;
            }
        }
        this.setWidth(this.getWidth() + (float)(seen2 ? best2 : 0.0));
        this.height = maxY - minY - this.getLineSpacing();
        this.lines = MathHelper.ceil(this.getHeight() / componentRenderer().height());
    }
    
    public List<RenderableComponent> getChildren() {
        return this.children;
    }
    
    private static ComponentRenderer componentRenderer() {
        return Laby.labyAPI().renderPipeline().componentRenderer();
    }
    
    private TextRenderer textRenderer() {
        return Laby.references().textRendererProvider().getRenderer();
    }
    
    private void setWidth(final float width) {
        this.width = (float)MathHelper.ceil(width);
    }
    
    public float getLineSpacing() {
        return this.lineSpacing;
    }
    
    public RenderableComponent copy() {
        final List<RenderableComponent> children = new ArrayList<RenderableComponent>(this.getChildren().size());
        for (final RenderableComponent child : this.getChildren()) {
            children.add(child.copy());
        }
        return new RenderableComponent(this.getText(), this.getIcon(), this.style(), this.getXOffset(), this.getYOffset(), this.getInnerY(), this.getSingleWidth(), this.getWidth(), this.getHeight(), this.getLines(), this.isClipped(), children, this.getLineSpacing());
    }
    
    public RenderableComponent copy(final String newText) {
        final List<RenderableComponent> children = new ArrayList<RenderableComponent>(this.getChildren().size());
        for (final RenderableComponent child : this.getChildren()) {
            children.add(child.copy());
        }
        return of(newText, this.getIcon(), this.style(), this.getXOffset(), this.getYOffset(), children, this.getLineSpacing());
    }
    
    public void setClipped(final boolean clipped) {
        this.clipped = clipped;
    }
    
    public boolean isClipped() {
        return this.clipped;
    }
    
    public int getLines() {
        return this.lines;
    }
    
    public VisualCharSequence getVisualCharSequence() {
        if (this.visualCharSequence == null) {
            this.visualCharSequence = Laby.references().stringFormatter().getVisualOrder(this.getText(), this.style());
        }
        return this.visualCharSequence;
    }
    
    public <T> HoverEvent.Action<T> getHoverAction() {
        final HoverEvent<?> hoverEvent = this.style().getHoverEvent();
        if (hoverEvent == null) {
            return null;
        }
        return (HoverEvent.Action<T>)hoverEvent.action();
    }
    
    public <T> T getHoverValue(final HoverEvent.Action<T> action) {
        final HoverEvent<?> hoverEvent = this.style().getHoverEvent();
        if (hoverEvent == null) {
            return null;
        }
        return (T)((hoverEvent.action() == action) ? hoverEvent.value() : null);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final RenderableComponent component = (RenderableComponent)o;
        return this.lines == component.lines && this.clipped == component.clipped && Objects.equals(this.text, component.text) && Objects.equals(this.icon, component.icon) && Objects.equals(this.style, component.style) && Objects.equals(this.children, component.children);
    }
    
    @Override
    public int hashCode() {
        int result = Objects.hashCode(this.text);
        result = 31 * result + Objects.hashCode(this.icon);
        result = 31 * result + Objects.hashCode(this.style);
        result = 31 * result + this.lines;
        result = 31 * result + Boolean.hashCode(this.clipped);
        result = 31 * result + Objects.hashCode(this.children);
        return result;
    }
    
    @ApiStatus.Experimental
    public Component toComponent() {
        return this.toComponent(this);
    }
    
    private Component toComponent(final RenderableComponent component) {
        final String text = component.getText();
        final IconComponent iconComponent = component.getIcon();
        Component rootComponent = Component.empty();
        if (text != null) {
            rootComponent = Component.text(text, component.style());
        }
        if (iconComponent != null) {
            rootComponent = iconComponent;
        }
        for (final RenderableComponent child : component.getChildren()) {
            rootComponent.append(this.toComponent(child));
        }
        return rootComponent;
    }
    
    private Style requireNonNullStyle(final Style style) {
        if (style == null) {
            RenderableComponent.LOGGER.warn("Style is null for component: {}", this);
            return Style.EMPTY;
        }
        return style;
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
