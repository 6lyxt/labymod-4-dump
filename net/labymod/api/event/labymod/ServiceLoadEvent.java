// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod;

import net.labymod.api.util.CrossVersionServiceLoader;
import net.labymod.api.service.CustomServiceLoader;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.event.Event;

public class ServiceLoadEvent implements Event
{
    private final ClassLoader classLoader;
    private final State state;
    
    public ServiceLoadEvent(@NotNull final ClassLoader classLoader, @NotNull final State state) {
        this.classLoader = classLoader;
        this.state = state;
    }
    
    @NotNull
    public ClassLoader classLoader() {
        return this.classLoader;
    }
    
    @NotNull
    public State state() {
        return this.state;
    }
    
    public <T> CustomServiceLoader<T> load(final Class<T> serviceClass, final CustomServiceLoader.ServiceType serviceType) {
        return CustomServiceLoader.load(serviceClass, this.classLoader, serviceType);
    }
    
    @Deprecated
    public <T> CrossVersionServiceLoader<T> load(final Class<T> serviceClass) {
        return CrossVersionServiceLoader.load(serviceClass, this.classLoader);
    }
    
    public enum State
    {
        START, 
        ADDON_LOADED, 
        LISTENER_REGISTERED;
    }
}
