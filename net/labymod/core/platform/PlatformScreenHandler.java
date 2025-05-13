// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.platform;

import net.labymod.api.client.gui.screen.ScreenInstance;
import java.util.function.Function;
import java.util.function.Supplier;
import net.labymod.api.client.gui.screen.ScreenFactory;
import net.labymod.api.client.gui.screen.ScreenName;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenService;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.game.GameScreenRegistry;

public abstract class PlatformScreenHandler<T>
{
    private static final GameScreenRegistry GAME_SCREEN_REGISTRY;
    private static final ScreenWrapper.Factory SCREEN_WRAPPER_FACTORY;
    protected final ScreenService screenService;
    
    public PlatformScreenHandler() {
        this.screenService = Laby.references().screenService();
    }
    
    protected void register(final GameScreen screen, final Class<?> screenClass) {
        this.screenService.register(screen, ScreenName.minecraft(screenClass));
        PlatformScreenHandler.GAME_SCREEN_REGISTRY.register(screen);
    }
    
    protected void registerFactory(final GameScreen screen, final ScreenFactory factory) {
        this.screenService.registerFactory(screen.getId(), factory);
    }
    
    protected void registerFactory(final GameScreen screen, final Supplier<T> screenFactory) {
        this.registerFactory(screen, () -> PlatformScreenHandler.SCREEN_WRAPPER_FACTORY.create(screenFactory.get()));
    }
    
    protected abstract void registerFactory(final GameScreen p0, final Function<T, T> p1);
    
    public abstract void onInitialize();
    
    public abstract boolean isInventoryScreen(final Object p0);
    
    public ScreenService getScreenService() {
        return this.screenService;
    }
    
    static {
        GAME_SCREEN_REGISTRY = Laby.references().gameScreenRegistry();
        SCREEN_WRAPPER_FACTORY = Laby.references().screenWrapperFactory();
    }
}
