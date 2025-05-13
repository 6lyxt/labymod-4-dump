// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ScreenService
{
    default void registerMinecraft(final String name, final Class<?> screenClass) {
        this.register(name, ScreenName.minecraft(screenClass));
    }
    
    default void registerForge(final String name, final Class<?> screenClass) {
        this.register(name, ScreenName.forge(screenClass));
    }
    
    default void registerFabric(final String name, final Class<?> screenClass) {
        this.register(name, ScreenName.fabric(screenClass));
    }
    
    void register(final String p0, final ScreenName p1);
    
    default void register(final GameScreen screen, final ScreenName screenName) {
        this.register(screen.getId(), screenName);
    }
    
    void registerFactory(final String p0, final ScreenFactory p1);
    
    default void registerFactory(final GameScreen screen, final ScreenFactory factory) {
        this.registerFactory(screen.getId(), factory);
    }
    
    ScreenInstance createScreen(final String p0);
    
    default ScreenInstance createScreen(final GameScreen screen) {
        return this.createScreen(screen.getId());
    }
    
    String getScreenNameByClass(final Class<?> p0);
    
    boolean isInventory(final Object p0);
    
    void setInventoryCondition(final Function<Object, Boolean> p0);
}
