// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util.io.web.connection;

import net.labymod.core.util.logging.DefaultLoggingFactory;
import java.util.concurrent.Executors;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import net.labymod.api.util.function.ThrowableConsumer;
import java.util.Iterator;
import java.net.URL;
import net.labymod.api.util.io.web.request.types.InputStreamRequest;
import net.labymod.api.util.io.web.WebInputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import net.labymod.api.util.io.web.exception.WebRequestException;
import net.labymod.api.util.io.IOUtil;
import java.nio.charset.StandardCharsets;
import net.labymod.api.util.StringUtil;
import java.util.List;
import net.labymod.api.util.collection.map.CaseInsensitiveStringHashMap;
import java.util.Map;
import java.net.HttpURLConnection;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.request.Callback;
import net.labymod.api.util.io.web.request.Request;
import javax.inject.Inject;
import net.labymod.api.util.logging.Logging;
import java.util.concurrent.Executor;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.util.io.web.request.WebResolver;

@Singleton
@Implements(WebResolver.class)
public class DefaultWebResolver extends WebResolver
{
    private static final String USER_AGENT_KEY = "User-Agent";
    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final String AUTHORIZATION_KEY = "Authorization";
    private static final String RETRY_AFTER_KEY = "retry-after";
    private static final Executor EXECUTOR;
    private static final Logging LOGGER;
    private static DefaultWebResolver connectionResolver;
    
    @Inject
    public DefaultWebResolver() {
        setInstance(this);
    }
    
    public static DefaultWebResolver instance() {
        if (DefaultWebResolver.connectionResolver == null) {
            DefaultWebResolver.connectionResolver = new DefaultWebResolver();
        }
        return DefaultWebResolver.connectionResolver;
    }
    
    private static void setInstance(final DefaultWebResolver connectionResolver) {
        DefaultWebResolver.connectionResolver = connectionResolver;
    }
    
    @Override
    public <T> void resolveConnection(final Request<T> request, final Callback<T> callback) {
        final WebRequest<T> webRequest = this.webRequest(request);
        if (webRequest.isAsync()) {
            DefaultWebResolver.EXECUTOR.execute(() -> callback.accept(this.resolve((WebRequest<Object>)webRequest)));
        }
        else {
            callback.accept(this.resolve(webRequest));
        }
    }
    
    @Override
    public <T> Response<T> resolveConnection(final Request<T> request) {
        final WebRequest<T> webRequest = this.webRequest(request);
        if (webRequest.isAsync()) {
            throw new IllegalStateException("You cannot create an async connection in a sync-only context!");
        }
        return this.resolve(webRequest);
    }
    
