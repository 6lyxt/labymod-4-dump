// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.util.Color;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.navigation.DefaultNavigationRegistry;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public class FancyNavigationButtonRenderer extends FancyButtonRenderer
{
    public FancyNavigationButtonRenderer() {
        this.name = "NavigationButton";
    }
    
    @Override
    public void renderPost(final AbstractWidget<?> widget, final ScreenContext context) {
        super.renderPost(widget, context);
        if (widget.isActive()) {
            final DefaultNavigationRegistry registry = (DefaultNavigationRegistry)this.labyAPI.navigationService();
            final long animationDuration = 150L;
            long timePassed = registry.getTimePassedSinceActiveChanged();
            timePassed += (long)TimeUtil.convertDeltaToMilliseconds(context.getTickDelta());
            registry.setTimePassedSinceActiveChanged(timePassed);
            final boolean transitionToRight = registry.getPreviousActiveIndex() > registry.getActiveIndex();
            float transitionProgress = 1.0f - Math.min(timePassed / (float)animationDuration, 1.0f);
            transitionProgress = (float)CubicBezier.EASE_OUT.curve(transitionProgress);
            final FancyThemeConfig config = Laby.labyAPI().themeService().getThemeConfig(FancyThemeConfig.class);
            if (config != null && !config.activityTransitions().get()) {
                transitionProgress = 0.0f;
            }
            final Rectangle outer = widget.bounds().rectangle(BoundsType.OUTER);
            final float height = 1.475f;
            float x = outer.getX();
            final float y = outer.getY() + outer.getHeight() - height;
            final float width = outer.getWidth();
            if (transitionToRight) {
                x += width * transitionProgress;
            }
            else {
                x -= width * transitionProgress;
            }
            final float padding = width / 100.0f * 45.0f / 2.0f;
            final int color = ColorFormat.ARGB32.mul(config.accentColor().get().get(), 3.5833f, 1.48f, 1.3125f, 1.0f);
            this.rectangleRenderer.pos(x + padding, y, x + width - padding, y + height).color(color).render(context.stack());
        }
    }
}
