// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.addon.exception;

public class AddonLoadException extends RuntimeException
{
    public AddonLoadException() {
    }
    
    public AddonLoadException(final String message) {
        super(message);
    }
    
    public AddonLoadException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
