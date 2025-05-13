// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.debug;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.render.draw.batch.BatchRectangleRenderer;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.ScreenContext;

public final class DebugWidgetRenderer
{
    public static void renderDebug(final ScreenContext context, final Widget widget) {
        if (Laby.references().controlEntryRegistry().isVisible("labymod:document")) {
            renderImGuiDebug(context, widget);
        }
        Laby.references().screenWindowManagement().renderWidgetOverlay(context.stack(), context.mouse(), widget);
    }
    
    private static void renderImGuiDebug(final ScreenContext context, final Widget widget) {
        final Widget targetWidget = Laby.references().documentHandler().getTargetWidget();
        final boolean isSelected = targetWidget != null && targetWidget.equals(widget);
        final int alpha = isSelected ? 255 : 42;
        final Bounds bounds = widget.bounds();
        final boolean useFloatingPoints = Laby.labyAPI().minecraft().isKeyPressed(Key.L_SHIFT);
        float x = bounds.getX(BoundsType.MIDDLE);
        float y = bounds.getY(BoundsType.MIDDLE);
        float width = bounds.getWidth(BoundsType.MIDDLE);
        float height = bounds.getHeight(BoundsType.MIDDLE);
        if (!useFloatingPoints) {
            x = (float)Math.round(x);
            y = (float)Math.round(y);
            width = (float)Math.round(width);
            height = (float)Math.round(height);
        }
        final float finalX = x;
        final float finalY = y;
        final float finalWidth = width;
        final float finalHeight = height;
        final BatchRectangleRenderer renderer = Laby.labyAPI().renderPipeline().rectangleRenderer().beginBatch(context.stack());
        renderer.renderOutline(finalX, finalY, finalX + finalWidth, finalY + finalHeight, NamedTextColor.WHITE.value() | alpha << 24, 1);
        if (isSelected) {
            renderer.pos(finalX, finalY, finalX + finalWidth, finalY + finalHeight).color(NamedTextColor.WHITE.value() | 0xF000000).build();
        }
        renderer.upload();
    }
}
