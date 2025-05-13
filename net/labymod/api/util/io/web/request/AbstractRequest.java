// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.web.request;

import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.GsonUtil;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;
import net.labymod.api.util.io.IOUtil;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.jetbrains.annotations.Nullable;
import java.net.MalformedURLException;
import java.util.Locale;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.io.IOException;
import java.io.OutputStream;
import net.labymod.api.util.function.ThrowableConsumer;
import java.net.URL;
import java.util.Map;

public abstract class AbstractRequest<T, R extends AbstractRequest<T, ?>> implements Request<T>
{
    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.88 Safari/537.36";
    private static final int DEFAULT_READ_TIMEOUT = 5000;
    private static final int DEFAULT_CONNECT_TIMEOUT = 2000;
    protected Map<String, String> headers;
    protected Method method;
    protected URL url;
    protected String authorization;
    protected String userAgent;
    protected String contentType;
    protected ThrowableConsumer<OutputStream, IOException> write;
    protected int readTimeout;
    protected int connectTimeout;
    protected boolean async;
    protected boolean read;
    protected boolean handleErrorStream;
    
    public AbstractRequest() {
        this.headers = new HashMap<String, String>();
        this.method = Method.GET;
        this.userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.88 Safari/537.36";
        this.readTimeout = 5000;
        this.connectTimeout = 2000;
        this.async = false;
        this.read = true;
        this.handleErrorStream = false;
    }
    
    @Override
    public R url(@NotNull String url, final Object... arguments) {
        Objects.requireNonNull(url, "URL cannot be null");
        if (arguments.length > 0) {
            url = String.format(Locale.ROOT, url, arguments);
        }
        try {
            return this.url(new URL(url));
        }
        catch (final MalformedURLException e) {
            throw new IllegalArgumentException("Invalid url: " + url);
        }
    }
    
    @Override
    public R url(@NotNull final URL url) {
        Objects.requireNonNull(url, "URL cannot be null");
        this.url = url;
        return (R)this;
    }
    
    @Override
    public R authorization(@NotNull final String authorizationType, @NotNull final String authorization) {
        Objects.requireNonNull(authorizationType, "Authorization type cannot be null");
        Objects.requireNonNull(authorization, "Authorization cannot be null");
        this.authorization = authorizationType + " " + authorization;
        return (R)this;
    }
    
    @Override
    public R userAgent(@Nullable final String userAgent) {
        this.userAgent = userAgent;
        return (R)this;
    }
    
    @Override
    public R contentType(@Nullable final String contentType) {
        this.contentType = contentType;
        return (R)this;
    }
    
    @Override
    public R readTimeout(final int readTimeout) {
        this.readTimeout = readTimeout;
        return (R)this;
    }
    
    @Override
    public R connectTimeout(final int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return (R)this;
    }
    
    @Override
    public R addHeader(final String key, final Object value) {
        this.headers.put(key, value.toString());
        return (R)this;
    }
    
    @Override
    public R async() {
        return this.async(true);
    }
    
    @Override
    public R async(final boolean value) {
        this.async = value;
        return (R)this;
    }
    
    @Override
    public R handleErrorStream() {
        return this.handleErrorStream(true);
    }
    
    @Override
    public R handleErrorStream(final boolean value) {
        this.handleErrorStream = value;
        return (R)this;
    }
    
    @Override
    public R read(final boolean read) {
        this.read = read;
        return (R)this;
    }
    
    @Override
    public R write(final Object write) {
        this.write = (outputStream -> {
            if (write instanceof final String s) {
                outputStream.write(s.getBytes(StandardCharsets.UTF_8));
            }
            else if (write instanceof final byte[]array) {
                outputStream.write(array);
            }
            else if (write instanceof final InputStream inputStream) {
                IOUtil.writeBytes(inputStream, outputStream);
            }
            else {
                throw new IllegalArgumentException("Unsupported write type: " + write.getClass().getName());
            }
            return;
        });
        return (R)this;
    }
    
