// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import java.awt.Color;
import net.labymod.api.client.render.draw.batch.BatchRectangleRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollbarWidget;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;

public class VanillaScrollbarRenderer extends ThemeRenderer<ScrollbarWidget>
{
    private static final int BUTTON_COLOR_SHADOW;
    private static final int BUTTON_COLOR;
    
    public VanillaScrollbarRenderer() {
        super("Scrollbar");
    }
    
    @Override
    public void renderPost(final ScrollbarWidget widget, final ScreenContext context) {
        final ScrollWidget scrollWidget = widget.scrollWidget();
        if (!scrollWidget.isScrollbarRequired()) {
            return;
        }
        final Bounds bounds = widget.bounds();
        final float scrollBackgroundTop = bounds.getTop(BoundsType.MIDDLE);
        final float scrollBackgroundBottom = bounds.getBottom(BoundsType.MIDDLE);
        final float scrollButtonTop = widget.bounds().getTop(BoundsType.MIDDLE) + widget.getScrollButtonOffset();
        final float scrollButtonBottom = scrollButtonTop + widget.getScrollButtonHeight();
        final float scrollLeft = bounds.getLeft(BoundsType.MIDDLE);
        final float scrollRight = bounds.getRight(BoundsType.MIDDLE);
        final BatchRectangleRenderer renderer = this.labyAPI.renderPipeline().rectangleRenderer().beginBatch(context.stack());
        renderer.pos(scrollLeft, scrollBackgroundTop, scrollRight, scrollBackgroundBottom).color(Color.BLACK).build();
        if (bounds.getWidth() == 1.0f) {
            renderer.pos(scrollLeft, scrollButtonTop, scrollRight, scrollButtonBottom).color(Color.WHITE).build();
        }
        else {
            renderer.pos(scrollLeft, scrollButtonTop, scrollRight, scrollButtonBottom).color(VanillaScrollbarRenderer.BUTTON_COLOR_SHADOW).build();
            renderer.pos(scrollLeft, scrollButtonTop, scrollRight - 1.0f, scrollButtonBottom - 1.0f).color(VanillaScrollbarRenderer.BUTTON_COLOR).build();
        }
        renderer.upload();
    }
    
    static {
        BUTTON_COLOR_SHADOW = ColorFormat.ARGB32.pack(128, 128, 128, 255);
        BUTTON_COLOR = ColorFormat.ARGB32.pack(192, 192, 192, 255);
    }
}
