// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.WindowWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@Deprecated
public class VanillaResizeRenderer extends VanillaBackgroundRenderer
{
    public VanillaResizeRenderer() {
        this.name = "Resize";
    }
    
    @Override
    public void renderPost(final AbstractWidget<?> widget, final ScreenContext context) {
        if (!(widget instanceof WindowWidget.RescaleWidget)) {
            super.renderPost(widget, context);
            return;
        }
        final WindowWidget.RescaleWidget rescaleWidget = (WindowWidget.RescaleWidget)widget;
        final WindowWidget.RescaleType type = rescaleWidget.type();
        if (!rescaleWidget.render().get() || type == WindowWidget.RescaleType.NONE) {
            return;
        }
        final Widget parentWidget = rescaleWidget.widget();
        if (parentWidget instanceof WindowWidget && !((WindowWidget)parentWidget).isWindowResizeable()) {
            return;
        }
        if (type == WindowWidget.RescaleType.EDGES || type == WindowWidget.RescaleType.CONTENT_EDGES) {
            this.renderEdge(context.stack(), context.mouse(), context.getTickDelta(), type, rescaleWidget);
        }
        else if (type == WindowWidget.RescaleType.BOTTOM_RIGHT_CORNER) {
            this.renderCorner(rescaleWidget, context.stack(), context.mouse(), context.getTickDelta());
        }
    }
    
    protected void renderCorner(final WindowWidget.RescaleWidget widget, final Stack stack, final MutableMouse mouse, final float delta) {
        this.rectangleRenderer.pos(widget.bounds()).color(widget.getCurrentColor()).render(stack);
    }
    
    protected void renderEdge(final Stack stack, final MutableMouse mouse, final float delta, final WindowWidget.RescaleType type, final WindowWidget.RescaleWidget widget) {
        if (!widget.isHovered() && !widget.isDragging()) {
            return;
        }
        final Rectangle window = widget.widget().bounds().rectangle(BoundsType.MIDDLE);
        float left = 0.0f;
        float top = 0.0f;
        switch (widget.position()) {
            case LEFT: {
                left = window.getX();
                top = MathHelper.clamp((float)mouse.getY(), window.getY(), window.getMaxY());
                break;
            }
            case TOP: {
                left = MathHelper.clamp((float)mouse.getX(), window.getX(), window.getMaxX());
                float y = window.getY();
                if (type == WindowWidget.RescaleType.CONTENT_EDGES) {
                    final Widget tabWidget = (Widget)widget.widget().tabWidget();
                    if (tabWidget != null && tabWidget.isVisible()) {
                        y += tabWidget.bounds().getHeight();
                    }
                }
                top = y;
                break;
            }
            case RIGHT: {
                left = window.getMaxX();
                top = MathHelper.clamp((float)mouse.getY(), window.getY(), window.getMaxY());
                break;
            }
            case BOTTOM: {
                left = MathHelper.clamp((float)mouse.getX(), window.getX(), window.getMaxX());
                top = window.getMaxY();
                break;
            }
        }
        final WindowWidget.RescalePosition position = widget.position();
        final String text = (position == WindowWidget.RescalePosition.TOP || position == WindowWidget.RescalePosition.BOTTOM) ? "=" : "||";
        this.textRenderer.text(text).pos(left, top - this.textRenderer.height() / 2.0f).centered(true).render(stack);
    }
}
