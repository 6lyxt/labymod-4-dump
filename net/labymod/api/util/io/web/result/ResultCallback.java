// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.web.result;

import org.jetbrains.annotations.NotNull;
import java.util.function.Consumer;

public interface ResultCallback<T> extends Consumer<Result<T>>
{
    void accept(final Result<T> p0);
    
    default void acceptRaw(final T value) {
        this.accept(Result.ofNullable(value));
    }
    
    default void acceptException(@NotNull final Exception e) {
        final Result<T> result = Result.empty();
        result.setException(e);
        this.accept(result);
    }
}
