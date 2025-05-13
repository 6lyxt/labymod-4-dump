// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.settings;

import net.labymod.api.configuration.settings.switchable.StringSwitchableHandler;
import net.labymod.api.configuration.settings.switchable.BooleanSwitchableHandler;
import java.util.HashMap;
import net.labymod.api.configuration.settings.SwitchableHandler;
import java.util.Map;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.configuration.settings.SwitchableHandlerRegistry;

@Singleton
@Implements(SwitchableHandlerRegistry.class)
public class DefaultSwitchableHandlerRegistry implements SwitchableHandlerRegistry
{
    private final Map<Class<? extends SwitchableHandler>, SwitchableHandler> handlerMap;
    
    public DefaultSwitchableHandlerRegistry() {
        this.handlerMap = new HashMap<Class<? extends SwitchableHandler>, SwitchableHandler>();
        this.registerHandler(new BooleanSwitchableHandler());
        this.registerHandler(new StringSwitchableHandler());
    }
    
    @Override
    public void registerHandler(final SwitchableHandler handler) {
        this.handlerMap.put(handler.getClass(), handler);
    }
    
    @Override
    public SwitchableHandler getHandler(final Class<? extends SwitchableHandler> handlerClass) {
        return this.handlerMap.get(handlerClass);
    }
}
