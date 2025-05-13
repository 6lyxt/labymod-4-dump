// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen;

import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.Laby;

public interface ParentScreen extends Parent
{
    void displayScreen(final ScreenInstance p0);
    
    void displayScreenRaw(final Object p0);
    
    default void displayScreen(final String name) {
        final ScreenInstance screen = Laby.labyAPI().screenService().createScreen(name);
        if (screen != null) {
            this.displayScreen(screen);
        }
    }
    
    default void displayScreen(final GameScreen screen) {
        this.displayScreen(screen.getId());
    }
    
    default void displayScreen(final NamedScreen screen) {
        this.displayScreen((GameScreen)screen);
    }
    
    default void closeScreen() {
        this.displayScreen((ScreenInstance)null);
    }
    
    ScreenWrapper currentScreen();
    
    default boolean isScreenOpened() {
        return this.currentScreen() != null;
    }
    
    default Object getCurrentVersionedScreen() {
        return this.getCurrentVersionedScreen(false);
    }
    
    default Object getCurrentVersionedScreen(final boolean checkMetadata) {
        final ScreenWrapper wrapper = this.currentScreen();
        if (wrapper == null) {
            return null;
        }
        if (checkMetadata) {
            final boolean isPreviousScreen = wrapper.metadata().getBoolean("isPreviousScreen", true);
            if (!isPreviousScreen) {
                return null;
            }
        }
        return wrapper.getVersionedScreen();
    }
    
    LabyScreen currentLabyScreen();
    
    String getCurrentScreenName();
    
    Object mostInnerScreen();
    
    default boolean isScreenDisplayed(final String screenName) {
        return screenName.equals(this.getCurrentScreenName());
    }
    
    default boolean isScreenDisplayed(final GameScreen screen) {
        return screen.getId().equals(this.getCurrentScreenName());
    }
    
    default boolean isScreenDisplayed(final NamedScreen screen) {
        return this.isScreenDisplayed((GameScreen)screen);
    }
}
