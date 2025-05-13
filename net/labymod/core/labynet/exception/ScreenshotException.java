// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet.exception;

public class ScreenshotException extends Exception
{
    private int statusCode;
    
    public ScreenshotException(final String message) {
        super(message);
        this.statusCode = -1;
    }
    
    public ScreenshotException(final String message, final int statusCode) {
        super(message);
        this.statusCode = -1;
        this.statusCode = statusCode;
    }
    
    public int getStatusCode() {
        return this.statusCode;
    }
}
