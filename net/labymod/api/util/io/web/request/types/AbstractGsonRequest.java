// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.web.request.types;

import net.labymod.api.util.GsonUtil;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.Response;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import java.util.function.Supplier;
import com.google.gson.Gson;
import net.labymod.api.util.io.web.request.AbstractRequest;

public abstract class AbstractGsonRequest<T, R extends AbstractGsonRequest<T, ?>> extends AbstractRequest<T, R>
{
    protected static final Gson DEFAULT_GSON;
    protected final Supplier<Gson> gsonSupplier;
    
    protected AbstractGsonRequest(@NotNull final Supplier<Gson> gsonSupplier) {
        Objects.requireNonNull(gsonSupplier, "Gson supplier cannot be null");
        this.gsonSupplier = gsonSupplier;
    }
    
    @ApiStatus.Internal
    @Override
    protected T handle(final Response<T> response, final WebInputStream inputStream) throws Exception {
        return this.handleGson(inputStream);
    }
    
    protected T handleGson(final WebInputStream inputStream) throws Exception {
        throw new UnsupportedOperationException("AbstractGsonRequest#handleGson is not implemented");
    }
    
    static {
        DEFAULT_GSON = GsonUtil.DEFAULT_GSON;
    }
}