    public <T> Response<T> resolve(final WebRequest<T> request) {
        final Response<T> result = Response.of(request);
        try {
            final URL url = request.getUrl();
            final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            request.getMethod().setRequestMethod(connection);
            connection.setDoInput(request.doRead());
            connection.setDoOutput(request.doWrite());
            for (final Map.Entry<String, String> header : request.getHeaders().entrySet()) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }
            final String userAgent = request.getUserAgent();
            if (userAgent != null) {
                connection.setRequestProperty("User-Agent", userAgent);
            }
            final String contentType = request.getContentType();
            if (contentType != null) {
                connection.setRequestProperty("Content-Type", contentType);
            }
            final String authorization = request.getAuthorization();
            final String urlAsString = request.getUrlString();
            if (authorization != null) {
                connection.setRequestProperty("Authorization", authorization);
            }
            connection.setReadTimeout(request.getReadTimeout());
            connection.setConnectTimeout(request.getConnectTimeout());
            final ThrowableConsumer<OutputStream, IOException> output = request.getOutput();
            if (output != null) {
                try (final OutputStream outputStream = connection.getOutputStream()) {
                    output.accept(outputStream);
                }
            }
            else {
                connection.connect();
            }
            if (!request.doRead()) {
                return result;
            }
            final int responseCode = connection.getResponseCode();
            result.setResponseCode(responseCode);
            final Map<String, List<String>> headerFields = connection.getHeaderFields();
            if (headerFields != null) {
                final Map<String, String> responseHeaders = (Map<String, String>)new CaseInsensitiveStringHashMap();
                for (final Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
                    final List<String> value = entry.getValue();
                    if (value != null) {
                        if (value.isEmpty()) {
                            continue;
                        }
                        responseHeaders.put(entry.getKey(), value.get(0));
                    }
                }
                result.setResponseHeaders(responseHeaders);
            }
            if (responseCode == 301) {
                DefaultWebResolver.LOGGER.info(urlAsString + " has been redirected to " + (String)result.getHeaders().get("Location") + " (" + StringUtil.join(result.getHeaders()), new Object[0]);
            }
            if (request.getMethod() == Request.Method.HEAD) {
                return result;
            }
            final boolean handleErrorStream = request.doHandleErrorStream();
            if (!handleErrorStream) {
                final InputStream errorStream = connection.getErrorStream();
                try {
                    if (errorStream != null) {
                        final String error = IOUtil.toString(errorStream, StandardCharsets.UTF_8);
                        if (!result.getHeaders().containsKey("retry-after")) {
                            this.setException(request, result, new WebRequestException(urlAsString, responseCode, error));
                            final Response<T> response = result;
                            if (errorStream != null) {
                                errorStream.close();
                            }
                            return response;
                        }
                        final String retryAfterHeader = result.getHeaders().get("retry-after");
                        long retryAfter = -1L;
                        try {
                            retryAfter = Long.parseLong(retryAfterHeader);
                        }
                        catch (final NumberFormatException e) {
                            try {
                                final ZonedDateTime zdt = ZonedDateTime.parse(retryAfterHeader, DateTimeFormatter.RFC_1123_DATE_TIME);
                                retryAfter = zdt.toInstant().toEpochMilli();
                            }
                            catch (final Exception ex) {}
                        }
                        this.setException(request, result, new WebRequestException(urlAsString, responseCode, error, retryAfter));
                        final Response<T> response2 = result;
                        if (errorStream != null) {
                            errorStream.close();
                        }
                        return response2;
                    }
                    else if (errorStream != null) {
                        errorStream.close();
                    }
                }
                catch (final Throwable t2) {
                    if (errorStream != null) {
                        try {
                            errorStream.close();
                        }
                        catch (final Throwable exception3) {
                            t2.addSuppressed(exception3);
                        }
                    }
                    throw t2;
                }
            }
            InputStream inputStream = null;
            if (handleErrorStream) {
                inputStream = connection.getErrorStream();
            }
            if (inputStream == null) {
                inputStream = connection.getInputStream();
            }
            if (inputStream == null) {
                this.setException(request, result, new WebRequestException(urlAsString, responseCode, "InputStream is null"));
                return result;
            }
            final int contentLength = connection.getContentLength();
            try (final WebInputStream webInputStream = new WebInputStream(inputStream, contentLength)) {
                final T handledResult = this.handle(request, result, webInputStream);
                result.set(handledResult);
            }
            try {
                if (!(request.request() instanceof InputStreamRequest)) {
                    inputStream.close();
                }
            }
            catch (final Exception ex2) {}
        }
        catch (final Throwable e2) {
            Exception exception;
            if (e2 instanceof final Exception ex3) {
                exception = ex3;
            }
            else {
                exception = new RuntimeException(e2);
            }
            this.setException(request, result, exception);
        }
        return result;
    }
    
    public <T> WebRequest<T> webRequest(final Request<T> request) {
        return super.webRequest(request);
    }
    
    @Deprecated
    private static boolean isAvailable(final String url, final String... properties) {
        try {
            final HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.88 Safari/537.36");
            for (int i = 0; i < properties.length; i += 2) {
                final String value = (i == 0) ? new String(Files.readAllBytes(Paths.get(properties[i + 1], new String[0]))) : properties[i + 1];
                connection.setRequestProperty(properties[i], value);
            }
            connection.connect();
            return connection.getResponseCode() / 100 == 2;
        }
        catch (final Exception e) {
            return false;
        }
    }
    
    private <T> void setException(final WebRequest<T> request, final Response<T> response, final Exception exception) {
        try {
            final T value = this.onException(request, exception);
            if (value == null) {
                response.setException(exception);
                return;
            }
            response.set(value);
        }
        catch (final Exception e) {
            response.setException(exception);
        }
    }
    
    static {
        EXECUTOR = Executors.newFixedThreadPool(5);
        LOGGER = DefaultLoggingFactory.createLogger("WebResolver");
        DefaultWebResolver.connectionResolver = null;
    }
}
