// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.network.server;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Textures;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.concurrent.CompletableFuture;
import java.io.IOException;
import java.io.EOFException;
import net.labymod.core.client.network.server.ping.LegacyServerPing;
import net.labymod.core.client.network.server.ping.ServerPing;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ServerInfo;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import net.labymod.api.client.network.server.ServerAddress;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.network.server.ServerPinger;

@Singleton
@Implements(ServerPinger.class)
public class DefaultServerPinger implements ServerPinger
{
    private final ExecutorService executorService;
    private final Map<ServerAddress, Request> pendingRequests;
    
    public DefaultServerPinger() {
        this.executorService = Executors.newFixedThreadPool(8);
        this.pendingRequests = new ConcurrentHashMap<ServerAddress, Request>();
    }
    
    @Override
    public ServerInfo pingServer(final String name, final ServerAddress address, final int timeout) throws IOException {
        final int clientVersion = Laby.labyAPI().minecraft().getProtocolVersion();
        try (final ServerPing ping = new ServerPing(name, address, timeout, clientVersion)) {
            return ping.retrieveServerInfo();
        }
        catch (final EOFException exception) {
            try (final LegacyServerPing legacyServerPing = new LegacyServerPing(name, address, timeout, clientVersion)) {
                return legacyServerPing.retrieveServerInfo();
            }
        }
    }
    
    @Override
    public CompletableFuture<ServerInfo> pingServerAsync(final String name, final ServerAddress address, final int timeout) {
        final CompletableFuture<ServerInfo> future = new CompletableFuture<ServerInfo>();
        final Request cachedRequest = this.pendingRequests.get(address);
        if (cachedRequest != null) {
            cachedRequest.futures.add(future);
            return future;
        }
        final Request request = new Request(future);
        this.pendingRequests.put(address, request);
        this.executorService.execute(() -> {
            try {
                final ServerInfo serverInfo = this.pingServer(name, address, timeout);
                this.pendingRequests.remove(address);
                request.complete(serverInfo);
            }
            catch (final Exception exception) {
                this.pendingRequests.remove(address);
                request.completeExceptionally(exception);
            }
            return;
        });
        return future;
    }
    
    @Override
    public ResourceLocation getDefaultServerIcon() {
        return Textures.DEFAULT_SERVER_ICON;
    }
    
    private static class Request
    {
        private final List<CompletableFuture<ServerInfo>> futures;
        
        private Request(final CompletableFuture<ServerInfo> future) {
            (this.futures = new ArrayList<CompletableFuture<ServerInfo>>()).add(future);
        }
        
        private void complete(final ServerInfo serverInfo) {
            for (final CompletableFuture<ServerInfo> future : this.futures) {
                future.complete(serverInfo);
            }
        }
        
        private void completeExceptionally(final Throwable throwable) {
            for (final CompletableFuture<ServerInfo> future : this.futures) {
                future.completeExceptionally(throwable);
            }
        }
    }
}
