// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.Future;
import net.labymod.api.util.math.MathHelper;
import java.util.concurrent.ExecutorService;
import net.labymod.api.util.logging.Logging;

public final class LabyExecutors
{
    private static final Logging LOGGER;
    public static final int MAX_THREAD_COUNT = 255;
    private static final ExecutorService BACKGROUND_EXECUTOR;
    
    private LabyExecutors() {
    }
    
    public static int getThreadCount(final int maxThreadCount) {
        final int availableProcessors = Runtime.getRuntime().availableProcessors();
        return MathHelper.clamp(availableProcessors - 1, 1, maxThreadCount);
    }
    
    public static Future<?> submitBackgroundTask(final Runnable task) {
        return LabyExecutors.BACKGROUND_EXECUTOR.submit(task);
    }
    
    public static void executeBackgroundTask(final Runnable task) {
        LabyExecutors.BACKGROUND_EXECUTOR.execute(task);
    }
    
    public static ExecutorService getBackgroundExecutor() {
        return LabyExecutors.BACKGROUND_EXECUTOR;
    }
    
    private static ExecutorService makeExecutor(final String name) {
        final int threadCount = getThreadCount(255);
        final AtomicInteger counter = new AtomicInteger(1);
        return new ForkJoinPool(threadCount, thread -> {
            final ForkJoinWorkerThread worker = new ForkJoinWorkerThread(thread) {
                @Override
                protected void onTermination(final Throwable exception) {
                    if (exception == null) {
                        LabyExecutors.LOGGER.debug("{} shutdown", this.getName());
                    }
                    else {
                        LabyExecutors.LOGGER.warn("{} died", this.getName());
                    }
                    super.onTermination(exception);
                }
            };
            worker.setName("LabyMod-Worker-" + name + "-" + counter.getAndIncrement());
            return worker;
        }, LabyExecutors::onThreadException, true);
    }
    
    private static void onThreadException(final Thread thread, final Throwable exception) {
        LabyExecutors.LOGGER.error("Caught exception in thread {}", thread.getName(), exception);
    }
    
    static {
        LOGGER = Logging.getLogger();
        BACKGROUND_EXECUTOR = makeExecutor("Main");
    }
}
