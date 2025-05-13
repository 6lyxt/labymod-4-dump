// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.session;

import net.labymod.api.util.gson.UUIDTypeAdapter;
import com.google.gson.GsonBuilder;
import net.labymod.api.client.gui.screen.activity.Activity;
import java.lang.reflect.Type;
import net.labymod.api.client.session.model.MojangTextureChangedResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketUserTracker;
import org.jetbrains.annotations.NotNull;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.sections.LabyConnectDirectChatActivity;
import net.labymod.api.util.ThreadSafe;
import org.jetbrains.annotations.Nullable;
import net.labymod.core.event.labymod.PacketAddonDevelopmentEvent;
import net.labymod.core.labyconnect.protocol.packets.PacketActionRequestResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketActionPlayResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketUpdateCosmetics;
import net.labymod.core.labyconnect.protocol.packets.PacketMojangStatus;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginTime;
import net.labymod.core.labyconnect.protocol.packets.PacketServerMessage;
import net.labymod.core.labyconnect.protocol.packets.PacketNotAllowed;
import net.labymod.core.labyconnect.lanworld.SharedLanWorldService;
import net.labymod.core.labyconnect.protocol.packets.PacketIceCredentials;
import net.labymod.labypeer.ice.IceCredentials;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRejectLanWorldInvite;
import net.labymod.api.labyconnect.LanWorldRejectReason;
import net.labymod.core.labyconnect.lanworld.SharedLanWorldInvite;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayAcceptLanWorldInvite;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayInviteLanWorld;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayFriendPlayingOn;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectOutgoingFriendRequestAddResponseEvent;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRequestAddFriendResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayTyping;
import net.labymod.api.labyconnect.protocol.model.chat.ChatMessage;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import net.labymod.core.labyconnect.protocol.model.chat.DefaultTextChatMessage;
import net.labymod.core.labyconnect.protocol.model.chat.DefaultChat;
import net.labymod.api.labyconnect.protocol.model.friend.ServerInfo;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendServerEvent;
import java.util.Objects;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayFriendStatus;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendRemoveEvent;
import net.labymod.api.util.CollectionHelper;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRequestRemove;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectBroadcastEvent;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayBroadcastPayload;
import net.labymod.core.main.user.DefaultGameUserService;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectPlayEmoteEvent;
import net.labymod.api.event.labymod.user.UserFamiliarEvent;
import net.labymod.api.user.GameUser;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.labyconnect.protocol.packets.PacketUserBadge;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendStatusEvent;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultServerInfo;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayPlayerOnline;
import net.labymod.core.labyconnect.protocol.packets.PacketDisconnect;
import net.labymod.api.event.labymod.labyconnect.session.login.LabyConnectIncomingFriendRequestAddBulkEvent;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectIncomingFriendRequestAddEvent;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginRequest;
import java.util.Iterator;
import net.labymod.api.event.labymod.labyconnect.session.login.LabyConnectFriendAddBulkEvent;
import java.util.ArrayList;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendAddEvent;
import java.util.Collection;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginFriend;
import net.labymod.core.labyconnect.util.Snooper;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginComplete;
import com.google.gson.JsonElement;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonMessage;
import com.google.gson.JsonObject;
import net.labymod.api.labyconnect.TokenStorage;
import net.labymod.core.labyconnect.protocol.packets.PacketPong;
import net.labymod.core.labyconnect.protocol.packets.PacketPing;
import net.labymod.core.labyconnect.protocol.packets.PacketKick;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.labymod.api.client.session.MinecraftAuthenticator;
import javax.crypto.SecretKey;
import java.security.PublicKey;
import net.labymod.core.labyconnect.pipeline.PacketEncryptingEncoder;
import io.netty.channel.ChannelHandler;
import net.labymod.core.labyconnect.pipeline.PacketEncryptingDecoder;
import java.security.Key;
import net.labymod.core.labyconnect.protocol.packets.PacketEncryptionResponse;
import java.math.BigInteger;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectDisconnectEvent;
import net.labymod.core.labyconnect.util.CryptManager;
import net.labymod.core.labyconnect.protocol.packets.PacketEncryptionRequest;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectRejectAuthenticationEvent;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginOptions;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginData;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginVersion;
import net.labymod.core.labyconnect.protocol.packets.PacketHelloPong;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectUpdateSettingEvent;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayChangeOptions;
import java.util.Calendar;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayServerStatusUpdate;
import net.labymod.api.client.network.server.ServerData;
import java.io.ByteArrayOutputStream;
import net.labymod.core.labyconnect.protocol.packets.PacketMessage;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.labyconnect.protocol.model.chat.TextChatMessage;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonDevelopment;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectOutgoingFriendRequestRemoveEvent;
import net.labymod.core.labyconnect.protocol.model.request.DefaultOutgoingFriendRequest;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayDenyFriendRequest;
import net.labymod.core.labyconnect.protocol.model.request.DefaultIncomingFriendRequest;
import net.labymod.api.event.Event;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectIncomingFriendRequestRemoveEvent;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRequestAddFriend;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayFriendRemove;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultFriend;
import net.labymod.api.util.math.Direction;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.packets.PacketActionPlay;
import net.labymod.core.labyconnect.protocol.packets.PacketActionBroadcast;
import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import net.labymod.core.labyconnect.session.message.FriendRequestResponseMessageListener;
import net.labymod.core.labyconnect.session.message.OutgoingFriendRequestsMessageListener;
import net.labymod.core.labyconnect.session.message.WebNotificationMessageListener;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.core.labyconnect.session.message.UpdateTokenMessageListener;
import net.labymod.core.labyconnect.session.message.TexturesUpdatedMessageListener;
import net.labymod.core.labyconnect.session.message.SkinApplyMessageListener;
import it.unimi.dsi.fastutil.ints.IntList;
import net.labymod.core.labyconnect.session.message.RolesMessageListener;
import net.labymod.core.labyconnect.session.message.LanguageFlagsMessageListener;
import net.labymod.core.labyconnect.session.message.IncentiveMessageListener;
import net.labymod.core.labyconnect.session.message.DashboardPinMessageListener;
import net.labymod.api.util.function.Consumers;
import net.labymod.core.labyconnect.session.spray.V2SprayProtocol;
import net.labymod.core.labyconnect.session.spray.V1SprayProtocol;
import net.labymod.core.main.LabyMod;
import net.labymod.core.labyconnect.protocol.model.DefaultUser;
import net.labymod.api.Laby;
import java.util.HashMap;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.labymod.core.labyconnect.session.message.MessageListener;
import java.util.function.Consumer;
import net.labymod.api.user.GameUserService;
import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;
import net.labymod.api.labyconnect.protocol.model.request.IncomingFriendRequest;
import java.util.UUID;
import java.util.Map;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import java.util.List;
import net.labymod.core.labyconnect.session.spray.SprayProtocol;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.client.session.Session;
import net.labymod.api.LabyAPI;
import net.labymod.core.labyconnect.DefaultLabyConnect;
import net.labymod.api.util.logging.Logging;
import com.google.gson.Gson;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.core.labyconnect.protocol.PacketHandler;

