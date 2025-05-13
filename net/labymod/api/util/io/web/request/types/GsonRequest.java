// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.web.request.types;

import net.labymod.api.util.io.web.request.AbstractRequest;
import java.io.IOException;
import net.labymod.api.util.io.web.WebInputStream;
import java.util.Objects;
import com.google.gson.Gson;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

public class GsonRequest<T> extends AbstractGsonRequest<T, GsonRequest<T>>
{
    protected final Class<T> type;
    
    protected GsonRequest(@NotNull final Class<T> type, @NotNull final Supplier<Gson> gsonSupplier) {
        super(gsonSupplier);
        Objects.requireNonNull(type, "Type cannot be null");
        this.type = type;
    }
    
    protected GsonRequest(@NotNull final Class<T> type) {
        this(type, () -> GsonRequest.DEFAULT_GSON);
    }
    
    public static <T> GsonRequest<T> of(@NotNull final Class<T> type) {
        return new GsonRequest<T>(type);
    }
    
    public static <T> GsonRequest<T> of(@NotNull final Class<T> type, @NotNull final Gson gson) {
        return new GsonRequest<T>(type, () -> gson);
    }
    
    public static <T> GsonRequest<T> of(@NotNull final Class<T> type, @NotNull final Supplier<Gson> gsonSupplier) {
        return new GsonRequest<T>(type, gsonSupplier);
    }
    
    @Override
    protected T handleGson(final WebInputStream inputStream) throws IOException {
        return (T)this.gsonSupplier.get().fromJson(this.readString(inputStream), (Class)this.type);
    }
    
    @Override
    protected GsonRequest<T> prepareCopy() {
        return new GsonRequest<T>(this.type, this.gsonSupplier);
    }
    
    public Class<T> getType() {
        return this.type;
    }
}
