// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.web.exception;

import java.io.IOException;

public class WebRequestException extends IOException
{
    private final String url;
    private final int responseCode;
    private final long retryAfter;
    
    public WebRequestException(final String url, final int responseCode) {
        super("Response Code: " + responseCode);
        this.url = url;
        this.responseCode = responseCode;
        this.retryAfter = -1L;
    }
    
    public WebRequestException(final String url, final int responseCode, final long retryAfter) {
        super("Response Code: " + responseCode);
        this.url = url;
        this.responseCode = responseCode;
        this.retryAfter = retryAfter;
    }
    
    public WebRequestException(final Exception exception) {
        super(exception.getMessage());
        this.url = "Unknown";
        this.responseCode = -1;
        this.retryAfter = -1L;
        this.initCause(exception);
    }
    
    public WebRequestException(final Exception exception, final long retryAfter) {
        super(exception.getMessage());
        this.url = "Unknown";
        this.responseCode = -1;
        this.retryAfter = retryAfter;
        this.initCause(exception);
    }
    
    public WebRequestException(final String url, final Exception exception) {
        super(exception.getMessage());
        this.url = url;
        this.responseCode = -1;
        this.retryAfter = -1L;
        this.initCause(exception);
    }
    
    public WebRequestException(final String url, final Exception exception, final long retryAfter) {
        super(exception.getMessage());
        this.url = url;
        this.responseCode = -1;
        this.retryAfter = retryAfter;
        this.initCause(exception);
    }
    
    public WebRequestException(final String url, final int responseCode, final String message, final Exception cause) {
        super(message, cause);
        this.url = url;
        this.responseCode = responseCode;
        this.retryAfter = -1L;
    }
    
    public WebRequestException(final String url, final int responseCode, final String message, final Exception cause, final long retryAfter) {
        super(message, cause);
        this.url = url;
        this.responseCode = responseCode;
        this.retryAfter = retryAfter;
    }
    
    public WebRequestException(final String url, final int responseCode, final String message) {
        super(message);
        this.url = url;
        this.responseCode = responseCode;
        this.retryAfter = -1L;
    }
    
    public WebRequestException(final String url, final int responseCode, final String message, final long retryAfter) {
        super(message);
        this.url = url;
        this.responseCode = responseCode;
        this.retryAfter = retryAfter;
    }
    
    public int getResponseCode() {
        return this.responseCode;
    }
    
    public long getRetryAfter() {
        return this.retryAfter;
    }
    
    @Override
    public String getMessage() {
        return "(" + this.url + ")" + super.getMessage();
    }
}
