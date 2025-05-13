// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event;

public interface Event
{
    default void interruptUpcomingListeners() {
        throw EventInterruptException.INSTANCE;
    }
}
