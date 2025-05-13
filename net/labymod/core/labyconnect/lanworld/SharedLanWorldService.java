// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.lanworld;

import java.nio.file.Path;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.session.Session;
import java.util.function.Supplier;
import net.labymod.labypeer.client.ClientConfig;
import net.labymod.api.event.Phase;
import net.labymod.api.server.event.IntegratedServerPlayerDisconnectEvent;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.client.world.WorldLeaveEvent;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.network.ClientPacketListener;
import java.util.concurrent.TimeUnit;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.labypeer.client.tunnel.config.TunnelConfig;
import net.labymod.api.notification.Notification;
import net.labymod.api.util.I18n;
import net.labymod.api.util.TextFormat;
import net.labymod.labypeer.client.tunnel.TunnelOpenException;
import net.labymod.labypeer.client.tunnel.handler.TunnelHandler;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.labypeer.client.tunnel.config.ClientTunnelConfig;
import net.labymod.api.util.io.IOUtil;
import net.labymod.labypeer.client.tunnel.config.ServerTunnelConfig;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.util.Iterator;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.server.LocalWorld;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayInviteLanWorld;
import java.util.Base64;
import java.io.IOException;
import java.nio.file.Files;
import javax.inject.Inject;
import net.labymod.core.main.LabyMod;
import net.labymod.labypeer.client.ice.credentials.IceCredentialsTransmitter;
import java.util.function.BiConsumer;
import net.labymod.labypeer.logger.LabyPeerLogger;
import java.util.Objects;
import net.labymod.api.Laby;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import net.labymod.labypeer.client.tunnel.PeerTunnel;
import java.util.UUID;
import java.util.Map;
import net.labymod.api.server.IntegratedServer;
import net.labymod.labypeer.client.PeerClient;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class SharedLanWorldService
{
    private static final Logging LOGGER;
    public static final String TRANSLATION_PREFIX = "labymod.activity.labyconnect.chat.lanworld.";
    private final PeerClient peerClient;
    private final IntegratedServer integratedServer;
    private final Map<UUID, PeerTunnel> openTunnels;
    private final List<SharedLanWorldInvite> invites;
    private final LabyConnectIceCredentialsTransmitter credentialsTransmitter;
    private final CandidateHarvesterService harvesterService;
    private boolean host;
    
    @Inject
    public SharedLanWorldService(final IntegratedServer integratedServer) {
        this.openTunnels = new ConcurrentHashMap<UUID, PeerTunnel>();
        this.invites = new ArrayList<SharedLanWorldInvite>();
        this.credentialsTransmitter = new LabyConnectIceCredentialsTransmitter();
        this.integratedServer = integratedServer;
        Object errorOnly;
        if (Laby.labyAPI().labyModLoader().isLabyModDevelopmentEnvironment()) {
            errorOnly = new DebugLabyPeerLogger();
        }
        else {
            final Logging logger = SharedLanWorldService.LOGGER;
            Objects.requireNonNull(logger);
            errorOnly = LabyPeerLogger.errorOnly((BiConsumer)logger::error);
        }
        this.peerClient = new PeerClient((LabyPeerLogger)errorOnly, (IceCredentialsTransmitter)this.credentialsTransmitter);
        this.harvesterService = LabyMod.references().candidateHarvesterService();
        this.updateConfig();
    }
    
    public SharedLanWorldInvite createInvite(final UUID sender, final UUID receiver) {
        final LocalWorld world = Laby.references().integratedServer().getLocalWorld();
        if (world == null || !world.isOpen()) {
            throw new IllegalStateException("Currently no LAN world is opened");
        }
        final PacketPlayInviteLanWorld.LanWorldOptions options = new PacketPlayInviteLanWorld.LanWorldOptions(world.worldName(), (byte)world.gameMode().getId(), world.allowCheats(), world.getScreenshotFile().map(path -> {
            try {
                return Files.readAllBytes(path);
            }
            catch (final IOException e) {
                return null;
            }
        }).map(bytes -> "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes)).orElse(null));
        return this.pushInvite(sender, receiver, options, TimeUtil.getMillis());
    }
    
    public SharedLanWorldInvite pushInvite(final UUID sender, final UUID receiver, final PacketPlayInviteLanWorld.LanWorldOptions options, final long timestamp) {
        final SharedLanWorldInvite invite = new SharedLanWorldInvite(sender, receiver, options, timestamp);
        this.dropInviteOfSender(sender, SharedLanWorldInvite.InviteStatus.EXPIRED);
        this.invites.add(invite);
        final LabyConnectSession session = Laby.references().labyConnect().getSession();
        if (session != null) {
            final Friend friend = session.getFriend(sender);
            if (friend != null) {
                friend.chat().addMessage(friend, invite.toString(), timestamp);
            }
        }
        return invite;
    }
    
    public SharedLanWorldInvite dropInviteOfSender(final UUID sender, final SharedLanWorldInvite.InviteStatus reason) {
        final Iterator<SharedLanWorldInvite> iterator = this.invites.iterator();
        while (iterator.hasNext()) {
            final SharedLanWorldInvite invite = iterator.next();
            if (!invite.getSender().equals(sender)) {
                continue;
            }
            invite.setStatus(reason);
            iterator.remove();
            return invite;
        }
        return null;
    }
    
    public SharedLanWorldInvite dropInviteOfReceiver(final UUID receiver, final SharedLanWorldInvite.InviteStatus reason) {
        final Iterator<SharedLanWorldInvite> iterator = this.invites.iterator();
        while (iterator.hasNext()) {
            final SharedLanWorldInvite invite = iterator.next();
            if (!invite.getReceiver().equals(receiver)) {
                continue;
            }
            invite.setStatus(reason);
            iterator.remove();
            return invite;
        }
        return null;
    }
    
    public void establishTunnelFor(final boolean host, final UUID uniqueId, final String username) {
        this.host = host;
        SharedLanWorldInvite invite;
        if (host) {
            invite = this.dropInviteOfReceiver(uniqueId, SharedLanWorldInvite.InviteStatus.ACCEPTED);
        }
        else {
            invite = this.dropInviteOfSender(uniqueId, SharedLanWorldInvite.InviteStatus.ACCEPTED);
        }
        TunnelConfig tunnelConfig;
        if (this.host) {
            final LocalWorld world = Laby.references().integratedServer().getLocalWorld();
            if (world == null || !world.isOpen()) {
                return;
            }
            tunnelConfig = (TunnelConfig)new ServerTunnelConfig(new InetSocketAddress(InetAddress.getLoopbackAddress(), world.port()));
        }
        else {
            int port;
            try {
                port = IOUtil.getFreePort();
            }
            catch (final IOException e) {
                e.printStackTrace();
                port = 50508;
            }
            tunnelConfig = (TunnelConfig)new ClientTunnelConfig(new InetSocketAddress(InetAddress.getLoopbackAddress(), port));
        }
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
            PopupWidget popup;
            if (!this.host) {
                Laby.labyAPI().serverController().leaveServer();
                popup = PopupWidget.builder().title(Component.text("Connecting...")).notClosable().build();
                popup.displayInOverlay();
            }
            else {
                popup = null;
            }
            try {
                final Throwable throwable;
                this.peerClient.openTunnel(uniqueId, tunnelConfig).handle((tunnel, throwable) -> {
                    if (popup != null) {
                        Laby.labyAPI().minecraft();
                        Objects.requireNonNull(popup);
                        final Minecraft minecraft;
                        minecraft.executeOnRenderThread(popup::close);
                    }
                    if (tunnel != null) {
                        final PeerTunnel oldTunnel = this.openTunnels.remove(uniqueId);
                        if (oldTunnel != null) {
                            try {
                                oldTunnel.close();
                            }
                            catch (final IOException exception) {
                                exception.printStackTrace();
                            }
                        }
                        this.openTunnels.put(uniqueId, tunnel);
                        tunnel.setTunnelHandler((TunnelHandler)new SharedLanWorldHandler(this, tunnel, username));
                        if (!uniqueId.equals(this.peerClient.config().uniqueId()) && tunnelConfig instanceof ClientTunnelConfig) {
                            Laby.labyAPI().minecraft().executeOnRenderThread(() -> Laby.labyAPI().serverController().joinServer("localhost", ((ClientTunnelConfig)tunnelConfig).hostAddress().getPort()));
                        }
                        else {
                            this.queueJoinTimeoutTask(uniqueId, tunnel);
                        }
                    }
                    else {
                        String errorMessage;
                        if (throwable != null) {
                            if (throwable instanceof final TunnelOpenException e2) {
                                errorMessage = ((e2.extendedError() != null) ? e2.extendedError() : I18n.translate("labymod.activity.labyconnect.chat.lanworld.connection.failed.error." + TextFormat.SNAKE_CASE.toLowerCamelCase(e2.error().name()), new Object[0]));
                            }
                            else {
                                errorMessage = throwable.getClass().getSimpleName();
                            }
                            SharedLanWorldService.LOGGER.error("Failed to open tunnel to {}", uniqueId, throwable);
                        }
                        else {
                            errorMessage = "unknown error";
                            SharedLanWorldService.LOGGER.error("Failed to open tunnel to {}", uniqueId);
                        }
                        Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                            final TranslatableComponent description = Component.translatable("labymod.activity.labyconnect.chat.lanworld.connection.failed.text", Component.text(errorMessage));
                            Laby.labyAPI().notificationController().push(Notification.builder().title(Component.translatable("labymod.activity.labyconnect.chat.lanworld.connection.failed.title", new Component[0])).text(description).build());
                            if (invite != null) {
                                invite.setConnectError(description);
                            }
                            return;
                        });
                    }
                    return null;
                });
            }
            catch (final Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
    
    private void queueJoinTimeoutTask(final UUID uniqueId, final PeerTunnel tunnel) {
        Task.builder(() -> {
            if (!(!tunnel.isOpen())) {
                final ClientPacketListener clientPacketListener = Laby.labyAPI().minecraft().getClientPacketListener();
                if (clientPacketListener == null || clientPacketListener.getNetworkPlayerInfo(uniqueId) == null) {
                    this.openTunnels.remove(uniqueId);
                    try {
                        tunnel.close();
                    }
                    catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).delay(10L, TimeUnit.SECONDS).build().executeOnRenderThread();
    }
    
    @Nullable
    public SharedLanWorldInvite getInviteOfSender(final UUID sender) {
        for (final SharedLanWorldInvite invite : this.invites) {
            if (invite.getSender().equals(sender)) {
                return invite;
            }
        }
        return null;
    }
    
    @Subscribe
    public void onSessionUpdate(final SessionUpdateEvent event) {
        this.updateConfig();
    }
    
    @Subscribe
    public void onLabyConnectStateUpdate(final LabyConnectStateUpdateEvent event) {
        if (event.state() == LabyConnectState.OFFLINE) {
            this.invites.clear();
        }
        else {
            this.updateConfig();
        }
    }
    
    @Subscribe
    public void onWorldLeave(final WorldLeaveEvent event) {
        this.disconnected();
    }
    
    @Subscribe
    public void onDisconnect(final ServerDisconnectEvent event) {
        this.disconnected();
    }
    
    @Subscribe
    public void onKick(final ServerKickEvent event) {
        this.disconnected();
    }
    
    private void disconnected() {
        if (!this.openTunnels.isEmpty()) {
            this.closeAllConnections();
        }
        final UUID self = Laby.labyAPI().minecraft().sessionAccessor().getSession().getUniqueId();
        final Iterator<SharedLanWorldInvite> iterator = this.invites.iterator();
        while (iterator.hasNext()) {
            final SharedLanWorldInvite invite = iterator.next();
            if (!invite.getSender().equals(self)) {
                continue;
            }
            invite.setStatus(SharedLanWorldInvite.InviteStatus.EXPIRED);
            iterator.remove();
        }
    }
    
    @Subscribe
    public void onPlayerDisconnect(final IntegratedServerPlayerDisconnectEvent event) {
        if (event.phase() != Phase.POST || this.openTunnels.isEmpty()) {
            return;
        }
        final UUID uniqueId = event.profile().getUniqueId();
        if (uniqueId.equals(Laby.labyAPI().getUniqueId())) {
            return;
        }
        Task.builder(() -> this.closeConnection(uniqueId)).delay(1L, TimeUnit.SECONDS).build().execute();
    }
    
    public void inviteToLan(final Friend friend) {
        if (!friend.isOnline() || !this.canSendInvite(friend.getUniqueId())) {
            return;
        }
        disclaimerConfirmation(() -> this.invite(friend));
    }
    
    private void invite(final Friend friend) {
        this.integratedServer.requestLanWorld(world -> {
            if (world == null) {
                Laby.labyAPI().notificationController().push(Notification.builder().title(Component.translatable("labymod.activity.labyconnect.chat.lanworld.failed.title", new Component[0])).text(Component.translatable("labymod.activity.labyconnect.chat.lanworld.failed.text", new Component[0])).build());
            }
            else {
                final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
                if (session != null) {
                    Laby.labyAPI().minecraft().executeNextTick(() -> session.inviteToLanWorld(friend));
                }
            }
        });
    }
    
    private void updateConfig() {
        final Session session = Laby.labyAPI().minecraft().sessionAccessor().getSession();
        if (session != null) {
            final PeerClient peerClient = this.peerClient;
            final UUID uniqueId = session.getUniqueId();
            final CandidateHarvesterService harvesterService = this.harvesterService;
            Objects.requireNonNull(harvesterService);
            peerClient.setConfig(new ClientConfig(uniqueId, (Supplier)harvesterService::buildHarvesters));
        }
    }
    
    public void closeConnection(final UUID uniqueId) {
        final PeerTunnel tunnel = this.openTunnels.remove(uniqueId);
        if (tunnel != null) {
            try {
                tunnel.close();
            }
            catch (final IOException e) {
                e.printStackTrace();
            }
        }
        if (this.openTunnels.isEmpty()) {
            this.host = false;
        }
    }
    
    public Map<UUID, PeerTunnel> getOpenTunnels() {
        return this.openTunnels;
    }
    
    public void closeAllConnections() {
        for (final PeerTunnel tunnel : this.openTunnels.values()) {
            try {
                tunnel.close();
            }
            catch (final IOException e) {
                e.printStackTrace();
            }
        }
        this.openTunnels.clear();
        this.host = false;
    }
    
    public boolean canSendInvite(final UUID uniqueId) {
        return !this.openTunnels.containsKey(uniqueId);
    }
    
    public boolean isHost() {
        return this.host;
    }
    
    public UUID getCurrentHost() {
        if (this.openTunnels.isEmpty()) {
            return null;
        }
        if (this.host) {
            return Laby.labyAPI().minecraft().sessionAccessor().getSession().getUniqueId();
        }
        return this.openTunnels.keySet().stream().findFirst().get();
    }
    
    public boolean hasOpenTunnel() {
        return !this.openTunnels.isEmpty();
    }
    
    public LabyConnectIceCredentialsTransmitter credentialsTransmitter() {
        return this.credentialsTransmitter;
    }
    
    public static void disclaimerConfirmation(final Pressable callback) {
        final PopupWidget popupWidget = PopupWidget.builder().title(Component.translatable("labymod.activity.labyconnect.chat.lanworld.invite.confirm.title", new Component[0])).text(Component.translatable("labymod.activity.labyconnect.chat.lanworld.invite.confirm.text", new Component[0])).confirmCallback(callback).build();
        popupWidget.displayInOverlay();
    }
    
    static {
        LOGGER = Logging.create(SharedLanWorldService.class);
    }
}