public class DefaultLabyConnectSession extends PacketHandler implements LabyConnectSession
{
    public static final Gson GSON;
    private static final int SPRAY_V1_PACKET_SIZE = 26;
    private static final int SPRAY_V2_PACKET_SIZE = 38;
    private static final Logging LOGGER;
    private final DefaultLabyConnect labyConnect;
    private final LabyAPI labyApi;
    private final Session session;
    private final User self;
    private final IntArrayList roles;
    private final Int2ObjectMap<SprayProtocol> sprayProtocols;
    private final List<Friend> friends;
    private final Map<UUID, Friend> uuid2Friend;
    private final List<Friend> unmodifiableFriends;
    private final List<IncomingFriendRequest> incomingRequests;
    private final List<IncomingFriendRequest> unmodifiableIncomingRequests;
    private final List<OutgoingFriendRequest> outgoingFriendRequests;
    private final List<OutgoingFriendRequest> unmodifiableOutgoingFriendRequests;
    private final GameUserService gameUserService;
    private final ApplyTextureController applyTextureController;
    private final DefaultTokenStorage tokenStorage;
    private Consumer<String> dashboardPinCallback;
    private boolean premium;
    private boolean connectionEstablished;
    private boolean authenticated;
    private final Map<UUID, byte[]> fileStorage;
    private final Map<String, MessageListener> messageListeners;
    
    public DefaultLabyConnectSession(final DefaultLabyConnect labyConnect, final Session session) {
        this.roles = new IntArrayList();
        this.sprayProtocols = (Int2ObjectMap<SprayProtocol>)new Int2ObjectOpenHashMap();
        this.friends = new CopyOnWriteArrayList<Friend>();
        this.uuid2Friend = new ConcurrentHashMap<UUID, Friend>();
        this.unmodifiableFriends = Collections.unmodifiableList((List<? extends Friend>)this.friends);
        this.incomingRequests = new CopyOnWriteArrayList<IncomingFriendRequest>();
        this.unmodifiableIncomingRequests = Collections.unmodifiableList((List<? extends IncomingFriendRequest>)this.incomingRequests);
        this.outgoingFriendRequests = new CopyOnWriteArrayList<OutgoingFriendRequest>();
        this.unmodifiableOutgoingFriendRequests = Collections.unmodifiableList((List<? extends OutgoingFriendRequest>)this.outgoingFriendRequests);
        this.fileStorage = new HashMap<UUID, byte[]>();
        this.messageListeners = new HashMap<String, MessageListener>();
        this.labyConnect = labyConnect;
        this.labyApi = Laby.labyAPI();
        this.session = session;
        this.premium = session.isPremium();
        this.connectionEstablished = false;
        this.authenticated = false;
        this.self = new DefaultUser(session.getUniqueId(), session.getUsername());
        this.gameUserService = this.labyApi.gameUserService();
        this.applyTextureController = LabyMod.references().applyTextureController();
        this.tokenStorage = (DefaultTokenStorage)Laby.references().tokenStorage();
        this.sprayProtocols.put(1, (Object)new V1SprayProtocol());
        this.sprayProtocols.put(2, (Object)new V2SprayProtocol());
        this.registerMessageListeners();
    }
    
