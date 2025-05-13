// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.localization;

public class I18nException extends RuntimeException
{
    public I18nException() {
    }
    
    public I18nException(final String message) {
        super(message);
    }
    
    public I18nException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public I18nException(final Throwable cause) {
        super(cause);
    }
    
    public I18nException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
