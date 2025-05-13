// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer.chat;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;

public class VanillaChatSliderRenderer extends ThemeRenderer<SliderWidget>
{
    public VanillaChatSliderRenderer() {
        super("ChatSlider");
    }
    
    @Override
    public void renderPre(final SliderWidget widget, final ScreenContext context) {
        final Bounds bounds = widget.bounds();
        final boolean hovered = widget.isHovered() || widget.isDragging();
        this.rectangleRenderer.pos(bounds.getX(), bounds.getCenterY()).size(bounds.getWidth(), 1.0f).color(hovered ? -2130706433 : 1090519039).render(context.stack());
        final float offset = bounds.getWidth() * widget.getPercentage();
        this.rectangleRenderer.pos(bounds.getX() + offset - 2.0f, bounds.getY()).size(4.0f, bounds.getHeight()).color(hovered ? -265080013 : -266198494).render(context.stack());
        this.componentRenderer.builder().text(widget.component()).pos(bounds.getCenterX(), bounds.getCenterY() - 4.0f).centered(true).color(-1).render(context.stack());
    }
}
