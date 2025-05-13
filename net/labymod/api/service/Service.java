// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.service;

import java.util.Objects;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.util.logging.Logging;

public abstract class Service
{
    protected static final Logging LOGGER;
    @Deprecated(forRemoval = true, since = "4.1.12")
    protected Logging logging;
    private boolean completed;
    private final List<Runnable> tasks;
    
    protected Service() {
        this.logging = Service.LOGGER;
        this.tasks = new ArrayList<Runnable>();
    }
    
    protected void prepare() {
    }
    
    public final void prepareSynchronously() {
        try {
            this.prepare();
            this.completed();
        }
        catch (final Exception exception) {
            this.onServiceError(exception);
        }
    }
    
    public final void prepareAsynchronously() {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(this::prepareSynchronously);
        executorService.shutdown();
    }
    
    public void onServiceError(final Exception exception) {
        this.logging.error("An error occurred in the service {}", this.getClass().getName(), exception);
    }
    
    public void onServiceUnload() {
    }
    
    public final void unload() {
        this.onServiceUnload();
        this.completed = false;
    }
    
    public void onServiceCompleted() {
    }
    
    public final void completed() {
        this.onServiceCompleted();
        this.completed = true;
        for (final Runnable task : this.tasks) {
            if (task == null) {
                continue;
            }
            task.run();
        }
        this.tasks.clear();
    }
    
    public void executeServiceTask(final Runnable runnable) {
        Objects.requireNonNull(runnable, "runnable must not be null");
        if (this.completed) {
            runnable.run();
        }
        else {
            this.tasks.add(runnable);
        }
    }
    
    public boolean isCompleted() {
        return this.completed;
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
