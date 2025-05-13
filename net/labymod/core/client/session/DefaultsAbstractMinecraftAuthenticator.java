// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.session;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.Comparator;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.util.time.TimeUtil;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import net.labymod.api.client.session.MinecraftAuthenticator;

public abstract class DefaultsAbstractMinecraftAuthenticator implements MinecraftAuthenticator
{
    private static final long LOGIN_DELAY;
    protected final List<AuthTask> authenticators;
    private long nextLogin;
    
    public DefaultsAbstractMinecraftAuthenticator() {
        this.authenticators = new CopyOnWriteArrayList<AuthTask>();
        this.nextLogin = -1L;
        Task.builder(() -> {
            if (this.nextLogin == -1L || TimeUtil.getCurrentTimeMillis() >= this.nextLogin) {
                final Runnable authenticator = this.takeAuthenticator();
                if (authenticator != null) {
                    authenticator.run();
                    this.nextLogin = TimeUtil.getCurrentTimeMillis() + DefaultsAbstractMinecraftAuthenticator.LOGIN_DELAY;
                }
            }
        }).delay(500L, TimeUnit.MILLISECONDS).repeat(500L, TimeUnit.MILLISECONDS).build().execute();
    }
    
    private Runnable takeAuthenticator() {
        if (this.authenticators.isEmpty()) {
            return null;
        }
        final List<AuthTask> tasks = new LinkedList<AuthTask>(this.authenticators);
        tasks.sort(Comparator.comparingInt(value -> value.priority));
        final AuthTask task = tasks.get(0);
        this.authenticators.remove(task);
        return task.runnable;
    }
    
    protected CompletableFuture<Boolean> queueTask(final int priority, final Consumer<CompletableFuture<Boolean>> task) {
        final CompletableFuture<Boolean> future = new CompletableFuture<Boolean>();
        this.authenticators.add(new AuthTask(priority, () -> task.accept(future)));
        return future;
    }
    
    static {
        LOGIN_DELAY = TimeUnit.SECONDS.toMillis(3L);
    }
    
    record AuthTask(int priority, Runnable runnable) {}
}
