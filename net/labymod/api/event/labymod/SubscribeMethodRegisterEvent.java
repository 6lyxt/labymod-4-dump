// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.event.method.SubscribeMethod;
import net.labymod.api.event.method.ListenerRegistry;
import net.labymod.api.event.Event;

public class SubscribeMethodRegisterEvent implements Event
{
    private final ListenerRegistry registry;
    private final SubscribeMethod method;
    
    public SubscribeMethodRegisterEvent(@NotNull final ListenerRegistry registry, @NotNull final SubscribeMethod method) {
        this.registry = registry;
        this.method = method;
    }
    
    @NotNull
    public ListenerRegistry registry() {
        return this.registry;
    }
    
    @NotNull
    public SubscribeMethod method() {
        return this.method;
    }
}
