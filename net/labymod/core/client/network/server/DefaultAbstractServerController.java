// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.network.server;

import net.labymod.core.util.logging.DefaultLoggingFactory;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import java.util.function.Consumer;
import net.labymod.api.event.client.network.server.ServerSwitchEvent;
import net.labymod.api.event.client.network.server.NetworkSwitchEvent;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import net.labymod.api.event.client.network.server.NetworkPayloadEvent;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.event.client.network.server.NetworkDisconnectEvent;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.event.Phase;
import net.labymod.api.client.network.server.payload.PayloadRegistry;
import java.util.Iterator;
import net.labymod.api.client.network.server.ServerAddress;
import java.util.concurrent.TimeUnit;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.serverapi.protocol.intave.packet.IntaveClientConfigPacket;
import net.labymod.core.main.user.permission.DefaultPermissionRegistry;
import net.labymod.api.event.client.network.server.SubServerSwitchEvent;
import net.labymod.api.event.client.network.server.NetworkServerSwitchEvent;
import net.labymod.api.event.client.network.server.NetworkLoginEvent;
import net.labymod.api.Laby;
import net.labymod.api.event.client.network.server.ServerJoinEvent;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.core.packet.serverbound.login.VersionLoginPacket;
import net.labymod.api.BuildData;
import net.labymod.core.client.network.server.servers.HypixelServer;
import net.labymod.core.client.network.server.global.DefaultPublicServerListService;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.client.network.server.ServerData;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.network.server.global.PublicServerListService;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.api.serverapi.LabyModProtocolService;
import net.labymod.api.client.network.server.AbstractServer;
import java.util.List;
import net.labymod.api.client.Minecraft;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.network.server.ServerController;

public abstract class DefaultAbstractServerController implements ServerController
{
    private static final ResourceLocation REGISTER_CHANNEL;
    private static final Logging LOGGING;
    protected final Minecraft minecraft;
    private final List<AbstractServer> servers;
    private final LabyModProtocolService protocolService;
    private final PermissionRegistry permissionRegistry;
    private final ResourceLocationFactory locationFactory;
    private final PublicServerListService publicServerListService;
    @Nullable
    protected Runnable loginExecutor;
    private ServerData currentServerData;
    private StorageServerData currentStorageServerData;
    private boolean hasRedeemedQuickPlayCommand;
    
    protected DefaultAbstractServerController(@NotNull final LabyAPI labyAPI, @NotNull final LabyModProtocolService protocolService, @NotNull final PermissionRegistry permissionRegistry, @NotNull final ResourceLocationFactory factory) {
        this.hasRedeemedQuickPlayCommand = false;
        this.protocolService = protocolService;
        this.servers = new ArrayList<AbstractServer>();
        this.minecraft = labyAPI.minecraft();
        this.permissionRegistry = permissionRegistry;
        this.locationFactory = factory;
        this.publicServerListService = new DefaultPublicServerListService();
        this.registerServer(new HypixelServer());
    }
    
    @Nullable
    @Override
    public StorageServerData getCurrentStorageServerData() {
        return this.currentStorageServerData;
    }
    
    @Override
    public void setCurrentStorageServerData(@Nullable final StorageServerData currentStorageServerData) {
        this.currentStorageServerData = currentStorageServerData;
    }
    
    @Override
    public void loginOrServerSwitch(final ServerData serverData) {
        if (serverData == null) {
            this.registerIncomingPluginChannels();
            this.protocolService.sendLabyModPacket((Packet)new VersionLoginPacket(BuildData.version().toString()));
            return;
        }
        AbstractServer.LoginPhase loginPhase;
        if (!this.isConnected()) {
            this.setCurrentServerData(serverData);
            Laby.fireEvent(new ServerJoinEvent(ConnectableServerData.from(serverData)));
            Laby.fireEvent(new NetworkLoginEvent(serverData));
            loginPhase = AbstractServer.LoginPhase.LOGIN;
        }
        else {
            Laby.fireEvent(new NetworkServerSwitchEvent());
            Laby.fireEvent(new SubServerSwitchEvent(ConnectableServerData.from(serverData)));
            loginPhase = AbstractServer.LoginPhase.SWITCH;
        }
        ((DefaultPermissionRegistry)this.permissionRegistry).resetPermissions();
        final ServerAddress address = serverData.address();
        this.handleServerLoginOrSwitch(loginPhase, address);
        this.registerIncomingPluginChannels();
        this.protocolService.sendLabyModPacket((Packet)new VersionLoginPacket(BuildData.version().toString()));
        final IntaveClientConfigPacket packet = new IntaveClientConfigPacket();
        packet.readConfig();
        LabyMod.references().intaveProtocol().sendPacket((Packet)packet);
        if (!this.hasRedeemedQuickPlayCommand) {
            for (final String argument : Laby.labyAPI().labyModLoader().getArguments()) {
                if (argument.startsWith("--quickPlayCommand")) {
                    final String[] split = argument.split("=");
                    if (split.length != 2) {
                        continue;
                    }
                    final String command = split[1];
                    this.hasRedeemedQuickPlayCommand = true;
                    Task.builder(() -> this.minecraft.chatExecutor().chat(command)).delay(2L, TimeUnit.SECONDS).build().execute();
                }
            }
        }
    }
    
