// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.concurrent.task;

import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import net.labymod.api.Laby;
import java.util.function.Supplier;
import java.util.concurrent.atomic.AtomicInteger;

public final class TaskBuilder
{
    private static final AtomicInteger COUNTER;
    private final TaskExecutor executor;
    private final Runnable runnable;
    private final boolean shutdown;
    private Supplier<String> debugLabel;
    private long delay;
    private long repeat;
    
    TaskBuilder(final Runnable runnable) {
        this(runnable, false);
    }
    
    TaskBuilder(final Runnable runnable, final boolean shutdown) {
        this.executor = Laby.labyAPI().taskExecutor();
        this.runnable = runnable;
        this.shutdown = shutdown;
    }
    
    public TaskBuilder delay(final long time, final TimeUnit unit) {
        this.delay = unit.toMillis(time);
        return this;
    }
    
    public TaskBuilder repeat(final long time, final TimeUnit unit) {
        this.repeat = unit.toMillis(time);
        return this;
    }
    
    public TaskBuilder clearDelay() {
        this.delay = 0L;
        return this;
    }
    
    public TaskBuilder clearRepeat() {
        this.repeat = 0L;
        return this;
    }
    
    public TaskBuilder debugLabel(final Supplier<String> debugLabel) {
        this.debugLabel = debugLabel;
        return this;
    }
    
    public Task build() {
        final Task buildTask = new Task((this.debugLabel == null) ? (() -> "Task" + TaskBuilder.COUNTER.getAndIncrement()) : this.debugLabel, this.executor, this.runnable, this.shutdown, this.delay, this.repeat);
        final List<Task> tasks = new ArrayList<Task>(this.shutdown ? this.executor.getShutdownTasks() : this.executor.getTasks());
        tasks.add(buildTask);
        this.executor.updateTasks(tasks, this.shutdown);
        return buildTask;
    }
    
    static {
        COUNTER = new AtomicInteger(0);
    }
}
