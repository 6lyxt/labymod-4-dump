// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.render.draw.CircleRenderer;
import net.labymod.api.util.bounds.Point;
import net.labymod.core.client.gui.screen.widget.widgets.NewWindowWidget;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;

public class FancyWindowRenderer extends ThemeRenderer<AbstractWidget<?>>
{
    public FancyWindowRenderer() {
        super("Window");
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        this.rectangleRenderer.pos(widget.bounds().rectangle(BoundsType.MIDDLE)).color(-2013265920).rounded(5.0f).render(context.stack());
        if (widget instanceof final NewWindowWidget windowWidget) {
            if (windowWidget.getRescaleType() == NewWindowWidget.RescaleType.CORNER) {
                final Rectangle corner = windowWidget.getCorner();
                final boolean mouseOverRescale = corner.isInRectangle(context.mouse()) || windowWidget.isRescaling();
                this.circleRenderer.pos(widget.bounds().getRight() - 7.0f, widget.bounds().getBottom() - 7.0f).partial(270.0f, 90.0f).donutRadius(8.0f, 10.0f).color(mouseOverRescale ? -1 : -6710887).render(context.stack());
            }
        }
    }
}
