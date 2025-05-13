// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.addon.exception;

public class AddonInitException extends RuntimeException
{
    public AddonInitException() {
    }
    
    public AddonInitException(final String message) {
        super(message);
    }
    
    public AddonInitException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
