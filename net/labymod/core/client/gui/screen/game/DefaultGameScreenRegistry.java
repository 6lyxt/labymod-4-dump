// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.game;

import java.util.Collection;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.game.GameScreen;
import java.util.List;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.game.GameScreenRegistry;

@Singleton
@Implements(GameScreenRegistry.class)
public class DefaultGameScreenRegistry implements GameScreenRegistry
{
    private static final Logging LOGGER;
    private final List<GameScreen> screens;
    
    public DefaultGameScreenRegistry() {
        this.screens = new ArrayList<GameScreen>();
    }
    
    @Override
    public void register(final GameScreen screen) {
        DefaultGameScreenRegistry.LOGGER.debug("Registering game screen: {}", screen.getId());
        this.screens.add(screen);
    }
    
    @Override
    public Collection<GameScreen> getScreens() {
        return this.screens;
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
