// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer.button;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public class VanillaSliderRenderer extends VanillaButtonRenderer
{
    public VanillaSliderRenderer() {
        super("Slider");
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        final SliderWidget sliderWidget = (SliderWidget)widget;
        final Rectangle bounds = widget.bounds().rectangle(BoundsType.MIDDLE);
        this.renderTexture(context.stack(), bounds, false, false, 0);
        final float offset = (bounds.getWidth() - 8.0f) * sliderWidget.getPercentage();
        final MutableRectangle stateRectangle = bounds.copy();
        stateRectangle.setWidth(8.0f);
        stateRectangle.setX(bounds.getX() + offset);
        this.renderTexture(context.stack(), stateRectangle, true, stateRectangle.isInRectangle(context.mouse()), 0);
    }
}