    @Override
    public R body(final Map<String, String> body) {
        final StringBuilder builder = new StringBuilder();
        for (final Map.Entry<String, String> entry : body.entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        this.write((Object)builder.toString());
        return (R)this;
    }
    
    @Override
    public R form(final FormData... formData) {
        if (formData.length == 0) {
            return (R)this;
        }
        return this.form(Arrays.asList(formData));
    }
    
    @Override
    public R form(final List<FormData> formData) {
        this.contentType = "multipart/form-data; boundary=" + FormData.BOUNDARY;
        long contentLength = 0L;
        for (final FormData data : formData) {
            contentLength += FormData.BOUNDARY_BYTES.length;
            contentLength += data.getContentDispositionHeader().getBytes(StandardCharsets.UTF_8).length;
            contentLength += FormData.NEW_LINE_BYTES.length;
            if (data.getContentType() != null) {
                contentLength += data.getContentTypeHeader().getBytes(StandardCharsets.UTF_8).length;
                contentLength += FormData.NEW_LINE_BYTES.length;
            }
            contentLength += FormData.NEW_LINE_BYTES.length;
            contentLength += data.getLength();
            contentLength += FormData.NEW_LINE_BYTES.length;
        }
        contentLength += FormData.BOUNDARY_END_BYTES.length;
        this.addHeader("Content-Length", (Object)contentLength);
        this.write = (outputStream -> {
            formData.iterator();
            final Iterator iterator2;
            while (iterator2.hasNext()) {
                final FormData data2 = iterator2.next();
                outputStream.write(FormData.BOUNDARY_BYTES);
                outputStream.write(data2.getContentDispositionHeader().getBytes(StandardCharsets.UTF_8));
                outputStream.write(FormData.NEW_LINE_BYTES);
                if (data2.getContentType() != null) {
                    outputStream.write(data2.getContentTypeHeader().getBytes(StandardCharsets.UTF_8));
                    outputStream.write(FormData.NEW_LINE_BYTES);
                }
                outputStream.write(FormData.NEW_LINE_BYTES);
                IOUtil.writeBytes(data2.getValue(), outputStream);
                outputStream.write(FormData.NEW_LINE_BYTES);
            }
            outputStream.write(FormData.BOUNDARY_END_BYTES);
            return;
        });
        return (R)this;
    }
    
    @Override
    public R json(final Object body) {
        if (this.contentType == null) {
            this.contentType = "application/json";
        }
        this.write((Object)GsonUtil.DEFAULT_GSON.toJson(body));
        return (R)this;
    }
    
    @Override
    public R method(@NotNull final Method method) {
        Objects.requireNonNull(method, "Method cannot be null");
        this.method = method;
        return (R)this;
    }
    
    @Override
    public void execute(final Callback<T> callback) {
        WebResolver.resolve(this, callback);
    }
    
    @Override
    public Response<T> executeSync() {
        return WebResolver.resolveSync((Request<T>)this);
    }
    
    @Override
    public R copy() {
        final R request = this.prepareCopy();
        request.headers = new HashMap<String, String>(this.headers);
        request.method = this.method;
        request.url = this.url;
        request.authorization = this.authorization;
        request.userAgent = this.userAgent;
        request.contentType = this.contentType;
        request.write = this.write;
        request.readTimeout = this.readTimeout;
        request.connectTimeout = this.connectTimeout;
        request.async = this.async;
        request.handleErrorStream = this.handleErrorStream;
        request.read = this.read;
        return request;
    }
    
    protected WebResolver.WebRequest<T> build() {
        return new WebResolver.WebRequest<T>(this);
    }
    
    protected final String readString(final WebInputStream inputStream) throws IOException {
        return IOUtil.toString(inputStream, StandardCharsets.UTF_8);
    }
    
    protected abstract R prepareCopy();
    
    protected abstract T handle(final Response<T> p0, final WebInputStream p1) throws Exception;
    
    protected T onException(final Exception exception) throws Exception {
        return null;
    }
}
