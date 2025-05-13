// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.web.request;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.Collections;
import java.io.IOException;
import java.io.OutputStream;
import net.labymod.api.util.function.ThrowableConsumer;
import java.net.URL;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.io.web.WebInputStream;
import com.google.gson.Gson;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public abstract class WebResolver
{
    @Deprecated(forRemoval = true, since = "4.2.0")
    public static final Gson GSON;
    private static final WebResolver INSTANCE;
    
    protected WebResolver() {
    }
    
    public static <T> void resolve(final Request<T> request, final Callback<T> callback) {
        WebResolver.INSTANCE.resolveConnection((Request<Object>)request, (Callback<Object>)callback);
    }
    
    public static <T> Response<T> resolveSync(final Request<T> request) {
        return (Response<T>)WebResolver.INSTANCE.resolveConnection((Request<Object>)request);
    }
    
    public abstract <T> void resolveConnection(final Request<T> p0, final Callback<T> p1);
    
    public abstract <T> Response<T> resolveConnection(final Request<T> p0);
    
    protected <T> WebRequest<T> webRequest(final Request<T> request) {
        if (request instanceof final AbstractRequest abstractRequest) {
            return new WebRequest<T>(abstractRequest);
        }
        throw new IllegalArgumentException("Request is not an instance of AbstractRequest<?,?>");
    }
    
    protected <T> T handle(final WebRequest<T> request, final Response<T> response, final WebInputStream inputStream) throws Exception {
        return request.request.handle(response, inputStream);
    }
    
    protected <T> T onException(final WebRequest<T> request, final Exception e) throws Exception {
        return request.request.onException(e);
    }
    
    static {
        GSON = GsonUtil.DEFAULT_GSON;
        INSTANCE = Laby.references().webResolver();
    }
    
    public static class WebRequest<T>
    {
        private final AbstractRequest<T, ?> request;
        private final Map<String, String> headers;
        private final Request.Method method;
        private final URL url;
        private final String userAgent;
        private final String contentType;
        private final String authorization;
        private final ThrowableConsumer<OutputStream, IOException> write;
        private final int readTimeout;
        private final int connectTimeout;
        private final boolean async;
        private final boolean read;
        private final boolean handleErrorStream;
        private String urlString;
        
        protected WebRequest(final AbstractRequest<T, ?> request) {
            this.request = request;
            this.headers = Collections.unmodifiableMap((Map<? extends String, ? extends String>)request.headers);
            this.method = request.method;
            this.url = request.url;
            this.userAgent = request.userAgent;
            this.contentType = request.contentType;
            this.authorization = request.authorization;
            this.write = request.write;
            this.readTimeout = request.readTimeout;
            this.connectTimeout = request.connectTimeout;
            this.async = request.async;
            this.read = request.read;
            this.handleErrorStream = request.handleErrorStream;
        }
        
        public AbstractRequest<T, ?> request() {
            return this.request;
        }
        
        @NotNull
        public URL getUrl() {
            return this.url;
        }
        
        @NotNull
        public String getUrlString() {
            if (this.urlString == null) {
                this.urlString = this.url.toString();
            }
            return this.urlString;
        }
        
        @Nullable
        public String getUserAgent() {
            return this.userAgent;
        }
        
        @Nullable
        public String getContentType() {
            return this.contentType;
        }
        
        @Nullable
        public String getAuthorization() {
            return this.authorization;
        }
        
        public ThrowableConsumer<OutputStream, IOException> getOutput() {
            return this.write;
        }
        
        @NotNull
        public Map<String, String> getHeaders() {
            return this.headers;
        }
        
        @NotNull
        public Request.Method getMethod() {
            return this.method;
        }
        
        public int getReadTimeout() {
            return this.readTimeout;
        }
        
        public int getConnectTimeout() {
            return this.connectTimeout;
        }
        
        public boolean isAsync() {
            return this.async;
        }
        
        public boolean doRead() {
            return this.read;
        }
        
        public boolean doWrite() {
            return this.write != null;
        }
        
        public boolean doHandleErrorStream() {
            return this.handleErrorStream;
        }
    }
}
