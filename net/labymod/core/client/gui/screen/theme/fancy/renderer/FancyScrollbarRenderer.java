// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollbarWidget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaScrollbarRenderer;

public class FancyScrollbarRenderer extends VanillaScrollbarRenderer
{
    private static final ModifyReason FANCY_SCROLLBAR_RENDER_PADDING;
    
    @Override
    public void renderPost(final ScrollbarWidget widget, final ScreenContext context) {
        final ScrollWidget scrollWidget = widget.scrollWidget();
        if (!scrollWidget.isScrollbarRequired()) {
            return;
        }
        final Bounds bounds = widget.bounds();
        final float width = bounds.getWidth();
        float radius = width - (width / 2.0f - 0.25f);
        this.rectangleRenderer.pos(bounds.getLeft(), bounds.getTop(), bounds.getRight(), bounds.getBottom()).color(widget.scrollBackgroundColor().get()).rounded(radius).lowerEdgeSoftness(-0.125f).upperEdgeSoftness(0.5f).render(context.stack());
        final float scrollTop = widget.bounds().getTop() + widget.getScrollButtonOffset();
        final float scrollBottom = scrollTop + widget.getScrollButtonHeight();
        final float padding = 1.0f;
        radius -= padding;
        bounds.shrink(padding, FancyScrollbarRenderer.FANCY_SCROLLBAR_RENDER_PADDING);
        this.rectangleRenderer.pos(bounds.getLeft(), scrollTop + 1.0f, bounds.getRight(), scrollBottom - 1.0f).color((context.mouse().isInside(bounds) || widget.isDragging()) ? widget.scrollHoverColor().get() : widget.scrollColor().get()).rounded(radius).lowerEdgeSoftness(-0.125f).upperEdgeSoftness(0.5f).render(context.stack());
        bounds.expand(padding, FancyScrollbarRenderer.FANCY_SCROLLBAR_RENDER_PADDING);
    }
    
    static {
        FANCY_SCROLLBAR_RENDER_PADDING = ModifyReason.renderOnly("fancyScrollbarRenderPadding");
    }
}
