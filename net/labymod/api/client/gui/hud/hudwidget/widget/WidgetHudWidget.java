// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.hudwidget.widget;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.widgets.hud.ScaledRectangle;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.util.bounds.Rectangle;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.meta.LinkMetaLoader;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;

@Links({ @Link("style.lss"), @Link("widget-hud-widget.lss") })
public abstract class WidgetHudWidget<T extends HudWidgetConfig> extends HudWidget<T>
{
    private final LinkMetaLoader linkMetaLoader;
    
    protected WidgetHudWidget(final String id, final Class<T> config) {
        super(id, config);
        this.linkMetaLoader = Laby.references().linkMetaLoader();
    }
    
    @Override
    public void initialize(final HudWidgetWidget widget) {
        widget.handleStyleSheet = true;
        widget.setSize(SizeType.ACTUAL, WidgetSide.WIDTH, WidgetSize.fitContent());
        widget.setSize(SizeType.ACTUAL, WidgetSide.HEIGHT, WidgetSize.fitContent());
        widget.setStencil(true);
        super.initialize(widget);
        widget.setInitializedAnchor(this.anchor);
    }
    
    @Override
    public void postInitialize(final HudWidgetWidget widget) {
        final LinkMetaLoader linkMetaLoader = this.linkMetaLoader;
        final Class<? extends WidgetHudWidget> class1 = this.getClass();
        Objects.requireNonNull(widget);
        linkMetaLoader.loadMeta(class1, widget::applyStyleSheet);
    }
    
    @Override
    public void onBoundsChanged(final HudWidgetWidget widget, final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(widget, previousRect, newRect);
        this.updateSize(widget, widget.accessor().isEditor(), widget.size());
        widget.setScale(widget.size().getScale());
        if (this.anchor != widget.getInitializedAnchor()) {
            widget.reInitialize();
        }
    }
    
    @Override
    public void updateSize(final HudWidgetWidget widget, final boolean isEditorContext, final HudSize size) {
        final Bounds bounds = widget.bounds();
        size.set((int)bounds.getWidth(), (int)bounds.getHeight());
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext, final HudSize size) {
    }
    
    @Override
    public void renderPost(final Stack stack, final MutableMouse mouse, final float tickDelta, final HudWidgetWidget widget) {
        stack.push();
        final ScaledRectangle rectangle = widget.scaledBounds();
        stack.translate(-rectangle.getX() - widget.getInterpolationOffsetX(), -rectangle.getY() - widget.getInterpolationOffsetY(), 0.0f);
        final ScreenContext screenContext2;
        final ScreenContext screenContext = screenContext2 = Laby.references().renderEnvironmentContext().screenContext();
        Objects.requireNonNull(widget);
        screenContext2.runInContext(stack, mouse, tickDelta, widget::render);
        stack.pop();
    }
    
    @Override
    public boolean canPreInitialize() {
        return false;
    }
    
    @Override
    public boolean handlesScaling() {
        return true;
    }
}
