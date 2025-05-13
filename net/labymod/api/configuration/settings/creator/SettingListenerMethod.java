// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator;

import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.util.logging.Logging;
import java.lang.reflect.Method;
import net.labymod.api.configuration.settings.annotation.SettingListener;

record SettingListenerMethod(String target, SettingListener.EventType eventType, Method method) {
    private static final Logging LOGGER;
    
    public SettingListenerMethod(final SettingListener listener, final Method method) {
        this(listener.target(), listener.type(), method);
    }
    
    public void invoke(final Object instance, final Setting setting) {
        try {
            this.method.invoke(instance, setting);
        }
        catch (final Exception exception) {
            SettingListenerMethod.LOGGER.error("Failed to invoke setting listener method", exception);
        }
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