    private void registerMessageListeners() {
        this.messageListeners.put("dashboard_pin", new DashboardPinMessageListener(pin -> Consumers.accept(this.dashboardPinCallback, pin)));
        this.messageListeners.put("incentive", new IncentiveMessageListener(this.self));
        this.messageListeners.put("language_flags", new LanguageFlagsMessageListener(this.gameUserService));
        this.messageListeners.put("roles", new RolesMessageListener((IntList)this.roles, this.self));
        this.messageListeners.put("skin_apply", new SkinApplyMessageListener(this.applyTextureController));
        this.messageListeners.put("textures_updated", new TexturesUpdatedMessageListener(this.applyTextureController));
        this.messageListeners.put("unauthenticated", message -> this.resetAuthentication());
        this.messageListeners.put("update_token", new UpdateTokenMessageListener(this.labyConnect, this.self, this.tokenStorage));
        this.messageListeners.put("web_notification", new WebNotificationMessageListener());
        this.messageListeners.put("outgoing_friend_requests", new OutgoingFriendRequestsMessageListener(this.labyConnect, this.outgoingFriendRequests));
        this.messageListeners.put("friend_request_response", new FriendRequestResponseMessageListener(this.labyConnect, this.outgoingFriendRequests));
        this.messageListeners.put("rplace", LabyMod.references().rPlaceRegistry());
    }
    
    @Override
    public void playEmote(final short id) {
        final byte[] data = ByteBuffer.wrap(new byte[2]).order(ByteOrder.LITTLE_ENDIAN).putShort(id).array();
        this.labyConnect.sendPacket(new PacketActionPlay(-1, PacketActionBroadcast.ActionType.EMOTE, data));
    }
    
    @Override
    public void spray(final short id, final int protocolVersion, final double x, final double y, final double z, final Direction direction, final float rotation) {
        final ByteBuffer buffer = ByteBuffer.allocate(38).order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort(id);
        buffer.putInt(protocolVersion);
        buffer.putDouble(x);
        buffer.putDouble(y);
        buffer.putDouble(z);
        buffer.putInt(direction.getIndex());
        buffer.putFloat(rotation);
        buffer.flip();
        final byte[] data = new byte[38];
        buffer.get(data);
        this.labyConnect.sendPacket(new PacketActionPlay(-1, PacketActionBroadcast.ActionType.SPRAY, data));
    }
    
    @Override
    public void removeFriend(final UUID uniqueId) {
        final Friend friend = this.getFriend(uniqueId);
        if (friend instanceof final DefaultFriend defaultFriend) {
            this.labyConnect.sendPacket(new PacketPlayFriendRemove(defaultFriend));
        }
    }
    
    @Override
    public void sendFriendRequest(final String username) {
        this.labyConnect.sendPacket(new PacketPlayRequestAddFriend(username));
    }
    
    @Override
    public void acceptFriendRequest(final UUID uniqueId) {
        final IncomingFriendRequest request = this.getIncomingRequest(uniqueId);
        if (request == null) {
            return;
        }
        this.labyConnect.sendPacket(new PacketPlayRequestAddFriend(request.getName()));
        this.incomingRequests.remove(request);
        this.labyConnect.fireEventSync(new LabyConnectIncomingFriendRequestRemoveEvent(this.labyConnect, request));
    }
    
    @Override
    public void declineFriendRequest(final UUID uniqueId) {
        final IncomingFriendRequest incomingRequest = this.getIncomingRequest(uniqueId);
        if (incomingRequest != null) {
            this.labyConnect.sendPacket(new PacketPlayDenyFriendRequest((DefaultUser)incomingRequest));
            this.incomingRequests.remove(incomingRequest);
            this.labyConnect.fireEventSync(new LabyConnectIncomingFriendRequestRemoveEvent(this.labyConnect, incomingRequest));
        }
        final OutgoingFriendRequest outgoingRequest = this.getOutgoingRequest(uniqueId);
        if (outgoingRequest != null) {
            this.labyConnect.sendPacket(new PacketPlayDenyFriendRequest((DefaultUser)outgoingRequest));
            this.outgoingFriendRequests.remove(outgoingRequest);
            this.labyConnect.fireEventSync(new LabyConnectOutgoingFriendRequestRemoveEvent(this.labyConnect, outgoingRequest));
        }
    }
    
    @Override
    public void sendAddonDevelopment(final String key, final UUID[] receivers, final byte[] data) {
        this.labyConnect.sendPacket(new PacketAddonDevelopment(this.session.getUniqueId(), receivers, key, data));
    }
    
    @Override
    public void sendChatMessage(final UUID chatIdentifier, final TextChatMessage message) {
        final Friend friend = this.getFriend(chatIdentifier);
        if (!(friend instanceof DefaultFriend)) {
            return;
        }
        this.labyConnect.sendPacket(new PacketMessage((DefaultUser)this.self, (DefaultUser)friend, message.getRawMessage(), 0L, 0.0, TimeUtil.getCurrentTimeMillis()));
    }
    
    @Override
    public UUID sendChatFile(final UUID chatIdentifier, final String type, final byte[] data) {
        final Friend friend = this.getFriend(chatIdentifier);
        if (!(friend instanceof DefaultFriend)) {
            return null;
        }
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final UUID identifier = UUID.randomUUID();
        out.write(ByteBuffer.allocate(16).putLong(identifier.getMostSignificantBits()).putLong(identifier.getLeastSignificantBits()).array(), 0, 16);
        out.write(data, 0, data.length);
        if (!this.storeFile(identifier, data)) {
            return null;
        }
        this.sendAddonDevelopment("labymod:file", new UUID[] { chatIdentifier }, out.toByteArray());
        friend.chat().sendMessage("file://" + String.valueOf(identifier) + "?type=" + type);
        return identifier;
    }
    
