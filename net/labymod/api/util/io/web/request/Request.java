// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.web.request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;
import org.jetbrains.annotations.Nullable;
import java.net.URL;
import java.util.function.Consumer;
import net.labymod.api.util.io.web.request.types.FileRequest;
import java.nio.file.Path;
import net.labymod.api.util.io.web.request.types.StringRequest;
import net.labymod.api.util.io.web.request.types.InputStreamRequest;
import java.util.List;
import net.labymod.api.util.io.web.request.types.TypeTokenGsonRequest;
import com.google.gson.reflect.TypeToken;
import java.util.function.Supplier;
import com.google.gson.Gson;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import org.jetbrains.annotations.NotNull;

public interface Request<T>
{
    default <T> GsonRequest<T> ofGson(@NotNull final Class<T> type) {
        return GsonRequest.of(type);
    }
    
    default <T> GsonRequest<T> ofGson(@NotNull final Class<T> type, @NotNull final Gson gson) {
        return GsonRequest.of(type, gson);
    }
    
    default <T> GsonRequest<T> ofGson(@NotNull final Class<T> type, @NotNull final Supplier<Gson> gsonSupplier) {
        return GsonRequest.of(type, gsonSupplier);
    }
    
    default <T> TypeTokenGsonRequest<T> ofGson(@NotNull final TypeToken<T> typeToken) {
        return TypeTokenGsonRequest.of(typeToken);
    }
    
    default <T> TypeTokenGsonRequest<T> ofGson(@NotNull final TypeToken<T> typeToken, @NotNull final Gson gson) {
        return TypeTokenGsonRequest.of(typeToken, gson);
    }
    
    default <T> TypeTokenGsonRequest<T> ofGson(@NotNull final TypeToken<T> typeToken, @NotNull final Supplier<Gson> gsonSupplier) {
        return TypeTokenGsonRequest.of(typeToken, gsonSupplier);
    }
    
    default <T> TypeTokenGsonRequest<List<T>> ofGsonList(@NotNull final Class<T> type) {
        return TypeTokenGsonRequest.ofList(type);
    }
    
    default <T> TypeTokenGsonRequest<List<T>> ofGsonList(@NotNull final Class<T> type, @NotNull final Supplier<Gson> gsonSupplier) {
        return TypeTokenGsonRequest.ofList(type, gsonSupplier);
    }
    
    default InputStreamRequest ofInputStream() {
        return InputStreamRequest.create();
    }
    
    default StringRequest ofString() {
        return StringRequest.create();
    }
    
    default FileRequest ofFile(@NotNull final Path path) {
        return FileRequest.of(path);
    }
    
    default FileRequest ofFile(@NotNull final Path path, final Consumer<Double> percentageConsumer) {
        return FileRequest.of(path, percentageConsumer);
    }
    
    Request<T> url(@NotNull final String p0, final Object... p1);
    
    Request<T> url(@NotNull final URL p0);
    
    Request<T> authorization(@NotNull final String p0, @NotNull final String p1);
    
    Request<T> userAgent(@Nullable final String p0);
    
    Request<T> contentType(@Nullable final String p0);
    
    Request<T> readTimeout(final int p0);
    
    Request<T> connectTimeout(final int p0);
    
    Request<T> addHeader(final String p0, final Object p1);
    
    Request<T> async();
    
    Request<T> async(final boolean p0);
    
    Request<T> handleErrorStream();
    
    Request<T> handleErrorStream(final boolean p0);
    
    Request<T> read(final boolean p0);
    
    Request<T> write(final Object p0);
    
    Request<T> body(final Map<String, String> p0);
    
    Request<T> form(final FormData... p0);
    
    Request<T> form(final List<FormData> p0);
    
    Request<T> json(final Object p0);
    
    Request<T> method(@NotNull final Method p0);
    
    void execute(final Callback<T> p0);
    
    Response<T> executeSync();
    
    Request<T> copy();
    
    public enum Method
    {
        GET, 
        HEAD, 
        POST, 
        PUT, 
        DELETE, 
        CONNECT, 
        OPTIONS, 
        TRACE, 
        PATCH;
        
        public void setRequestMethod(final HttpURLConnection connection) throws IOException {
            if (this == Method.PATCH) {
                connection.setRequestMethod(Method.POST.name());
                connection.setRequestProperty("X-HTTP-Method-Override", this.name());
            }
            else {
                connection.setRequestMethod(this.name());
            }
        }
    }
}
