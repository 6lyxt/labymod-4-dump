// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import net.labymod.api.reference.annotation.Referenceable;
import com.google.gson.JsonObject;
import net.labymod.api.util.io.web.result.Result;
import java.util.function.ToLongFunction;
import java.text.SimpleDateFormat;
import java.util.function.LongConsumer;
import net.labymod.api.util.io.web.result.ResultCallback;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;
import com.google.gson.JsonElement;

public interface JsonFileCache<T extends JsonElement>
{
    default <T extends JsonElement> JsonFileCache<T> create(@NotNull final Path path, @NotNull final String url, @NotNull final String name, @NotNull final Class<T> cls) {
        return Laby.references().jsonFileCacheFactory().create(path, url, name, cls);
    }
    
    default <T extends JsonElement> JsonFileCache<T> create(@NotNull final Path path, @NotNull final Request<T> request, @NotNull final String name) {
        return Laby.references().jsonFileCacheFactory().create(path, request, name);
    }
    
    void read(final boolean p0, final ResultCallback<T> p1);
    
    void update(final boolean p0, final ResultCallback<T> p1);
    
    void download(final boolean p0, final ResultCallback<T> p1);
    
    boolean isUpToDate();
    
    JsonFileCache<T> readLastModifiedLongFromHeader(final String p0);
    
    JsonFileCache<T> readLastModifiedLongFromHeader(final String p0, final LongConsumer p1);
    
    JsonFileCache<T> readLastModifiedDateFromHeader(final String p0, final SimpleDateFormat p1);
    
    JsonFileCache<T> readLastModifiedDateFromHeader(final String p0, final SimpleDateFormat p1, final LongConsumer p2);
    
    JsonFileCache<T> readLastModifiedFromUrl(final String p0, final ToLongFunction<Result<String>> p1);
    
    JsonFileCache<T> readLastModifiedFromUrl(final String p0, final ToLongFunction<Result<String>> p1, final LongConsumer p2);
    
    JsonFileCache<T> setLastModified(final long p0);
    
     <R> R deserialize(final Class<R> p0);
    
    Result<JsonObject> getJsonObject();
    
    @Referenceable
    public interface Factory
    {
        @NotNull
         <T extends JsonElement> JsonFileCache<T> create(final Path p0, final String p1, final String p2, final Class<T> p3);
        
        @NotNull
         <T extends JsonElement> JsonFileCache<T> create(@NotNull final Path p0, @NotNull final Request<T> p1, @NotNull final String p2);
    }
}
