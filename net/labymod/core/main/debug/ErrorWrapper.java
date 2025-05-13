// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.debug;

import net.labymod.core.util.logging.DefaultLoggingFactory;
import java.util.function.Supplier;
import net.labymod.api.util.logging.Logging;

public final class ErrorWrapper
{
    private static final Logging LOGGER;
    
    public static void wrap(final Runnable executor, final Supplier<String> message) {
        wrap(executor, message, null);
    }
    
    public static void wrap(final Runnable executor, final Supplier<String> message, final ErrorWatcher errorWatcher) {
        try {
            executor.run();
        }
        catch (final Throwable throwable) {
            ErrorWrapper.LOGGER.error(message.get(), throwable);
            if (errorWatcher != null) {
                errorWatcher.onError();
            }
        }
    }
    
    public static <T> T wrap(final ReturnStatement<T> statement, final T errorFallback, final Supplier<String> message) {
        return wrap(statement, errorFallback, message, null);
    }
    
    public static <T> T wrap(final ReturnStatement<T> statement, final T errorFallback, final Supplier<String> message, final ErrorWatcher errorWatcher) {
        try {
            return statement.get();
        }
        catch (final Throwable throwable) {
            ErrorWrapper.LOGGER.error(message.get(), throwable);
            if (errorWatcher != null) {
                errorWatcher.onError();
            }
            return errorFallback;
        }
    }
    
    static {
        LOGGER = DefaultLoggingFactory.createLogger("Error Wrapper");
    }
    
    public interface ErrorWatcher
    {
        void onError();
    }
    
    @FunctionalInterface
    public interface ReturnStatement<T>
    {
        T get();
    }
}
