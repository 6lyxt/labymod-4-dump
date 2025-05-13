// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.notification.Notification;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.widget.widgets.notification.NotificationWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public class FancyNotificationRenderer extends FancyBackgroundRenderer
{
    public FancyNotificationRenderer() {
        this.name = "Notification";
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        super.renderPre(widget, context);
        final Bounds bounds = widget.bounds();
        final Notification notification = ((NotificationWidget)widget).notification();
        final float progress = notification.progress();
        final float padding = 5.0f;
        final float progressHeight = 2.0f;
        final RectangleRenderer rectangleRenderer = Laby.labyAPI().renderPipeline().rectangleRenderer();
        rectangleRenderer.pos(bounds.getX() + padding, bounds.getBottom() - progressHeight - 2.0f).size(bounds.getWidth() - padding * 2.0f, progressHeight).color(0, 50, 130, 155).rounded(1.0f).lowerEdgeSoftness(-0.5f).render(context.stack());
        rectangleRenderer.pos(bounds.getX() + padding, bounds.getBottom() - progressHeight - 2.0f).size((bounds.getWidth() - padding * 2.0f) * progress, progressHeight).color(0, 150, 230, 255).rounded(1.0f).lowerEdgeSoftness(-0.5f).render(context.stack());
    }
}
