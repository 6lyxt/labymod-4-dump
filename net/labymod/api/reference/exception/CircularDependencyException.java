// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.reference.exception;

public class CircularDependencyException extends RuntimeException
{
    public CircularDependencyException() {
    }
    
    public CircularDependencyException(final String message) {
        super(message);
    }
    
    public CircularDependencyException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public CircularDependencyException(final Throwable cause) {
        super(cause);
    }
    
    public CircularDependencyException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
