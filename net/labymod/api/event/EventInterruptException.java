// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event;

public class EventInterruptException extends RuntimeException
{
    public static final EventInterruptException INSTANCE;
    
    private EventInterruptException() {
        super("Event invocation has been interrupted by Event.interruptUpcomingListeners();");
    }
    
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
    
    static {
        INSTANCE = new EventInterruptException();
    }
}
