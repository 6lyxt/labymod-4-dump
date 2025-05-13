// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.network.server.lan;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import net.labymod.api.client.network.server.lan.LanServerCallback;
import java.util.Iterator;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.Executors;
import java.util.HashSet;
import java.util.concurrent.ScheduledFuture;
import java.util.Collection;
import java.net.MulticastSocket;
import java.net.InetAddress;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.models.Implements;
import net.labymod.api.client.network.server.lan.LanServerDetector;

@Implements(LanServerDetector.class)
public class DefaultLanServerDetector implements LanServerDetector
{
    private static final Logging LOGGER;
    private static final AtomicInteger THREAD_ID;
    private static final String MULTICAST_ADDRESS = "224.0.2.60";
    private static final int MULTICAST_PORT = 4445;
    private static final int SOCKET_TIMEOUT = 5000;
    private final ScheduledExecutorService executor;
    private InetAddress multicastAddress;
    private MulticastSocket socket;
    private final LanServerDetectionTask detectionTask;
    private final Collection<ScheduledFuture<?>> tasks;
    
    public DefaultLanServerDetector() {
        this.tasks = new HashSet<ScheduledFuture<?>>();
        this.detectionTask = new LanServerDetectionTask();
        final AtomicInteger threadId = new AtomicInteger();
        this.executor = Executors.newScheduledThreadPool(2, r -> new Thread(r, "LanServerDetector-" + DefaultLanServerDetector.THREAD_ID.incrementAndGet() + "-" + threadId.incrementAndGet()));
    }
    
    @NotNull
    @Override
    public InetAddress broadCastAddress() {
        return this.multicastAddress;
    }
    
    @Override
    public void terminateDetectionTask() {
        if (this.socket != null) {
            this.socket.close();
            this.socket = null;
        }
        if (!this.tasks.isEmpty()) {
            for (final ScheduledFuture<?> task : this.tasks) {
                task.cancel(true);
            }
            this.tasks.clear();
        }
    }
    
    @Override
    public void startDetectionTask(@NotNull final LanServerCallback callback) {
        try {
            this.terminateDetectionTask();
            this.multicastAddress = InetAddress.getByName("224.0.2.60");
            (this.socket = new MulticastSocket(4445)).setSoTimeout(5000);
            this.socket.joinGroup(new InetSocketAddress(this.multicastAddress, 0), null);
            this.detectionTask.setSocket(this.socket);
            this.detectionTask.setCallback(callback);
            this.tasks.add(this.executor.scheduleWithFixedDelay(this.detectionTask, 1L, 1L, TimeUnit.MILLISECONDS));
            final Collection<ScheduledFuture<?>> tasks = this.tasks;
            final ScheduledExecutorService executor = this.executor;
            final LanServerDetectionTask detectionTask = this.detectionTask;
            Objects.requireNonNull(detectionTask);
            tasks.add(executor.scheduleAtFixedRate(detectionTask::handleRemoval, 1L, 1L, TimeUnit.SECONDS));
        }
        catch (final Exception exception) {
            DefaultLanServerDetector.LOGGER.error("Failed to initialize LanServerDetector, skipping lan server collection", exception);
        }
    }
    
    @Override
    public void reset() {
        this.detectionTask.reset();
    }
    
    @Override
    public void close() {
        this.terminateDetectionTask();
        this.executor.shutdownNow();
    }
    
    static {
        LOGGER = Logging.create(DefaultLanServerDetector.class);
        THREAD_ID = new AtomicInteger();
    }
}
