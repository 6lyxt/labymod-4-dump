// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util.jsonfilecache;

import net.labymod.api.util.io.web.request.Response;
import java.nio.file.OpenOption;
import net.labymod.api.util.time.TimeUtil;
import java.util.function.ToLongFunction;
import java.text.SimpleDateFormat;
import java.util.function.LongConsumer;
import com.google.gson.JsonSyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.io.web.result.ResultCallback;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import com.google.gson.JsonObject;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.util.io.web.request.Request;
import java.nio.file.Path;
import net.labymod.api.util.logging.Logging;
import com.google.gson.Gson;
import net.labymod.core.util.io.web.connection.DefaultWebResolver;
import net.labymod.api.util.JsonFileCache;
import com.google.gson.JsonElement;

public class DefaultJsonFileCache<T extends JsonElement> implements JsonFileCache<T>
{
    private static final DefaultWebResolver WEB_RESOLVER;
    private static final Gson GSON;
    private static final Logging LOGGING;
    private final Path path;
    private final String name;
    private final Request<T> request;
    private final Class<T> type;
    private Result<JsonObject> latestCache;
    private Long lastModified;
    
    protected DefaultJsonFileCache(final Path path, final Request<T> request, final String name) {
        if (!(request instanceof GsonRequest)) {
            throw new UnsupportedOperationException("Request has to be a GsonRequest!");
        }
        this.path = path;
        this.request = request;
        this.name = name;
        this.type = ((GsonRequest)request).getType();
        this.lastModified = 0L;
        this.latestCache = Result.empty();
    }
    
    @Override
    public void read(final boolean async, final ResultCallback<T> callback) {
        if (!IOUtil.exists(this.path)) {
            this.download(async, callback);
            return;
        }
        JsonObject cachedFile;
        try {
            final String rawJson = new String(Files.readAllBytes(this.path), StandardCharsets.UTF_8);
            cachedFile = (JsonObject)DefaultJsonFileCache.GSON.fromJson(rawJson, (Class)JsonObject.class);
        }
        catch (final Exception e) {
            DefaultJsonFileCache.LOGGING.warn("An exception occurred while reading the local file.", e);
            this.download(async, callback);
            return;
        }
        if (this.isUpToDate(cachedFile)) {
            final String name = this.getName(true);
            if (cachedFile.has(name)) {
                try {
                    this.latestCache = Result.of(cachedFile);
                    final JsonElement jsonElement = cachedFile.get(name);
                    if (jsonElement.isJsonArray()) {
                        callback.acceptRaw((T)DefaultJsonFileCache.GSON.fromJson(jsonElement, (Class)this.type));
                    }
                    else {
                        callback.acceptRaw((T)DefaultJsonFileCache.GSON.fromJson((JsonElement)cachedFile, (Class)this.type));
                    }
                    return;
                }
                catch (final JsonSyntaxException e2) {
                    DefaultJsonFileCache.LOGGING.warn("An exception occurred while deserializing the json cache.", (Throwable)e2);
                }
            }
        }
        this.download(async, callback);
    }
    
    @Override
    public void update(final boolean async, final ResultCallback<T> callback) {
        final Result<JsonObject> latestResult = this.latestCache;
        if (!latestResult.isPresent()) {
            this.read(async, callback);
            return;
        }
        if (!this.isUpToDate()) {
            this.download(async, result -> {
                if (result.isPresent()) {
                    callback.accept(result);
                }
                else {
                    this.latestCache = latestResult;
                }
            });
        }
    }
    
    @Override
    public void download(final boolean async, final ResultCallback<T> callback) {
        DefaultJsonFileCache.WEB_RESOLVER.resolveConnection(this.request.copy().async(async), response -> {
            if (response.hasException()) {
                callback.acceptException(response.exception());
            }
            else {
                final T jsonElement = (T)response.get();
                final DefaultJsonFileCache<T> fileCache = this;
                fileCache.latestCache = Result.of(fileCache.saveToFile(jsonElement));
                callback.acceptRaw(jsonElement);
            }
        });
    }
    
    @Override
    public boolean isUpToDate() {
        return this.latestCache.isPresent() && this.isUpToDate(this.latestCache.get());
    }
    
    @Override
    public JsonFileCache<T> readLastModifiedLongFromHeader(final String headerKey) {
        return this.readLastModifiedLongFromHeader(headerKey, null);
    }
    
    @Override
    public JsonFileCache<T> readLastModifiedLongFromHeader(final String headerKey, final LongConsumer consumer) {
        this.sendHeaderRequest(consumer != null, headerKey, result -> {
            if (result.isPresent()) {
                try {
                    this.lastModified = Long.parseLong((String)result.get());
                }
                catch (final Exception e) {
                    this.lastModified = 0L;
                }
                if (consumer != null) {
                    consumer.accept(this.lastModified);
                }
            }
            return;
        });
        return this;
    }
    
    @Override
    public JsonFileCache<T> readLastModifiedDateFromHeader(final String headerKey, final SimpleDateFormat dateFormat) {
        return this.readLastModifiedDateFromHeader(headerKey, dateFormat, null);
    }
    
