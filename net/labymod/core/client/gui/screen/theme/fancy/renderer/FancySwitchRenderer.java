// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.draw.CircleRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer;

public class FancySwitchRenderer extends VanillaBackgroundRenderer
{
    public FancySwitchRenderer() {
        this.name = "Switch";
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        super.renderPre(widget, context);
        final SwitchWidget switchWidget = (SwitchWidget)widget;
        final Rectangle rectangle = widget.bounds().rectangle(BoundsType.INNER);
        final float padding = 2.0f;
        final float stateWidth = rectangle.getHeight() - padding * 2.0f;
        final float left = rectangle.getLeft() + padding;
        final float right = rectangle.getRight() - padding - stateWidth;
        final long timePassed = TimeUtil.getMillis() - widget.getLastActionTime();
        final float percentage = MathHelper.sigmoid(MathHelper.clamp(0.01f * timePassed, 0.0f, 1.0f));
        final float directionalPercentage = switchWidget.getValue() ? (1.0f - percentage) : percentage;
        final float x = left * directionalPercentage + right * (1.0f - directionalPercentage);
        final boolean enabled = directionalPercentage < 0.5;
        final float radius = 4.0f;
        this.rectangleRenderer.pos(x, rectangle.getTop() + padding, x + stateWidth, rectangle.getBottom() - padding).rounded(radius).lowerEdgeSoftness(BorderRadius.DEFAULT_EDGE_SOFTNESS).color(enabled ? -13202129 : -7524552).render(context.stack());
        final float centerX = x + stateWidth / 2.0f;
        final float centerY = rectangle.getCenterY();
        if (enabled) {
            final int radius2 = 1;
            this.rectangleRenderer.pos(centerX - 0.75f, centerY - 3.0f, centerX + 0.75f, centerY + 3.0f).rounded((float)radius2).lowerEdgeSoftness(BorderRadius.DEFAULT_EDGE_SOFTNESS).color(-3285300).render(context.stack());
        }
        else {
            this.labyAPI.renderPipeline().circleRenderer().pos(centerX, centerY).radius(2.5f).color(-3285300).render(context.stack());
        }
    }
}
