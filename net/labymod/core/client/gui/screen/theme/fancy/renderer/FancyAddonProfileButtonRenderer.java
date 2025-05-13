// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public class FancyAddonProfileButtonRenderer extends FancyButtonRenderer
{
    public FancyAddonProfileButtonRenderer() {
        this.name = "AddonProfileButton";
    }
    
    @Override
    public void renderPost(final AbstractWidget<?> widget, final ScreenContext context) {
        super.renderPost(widget, context);
        if (!widget.isActive()) {
            return;
        }
        final Rectangle rectangle = widget.bounds().rectangle(BoundsType.OUTER);
        final float height = 0.75f;
        final float x = rectangle.getX();
        final float y = rectangle.getY() + rectangle.getHeight() - 2.0f - height;
        final float width = rectangle.getWidth();
        this.rectangleRenderer.pos(x, y, x + width, y + height).color(ColorFormat.ARGB32.pack(2857703, 255)).render(context.stack());
    }
}
