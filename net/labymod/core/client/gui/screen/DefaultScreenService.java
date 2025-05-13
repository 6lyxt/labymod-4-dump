// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen;

import java.util.Iterator;
import net.labymod.api.client.gui.screen.ScreenInstance;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.function.Function;
import net.labymod.api.client.gui.screen.ScreenFactory;
import net.labymod.api.client.gui.screen.ScreenName;
import java.util.Map;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.ScreenService;

@Singleton
@Implements(ScreenService.class)
public class DefaultScreenService implements ScreenService
{
    private final Map<String, ScreenName> screens;
    private final Map<String, ScreenFactory> factories;
    private Function<Object, Boolean> inventoryCondition;
    
    @Inject
    public DefaultScreenService() {
        this.inventoryCondition = (screen -> {
            throw new UnsupportedOperationException();
        });
        this.screens = new HashMap<String, ScreenName>();
        this.factories = new HashMap<String, ScreenFactory>();
    }
    
    @Override
    public void register(final String name, final ScreenName screenName) {
        this.screens.putIfAbsent(name, screenName);
    }
    
    @Override
    public void registerFactory(final String name, final ScreenFactory factory) {
        this.factories.put(name, factory);
    }
    
    @Override
    public ScreenInstance createScreen(final String name) {
        final ScreenFactory factory = this.factories.get(name);
        return (factory != null) ? factory.create() : null;
    }
    
    @Override
    public String getScreenNameByClass(final Class<?> screenClass) {
        for (final Map.Entry<String, ScreenName> entry : this.screens.entrySet()) {
            if (entry.getValue().getIdentifier().equals(screenClass)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    @Override
    public boolean isInventory(final Object screen) {
        return this.inventoryCondition.apply(screen);
    }
    
    @Override
    public void setInventoryCondition(final Function<Object, Boolean> inventoryCondition) {
        this.inventoryCondition = inventoryCondition;
    }
}
