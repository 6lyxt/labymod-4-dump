// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.method;

import net.labymod.api.event.Event;
import java.util.Map;

public interface ListenerRegistry
{
    Map<Class<?>, SubscribeMethodList> getListeners();
    
    boolean hasListeners(final Class<?> p0);
    
    boolean invokeListeners(final ClassLoader p0, final Class<?> p1, final Event p2);
    
    default void invokeMethod(final SubscribeMethod method, final Event event) {
        this.invokeMethod(method, method.getEventType(), event);
    }
    
    void invokeMethod(final SubscribeMethod p0, final Class<?> p1, final Event p2);
    
    void register(final SubscribeMethod p0);
    
    void unregister(final SubscribeMethod p0);
    
    void merge(final ListenerRegistry p0);
}
