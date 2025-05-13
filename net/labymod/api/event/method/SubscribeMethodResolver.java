// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.method;

import net.labymod.api.event.Event;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface SubscribeMethodResolver
{
    ListenerRegistry resolve(@NotNull final Object p0);
    
    SubscribeMethod createCustom(final byte p0, final Class<?> p1, @NotNull final Consumer<Event> p2);
    
    SubscribeMethod createCustom(final ClassLoader p0, final byte p1, final Class<?> p2, @NotNull final Consumer<Event> p3);
}
