// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.serverapi.protocol.payload.exception;

@Deprecated(forRemoval = true, since = "4.2.24")
public class PayloadReaderException extends PayloadException
{
    public PayloadReaderException(final String message) {
        super(message);
    }
    
    public PayloadReaderException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