    @Override
    public void sendCurrentServer(final ServerData serverData, final String gameMode, final boolean viaServerList) {
        final PacketPlayServerStatusUpdate packet = new PacketPlayServerStatusUpdate(serverData.address().getHost(), serverData.address().getPort(), gameMode, viaServerList);
        this.labyConnect.sendPacket(packet);
    }
    
    @Override
    public void sendLeaveCurrentServer() {
        this.labyConnect.sendPacket(new PacketPlayServerStatusUpdate("", 0, "", false));
    }
    
    @Override
    public void sendSettings() {
        final LabyConnectConfigAccessor config = this.labyConnect.configProvider().get();
        final boolean showServer = config.showConnectedServer().get();
        final UserStatus status = config.onlineStatus().get();
        this.self.updateStatus(config.onlineStatus().get());
        this.labyConnect.sendPacket(new PacketPlayChangeOptions(showServer, status, Calendar.getInstance().getTimeZone()));
        this.labyConnect.fireEventSync(new LabyConnectUpdateSettingEvent(this.labyConnect, config));
    }
    
    @Override
    protected void handlePacket(final Packet packet) {
        this.connectionEstablished = true;
        super.handlePacket(packet);
        this.labyConnect.keepAlive();
    }
    
    @Override
    public void handle(final PacketHelloPong packet) {
        this.labyConnect.sendPacket(new PacketLoginVersion(29, this.labyApi.minecraft().getVersionId() + "_" + this.labyApi.getVersion(), this.labyApi.minecraft().getProtocolVersion()));
        if (this.session.isPremium()) {
            this.labyConnect.updateState(LabyConnectState.LOGIN);
            final LabyConnectConfigAccessor config = this.labyConnect.configProvider().get();
            final boolean showServer = config.showConnectedServer().get();
            final UserStatus status = config.onlineStatus().get();
            this.labyConnect.sendPacket(new PacketLoginData(this.session.getUniqueId(), this.session.getUsername(), ""));
            this.self.updateStatus(config.onlineStatus().get());
            this.labyConnect.sendPacket(new PacketLoginOptions(showServer, status, Calendar.getInstance().getTimeZone()));
        }
        else {
            this.labyConnect.fireEventSync(new LabyConnectRejectAuthenticationEvent(this.labyConnect));
        }
        this.labyConnect.keepAlive();
    }
    
    @Override
    public void handle(final PacketEncryptionRequest encryptionRequest) {
        try {
            final PublicKey publicKey = CryptManager.decodePublicKey(encryptionRequest.getPublicKey());
            final SecretKey secretKey = CryptManager.createNewSharedKey();
            final String serverId = encryptionRequest.getServerId();
            final MinecraftAuthenticator authenticator = this.labyApi.minecraft().authenticator();
            final byte[] bytes = CryptManager.getServerIdHash(serverId, publicKey, secretKey);
            if (bytes == null) {
                this.labyConnect.disconnect(LabyConnectDisconnectEvent.Initiator.CLIENT, "Failed to hash server id");
                return;
            }
            final String hash = new BigInteger(bytes).toString(16);
            final NioSocketChannel nio = this.labyConnect.getChannel();
            authenticator.joinServer(this.session, hash).exceptionally(throwable -> false).thenAccept(result -> {
                if (this.labyConnect.getChannel() == nio) {
                    final byte[] verifyTokenBuffer = this.labyConnect.getVerifyToken();
                    System.arraycopy(encryptionRequest.getVerifyToken(), 0, verifyTokenBuffer, 0, 4);
                    this.labyConnect.sendPacket(new PacketEncryptionResponse(secretKey, publicKey, verifyTokenBuffer), channel -> {
                        channel.pipeline().addBefore("splitter", "decrypt", (ChannelHandler)new PacketEncryptingDecoder(CryptManager.createNetCipherInstance(2, secretKey)));
                        channel.pipeline().addBefore("prepender", "encrypt", (ChannelHandler)new PacketEncryptingEncoder(CryptManager.createNetCipherInstance(1, secretKey)));
                    });
                }
            });
        }
        catch (final Exception e) {
            e.printStackTrace();
            this.labyConnect.disconnect(LabyConnectDisconnectEvent.Initiator.CLIENT, e.getMessage());
        }
    }
    
    @Override
    public void handle(final PacketKick packet) {
        this.labyConnect.disconnect(LabyConnectDisconnectEvent.Initiator.SERVER, packet.getReason());
    }
    
    @Override
    public void handle(final PacketPing packet) {
        this.labyConnect.sendPacket(new PacketPong());
        this.labyConnect.keepAlive();
        if (this.isAuthenticated()) {
            for (final TokenStorage.Purpose purpose : TokenStorage.Purpose.values()) {
                if (!this.tokenStorage.hasValidToken(purpose, this.self.getUniqueId())) {
                    final JsonObject payload = new JsonObject();
                    payload.addProperty("purpose", purpose.name());
                    this.labyConnect.sendPacket(new PacketAddonMessage("request_token", (JsonElement)payload));
                }
            }
        }
    }
    
