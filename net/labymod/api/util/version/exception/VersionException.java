// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.version.exception;

public class VersionException extends RuntimeException
{
    public VersionException(final String message) {
        super(message);
    }
    
    public VersionException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public VersionException(final Throwable cause) {
        super(cause);
    }
}
