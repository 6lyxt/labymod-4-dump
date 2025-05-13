// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.navigation;

import net.labymod.api.service.Registry;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.LabyScreen;
import java.util.Iterator;
import net.labymod.core.client.gui.screen.activity.activities.NavigationActivity;
import net.labymod.api.client.gui.screen.LabyScreenAccessor;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.client.gui.screen.activity.activities.menu.MainMenuActivity;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.event.client.world.WorldLoadEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.core.client.gui.navigation.elements.VersionNavigationElement;
import net.labymod.core.client.gui.navigation.elements.AccountNavigationElement;
import net.labymod.core.client.gui.navigation.elements.OptionsNavigationElement;
import net.labymod.core.client.gui.navigation.elements.LabyModNavigationElement;
import net.labymod.core.client.gui.navigation.elements.LabyConnectNavigationElement;
import net.labymod.core.client.gui.navigation.elements.MultiplayerNavigationElement;
import net.labymod.core.client.gui.navigation.elements.SingleplayerNavigationElement;
import net.labymod.core.client.gui.navigation.elements.MenuNavigationElement;
import net.labymod.core.event.client.lifecycle.GameInitializeEvent;
import javax.inject.Inject;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.gui.navigation.elements.ScreenBaseNavigationElement;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.navigation.NavigationRegistry;
import net.labymod.api.client.gui.navigation.NavigationElement;
import net.labymod.api.service.DefaultRegistry;

@Singleton
@Implements(NavigationRegistry.class)
public class DefaultNavigationRegistry extends DefaultRegistry<NavigationElement<?>> implements NavigationRegistry
{
    private ScreenBaseNavigationElement<?> activeScreenElement;
    private long timeLastActiveChanged;
    private int previousActiveIndex;
    private int activeIndex;
    private long timePassedSinceActiveChanged;
    
    @Inject
    public DefaultNavigationRegistry(final EventBus eventBus) {
        this.timeLastActiveChanged = 0L;
        this.previousActiveIndex = 0;
        this.activeIndex = 0;
        this.timePassedSinceActiveChanged = 0L;
        eventBus.registerListener(this);
    }
    
    @Subscribe
    public void onGameInitialize(final GameInitializeEvent event) {
        if (event.getLifecycle() != GameInitializeEvent.Lifecycle.POST_GAME_STARTED) {
            return;
        }
        try {
            ((DefaultRegistry<MenuNavigationElement>)this).register("menu", new MenuNavigationElement());
            ((DefaultRegistry<SingleplayerNavigationElement>)this).register("singleplayer", new SingleplayerNavigationElement());
            ((DefaultRegistry<MultiplayerNavigationElement>)this).register("multiplayer", new MultiplayerNavigationElement());
            ((DefaultRegistry<LabyConnectNavigationElement>)this).register("labyconnect", new LabyConnectNavigationElement());
            ((DefaultRegistry<LabyModNavigationElement>)this).register("labymod", new LabyModNavigationElement());
            ((DefaultRegistry<OptionsNavigationElement>)this).register("options", new OptionsNavigationElement());
            ((DefaultRegistry<AccountNavigationElement>)this).register("account", new AccountNavigationElement());
            ((DefaultRegistry<VersionNavigationElement>)this).register("version", new VersionNavigationElement());
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    @Subscribe
    public void onWorldLoad(final WorldLoadEvent event) {
        this.setIngameMenuActive();
    }
    
    @Subscribe(-64)
    public void onScreenOpenPre(final ScreenDisplayEvent event) {
        final ScreenInstance screen = event.getScreen();
        if (screen == null) {
            return;
        }
        if (NamedScreen.INGAME_MENU.isScreen(screen) && event.getPreviousScreen() == null && this.activeScreenElement != null && !(this.activeScreenElement instanceof MenuNavigationElement)) {
            if (!Laby.labyAPI().config().appearance().navigation().rememberLastTab().get()) {
                this.setIngameMenuActive();
            }
            event.setScreen(this.activeScreenElement.getScreen());
        }
        final LabyAPI labyAPI = Laby.labyAPI();
        final boolean inGame = labyAPI.minecraft().isIngame();
        final boolean isMainMenu = NamedScreen.MAIN_MENU.isScreen(screen) || screen.mostInnerScreen() instanceof MainMenuActivity;
        final boolean isPauseMenu = NamedScreen.INGAME_MENU.isScreen(screen);
        if (inGame) {
            if (!isMainMenu) {
                return;
            }
        }
        else if (!isPauseMenu) {
            return;
        }
        event.setScreen(null);
    }
    
    @Subscribe(64)
    public void onScreenOpenPost(final ScreenDisplayEvent event) {
        final ScreenInstance wrapper = event.getScreen();
        if (wrapper == null) {
            return;
        }
        final ScreenInstance instance = wrapper.mostInnerScreenInstance();
        final Object raw = instance.mostInnerScreen();
        for (final NavigationElement<?> element : this.values()) {
            if (element instanceof final ScreenBaseNavigationElement screenBaseNavigationElement) {
                final ScreenBaseNavigationElement<?> screenElement = screenBaseNavigationElement;
                if (!screenElement.isScreen(raw)) {
                    continue;
                }
                screenElement.onUpdate(wrapper.unwrap());
                this.activeScreenElement = screenElement;
                final ScreenInstance prev = event.getPreviousScreen();
                if (prev instanceof final ScreenWrapper screenWrapper) {
                    final Object versioned = screenWrapper.getVersionedScreen();
                    if (versioned instanceof final LabyScreenAccessor labyScreenAccessor) {
                        final LabyScreen labyScreen = labyScreenAccessor.screen();
                        if (labyScreen instanceof final NavigationActivity activity) {
                            activity.displayScreen(screenElement);
                            event.setScreen(activity);
                            return;
                        }
                    }
                }
                event.setScreen(new NavigationActivity(screenElement));
            }
        }
    }
    
    @Override
    public NavigationElement<?> getActiveScreenElement() {
        return this.activeScreenElement;
    }
    
    @Override
    public void setIngameMenuActive() {
        this.setActiveElement(((Registry<ScreenBaseNavigationElement<?>>)this).getById("menu"));
    }
    
    @Override
    public ScreenInstance createNavigation(final ScreenBaseNavigationElement<?> element) {
        return new NavigationActivity(element);
    }
    
    public void setActiveElement(final ScreenBaseNavigationElement<?> element) {
        if (this.activeScreenElement == element) {
            return;
        }
        this.activeScreenElement = element;
        this.timeLastActiveChanged = TimeUtil.getMillis();
        this.timePassedSinceActiveChanged = 0L;
        this.previousActiveIndex = this.activeIndex;
        this.activeIndex = ((Registry<ScreenBaseNavigationElement<?>>)this).indexOf(element);
    }
    
    public int getActiveIndex() {
        return this.activeIndex;
    }
    
    public int getPreviousActiveIndex() {
        return this.previousActiveIndex;
    }
    
    public long getTimeLastActiveChanged() {
        return this.timeLastActiveChanged;
    }
    
    public long getTimePassedSinceActiveChanged() {
        return this.timePassedSinceActiveChanged;
    }
    
    public void setTimePassedSinceActiveChanged(final long timePassedSinceActiveChanged) {
        this.timePassedSinceActiveChanged = timePassedSinceActiveChanged;
    }
}
