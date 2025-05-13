// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.web.result;

import java.util.NoSuchElementException;
import org.jetbrains.annotations.Nullable;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.io.web.exception.WebRequestException;
import java.util.function.Consumer;

public class Result<T> extends AbstractResult<T>
{
    private Consumer<Result<T>> callback;
    private WebRequestException exception;
    
    protected Result(final T value, final WebRequestException exception) {
        super(value);
        this.exception = exception;
    }
    
    public static <T> Result<T> of(@NotNull final T value) {
        Objects.requireNonNull(value, "Value cannot be null");
        return new Result<T>(value, null);
    }
    
    public static <T> Result<T> ofNullable(@Nullable final T value) {
        return (Result<T>)((value == null) ? empty() : of((Object)value));
    }
    
    public static <T> Result<T> ofException(@NotNull final Exception exception) {
        Objects.requireNonNull(exception, "Exception cannot be null");
        WebRequestException webRequestException;
        if (exception instanceof final WebRequestException ex) {
            webRequestException = ex;
        }
        else {
            webRequestException = new WebRequestException(exception);
        }
        return new Result<T>(null, webRequestException);
    }
    
    public static <T> Result<T> empty() {
        return new Result<T>(null, null);
    }
    
    public boolean hasException() {
        return this.exception != null;
    }
    
    @Override
    public boolean isEmpty() {
        return super.isEmpty() && !this.hasException();
    }
    
    public void set(@NotNull final T value) {
        Objects.requireNonNull(value, "Value cannot be null");
        if (this.value != null) {
            throw new IllegalStateException("Cannot overwrite already set result");
        }
        if (this.exception != null) {
            throw new IllegalStateException("Cannot set value when exception is present");
        }
        this.value = value;
        if (this.callback != null) {
            this.callback.accept(this);
        }
    }
    
    @NotNull
    public WebRequestException exception() {
        if (!this.hasException()) {
            throw new NoSuchElementException("No exception present");
        }
        return this.exception;
    }
    
    public void setException(@NotNull final Exception exception) {
        Objects.requireNonNull(exception, "Exception cannot be null");
        if (this.exception != null) {
            throw new IllegalStateException("Cannot overwrite already set exception");
        }
        if (this.value != null) {
            throw new IllegalStateException("Cannot set exception when value is present");
        }
        WebRequestException webRequestException;
        if (exception instanceof final WebRequestException ex) {
            webRequestException = ex;
        }
        else {
            webRequestException = new WebRequestException(exception);
        }
        this.value = null;
        this.exception = webRequestException;
        if (this.callback != null) {
            this.callback.accept(this);
        }
    }
    
    @NotNull
    public Result<T> callback(@NotNull final Consumer<Result<T>> callback) {
        Objects.requireNonNull(callback, "Callback cannot be null");
        if (!this.isEmpty()) {
            throw new IllegalStateException("Cannot set callback when result is not empty");
        }
        this.callback = callback;
        return this;
    }
}
