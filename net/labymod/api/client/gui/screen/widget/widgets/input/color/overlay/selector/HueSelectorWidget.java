// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.Color;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.Orientation;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorData;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;

@AutoWidget
public class HueSelectorWidget extends SelectorWidget
{
    public HueSelectorWidget(final ColorData colorData) {
        super(colorData);
    }
    
    @Override
    public void renderSelector(final Orientation orientation, final Stack stack) {
        HueSelectorWidget.RENDER_CONTEXT.begin(stack);
        final Bounds bounds = this.bounds();
        final float width = bounds.getWidth();
        final float sections = 0.0027777778f;
        final float step = width / 360.0f;
        int index = 0;
        if (orientation == Orientation.HORIZONTAL) {
            for (float x = 0.0f; x < width; x += step) {
                HueSelectorWidget.RENDER_CONTEXT.renderGradientHorizontally(bounds.getLeft() + x, bounds.getTop(), bounds.getLeft() + x + step, bounds.getBottom(), Color.HSBtoRGB(sections * (index - 1), 1.0f, 1.0f), Color.HSBtoRGB(sections * index, 1.0f, 1.0f));
                ++index;
            }
        }
        else {
            for (float x = 0.0f; x < width; x += step) {
                HueSelectorWidget.RENDER_CONTEXT.renderGradientVertically(bounds.getLeft() + x, bounds.getTop(), bounds.getLeft() + x + step, bounds.getBottom(), Color.HSBtoRGB(sections * (index - 1), 1.0f, 1.0f), Color.HSBtoRGB(sections * index, 1.0f, 1.0f));
                ++index;
            }
        }
        HueSelectorWidget.RENDER_CONTEXT.uploadToBuffer();
    }
    
    @Override
    public void update(final float posX, final float posY) {
        this.updateMarkerPosition(posX, this.bounds().getHeight() / 2.0f);
        final float hue = MathHelper.clamp(posX * 360.0f / this.bounds().getWidth(), 0.0f, 360.0f);
        this.colorData().setHue((int)hue);
    }
    
    @Override
    public void updateMarkerPosition() {
        final float mouseX = this.colorData().getColor().getHue() / 360.0f * this.bounds().getWidth();
        this.updateMarkerPosition(mouseX, this.bounds().getHeight() / 2.0f);
    }
}
