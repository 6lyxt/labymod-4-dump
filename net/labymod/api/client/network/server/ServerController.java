// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network.server;

import net.labymod.api.client.network.server.global.PublicServerListService;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import java.util.function.Consumer;
import net.labymod.api.client.network.server.storage.ServerList;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.event.Phase;
import net.labymod.api.client.network.server.storage.StorageServerData;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ServerController
{
    ServerData createServerData(final Object p0);
    
    void setCurrentServerData(final ServerData p0);
    
    @Nullable
    ServerData getCurrentServerData();
    
    void setCurrentStorageServerData(@Nullable final StorageServerData p0);
    
    @Nullable
    StorageServerData getCurrentStorageServerData();
    
    void loginOrServerSwitch(final ServerData p0);
    
    @ApiStatus.Internal
    void disconnect(final Phase p0);
    
    default boolean isConnected() {
        return this.getCurrentServerData() != null;
    }
    
    void sendPayload(final ResourceLocation p0, final byte[] p1);
    
    boolean handleCustomPayload(final PayloadChannelIdentifier p0, final byte[] p1);
    
    void leaveServer();
    
    @Deprecated
    default void joinServer(final ServerInfo serverInfo) {
        this.joinServer(serverInfo, null);
    }
    
    @Deprecated
    default void joinServer(final ServerInfo serverInfo, @Nullable final Runnable loginExecutor) {
        this.joinServer(serverInfo.getName(), serverInfo.address().toString(), false, loginExecutor);
    }
    
    @Deprecated
    default void joinServer(final String name, final String host, final boolean lan) {
        this.joinServer(name, host, lan, ServerResourcePackStatus.PROMPT, null);
    }
    
    @Deprecated
    default void joinServer(final String name, final String host, final boolean lan, final ServerResourcePackStatus resourcePackStatus) {
        this.joinServer(name, host, lan, resourcePackStatus, null);
    }
    
    @Deprecated
    default void joinServer(final String name, final String host, final boolean lan, @Nullable final Runnable loginExecutor) {
        this.joinServer(name, host, lan, ServerResourcePackStatus.PROMPT, loginExecutor);
    }
    
    @Deprecated
    default void joinServer(final String name, final String host, final boolean lan, final ServerResourcePackStatus resourcePackStatus, @Nullable final Runnable loginExecutor) {
        this.joinServer(ConnectableServerData.builder().name(name).address(host).lan(lan).resourcePackStatus(resourcePackStatus).build(), loginExecutor);
    }
    
    default void joinServer(@NotNull final ConnectableServerData connectableServerData) {
        this.joinServer(connectableServerData, null);
    }
    
    void joinServer(@NotNull final ConnectableServerData p0, @Nullable final Runnable p1);
    
    default void joinServer(final String host) {
        this.joinServer("", host, false);
    }
    
    default void joinServer(final String address, final int port) {
        this.joinServer(address + ":" + port);
    }
    
    boolean isOfflineMode();
    
    ServerList serverList();
    
    ScreenWrapper createNewServerScreen(final Consumer<StorageServerData> p0);
    
    ScreenWrapper createEditServerScreen(final StorageServerData p0, final Consumer<StorageServerData> p1);
    
    PublicServerListService publicServerListService();
    
    void registerServer(final AbstractServer p0);
    
    void executeLoginExecutor();
}
