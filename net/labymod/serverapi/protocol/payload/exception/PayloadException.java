// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.serverapi.protocol.payload.exception;

@Deprecated(forRemoval = true, since = "4.2.24")
public class PayloadException extends RuntimeException
{
    public PayloadException() {
    }
    
    public PayloadException(final String message) {
        super(message);
    }
    
    public PayloadException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public PayloadException(final Throwable cause) {
        super(cause);
    }
    
    public PayloadException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
