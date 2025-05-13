// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity;

import java.util.Iterator;
import net.labymod.api.util.KeyValue;
import net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.Phase;
import net.labymod.api.event.labymod.config.SettingUpdateEvent;
import net.labymod.api.configuration.labymod.main.laby.AppearanceConfig;
import javax.inject.Inject;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.SkinActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.LabyModActivity;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.activity.activities.options.SkinCustomizationOverlay;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.MultiplayerActivity;
import net.labymod.core.client.gui.screen.activity.activities.singleplayer.SingleplayerOverlay;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.labymod.core.client.gui.screen.accessor.PauseScreenAccessor;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.core.client.gui.screen.activity.activities.menu.IngameMenuOverlay;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.connect.DisconnectedOverlay;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.connect.ConnectOverlay;
import net.labymod.core.client.gui.screen.activity.activities.options.OptionsOverlay;
import net.labymod.core.client.gui.screen.activity.activities.menu.MainMenuActivity;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.LabyAPI;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.activity.overlay.OverlayRegistry;
import net.labymod.api.client.gui.screen.activity.overlay.RegisteredReplacement;
import net.labymod.api.service.DefaultRegistry;

@Singleton
@Implements(OverlayRegistry.class)
public class DefaultOverlayRegistry extends DefaultRegistry<RegisteredReplacement> implements OverlayRegistry
{
    private final LabyAPI api;
    
    @Inject
    public DefaultOverlayRegistry(final LabyAPI api) {
        this.api = api;
        this.api.eventBus().registerListener(this);
        this.register(NamedScreen.MAIN_MENU, MainMenuActivity.class, MainMenuActivity::new);
        this.register(NamedScreen.OPTIONS, OptionsOverlay.class, OptionsOverlay::new);
        this.register(NamedScreen.CONNECTING, ConnectOverlay.class, ConnectOverlay::new);
        this.register(NamedScreen.DISCONNECTED, DisconnectedOverlay.class, DisconnectedOverlay::new);
        this.register(NamedScreen.INGAME_MENU, IngameMenuOverlay.class, screenInstance -> {
            if (screenInstance instanceof final ScreenWrapper wrapper) {
                final Object versionedScreen = wrapper.getVersionedScreen();
                if (versionedScreen instanceof final PauseScreenAccessor accessor) {
                    if (!accessor.showPauseMenu()) {
                        return null;
                    }
                }
            }
            return new IngameMenuOverlay(screenInstance);
        });
        this.register(NamedScreen.CHAT_INPUT, ChatInputOverlay.class, ChatInputOverlay::new);
        this.register(NamedScreen.CHAT_INPUT_IN_BED, ChatInputOverlay.class, ChatInputOverlay::new);
        this.register(NamedScreen.SINGLEPLAYER, SingleplayerOverlay.class, SingleplayerOverlay::new);
        this.register(NamedScreen.MULTIPLAYER, MultiplayerActivity.class, () -> MultiplayerActivity.INSTANCE);
        this.register(NamedScreen.SKIN_CUSTOMIZATION, SkinCustomizationOverlay.class, parentScreen -> {
            final LabyAPI labyAPI = Laby.labyAPI();
            if (labyAPI.config().appearance().replaceSkinCustomization().get()) {
                final LabyModActivity labyModActivity = LabyModActivity.getFromNavigationRegistry();
                if (labyModActivity != null) {
                    final PlayerActivity playerActivity = labyModActivity.switchTab((Class<? extends PlayerActivity>)PlayerActivity.class);
                    if (playerActivity != null) {
                        playerActivity.displayChild(SkinActivity.class);
                        return labyModActivity;
                    }
                }
            }
            return new SkinCustomizationOverlay(parentScreen);
        });
        this.updateSettings();
    }
    
    private void updateSettings() {
        final AppearanceConfig appearance = Laby.labyAPI().config().appearance();
        this.get(NamedScreen.MAIN_MENU).setEnabled(appearance.titleScreen().custom().get());
    }
    
    @Subscribe
    public void onSettingUpdate(final SettingUpdateEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        if (event.setting().getPath().equals("settings.appearance.customTitleScreen.enabled")) {
            this.updateSettings();
        }
    }
    
    @Subscribe
    public void onScreenOpen(final ScreenDisplayEvent event) {
        final ScreenInstance wrapper = event.getScreen();
        if (wrapper == null) {
            return;
        }
        final ScreenInstance screen = wrapper.mostInnerScreenInstance();
        final Activity activity = this.toOverlay(screen);
        if (activity == null) {
            return;
        }
        event.setScreen(activity);
    }
    
    @Override
    public Activity toOverlay(final ScreenInstance screen) {
        if (screen instanceof AbstractLayerActivity) {
            return (Activity)screen;
        }
        final Object raw = screen.mostInnerScreen();
        final String screenName = this.api.screenService().getScreenNameByClass(raw.getClass());
        if (screenName == null) {
            return null;
        }
        final RegisteredReplacement registered = this.getById(screenName);
        if (registered == null || !registered.isEnabled()) {
            return null;
        }
        return registered.getInitializer().create(screen);
    }
    
    @Override
    public Object toRawScreen(final Activity overlay) {
        for (final KeyValue<RegisteredReplacement> element : this.getElements()) {
            if (element.getValue().getClazz().equals(overlay.getClass())) {
                return this.api.screenService().createScreen(element.getKey());
            }
        }
        return null;
    }
}
