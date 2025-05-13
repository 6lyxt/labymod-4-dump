// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.web.request.types;

import net.labymod.api.util.io.web.request.AbstractRequest;
import java.io.IOException;
import net.labymod.api.util.io.web.WebInputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import com.google.gson.Gson;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import com.google.gson.reflect.TypeToken;

public class TypeTokenGsonRequest<T> extends AbstractGsonRequest<T, TypeTokenGsonRequest<T>>
{
    protected final TypeToken<T> typeToken;
    
    protected TypeTokenGsonRequest(@NotNull final TypeToken<T> typeToken, @NotNull final Supplier<Gson> gsonSupplier) {
        super(gsonSupplier);
        Objects.requireNonNull(typeToken, "Type cannot be null");
        this.typeToken = typeToken;
    }
    
    public static <T> TypeTokenGsonRequest<T> of(@NotNull final TypeToken<T> type) {
        return of(type, () -> TypeTokenGsonRequest.DEFAULT_GSON);
    }
    
    public static <T> TypeTokenGsonRequest<T> of(@NotNull final TypeToken<T> type, @NotNull final Gson gson) {
        return of(type, () -> gson);
    }
    
    public static <T> TypeTokenGsonRequest<T> of(@NotNull final TypeToken<T> type, @NotNull final Supplier<Gson> gsonSupplier) {
        return new TypeTokenGsonRequest<T>(type, gsonSupplier);
    }
    
    public static <T> TypeTokenGsonRequest<List<T>> ofList(final Class<T> type) {
        return ofList(type, () -> TypeTokenGsonRequest.DEFAULT_GSON);
    }
    
    public static <T> TypeTokenGsonRequest<List<T>> ofList(final Class<T> type, final Supplier<Gson> gsonSupplier) {
        return of((com.google.gson.reflect.TypeToken<List<T>>)TypeToken.getParameterized((Type)List.class, new Type[] { type }), gsonSupplier);
    }
    
    @Override
    protected T handleGson(final WebInputStream inputStream) throws IOException {
        return (T)this.gsonSupplier.get().fromJson(this.readString(inputStream), this.typeToken.getType());
    }
    
    @Override
    protected TypeTokenGsonRequest<T> prepareCopy() {
        return new TypeTokenGsonRequest<T>(this.typeToken, this.gsonSupplier);
    }
    
    public TypeToken<T> getTypeToken() {
        return this.typeToken;
    }
}