    @Override
    public void handle(final PacketLoginComplete packet) {
        this.labyConnect.updateState(LabyConnectState.PLAY);
        this.authenticated = true;
        if (packet.getDashboardPin() != null && !packet.getDashboardPin().isEmpty()) {
            try {
                final JsonObject jsonObject = (JsonObject)DefaultLabyConnectSession.GSON.fromJson(packet.getDashboardPin(), (Class)JsonObject.class);
                if (jsonObject.has("pin")) {
                    final String pin = jsonObject.get("pin").getAsString();
                    final long expiresAt = jsonObject.get("expires_at").getAsLong();
                    this.tokenStorage.updateToken(TokenStorage.Purpose.CLIENT, this.self.getUniqueId(), new TokenStorage.Token(pin, expiresAt));
                }
            }
            catch (final Exception e) {
                e.printStackTrace();
            }
        }
        this.labyConnect.keepAlive();
        final ServerData serverData = this.labyApi.serverController().getCurrentServerData();
        if (serverData != null) {
            this.sendCurrentServer(serverData, "", false);
        }
        final Snooper snooper = this.labyConnect.getSnooper();
        if (snooper != null) {
            snooper.collect().thenAccept(obj -> this.labyConnect.sendPacket(new PacketAddonMessage("anonymous_statistics", (JsonElement)obj)));
        }
    }
    
    @Override
    public void handle(final PacketLoginFriend packet) {
        final List<DefaultFriend> friends = packet.getFriends();
        if (friends.isEmpty()) {
            return;
        }
        this.friends.addAll(friends);
        for (final DefaultFriend friend : friends) {
            this.uuid2Friend.put(friend.getUniqueId(), friend);
        }
        if (friends.size() == 1) {
            this.labyConnect.fireEventSync(new LabyConnectFriendAddEvent(this.labyConnect, friends.get(0)));
        }
        else {
            this.labyConnect.fireEventSync(new LabyConnectFriendAddBulkEvent(this.labyConnect, new ArrayList<Friend>(friends)));
        }
    }
    
    @Override
    public void handle(final PacketLoginRequest packet) {
        final List<DefaultIncomingFriendRequest> requests = packet.getRequests();
        if (requests.isEmpty()) {
            return;
        }
        this.incomingRequests.addAll(requests);
        if (requests.size() == 1) {
            this.labyConnect.fireEventSync(new LabyConnectIncomingFriendRequestAddEvent(this.labyConnect, requests.get(0)));
        }
        else {
            this.labyConnect.fireEventSync(new LabyConnectIncomingFriendRequestAddBulkEvent(this.labyConnect, new ArrayList<IncomingFriendRequest>(requests)));
        }
    }
    
    @Override
    public void handle(final PacketDisconnect packet) {
        this.labyConnect.disconnect(LabyConnectDisconnectEvent.Initiator.SERVER, packet.getReason());
    }
    
    @Override
    public void handle(final PacketPlayPlayerOnline packet) {
        final Friend friend = this.getFriend(packet.getPlayer().getUniqueId());
        if (friend instanceof final DefaultFriend defaultFriend) {
            final UserStatus previousState = friend.userStatus();
            defaultFriend.updateStatus(packet.getPlayer().userStatus());
            defaultFriend.setStatusMessage(packet.getPlayer().getStatusMessage());
            if (packet.getPlayer().getServer() != null) {
                defaultFriend.setServer((DefaultServerInfo)packet.getPlayer().getServer());
            }
            else {
                defaultFriend.setServer(null);
            }
            this.labyConnect.fireEventSync(new LabyConnectFriendStatusEvent(this.labyConnect, friend, previousState, friend.userStatus()));
        }
    }
    
    @Override
    public void handle(final PacketUserBadge packet) {
        final GameUserService service = this.labyApi.gameUserService();
        final byte[] ranks = packet.getRanks();
        final UUID[] uuids = packet.getUuids();
        for (int i = 0; i < uuids.length; ++i) {
            final DefaultGameUser gameUser = (DefaultGameUser)service.gameUser(uuids[i]);
            gameUser.setTag(GameUser.FAMILIAR);
            gameUser.setVisibleGroup(ranks[i]);
            this.labyConnect.fireEventSync(new UserFamiliarEvent(gameUser));
        }
    }
    
    @Override
    public void handle(final PacketActionBroadcast packet) {
        switch (packet.getType()) {
            case EMOTE: {
                final short emoteId = ByteBuffer.wrap(packet.getData()).order(ByteOrder.LITTLE_ENDIAN).getShort();
                this.labyConnect.fireEventSync(new LabyConnectPlayEmoteEvent(this.labyConnect, packet.getUniqueId(), emoteId));
                break;
            }
            case COSMETIC_CHANGE: {
                final DefaultGameUserService service = (DefaultGameUserService)this.labyApi.gameUserService();
                final DefaultGameUser gameUser = (DefaultGameUser)service.gameUser(packet.getUniqueId());
                final String json = new String(packet.getData());
                final JsonElement element = (JsonElement)service.getUserGson().fromJson(json, (Class)JsonElement.class);
                gameUser.updateUserData(element);
                break;
            }
            case SPRAY: {
                final ByteBuffer buffer = ByteBuffer.wrap(packet.getData()).order(ByteOrder.LITTLE_ENDIAN);
                final short sprayId = buffer.getShort();
                if (!buffer.hasRemaining()) {
                    break;
                }
                final int protocolVersion = buffer.getInt();
                final SprayProtocol protocol = (SprayProtocol)this.sprayProtocols.get(protocolVersion);
                if (protocol != null) {
                    protocol.handle(this.labyConnect, sprayId, protocolVersion, packet.getUniqueId(), buffer);
                    break;
                }
                break;
            }
        }
    }
    
