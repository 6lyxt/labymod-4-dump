// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.background.title;

import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.widgets.activity.title.header.SplashWidgetAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.activity.title.header.LogoWidgetAccessor;
import net.labymod.core.event.labymod.gui.title.TitleScreenLogoPreRenderEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.gui.screen.widget.widgets.title.header.dynamic.AnimatedMinecraftTextWidget;
import net.labymod.api.event.client.gui.screen.title.TitleScreenLogoInitializeEvent;
import net.labymod.core.client.gui.background.DynamicBackgroundController;

public class TitleScreenLogoListener
{
    private final DynamicBackgroundController controller;
    
    public TitleScreenLogoListener(final DynamicBackgroundController controller) {
        this.controller = controller;
    }
    
    @Subscribe
    public void onTitleScreenLogoInitialize(final TitleScreenLogoInitializeEvent event) {
        if (!DynamicBackgroundController.isEnabled()) {
            return;
        }
        final AnimatedMinecraftTextWidget widget = new AnimatedMinecraftTextWidget();
        widget.addId("animated-minecraft");
        event.setMinecraftWidget(widget);
    }
    
    @Subscribe
    public void onTitleScreenLogoInitialize(final TitleScreenLogoPreRenderEvent event) {
        if (!DynamicBackgroundController.isEnabled()) {
            return;
        }
        final float timePassed = this.controller.getTimePassed();
        final LogoWidgetAccessor logo = event.logo();
        final SplashWidgetAccessor splashWidget = logo.getSplashWidget();
        if (splashWidget != null) {
            splashWidget.setScale(this.curve(timePassed - 2900.0f, 500.0f));
        }
        final Widget editionWidget = logo.getEditionWidget();
        if (editionWidget != null) {
            editionWidget.setScale(this.curve(timePassed - 2700.0f, 500.0f));
        }
    }
    
    private float curve(final float timePassed, final float speed) {
        final float scale = MathHelper.clamp(timePassed / speed, 0.0f, 1.0f);
        return (float)CubicBezier.BOUNCE.curve(scale);
    }
}
