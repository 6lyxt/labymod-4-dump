// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.concurrent;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ThreadFactoryBuilder
{
    private String nameFormat;
    private boolean daemon;
    private int priority;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private ThreadFactory backingThreadFactory;
    
    public ThreadFactoryBuilder withNameFormat(final String nameFormat) {
        this.nameFormat = nameFormat;
        return this;
    }
    
    public ThreadFactoryBuilder withDaemon(final boolean daemon) {
        this.daemon = daemon;
        return this;
    }
    
    public ThreadFactoryBuilder withPriority(final int priority) {
        this.priority = priority;
        return this;
    }
    
    public ThreadFactoryBuilder withUncaughtExceptionHandler(final Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
        return this;
    }
    
    public ThreadFactoryBuilder withBackingThreadFactory(final ThreadFactory backingThreadFactory) {
        this.backingThreadFactory = backingThreadFactory;
        return this;
    }
    
    public ThreadFactory build() {
        final String nameFormat = this.nameFormat;
        final boolean daemon = this.daemon;
        final int priority = this.priority;
        final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.uncaughtExceptionHandler;
        final ThreadFactory backingThreadFactory = (this.backingThreadFactory != null) ? this.backingThreadFactory : Executors.defaultThreadFactory();
        final AtomicLong count = (nameFormat != null) ? new AtomicLong(0L) : null;
        return r -> {
            final Thread thread = backingThreadFactory.newThread(r);
            if (nameFormat != null) {
                thread.setName(String.format(Locale.ROOT, nameFormat, count.getAndIncrement()));
            }
            thread.setDaemon(daemon);
            if (priority <= 10 && priority >= 1) {
                thread.setPriority(priority);
            }
            if (uncaughtExceptionHandler != null) {
                thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
            }
            return thread;
        };
    }
}
