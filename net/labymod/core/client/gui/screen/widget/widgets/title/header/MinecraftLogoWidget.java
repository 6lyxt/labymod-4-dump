// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.title.header;

import net.labymod.core.client.gui.screen.widget.widgets.title.header.type.ModernMinecraftLogoWidget;
import net.labymod.core.client.gui.screen.widget.widgets.title.header.type.LegacyMinecraftLogoWidget;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.client.gui.screen.widget.widgets.activity.title.header.SplashWidgetAccessor;
import org.jetbrains.annotations.Nullable;
import net.labymod.core.event.labymod.gui.title.TitleScreenLogoPreRenderEvent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.core.client.resources.SplashLoader;
import net.labymod.api.event.client.gui.screen.title.TitleScreenSplashTextEvent;
import net.labymod.api.Laby;
import net.labymod.api.event.client.gui.screen.title.TitleScreenLogoInitializeEvent;
import net.labymod.api.client.gui.screen.Parent;
import java.util.Locale;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.title.header.LogoWidgetAccessor;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public abstract class MinecraftLogoWidget extends AbstractWidget<Widget> implements LogoWidgetAccessor
{
    private final String type;
    private Widget minecraftWidget;
    private Widget editionWidget;
    private SplashWidget splashWidget;
    
    public MinecraftLogoWidget(final String type) {
        this.type = type;
        this.addId(String.format(Locale.ROOT, "%s-logo", type));
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final TitleScreenLogoInitializeEvent event = Laby.fireEvent(new TitleScreenLogoInitializeEvent(this.type, this.createMinecraftWidget(), this.createEditionWidget()));
        this.minecraftWidget = event.getMinecraftWidget();
        if (this.minecraftWidget != null) {
            this.minecraftWidget.addId("minecraft", String.format(Locale.ROOT, "minecraft-%s", this.type));
            this.addChild(this.minecraftWidget);
        }
        this.editionWidget = event.getEditionWidget();
        if (this.editionWidget != null) {
            this.editionWidget.addId("edition", String.format(Locale.ROOT, "edition-%s", this.type));
            this.addChild(this.editionWidget);
        }
        final TitleScreenSplashTextEvent splashTextEvent = Laby.fireEvent(new TitleScreenSplashTextEvent(SplashLoader.INSTANCE.getSplashToday()));
        boolean showSplash = !splashTextEvent.isCancelled();
        if (this.labyAPI.minecraft().options().isHideSplashTexts()) {
            showSplash = false;
        }
        if (showSplash) {
            (this.splashWidget = new SplashWidget()).addId("splash", String.format(Locale.ROOT, "splash-%s", this.type));
            this.splashWidget.setSplashText(splashTextEvent.getSplashText());
            ((AbstractWidget<SplashWidget>)this).addChild(this.splashWidget);
        }
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        Laby.fireEvent(new TitleScreenLogoPreRenderEvent(context.stack(), context.mouse(), context.getTickDelta(), this));
        super.renderWidget(context);
    }
    
    @Override
    public Widget getMinecraftWidget() {
        return this.minecraftWidget;
    }
    
    @Nullable
    @Override
    public Widget getEditionWidget() {
        return this.editionWidget;
    }
    
    @Override
    public SplashWidgetAccessor getSplashWidget() {
        return this.splashWidget;
    }
    
    @Nullable
    protected abstract Widget createMinecraftWidget();
    
    @Nullable
    protected abstract Widget createEditionWidget();
    
    public static MinecraftLogoWidget create() {
        final boolean legacy = MinecraftVersions.V1_19_4.orOlder();
        return legacy ? new LegacyMinecraftLogoWidget() : new ModernMinecraftLogoWidget();
    }
}
