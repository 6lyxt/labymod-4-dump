// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration;

import net.labymod.api.event.EventBus;
import net.labymod.core.configuration.loader.serial.JsonConfigLoaderInitializeListener;
import net.labymod.api.Laby;

public final class ConfigurationEventListenerRegistry
{
    private static boolean registered;
    
    private ConfigurationEventListenerRegistry() {
    }
    
    public static void register() {
        if (ConfigurationEventListenerRegistry.registered) {
            return;
        }
        ConfigurationEventListenerRegistry.registered = true;
        final EventBus eventBus = Laby.references().eventBus();
        eventBus.registerListener(new JsonConfigLoaderInitializeListener());
        eventBus.registerListener(new ConfigurationVersionUpdateListener());
    }
}