    @Override
    public void handle(final PacketPlayBroadcastPayload packet) {
        try {
            if (packet.getSender().equals(this.self.getUniqueId())) {
                return;
            }
            final JsonElement jsonElement = (JsonElement)DefaultLabyConnectSession.GSON.fromJson(packet.getJson(), (Class)JsonElement.class);
            Laby.fireEvent(new LabyConnectBroadcastEvent(this.labyConnect, LabyConnectBroadcastEvent.Action.RECEIVE, packet.getSender(), packet.getKey(), jsonElement));
        }
        catch (final Exception e) {
            DefaultLabyConnect.LOGGER.info("Failed to parse broadcast payload from " + String.valueOf(packet.getSender()), new Object[0]);
        }
    }
    
    @Override
    public void handle(final PacketPlayRequestRemove packet) {
        final IncomingFriendRequest incomingTarget = CollectionHelper.removeFirstIf(this.incomingRequests, request -> request.getName().equals(packet.getPlayerName()));
        if (incomingTarget != null) {
            this.labyConnect.fireEventSync(new LabyConnectIncomingFriendRequestRemoveEvent(this.labyConnect, incomingTarget));
        }
        final OutgoingFriendRequest outgoingTarget = CollectionHelper.removeFirstIf(this.outgoingFriendRequests, request -> request.getName().equals(packet.getPlayerName()));
        if (outgoingTarget != null) {
            this.labyConnect.fireEventSync(new LabyConnectOutgoingFriendRequestRemoveEvent(this.labyConnect, outgoingTarget));
        }
    }
    
    @Override
    public void handle(final PacketPlayFriendRemove packet) {
        final Friend target = CollectionHelper.removeFirstIf(this.friends, friend -> friend.getUniqueId().equals(packet.getToRemove().getUniqueId()));
        this.uuid2Friend.remove(packet.getToRemove().getUniqueId());
        if (target != null) {
            this.labyConnect.fireEventSync(new LabyConnectFriendRemoveEvent(this.labyConnect, target));
        }
    }
    
    @Override
    public void handle(final PacketPlayFriendStatus packet) {
        final Friend friend = this.getFriend(packet.getPlayer().getUniqueId());
        if (friend == null) {
            return;
        }
        final DefaultServerInfo newServerInfo = packet.getPlayerInfo();
        final DefaultFriend defaultFriend = (DefaultFriend)friend;
        final UserStatus prevStatus = defaultFriend.userStatus();
        final UserStatus newStatus = packet.getPlayer().userStatus();
        if (prevStatus != newStatus) {
            defaultFriend.updateStatus(newStatus);
            this.labyConnect.fireEventSync(new LabyConnectFriendStatusEvent(this.labyConnect, friend, prevStatus, newStatus));
        }
        final DefaultServerInfo serverInfo = (DefaultServerInfo)defaultFriend.getServer();
        if (Objects.equals(serverInfo, newServerInfo)) {
            return;
        }
        if (newServerInfo.getAddress().isEmpty()) {
            defaultFriend.setServer(null);
        }
        else {
            defaultFriend.setServer(newServerInfo);
        }
        this.labyConnect.fireEventSync(new LabyConnectFriendServerEvent(this.labyConnect, friend, defaultFriend.getServer(), serverInfo));
    }
    
