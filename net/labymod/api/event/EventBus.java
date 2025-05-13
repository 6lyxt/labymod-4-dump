// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event;

import net.labymod.api.event.method.ListenerRegistry;
import net.labymod.api.client.Minecraft;
import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface EventBus
{
    default <T extends Event> void fireNextTick(@NotNull final T event) {
        this.fireNextTick(event, null);
    }
    
    default <T extends Event> void fireNextTick(@NotNull final T event, @Nullable final Consumer<T> eventConsumer) {
        final Minecraft minecraft = Laby.labyAPI().minecraft();
        if (minecraft == null) {
            this.fire(event);
            if (eventConsumer != null) {
                eventConsumer.accept(event);
            }
            return;
        }
        minecraft.executeNextTick(() -> {
            this.fire(event);
            if (eventConsumer != null) {
                eventConsumer.accept(event);
            }
        });
    }
    
     <T extends Event> void fire(final T p0);
    
     <T extends Event> void fire(final ClassLoader p0, final T p1);
    
    void registerListener(final Object p0);
    
    void unregisterListener(final Object p0);
    
    void unregisterListeners(final Object p0);
    
    boolean isListenerRegistered(final Object p0);
    
    boolean hasListeners(final Class<? extends Event> p0);
    
    ListenerRegistry registry();
}
