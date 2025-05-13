// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer;

public class FancySliderRenderer extends VanillaBackgroundRenderer
{
    public FancySliderRenderer() {
        this.name = "Slider";
    }
    
    @Override
    public void renderPost(final AbstractWidget<?> widget, final ScreenContext context) {
        super.renderPost(widget, context);
        final SliderWidget slider = (SliderWidget)widget;
        final Rectangle bounds = slider.bounds().rectangle(BoundsType.MIDDLE);
        final float offset = this.getOffset(slider, bounds, context.mouse());
        this.renderKnob(context.stack(), bounds.getLeft() + offset, bounds.getTop(), 8.0f, bounds.getHeight(), 4.0f);
    }
    
    protected float getOffset(final SliderWidget slider, final Rectangle bounds, final MutableMouse mouse) {
        if (slider.isDragging()) {
            return MathHelper.clamp(mouse.getX() - bounds.getX() - 4.0f, 0.0f, bounds.getWidth() - 8.0f);
        }
        return (bounds.getWidth() - 8.0f) * slider.getPercentage();
    }
    
    protected void renderKnob(final Stack stack, final float x, final float y, final float width, final float height, final float radius) {
        this.rectangleRenderer.pos(x, y, x + width, y + height).rounded(radius).lowerEdgeSoftness(-0.5f).color(ColorFormat.ARGB32.pack(255, 255, 255, 150)).render(stack);
    }
}
