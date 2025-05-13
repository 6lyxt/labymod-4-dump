// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.Tab;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.TabWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public class FancyTabRenderer extends FancyBackgroundRenderer
{
    public FancyTabRenderer() {
        this.name = "Tab";
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        super.renderPre(widget, context);
        if (widget.isActive()) {
            final Tab tab = ((TabWidget)widget).getTab();
            final long animationDuration = 100L;
            final long initialTimeActiveChanged = TimeUtil.getMillis() - widget.getLastActiveChangedTime();
            final boolean transitionToRight = tab.isTransitionToRight();
            float transitionProgress = 1.0f - Math.min(initialTimeActiveChanged / (float)animationDuration, 1.0f);
            transitionProgress = (float)CubicBezier.EASE_OUT.curve(transitionProgress);
            final FancyThemeConfig config = Laby.labyAPI().themeService().getThemeConfig(FancyThemeConfig.class);
            if (config != null && !config.activityTransitions().get()) {
                transitionProgress = 0.0f;
            }
            final Rectangle outer = widget.bounds().rectangle(BoundsType.OUTER);
            float x = outer.getX();
            final float y = outer.getY();
            final float width = outer.getWidth();
            final float height = outer.getHeight();
            final Parent parent = widget.getParent();
            if (!(parent instanceof AbstractWidget) || ((AbstractWidget)parent).getLastInitialTime() + animationDuration < TimeUtil.getMillis()) {
                if (transitionToRight) {
                    x += width * transitionProgress;
                }
                else {
                    x -= width * transitionProgress;
                }
            }
            final boolean inGame = this.labyAPI.minecraft().isIngame();
            final BorderRadius borderRadius = widget.getBorderRadius();
            if (borderRadius == null) {
                return;
            }
            this.rectangleRenderer.pos(x, y, x + width, y + height).rounded(borderRadius.getLeftTop(), borderRadius.getRightTop(), borderRadius.getLeftBottom(), borderRadius.getRightBottom()).color(ColorFormat.ARGB32.pack(140, 140, 140, 80)).borderSoftness(borderRadius.getBorderSoftness()).borderThickness(borderRadius.getThickness()).lowerEdgeSoftness(borderRadius.getLowerEdgeSoftness()).upperEdgeSoftness(borderRadius.getUpperEdgeSoftness()).render(context.stack());
        }
    }
}
