// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util.logging;

import net.labymod.api.util.logging.ConditionLogging;
import java.util.function.BooleanSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.util.logging.Logging;

@Singleton
@Implements(Logging.Factory.class)
public class DefaultLoggingFactory implements Logging.Factory
{
    private static Logging.Factory instance;
    
    private static Logging.Factory getInstance() {
        if (DefaultLoggingFactory.instance == null) {
            DefaultLoggingFactory.instance = new DefaultLoggingFactory();
        }
        return DefaultLoggingFactory.instance;
    }
    
    @Inject
    public DefaultLoggingFactory() {
    }
    
    @ApiStatus.Internal
    public static Logging createLogger(final String name) {
        return getInstance().create(name);
    }
    
    @ApiStatus.Internal
    public static Logging createLogger(final Class<?> cls) {
        return getInstance().create(cls);
    }
    
    @NotNull
    @Override
    public Logging create(final String name) {
        return new DefaultLogging(name);
    }
    
    @NotNull
    @Override
    public Logging create(final Class<?> cls) {
        return new DefaultLogging(cls);
    }
    
    @Override
    public Logging create(final String name, final BooleanSupplier condition) {
        if (condition == null) {
            return this.create(name);
        }
        return new ConditionLogging(this.create(name), condition);
    }
    
    @Override
    public Logging create(final Class<?> cls, final BooleanSupplier condition) {
        if (condition == null) {
            return this.create(cls);
        }
        return new ConditionLogging(this.create(cls), condition);
    }
}
