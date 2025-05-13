// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.game;

import java.util.Collection;
import java.util.Iterator;
import net.labymod.api.Laby;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface GameScreenRegistry
{
    default GameScreen from(final Object screen) {
        final GameScreenRegistry registry = Laby.references().gameScreenRegistry();
        for (final GameScreen gameScreen : registry.getScreens()) {
            if (gameScreen.isScreen(screen)) {
                return gameScreen;
            }
        }
        return null;
    }
    
    void register(final GameScreen p0);
    
    Collection<GameScreen> getScreens();
}