    @Override
    public JsonFileCache<T> readLastModifiedDateFromHeader(final String headerKey, final SimpleDateFormat dateFormat, final LongConsumer consumer) {
        this.sendHeaderRequest(consumer != null, headerKey, result -> {
            if (result.isPresent()) {
                try {
                    this.lastModified = dateFormat.parse((String)result.get()).getTime();
                }
                catch (final Exception e) {
                    this.lastModified = 0L;
                }
                if (consumer != null) {
                    consumer.accept(this.lastModified);
                }
            }
            return;
        });
        return this;
    }
    
    @Override
    public JsonFileCache<T> readLastModifiedFromUrl(final String url, final ToLongFunction<Result<String>> parser) {
        return this.readLastModifiedFromUrl(url, parser, null);
    }
    
    @Override
    public JsonFileCache<T> readLastModifiedFromUrl(final String url, final ToLongFunction<Result<String>> parser, final LongConsumer callback) {
        final Request<String> request = Request.ofString();
        request.url(url, new Object[0]);
        request.async(callback != null);
        DefaultJsonFileCache.WEB_RESOLVER.resolveConnection(request, response -> {
            long lastModified;
            try {
                lastModified = parser.applyAsLong(response);
            }
            catch (final Exception e) {
                lastModified = 0L;
            }
            final String string = String.valueOf(lastModified);
            if (string.length() == 10) {
                lastModified *= 1000L;
            }
            this.lastModified = lastModified;
            if (callback != null) {
                callback.accept(lastModified);
            }
            return;
        });
        return this;
    }
    
    @Override
    public JsonFileCache<T> setLastModified(final long lastModified) {
        this.lastModified = lastModified;
        return this;
    }
    
    @Override
    public <R> R deserialize(final Class<R> cls) {
        final String name = this.getName(true);
        if (!this.latestCache.isPresent() || !this.latestCache.get().has(name)) {
            return null;
        }
        try {
            JsonElement element;
            final JsonObject jsonObject = (JsonObject)(element = (JsonElement)this.latestCache.get());
            if (this.name == null || this.name.length() == 0) {
                element = jsonObject.get(name);
            }
            return (R)DefaultJsonFileCache.GSON.fromJson(element, (Class)cls);
        }
        catch (final Exception e) {
            DefaultJsonFileCache.LOGGING.warn("An exception occurred while deserializing the json cache.", e);
            return null;
        }
    }
    
    @Override
    public Result<JsonObject> getJsonObject() {
        return this.latestCache;
    }
    
    private JsonObject saveToFile(JsonElement jsonElement) {
        if (jsonElement.isJsonObject()) {
            final String name = this.getName(false);
            final JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (name != null && jsonObject.has(name) && jsonObject.entrySet().size() == 1) {
                jsonElement = jsonObject.get(name);
            }
        }
        final JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("updated_at", (Number)TimeUtil.getCurrentTimeMillis());
        jsonObject2.add(this.getName(true), jsonElement);
        try {
            if (!IOUtil.exists(this.path)) {
                IOUtil.createDirectories(this.path.getParent());
                IOUtil.createFile(this.path);
            }
            Files.write(this.path, DefaultJsonFileCache.GSON.toJson((JsonElement)jsonObject2).getBytes(StandardCharsets.UTF_8), new OpenOption[0]);
        }
        catch (final Exception e) {
            DefaultJsonFileCache.LOGGING.warn("An exception occurred while writing the json cache to hard drive.", e);
        }
        return jsonObject2;
    }
    
    private boolean isUpToDate(final JsonObject jsonObject) {
        return jsonObject != null && jsonObject.has("updated_at") && (this.lastModified == 0L || jsonObject.get("updated_at").getAsLong() > this.lastModified);
    }
    
    private void sendHeaderRequest(final boolean async, final String key, final ResultCallback<String> callback) {
        final Request<T> request = this.request.copy();
        request.async(async);
        request.method(Request.Method.HEAD);
        DefaultJsonFileCache.WEB_RESOLVER.resolveConnection(request, response -> {
            if (response.hasException()) {
                callback.acceptException(response.exception());
            }
            else if (response.getStatusCode() != 200) {
                callback.acceptException(new UnsupportedOperationException("Response code is not 200 (was " + response.getStatusCode()));
            }
            else {
                final String header = (String)response.getHeaders().get(key);
                if (header == null) {
                    callback.acceptException(new UnsupportedOperationException("Header \"" + key + "\" not found."));
                }
                else {
                    callback.acceptRaw(header);
                }
            }
        });
    }
    
    private String getName(final boolean cache) {
        if (this.name != null && this.name.length() != 0) {
            return this.name;
        }
        return cache ? "cache" : null;
    }
    
    public static Gson getGson() {
        return DefaultJsonFileCache.GSON;
    }
    
    static {
        WEB_RESOLVER = DefaultWebResolver.instance();
        GSON = new Gson();
        LOGGING = Logging.create(JsonFileCache.class);
    }
}