    protected void registerIncomingPluginChannels() {
        final StringBuilder channelsBuilder = new StringBuilder();
        final PayloadRegistry payloadRegistry = Laby.references().payloadRegistry();
        payloadRegistry.forEachRegisteredChannel(channelIdentifier -> this.appendProtocolChannel(channelsBuilder, channelIdentifier));
        this.sendPayload(DefaultAbstractServerController.REGISTER_CHANNEL, channelsBuilder.toString().getBytes());
    }
    
    private void appendProtocolChannel(final StringBuilder stringBuilder, final ResourceLocation identifier) {
        this.appendProtocolChannel(stringBuilder, identifier.getNamespace(), identifier.getPath());
    }
    
    private void appendProtocolChannel(final StringBuilder stringBuilder, final String namespace, final String path) {
        stringBuilder.append(namespace).append(':').append(path).append('\0');
    }
    
    @Override
    public void disconnect(final Phase phase) {
        if (!this.isConnected()) {
            return;
        }
        if (phase == Phase.POST) {
            ServerData serverData = (this.currentStorageServerData == null) ? this.currentServerData : this.currentStorageServerData;
            if (serverData == null) {
                serverData = ConnectableServerData.builder().address("unknown").build();
            }
            if (LabyConfigProvider.INSTANCE.get().multiplayer().clearTitles().get()) {
                this.minecraft.clearTitle();
            }
            Laby.fireEvent(new ServerDisconnectEvent(ConnectableServerData.from(serverData)));
        }
        Laby.fireEvent(new NetworkDisconnectEvent(phase));
        this.handleServerDisconnect(phase);
        ((DefaultPermissionRegistry)this.permissionRegistry).resetPermissions();
        if (phase == Phase.POST) {
            this.setCurrentServerData(null);
        }
    }
    
    @ApiStatus.Internal
    public void payloadReceive(final String namespace, final String path, final byte[] payload) {
        this.payloadReceive(this.locationFactory.create(namespace, path), payload);
    }
    
    @ApiStatus.Internal
    public void payloadReceive(final ResourceLocation identifier, final byte[] payload) {
        Laby.fireEvent(NetworkPayloadEvent.createReceive(identifier, payload));
    }
    
    @Override
    public boolean handleCustomPayload(final PayloadChannelIdentifier identifier, final byte[] payload) {
        try {
            return this.protocolService.readPayload(identifier, payload);
        }
        catch (final Throwable throwable) {
            DefaultAbstractServerController.LOGGING.error("Error occurred while handling custom payload (ChannelId: {})", identifier, throwable);
            return false;
        }
    }
    
    @Override
    public ServerData getCurrentServerData() {
        return this.currentServerData;
    }
    
    @Override
    public void setCurrentServerData(final ServerData currentServerData) {
        Laby.fireEvent(new NetworkSwitchEvent(this.currentServerData, currentServerData));
        if (this.currentServerData != null && currentServerData != null) {
            Laby.fireEvent(new ServerSwitchEvent(ConnectableServerData.from(this.currentServerData), ConnectableServerData.from(currentServerData)));
        }
        if ((this.currentServerData = currentServerData) == null) {
            this.setCurrentStorageServerData(null);
        }
    }
    
    @Override
    public ScreenWrapper createNewServerScreen(final Consumer<StorageServerData> callback) {
        final StorageServerData data = StorageServerData.of(this.getDefaultServerName(), "");
        return this.createEditServerScreen(data, callback);
    }
    
    @Override
    public void registerServer(final AbstractServer server) {
        this.servers.add(server);
    }
    
    protected abstract String getDefaultServerName();
    
    @Override
    public PublicServerListService publicServerListService() {
        return this.publicServerListService;
    }
    
    @Override
    public void executeLoginExecutor() {
        if (this.loginExecutor == null) {
            return;
        }
        ThreadSafe.executeOnRenderThread(() -> {
            this.loginExecutor.run();
            this.loginExecutor = null;
        });
    }
    
    protected final String getLegacyChannelName(String channelName) {
        if (channelName.equalsIgnoreCase("minecraft:brand")) {
            channelName = "MC|Brand";
        }
        else if (channelName.equalsIgnoreCase("minecraft:bopen")) {
            channelName = "MC|BOpen";
        }
        else if (channelName.equalsIgnoreCase("minecraft:trilist")) {
            channelName = "MC|TriList";
        }
        else if (channelName.equalsIgnoreCase("minecraft:register")) {
            channelName = "REGISTER";
        }
        else if (channelName.equalsIgnoreCase("minecraft:unregister")) {
            channelName = "UNREGISTER";
        }
        return channelName;
    }
    
    private void handleServerLoginOrSwitch(final AbstractServer.LoginPhase phase, final ServerAddress address) {
        for (final AbstractServer server : this.servers) {
            if (server.isServer(address)) {
                server.loginOrSwitch(phase);
            }
        }
    }
    
    private void handleServerDisconnect(final Phase phase) {
        for (final AbstractServer server : this.servers) {
            if (this.currentServerData == null) {
                break;
            }
            if (this.currentServerData.address() == null) {
                break;
            }
            if (!server.isServer(this.currentServerData.address())) {
                continue;
            }
            server.disconnect(phase);
        }
    }
    
    static {
        REGISTER_CHANNEL = ResourceLocation.create("minecraft", "register");
        LOGGING = DefaultLoggingFactory.createLogger(ServerController.class);
    }
}
