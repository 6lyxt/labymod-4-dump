// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.exception;

public final class ListenerResolveException extends RuntimeException
{
    public ListenerResolveException() {
    }
    
    public ListenerResolveException(final String message) {
        super(message);
    }
    
    public ListenerResolveException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public ListenerResolveException(final Throwable cause) {
        super(cause);
    }
    
    public ListenerResolveException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
