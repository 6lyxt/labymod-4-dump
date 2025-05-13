// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.serverapi.protocol.packet.protocol.execption;

@Deprecated(forRemoval = true, since = "4.2.24")
public class ProtocolException extends RuntimeException
{
    public ProtocolException(final String message) {
        super(message);
    }
    
    public ProtocolException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
