// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.addon.exception;

public class AddonInvalidException extends IllegalStateException
{
    public AddonInvalidException() {
    }
    
    public AddonInvalidException(final String message) {
        super(message);
    }
    
    public AddonInvalidException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
