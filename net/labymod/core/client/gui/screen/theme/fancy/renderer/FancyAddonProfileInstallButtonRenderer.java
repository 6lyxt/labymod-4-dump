// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.lss.variable.LssVariable;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public class FancyAddonProfileInstallButtonRenderer extends FancyButtonRenderer
{
    public FancyAddonProfileInstallButtonRenderer() {
        this.name = "AddonProfileInstallButton";
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        final LssVariable variable = widget.getVariable("--download-percentage");
        if (variable == null) {
            super.renderPre(widget, context);
            return;
        }
        final String value = variable.value();
        float percentage;
        try {
            percentage = Float.parseFloat(value);
        }
        catch (final NumberFormatException exception) {
            exception.printStackTrace();
            super.renderPre(widget, context);
            return;
        }
        final Rectangle outer = widget.bounds().rectangle(BoundsType.OUTER);
        final float outerWidth = outer.getWidth();
        final float widthPerPercentage = outerWidth / 100.0f;
        final float x = outer.getX();
        final float y = outer.getY();
        final float width = x + widthPerPercentage * percentage;
        this.rectangleRenderer.pos(x, y, width, y + outer.getHeight()).color(ColorFormat.ARGB32.pack(0, 255, 0, 180));
        final BorderRadius borderRadius = widget.getBorderRadius();
        if (borderRadius != null) {
            final float leftTop = borderRadius.getLeftTop();
            final float leftBottom = borderRadius.getLeftBottom();
            final float finalLeftTop = leftTop / (leftTop * 3.0f);
            final float finalLeftBottom = leftBottom / (leftBottom * 3.0f);
            this.rectangleRenderer.rounded(MathHelper.clamp(finalLeftTop * percentage, 0.0f, leftTop), 0.0f, MathHelper.clamp(finalLeftBottom * percentage, 0.0f, leftBottom), 0.0f).upperEdgeSoftness(borderRadius.getUpperEdgeSoftness()).lowerEdgeSoftness(borderRadius.getLowerEdgeSoftness());
        }
        this.rectangleRenderer.render(context.stack());
        super.renderPre(widget, context);
    }
}
