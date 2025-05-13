// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.hudwidget.text;

import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.util.Color;
import java.util.Objects;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentRendererBuilder;

public class TextLine
{
    protected static final ComponentRendererBuilder BUILDER;
    protected final Component keyComponent;
    protected final TextHudWidget<?> hudWidget;
    protected Component valueComponent;
    protected RenderableComponent renderableComponent;
    protected State state;
    protected Object lastValue;
    protected boolean floatingPointPosition;
    
    public TextLine(final TextHudWidget<?> hudWidget, final String key, final Object value) {
        this(hudWidget, Component.text(key), value);
    }
    
    public TextLine(final TextHudWidget<?> hudWidget, final Component key, final Object value) {
        this.hudWidget = hudWidget;
        this.keyComponent = key;
        this.state = State.VISIBLE;
        this.update(value);
        this.flushInternal();
    }
    
    public boolean update(final Object value) {
        if (Objects.equals(this.lastValue, value)) {
            return false;
        }
        this.lastValue = value;
        this.valueComponent = ((value instanceof Component) ? ((Component)value) : Component.text(String.valueOf(value)));
        return true;
    }
    
    public boolean updateAndFlush(final Object value) {
        if (this.update(value)) {
            this.flushInternal();
            return true;
        }
        return false;
    }
    
    protected void flushInternal() {
        final TextHudWidgetConfig config = this.hudWidget.getConfig();
        final TextColor bracketColor = TextColor.color(config.bracketColor().get().get());
        final TextColor labelColor = TextColor.color(config.labelColor().get().get());
        final TextColor valueColor = TextColor.color(config.valueColor().get().get());
        final Component keyComponent = this.updateColor(this.keyComponent, labelColor);
        final Component valueComponent = this.updateColor(this.valueComponent, valueColor);
        final Formatting formatting = config.formatting().get();
        final Component componentLine = formatting.build(keyComponent, valueComponent, this.isLeftAligned(), bracketColor);
        this.renderableComponent = RenderableComponent.builder().disableCache().format(componentLine).disableWidthCaching();
    }
    
    public void renderLine(final Stack stack, final float x, final float y, final float space, final HudSize hudWidgetSize) {
        TextLine.BUILDER.pos(x, y).shadow(true).useFloatingPointPosition(this.floatingPointPosition).text(this.renderableComponent).render(stack);
    }
    
    protected Component updateColor(final Component component, final TextColor otherColor) {
        final TextColor color = component.getColor();
        return (color == null || !color.equals(otherColor)) ? component.color(otherColor) : component;
    }
    
    public void setFloatingPointPosition(final boolean floatingPointPosition) {
        this.floatingPointPosition = floatingPointPosition;
    }
    
    public void setState(final State state) {
        this.state = state;
    }
    
    public State state() {
        return this.state;
    }
    
    public float getWidth() {
        return this.renderableComponent.getWidth();
    }
    
    public float getHeight() {
        return this.renderableComponent.getHeight();
    }
    
    protected boolean isLeftAligned() {
        return !this.hudWidget.anchor().isRight();
    }
    
    public <T extends TextHudWidgetConfig> T config() {
        return (T)this.hudWidget.getConfig();
    }
    
    @Nullable
    public RenderableComponent getRenderableComponent() {
        return this.renderableComponent;
    }
    
    @Deprecated
    public boolean isVisible() {
        return this.state == State.VISIBLE;
    }
    
    @Deprecated
    public void setVisible(final boolean visible) {
        this.state = (visible ? State.VISIBLE : State.HIDDEN);
    }
    
    @Deprecated
    public TextLine flush() {
        this.flushInternal();
        return this;
    }
    
    static {
        BUILDER = Laby.labyAPI().renderPipeline().componentRenderer().builder();
    }
    
    public enum State
    {
        DISABLED, 
        HIDDEN, 
        VISIBLE;
    }
}
