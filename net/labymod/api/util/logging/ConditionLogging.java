// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.logging;

import java.util.function.BooleanSupplier;

public class ConditionLogging implements Logging
{
    private final Logging delegate;
    private final BooleanSupplier condition;
    
    public ConditionLogging(final Logging delegate, final BooleanSupplier condition) {
        this.delegate = delegate;
        this.condition = condition;
    }
    
    @Override
    public void info(final CharSequence message, final Throwable throwable) {
        if (this.condition.getAsBoolean()) {
            this.delegate.info(message, throwable);
        }
    }
    
    @Override
    public void info(final CharSequence message, final Object... arguments) {
        if (this.condition.getAsBoolean()) {
            this.delegate.info(message, arguments);
        }
    }
    
    @Override
    public void warn(final CharSequence message, final Throwable throwable) {
        if (this.condition.getAsBoolean()) {
            this.delegate.warn(message, throwable);
        }
    }
    
    @Override
    public void warn(final CharSequence message, final Object... arguments) {
        if (this.condition.getAsBoolean()) {
            this.delegate.warn(message, arguments);
        }
    }
    
    @Override
    public void error(final CharSequence message, final Throwable throwable) {
        if (this.condition.getAsBoolean()) {
            this.delegate.error(message, throwable);
        }
    }
    
    @Override
    public void error(final CharSequence message, final Object... arguments) {
        if (this.condition.getAsBoolean()) {
            this.delegate.error(message, arguments);
        }
    }
    
    @Override
    public void debug(final CharSequence message, final Throwable throwable) {
        if (this.condition.getAsBoolean()) {
            this.delegate.debug(message, throwable);
        }
    }
    
    @Override
    public void debug(final CharSequence message, final Object... arguments) {
        if (this.condition.getAsBoolean()) {
            this.delegate.debug(message, arguments);
        }
    }
}
