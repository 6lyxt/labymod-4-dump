// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.annotation.processing.exception;

public class ProcessingException extends RuntimeException
{
    public ProcessingException() {
    }
    
    public ProcessingException(final String message) {
        super(message);
    }
    
    public ProcessingException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public ProcessingException(final Throwable cause) {
        super(cause);
    }
}
