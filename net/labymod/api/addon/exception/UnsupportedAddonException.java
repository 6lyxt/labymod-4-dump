// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.addon.exception;

public class UnsupportedAddonException extends RuntimeException
{
    public UnsupportedAddonException() {
    }
    
    public UnsupportedAddonException(final String message) {
        super(message);
    }
    
    public UnsupportedAddonException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
