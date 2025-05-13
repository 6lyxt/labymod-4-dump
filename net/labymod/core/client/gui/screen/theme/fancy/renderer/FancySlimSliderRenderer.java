// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public class FancySlimSliderRenderer extends FancySliderRenderer
{
    public FancySlimSliderRenderer() {
        this.name = "SlimSlider";
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        final int height = 2;
        final ReasonableMutableRectangle bounds = widget.bounds().rectangle(BoundsType.MIDDLE);
        final Rectangle rectangle = Rectangle.relative(bounds.getX(), bounds.getCenterY() - height / 2.0f, bounds.getWidth(), (float)height);
        final Rectangle border = widget.bounds().rectangle(BoundsType.BORDER);
        this.renderBackground(context.stack(), widget, rectangle, border, context.getTickDelta());
    }
    
    @Override
    public void renderPost(final AbstractWidget<?> widget, final ScreenContext context) {
        final SliderWidget slider = (SliderWidget)widget;
        final Rectangle bounds = slider.bounds().rectangle(BoundsType.MIDDLE);
        final float offset = this.getOffset(slider, bounds, context.mouse());
        final float size = 8.0f;
        this.renderKnob(context.stack(), bounds.getLeft() + offset, bounds.getTop() + size / 4.0f, size, size, 4.0f);
    }
}