    @Override
    public void handle(final PacketMessage packet) {
        final Friend friend = this.getFriend(packet.getSender().getUniqueId());
        if (friend == null) {
            return;
        }
        final DefaultChat chat = (DefaultChat)friend.chat();
        final DefaultTextChatMessage message = new DefaultTextChatMessage(chat, friend, packet.getSentTime(), packet.getMessage());
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> chat.addMessage(message));
    }
    
    @Override
    public void handle(final PacketPlayTyping packet) {
        final Friend friend = this.getFriend(packet.getPlayer().getUniqueId());
        if (friend == null) {
            return;
        }
        ((DefaultChat)friend.chat()).updateTyping(packet.isTyping());
    }
    
    @Override
    public void handle(final PacketPlayRequestAddFriendResponse packet) {
        if (!packet.isRequestSent()) {
            this.labyConnect.fireEventSync(new LabyConnectOutgoingFriendRequestAddResponseEvent(this.labyConnect, packet.getSearched(), packet.getReason()));
        }
    }
    
    @Override
    public void handle(final PacketPlayFriendPlayingOn packet) {
    }
    
    @Override
    public void handle(final PacketPlayInviteLanWorld packet) {
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> LabyMod.references().sharedLanWorldService().pushInvite(packet.getPlayer(), this.self.getUniqueId(), packet.getOptions(), TimeUtil.getCurrentTimeMillis()));
    }
    
    @Override
    public void handle(final PacketPlayAcceptLanWorldInvite packet) {
        final Friend friend = this.getFriend(packet.getPlayer());
        if (friend == null) {
            return;
        }
        LabyMod.references().sharedLanWorldService().establishTunnelFor(packet.isHost(), packet.getPlayer(), friend.getName());
    }
    
    @Override
    public void inviteToLanWorld(final Friend friend) {
        final SharedLanWorldInvite invite = LabyMod.references().sharedLanWorldService().createInvite(this.self.getUniqueId(), friend.getUniqueId());
        this.labyConnect.sendPacket(new PacketPlayInviteLanWorld(invite));
        friend.chat().addMessage(this.self, invite.toString());
    }
    
    @Override
    public void acceptLanWorldInvite(final Friend friend) {
        this.labyConnect.sendPacket(new PacketPlayAcceptLanWorldInvite(friend.getUniqueId(), false));
    }
    
    @Override
    public void rejectLanWorldInvite(final Friend friend) {
        this.labyConnect.sendPacket(new PacketPlayRejectLanWorldInvite(friend.getUniqueId(), LanWorldRejectReason.REQUEST_REJECTED, false));
        LabyMod.references().sharedLanWorldService().dropInviteOfSender(friend.getUniqueId(), SharedLanWorldInvite.InviteStatus.REJECTED);
    }
    
    @Override
    public void retractLanWorldInvite(final Friend friend) {
        this.labyConnect.sendPacket(new PacketPlayRejectLanWorldInvite(friend.getUniqueId(), LanWorldRejectReason.REQUEST_RETRACTED, true));
        LabyMod.references().sharedLanWorldService().dropInviteOfReceiver(friend.getUniqueId(), SharedLanWorldInvite.InviteStatus.RETRACTED);
    }
    
    public void sendIceCredentials(final UUID targetUser, final IceCredentials credentials) {
        this.labyConnect.sendPacket(new PacketIceCredentials(targetUser, credentials));
    }
    
    @Override
    public void handle(final PacketPlayRejectLanWorldInvite packet) {
        Laby.labyAPI().minecraft().executeNextTick(() -> {
            final SharedLanWorldService service = LabyMod.references().sharedLanWorldService();
            SharedLanWorldInvite.InviteStatus status = null;
            switch (packet.getReason()) {
                case USER_DISCONNECTED: {
                    status = SharedLanWorldInvite.InviteStatus.DISCONNECTED;
                    break;
                }
                case INCOMPATIBLE_MINECRAFT_VERSION: {
                    status = SharedLanWorldInvite.InviteStatus.INCOMPATIBLE_MINECRAFT_VERSION;
                    break;
                }
                default: {
                    status = (packet.isHost() ? SharedLanWorldInvite.InviteStatus.REJECTED : SharedLanWorldInvite.InviteStatus.EXPIRED);
                    break;
                }
            }
            if (packet.isHost()) {
                service.dropInviteOfReceiver(packet.getPlayer(), status);
            }
            else {
                service.dropInviteOfSender(packet.getPlayer(), status);
            }
        });
    }
    
    @Override
    public void handle(final PacketIceCredentials packet) {
        if (!packet.getTargetUser().equals(this.self.getUniqueId())) {
            return;
        }
        final SharedLanWorldService service = LabyMod.references().sharedLanWorldService();
        service.credentialsTransmitter().credentialsReceived(packet.getCredentials());
    }
    
    @Override
    public void handle(final PacketNotAllowed packet) {
    }
    
    @Override
    public void handle(final PacketServerMessage packet) {
    }
    
    @Override
    public void handle(final PacketLoginTime packet) {
    }
    
    @Override
    public void handle(final PacketMojangStatus packet) {
    }
    
    @Override
    public void handle(final PacketUpdateCosmetics packet) {
        final String json = packet.getJson();
        if (json == null) {
            return;
        }
        final GameUser clientGameUser = this.labyApi.gameUserService().clientGameUser();
        if (!(clientGameUser instanceof DefaultGameUser)) {
            return;
        }
        ((DefaultGameUser)clientGameUser).updateUserData((JsonElement)DefaultLabyConnectSession.GSON.fromJson(json, (Class)JsonElement.class));
    }
    
    @Override
    public void handle(final PacketAddonMessage packet) {
        final String key = packet.getKey();
        final MessageListener messageListener = this.messageListeners.get(key);
        if (messageListener == null) {
            DefaultLabyConnectSession.LOGGER.debug("Unknown addon message {}", key);
        }
        else {
            messageListener.listen(packet.getJson());
        }
    }
    
    @Override
    public void handle(final PacketActionPlayResponse packet) {
    }
    
    @Override
    public void handle(final PacketActionRequestResponse packet) {
    }
    
    @Override
    public void handle(final PacketAddonDevelopment packet) {
        Laby.fireEvent(new PacketAddonDevelopmentEvent(packet));
        if (packet.getKey().equals("labymod:file")) {
            final byte[] data = packet.getData();
            if (data.length < 16) {
                return;
            }
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final UUID identifier = new UUID(buffer.getLong(), buffer.getLong());
            final byte[] fileData = new byte[data.length - 16];
            buffer.get(fileData);
            this.storeFile(identifier, fileData);
        }
    }
    
    private boolean storeFile(final UUID identifier, final byte[] fileData) {
        if (this.fileStorage.size() > 20) {
            this.fileStorage.remove(this.fileStorage.keySet().iterator().next());
        }
        if (fileData.length > 5242880) {
            return false;
        }
        this.fileStorage.put(identifier, fileData);
        return true;
    }
    
    @Nullable
    @Override
    public Friend getFriend(final UUID uniqueId) {
        return this.uuid2Friend.get(uniqueId);
    }
    
    @Nullable
    @Override
    public Chat getChat(final UUID uniqueId) {
        final Friend friend = this.getFriend(uniqueId);
        return (friend != null) ? friend.chat() : null;
    }
    
    @Nullable
    @Override
    public IncomingFriendRequest getIncomingRequest(final UUID uniqueId) {
        for (final IncomingFriendRequest request : this.incomingRequests) {
            if (request.getUniqueId().equals(uniqueId)) {
                return request;
            }
        }
        return null;
    }
    
    @Nullable
    @Override
    public OutgoingFriendRequest getOutgoingRequest(final UUID uniqueId) {
        for (final OutgoingFriendRequest request : this.outgoingFriendRequests) {
            if (request.getUniqueId().equals(uniqueId)) {
                return request;
            }
        }
        return null;
    }
    
    @Override
    public List<Friend> getFriends() {
        return this.unmodifiableFriends;
    }
    
    @Override
    public List<Chat> getChats() {
        final List<Chat> chats = new ArrayList<Chat>();
        for (final Friend friend : this.friends) {
            chats.add(friend.chat());
        }
        return chats;
    }
    
    @Override
    public List<IncomingFriendRequest> getIncomingRequests() {
        return this.unmodifiableIncomingRequests;
    }
    
    @Override
    public List<OutgoingFriendRequest> getOutgoingRequests() {
        return this.unmodifiableOutgoingFriendRequests;
    }
    
    @Override
    public User self() {
        return this.self;
    }
    
    @Override
    public TokenStorage tokenStorage() {
        return this.tokenStorage;
    }
    
    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }
    
    @Override
    public boolean isPremium() {
        return this.premium;
    }
    
    @Override
    public boolean isConnectionEstablished() {
        return this.connectionEstablished;
    }
    
    public void requestDashboardPin(final Consumer<String> callback) {
        this.dashboardPinCallback = callback;
        this.labyConnect.sendPacket(new PacketAddonMessage("dashboard_pin", (JsonElement)new JsonObject()));
    }
    
    public void requestIncentive(final int typeId) {
        final JsonObject object = new JsonObject();
        final JsonObject openObject = new JsonObject();
        openObject.addProperty("type", (Number)typeId);
        object.add("open", (JsonElement)openObject);
        this.labyConnect.sendPacket(new PacketAddonMessage("incentive", (JsonElement)object));
    }
    
    @Deprecated
    @Override
    public void refreshOpenChat(final UUID friendId) {
        ThreadSafe.ensureRenderThread();
        Laby.references().activityController().getActivityTree(activity -> {
            if (activity instanceof LabyConnectDirectChatActivity && ((LabyConnectDirectChatActivity)activity).friend().getUniqueId().equals(friendId)) {
                activity.reload();
            }
        });
    }
    
    @Deprecated
    public String getJwt() {
        final TokenStorage.Token token = this.tokenStorage.getToken(TokenStorage.Purpose.JWT, this.self.getUniqueId());
        return (token == null) ? null : token.getToken();
    }
    
    @Deprecated
    public String getPin() {
        final TokenStorage.Token token = this.tokenStorage.getToken(TokenStorage.Purpose.CLIENT, this.self.getUniqueId());
        return (token == null) ? null : token.getToken();
    }
    
    @Override
    public void sendBroadcastPayload(@NotNull final String key, @NotNull final JsonElement payload) {
        this.sendBroadcastPayload(PacketUserTracker.EnumTrackingChannel.LIST, key, payload);
    }
    
    @Override
    public void sendSurroundingBroadcastPayload(@NotNull final String key, @NotNull final JsonElement payload) {
        this.sendBroadcastPayload(PacketUserTracker.EnumTrackingChannel.ENTITIES, key, payload);
    }
    
    private void sendBroadcastPayload(final PacketUserTracker.EnumTrackingChannel channel, final String key, final JsonElement payload) {
        Objects.requireNonNull(key, "Key cannot be null");
        Objects.requireNonNull(payload, "Payload cannot be null");
        final PacketPlayBroadcastPayload packet = new PacketPlayBroadcastPayload(channel, key, DefaultLabyConnectSession.GSON.toJson(payload));
        this.labyConnect.sendPacket(packet);
        Laby.fireEvent(new LabyConnectBroadcastEvent(this.labyConnect, LabyConnectBroadcastEvent.Action.SEND, this.self.getUniqueId(), key, payload));
    }
    
    @Override
    public void sendTextureUpdated(final MojangTextureChangedResponse response) {
        response.filterActiveTextures();
        final String json = DefaultLabyConnectSession.GSON.toJson((Object)response, (Type)MojangTextureChangedResponse.class);
        this.labyConnect.sendPacket(new PacketAddonMessage("textures_updated", json));
    }
    
    @Override
    public byte[] getFile(final UUID identifier) {
        return this.fileStorage.get(identifier);
    }
    
    public void dispose() {
        this.connectionEstablished = false;
    }
    
    private void resetAuthentication() {
        this.authenticated = false;
        this.premium = false;
        this.labyConnect.updateState(LabyConnectState.HELLO);
        this.labyConnect.fireEventSync(new LabyConnectRejectAuthenticationEvent(this.labyConnect));
    }
    
    static {
        GSON = new GsonBuilder().registerTypeAdapter((Type)UUID.class, (Object)new UUIDTypeAdapter()).create();
        LOGGER = Logging.getLogger();
    }
}
