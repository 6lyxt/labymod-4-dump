// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.concurrent.task;

import java.util.List;
import java.util.concurrent.TimeUnit;
import net.labymod.api.util.ThreadSafe;
import java.util.function.Consumer;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Supplier;
import net.labymod.api.util.logging.Logging;

public final class Task implements Runnable
{
    private static final Logging LOGGER;
    private final Supplier<String> debugLabel;
    private final TaskExecutor executor;
    private final Runnable runnable;
    private final int id;
    private final boolean shutdown;
    private final long delay;
    private final long repeat;
    private ScheduledFuture<?> future;
    private Thread currentTaskThread;
    
    Task(final Supplier<String> debugLabel, final TaskExecutor executor, final Runnable runnable, final boolean shutdown, final long delay, final long repeat) {
        this.debugLabel = debugLabel;
        this.executor = executor;
        this.runnable = runnable;
        this.id = (shutdown ? this.executor.getShutdownCounterId() : this.executor.getCounterId());
        this.shutdown = shutdown;
        this.delay = delay;
        this.repeat = repeat;
    }
    
    public static TaskBuilder builder(final Runnable runnable) {
        return new TaskBuilder(runnable);
    }
    
    public static TaskBuilder builderShutdown(final Runnable runnable) {
        return new TaskBuilder(runnable, true);
    }
    
    @Override
    public void run() {
        this.executor.getPool().execute(() -> {
            this.currentTaskThread = Thread.currentThread();
            try {
                this.runnable.run();
            }
            catch (final Throwable throwable) {
                Task.LOGGER.error("Caught exception in task {}:{}", this.debugLabel.get(), this.getId(), throwable);
                if (throwable instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
            }
            finally {
                if (this.repeat == 0L) {
                    this.removeTask();
                }
                this.currentTaskThread = null;
            }
        });
    }
    
    public void execute() {
        this.execute(this);
    }
    
    public void executeOn(final Consumer<Runnable> action) {
        this.execute(() -> action.accept(this.runnable));
    }
    
    public void executeOnRenderThread() {
        this.executeOn(ThreadSafe::executeOnRenderThread);
    }
    
    private void execute(final Runnable command) {
        this.future = ((this.repeat == 0L) ? this.executor.getScheduledPool().schedule(command, this.delay, TimeUnit.MILLISECONDS) : this.executor.getScheduledPool().scheduleAtFixedRate(command, this.delay, this.repeat, TimeUnit.MILLISECONDS));
    }
    
    public void forceExecute() {
        this.runnable.run();
    }
    
    public Status getStatus() {
        if (this.future == null) {
            return Status.NOT_STARTED;
        }
        if (this.future.isCancelled()) {
            return Status.CANCELLED;
        }
        if (this.future.isDone()) {
            return Status.FINISHED;
        }
        return Status.RUNNING;
    }
    
    public boolean isRunning() {
        return this.getStatus() == Status.RUNNING;
    }
    
    public void cancel() {
        if (this.future != null) {
            this.future.cancel(false);
            final Thread taskThread = this.currentTaskThread;
            if (taskThread != null) {
                taskThread.interrupt();
            }
            this.removeTask();
        }
    }
    
    public int getId() {
        return this.id;
    }
    
    private void removeTask() {
        final List<Task> tasks = this.shutdown ? this.executor.getShutdownTasks() : this.executor.getTasks();
        tasks.removeIf(task -> task == null || task.id == this.id);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final Task task = (Task)obj;
        return this.id == task.id;
    }
    
    @Override
    public int hashCode() {
        return this.id;
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
    
    public enum Status
    {
        RUNNING, 
        CANCELLED, 
        FINISHED, 
        NOT_STARTED;
    }
}
