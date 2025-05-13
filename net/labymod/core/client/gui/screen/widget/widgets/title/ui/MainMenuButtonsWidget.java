// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.title.ui;

import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.Component;
import net.labymod.api.Textures;
import java.util.Objects;
import net.labymod.api.BuildData;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.screenshots.ScreenshotBrowserActivity;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class MainMenuButtonsWidget extends AbstractWidget<Widget>
{
    public static final boolean IS_ACCESSIBILITY_VERSION;
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final Minecraft minecraft = this.labyAPI.minecraft();
        final ButtonWidget singleplayerWidget = ButtonWidget.i18nMinecraft("menu.singleplayer", () -> this.display(NamedScreen.SINGLEPLAYER));
        ((AbstractWidget<Widget>)singleplayerWidget).addId("singleplayer", "large-button");
        ((AbstractWidget<ButtonWidget>)this).addChild(singleplayerWidget);
        final ButtonWidget multiplayerWidget = ButtonWidget.i18nMinecraft("menu.multiplayer", () -> this.display(NamedScreen.MULTIPLAYER));
        ((AbstractWidget<Widget>)multiplayerWidget).addId("multiplayer", "large-button");
        ((AbstractWidget<ButtonWidget>)this).addChild(multiplayerWidget);
        final ButtonWidget screenshotsWidget = ButtonWidget.i18n("labymod.activity.menu.button.screenshots", () -> this.display(ScreenshotBrowserActivity.INSTANCE));
        ((AbstractWidget<Widget>)screenshotsWidget).addId("screenshots", "small-button");
        ((AbstractWidget<ButtonWidget>)this).addChild(screenshotsWidget);
        final ButtonWidget realmsWidget = ButtonWidget.i18nMinecraft("menu.online", () -> this.display(NamedScreen.REALMS));
        ((AbstractWidget<Widget>)realmsWidget).addId("realms", "small-button");
        realmsWidget.setEnabled(BuildData.hasRealms());
        ((AbstractWidget<ButtonWidget>)this).addChild(realmsWidget);
        final ButtonWidget optionsWidget = ButtonWidget.i18nMinecraft("menu.options", () -> this.display(NamedScreen.OPTIONS));
        ((AbstractWidget<Widget>)optionsWidget).addId("options", "small-button");
        ((AbstractWidget<ButtonWidget>)this).addChild(optionsWidget);
        final String key = "menu.quit";
        final Minecraft obj = minecraft;
        Objects.requireNonNull(obj);
        final ButtonWidget quitWidget = ButtonWidget.i18nMinecraft(key, obj::shutdownGame);
        ((AbstractWidget<Widget>)quitWidget).addId("quit", "small-button");
        ((AbstractWidget<ButtonWidget>)this).addChild(quitWidget);
        if (this.labyAPI.config().appearance().titleScreen().quickPlay().get()) {
            final String address = this.labyAPI.minecraft().options().getLastKnownServer();
            if (address != null && !address.isEmpty()) {
                final ButtonWidget quickPlayWidget = ButtonWidget.icon(Textures.SpriteCommon.FORWARD_BUTTON);
                ((AbstractWidget<Widget>)quickPlayWidget).addId("quick-play", "icon-button");
                quickPlayWidget.setHoverComponent(Component.translatable("labymod.activity.menu.button.quickPlay", new Component[0]).arguments(Component.text(address)));
                quickPlayWidget.setPressable(() -> this.labyAPI.serverController().joinServer(address, address, false));
                ((AbstractWidget<ButtonWidget>)this).addChild(quickPlayWidget);
            }
        }
        final ButtonWidget languageWidget = ButtonWidget.icon(Textures.SpriteMinecraftIcons.LANGUAGE, () -> this.display(NamedScreen.LANGUAGE_SELECTION));
        ((AbstractWidget<Widget>)languageWidget).addId("language", "icon-button");
        ((AbstractWidget<ButtonWidget>)this).addChild(languageWidget);
        if (MainMenuButtonsWidget.IS_ACCESSIBILITY_VERSION) {
            final ButtonWidget accessibilityWidget = ButtonWidget.icon(Textures.SpriteMinecraftIcons.ACCESSIBILITY, () -> this.display(NamedScreen.ACCESSIBILITY_SETTINGS));
            ((AbstractWidget<Widget>)accessibilityWidget).addId("accessibility", "icon-button");
            ((AbstractWidget<ButtonWidget>)this).addChild(accessibilityWidget);
        }
    }
    
    private void display(final NamedScreen namedScreen) {
        this.labyAPI.minecraft().minecraftWindow().displayScreen(namedScreen);
    }
    
    private void display(final Activity activity) {
        this.labyAPI.minecraft().minecraftWindow().displayScreen(activity);
    }
    
    static {
        IS_ACCESSIBILITY_VERSION = MinecraftVersions.V19w13a.orNewer();
    }
}
