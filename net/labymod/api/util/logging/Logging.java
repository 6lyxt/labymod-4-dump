// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.logging;

import net.labymod.api.reference.annotation.Referenceable;
import java.util.function.BooleanSupplier;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;

public interface Logging
{
    public static final StackWalker WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    
    default Logging getLogger() {
        return create(Logging.WALKER.getCallerClass());
    }
    
    default Logging create(@NotNull final String name) {
        return Laby.references().loggingFactory().create(name);
    }
    
    default Logging create(@NotNull final Class<?> cls) {
        return Laby.references().loggingFactory().create(cls);
    }
    
    default Logging create(@NotNull final String name, final boolean condition) {
        return create(name, () -> condition);
    }
    
    default Logging create(@NotNull final String name, final BooleanSupplier condition) {
        return Laby.references().loggingFactory().create(name, condition);
    }
    
    default Logging create(@NotNull final Class<?> cls, final boolean condition) {
        return create(cls, () -> condition);
    }
    
    default Logging create(@NotNull final Class<?> cls, final BooleanSupplier condition) {
        return Laby.references().loggingFactory().create(cls, condition);
    }
    
    void info(final CharSequence p0, final Throwable p1);
    
    void info(final CharSequence p0, final Object... p1);
    
    void warn(final CharSequence p0, final Throwable p1);
    
    void warn(final CharSequence p0, final Object... p1);
    
    void error(final CharSequence p0, final Throwable p1);
    
    void error(final CharSequence p0, final Object... p1);
    
    void debug(final CharSequence p0, final Throwable p1);
    
    void debug(final CharSequence p0, final Object... p1);
    
    @Referenceable
    public interface Factory
    {
        @NotNull
        Logging create(final String p0);
        
        @NotNull
        Logging create(final Class<?> p0);
        
        default Logging create(final String name, final boolean condition) {
            return this.create(name, () -> condition);
        }
        
        default Logging create(final Class<?> cls, final boolean condition) {
            return this.create(cls, () -> condition);
        }
        
        Logging create(final String p0, final BooleanSupplier p1);
        
        Logging create(final Class<?> p0, final BooleanSupplier p1);
    }
}
