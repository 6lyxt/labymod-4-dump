// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util.jsonfilecache;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.io.web.request.Request;
import com.google.gson.JsonElement;
import java.nio.file.Path;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.util.JsonFileCache;

@Singleton
@Implements(JsonFileCache.Factory.class)
public class DefaultJsonFileCacheFactory implements JsonFileCache.Factory
{
    private static final DefaultJsonFileCacheFactory INSTANCE;
    
    public static <T extends JsonElement> JsonFileCache<T> createJsonFileCache(final Path path, final String url, final String name, final Class<T> cls) {
        return (JsonFileCache<T>)DefaultJsonFileCacheFactory.INSTANCE.create(path, url, name, (Class<JsonElement>)cls);
    }
    
    public static <T extends JsonElement> JsonFileCache<T> createJsonFileCache(final Path path, final Request<T> request, final String name) {
        return (JsonFileCache<T>)DefaultJsonFileCacheFactory.INSTANCE.create(path, (Request<JsonElement>)request, name);
    }
    
    @NotNull
    @Override
    public <T extends JsonElement> JsonFileCache<T> create(final Path path, final String url, final String name, final Class<T> cls) {
        final Request<T> request = (Request<T>)Request.ofGson(cls).url(url, new Object[0]);
        return new DefaultJsonFileCache<T>(path, request, name);
    }
    
    @NotNull
    @Override
    public <T extends JsonElement> JsonFileCache<T> create(@NotNull final Path path, @NotNull final Request<T> request, @NotNull final String name) {
        return new DefaultJsonFileCache<T>(path, request, name);
    }
    
    static {
        INSTANCE = new DefaultJsonFileCacheFactory();
    }
}
