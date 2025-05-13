// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.web.request;

import java.util.HashMap;
import java.util.Collections;
import java.util.Objects;
import net.labymod.api.util.io.web.exception.WebRequestException;
import org.jetbrains.annotations.NotNull;
import java.util.Map;
import net.labymod.api.util.io.web.result.Result;

public class Response<T> extends Result<T>
{
    private static final Map<String, String> DEFAULT_HEADERS;
    private final WebResolver.WebRequest<T> request;
    private int responseCode;
    private Map<String, String> headers;
    
    protected Response(@NotNull final WebResolver.WebRequest<T> request) {
        super(null, null);
        this.headers = Response.DEFAULT_HEADERS;
        Objects.requireNonNull(request, "Request cannot be null");
        this.request = request;
        this.responseCode = -1;
    }
    
    public static <T> Response<T> of(@NotNull final WebResolver.WebRequest<T> request) {
        return new Response<T>(request);
    }
    
    @NotNull
    public WebResolver.WebRequest<T> connectionRequest() {
        return this.request;
    }
    
    public int getStatusCode() {
        return this.responseCode;
    }
    
    @NotNull
    public Map<String, String> getHeaders() {
        return this.headers;
    }
    
    public void setResponseCode(final int responseCode) {
        if (!this.isEmpty()) {
            throw new IllegalStateException("Response code cannot be set if the result is not empty");
        }
        this.responseCode = responseCode;
    }
    
    public void setResponseHeaders(@NotNull final Map<String, String> headers) {
        Objects.requireNonNull(headers, "Headers cannot be null");
        if (!this.isEmpty()) {
            throw new IllegalStateException("Response headers cannot be set if the result is not empty");
        }
        this.headers = Collections.unmodifiableMap((Map<? extends String, ? extends String>)headers);
    }
    
    @Deprecated
    @NotNull
    public Map<String, String> getResponseHeaders() {
        return this.getHeaders();
    }
    
    @Deprecated
    public int getResponseCode() {
        return this.getStatusCode();
    }
    
    static {
        DEFAULT_HEADERS = Collections.unmodifiableMap((Map<? extends String, ? extends String>)new HashMap<String, String>());
    }
}
