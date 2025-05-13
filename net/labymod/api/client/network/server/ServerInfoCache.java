// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network.server;

import net.labymod.api.Laby;
import java.net.UnknownHostException;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.Collection;

public class ServerInfoCache<T extends ServerData>
{
    private static final ServerPinger SERVER_PINGER;
    private final Collection<Consumer<ServerInfoCache<T>>> callbacks;
    protected final T serverData;
    private ServerAddress lastServerAddress;
    private ServerInfo serverInfo;
    private int timeout;
    
    public ServerInfoCache(final T serverData, final Consumer<ServerInfoCache<T>> callback) {
        this.callbacks = new ArrayList<Consumer<ServerInfoCache<T>>>();
        this.timeout = 2500;
        this.serverData = serverData;
        this.refresh(false);
        if (callback != null) {
            this.callbacks.add(callback);
            callback.accept(this);
        }
    }
    
    public String getName() {
        return this.serverData.getName();
    }
    
    public ServerAddress serverAddress() {
        return this.serverData.address();
    }
    
    public ServerInfo serverInfo() {
        return this.serverInfo;
    }
    
    public void setCallback(final Consumer<ServerInfoCache<T>> callback) {
        this.callbacks.clear();
        this.callbacks.add(callback);
        callback.accept(this);
    }
    
    public void addCallback(final Consumer<ServerInfoCache<T>> callback) {
        this.callbacks.add(callback);
    }
    
    public boolean matches(final ServerInfoCache<T> serverInfoCache) {
        return this.matches(serverInfoCache.serverData);
    }
    
    public boolean matches(final T serverData) {
        return this.serverData.equals(serverData);
    }
    
    public void refresh(final boolean callback) {
        final ServerAddress address = this.serverData.address();
        if (address == null) {
            return;
        }
        this.serverInfo = ServerInfo.loading(this.serverData.getName(), address);
        this.lastServerAddress = address;
        if (callback && !this.callbacks.isEmpty()) {
            for (final Consumer<ServerInfoCache<T>> consumer : this.callbacks) {
                consumer.accept(this);
            }
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ServerInfoCache<?> that = (ServerInfoCache<?>)o;
        return this.serverData.equals(that.serverData);
    }
    
    @Override
    public int hashCode() {
        return (this.serverData != null) ? this.serverData.hashCode() : 0;
    }
    
    public void setServerInfo(final ServerInfo serverInfo) {
        final CompletableResourceLocation newIcon = serverInfo.getIcon();
        final CompletableResourceLocation oldIcon = (this.serverInfo == null) ? null : this.serverInfo.getIcon();
        if (oldIcon != null && oldIcon.isLoading() && oldIcon != newIcon) {
            newIcon.addCompletableListener(() -> oldIcon.executeCompletableListeners(newIcon.getCompleted()));
        }
        this.serverInfo = serverInfo;
        if (!this.callbacks.isEmpty()) {
            for (final Consumer<ServerInfoCache<T>> consumer : this.callbacks) {
                consumer.accept(this);
            }
        }
    }
    
    public void setTimeout(final int timeout) {
        this.timeout = timeout;
    }
    
    public void update() {
        final String name = this.getName();
        final ServerAddress address = this.serverAddress();
        if (!address.equals(this.lastServerAddress)) {
            this.refresh(false);
        }
        ServerInfoCache.SERVER_PINGER.pingServerAsync(name, address, this.timeout).exceptionally(throwable -> ServerInfo.failed(name, address, (throwable instanceof UnknownHostException) ? ServerInfo.Status.UNKNOWN_HOST : ServerInfo.Status.CANNOT_CONNECT)).thenAccept(serverInfo -> Laby.labyAPI().minecraft().executeOnRenderThread(() -> this.setServerInfo(serverInfo)));
    }
    
    static {
        SERVER_PINGER = Laby.references().serverPinger();
    }
}
