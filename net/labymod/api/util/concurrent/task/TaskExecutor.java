// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.concurrent.task;

import java.util.Iterator;
import java.util.Optional;
import org.jetbrains.annotations.ApiStatus;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskExecutor
{
    private final AtomicInteger counter;
    private final AtomicInteger shutdownCounter;
    private final ExecutorService pool;
    private final ScheduledExecutorService scheduledPool;
    private List<Task> tasks;
    private List<Task> shutdownTasks;
    
    public TaskExecutor() {
        this.counter = new AtomicInteger();
        this.shutdownCounter = new AtomicInteger();
        this.pool = Executors.newSingleThreadExecutor();
        this.scheduledPool = Executors.newSingleThreadScheduledExecutor();
        this.tasks = new ArrayList<Task>();
        this.shutdownTasks = new ArrayList<Task>();
    }
    
    public int getCounterId() {
        return this.counter.incrementAndGet();
    }
    
    public int getShutdownCounterId() {
        return this.shutdownCounter.incrementAndGet();
    }
    
    @ApiStatus.Internal
    public ExecutorService getPool() {
        return this.pool;
    }
    
    @ApiStatus.Internal
    public ScheduledExecutorService getScheduledPool() {
        return this.scheduledPool;
    }
    
    public List<Task> getTasks() {
        return this.tasks;
    }
    
    public List<Task> getShutdownTasks() {
        return this.shutdownTasks;
    }
    
    public Optional<Task> getTask(final int id) {
        for (final Task task : this.tasks) {
            if (task.getId() == id) {
                return Optional.of(task);
            }
        }
        return Optional.empty();
    }
    
    public Optional<Task> getShutdownTask(final int id) {
        for (final Task task : this.shutdownTasks) {
            if (task.getId() == id) {
                return Optional.of(task);
            }
        }
        return Optional.empty();
    }
    
    public void cancelTask(final Task task) {
        task.cancel();
    }
    
    public void shutdown() {
        for (final Task shutdownTask : this.shutdownTasks) {
            shutdownTask.forceExecute();
        }
        this.scheduledPool.shutdown();
        this.pool.shutdown();
    }
    
    void updateTasks(final List<Task> tasks, final boolean shutdown) {
        if (shutdown) {
            this.shutdownTasks = tasks;
        }
        else {
            this.tasks = tasks;
        }
    }
}
