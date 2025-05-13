// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect;

import net.labymod.core.util.logging.DefaultLoggingFactory;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.Laby;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.function.Consumer;
import net.labymod.core.labyconnect.protocol.packets.PacketDisconnect;
import net.labymod.api.event.Event;
import net.labymod.api.event.client.network.server.NetworkDisconnectEvent;
import net.labymod.api.event.client.network.server.NetworkLoginEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.packets.PacketHelloPing;
import net.labymod.core.client.network.server.connect.ConnectAddressResolver;
import io.netty.channel.ChannelHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.client.session.DefaultSession;
import net.labymod.api.client.session.Session;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import net.labymod.api.util.I18n;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectDisconnectEvent;
import javax.inject.Inject;
import net.labymod.core.labyconnect.session.LabyConnectAwayTracker;
import net.labymod.core.labyconnect.session.LabyConnectUserTracker;
import net.labymod.core.client.world.rplace.RPlaceOverlayCommand;
import net.labymod.core.client.screenshot.command.ScreenshotViewerCommand;
import net.labymod.core.labyconnect.commands.CapeReportCommand;
import net.labymod.core.labyconnect.configuration.DefaultLabyConnectConfig;
import net.labymod.api.util.time.TimeUtil;
import java.util.concurrent.Executors;
import net.labymod.api.concurrent.ThreadFactoryBuilder;
import net.labymod.api.client.chat.command.CommandService;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import io.netty.bootstrap.Bootstrap;
import org.jetbrains.annotations.Nullable;
import net.labymod.core.labyconnect.util.Snooper;
import net.labymod.core.labyconnect.session.DefaultLabyConnectSession;
import net.labymod.api.notification.NotificationController;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.core.labyconnect.protocol.Protocol;
import java.util.concurrent.ExecutorService;
import io.netty.channel.nio.NioEventLoopGroup;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor;
import net.labymod.api.configuration.loader.ConfigProvider;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.service.Service;

@Singleton
@Implements(LabyConnect.class)
public class DefaultLabyConnect extends Service implements LabyConnect
{
    private final ConfigProvider<LabyConnectConfigAccessor> labyConnectConfigProvider;
    public static final Logging LOGGER;
    public static final String ADDRESS = "chat.labymod.net";
    public static final String ADDRESS_BACKUP = "chat2.labymod.net";
    public static final int PORT = 30336;
    public static final int PORT_TEST_SERVER = 30337;
    public static final int PROTOCOL_VERSION = 29;
    private final NioEventLoopGroup nioEventLoopGroup;
    private final ExecutorService executor;
    private final Protocol protocol;
    private final SessionAccessor sessionAccessor;
    private final EventBus eventBus;
    private final NotificationController notifications;
    private final byte[] verifyToken;
    private DefaultLabyConnectSession session;
    private LabyConnectChannelHandler channelHandler;
    @Nullable
    private Snooper snooper;
    private Bootstrap bootstrap;
    private volatile LabyConnectState state;
    private long timeLastKeepAlive;
    private long timeNextConnect;
    private int connectTries;
    private long lastConnectTriesReset;
    private String lastDisconnectReason;
    
    @Inject
    public DefaultLabyConnect(final SessionAccessor sessionAccessor, final EventBus eventBus, final NotificationController notifications, final CommandService commandService) {
        this.nioEventLoopGroup = new NioEventLoopGroup(0, new ThreadFactoryBuilder().withNameFormat("LabyConnectNio#%d").build());
        this.executor = Executors.newFixedThreadPool(2, new ThreadFactoryBuilder().withNameFormat("LabyConnectExecutor#%d").build());
        this.protocol = new Protocol();
        this.verifyToken = new byte[10];
        this.session = null;
        this.channelHandler = null;
        this.state = LabyConnectState.OFFLINE;
        this.timeNextConnect = TimeUtil.getMillis();
        this.connectTries = 0;
        this.lastConnectTriesReset = 0L;
        (this.labyConnectConfigProvider = DefaultLabyConnectConfig.LabyConnectConfigProvider.INSTANCE).loadJson();
        this.sessionAccessor = sessionAccessor;
        this.eventBus = eventBus;
        this.notifications = notifications;
        try {
            this.snooper = new Snooper();
        }
        catch (final Throwable e) {
            e.printStackTrace();
        }
        commandService.register(new CapeReportCommand());
        commandService.register(new ScreenshotViewerCommand());
        commandService.register(new RPlaceOverlayCommand());
        eventBus.registerListener(new LabyConnectNotifications(this));
        eventBus.registerListener(new LabyConnectUserTracker(this));
        eventBus.registerListener(new LabyConnectAwayTracker(this));
        eventBus.registerListener(this);
    }
    
    @Override
    protected void prepare() {
        super.prepare();
        Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(() -> {
            try {
                final long durationKeepAlive = TimeUtil.getMillis() - this.timeLastKeepAlive;
                final long durationConnect = this.timeNextConnect - TimeUtil.getMillis();
                if (this.state != LabyConnectState.OFFLINE && durationKeepAlive > 25000L) {
                    this.disconnect(LabyConnectDisconnectEvent.Initiator.CLIENT, I18n.translate("labymod.activity.labyconnect.protocol.disconnect.timeout", new Object[0]));
                }
                if (this.state == LabyConnectState.OFFLINE && durationConnect < 0L) {
                    this.connect();
                }
                if (this.lastConnectTriesReset + 300000L < TimeUtil.getMillis()) {
                    this.lastConnectTriesReset = TimeUtil.getMillis();
                    this.connectTries = 0;
                }
            }
            catch (final Throwable e) {
                e.printStackTrace();
            }
        }, 0L, 5L, TimeUnit.SECONDS);
    }
    
    @Override
    public void connect() {
        final boolean useBackupServer = this.connectTries >= 4 || System.getProperty("labyconnectBackupServer") != null;
        this.connect(useBackupServer ? "chat2.labymod.net" : "chat.labymod.net", 30336);
    }
    
    @Override
    public void connect(final String address, final int port) {
        this.executor.execute(() -> {
            synchronized (this) {
                if (this.state == LabyConnectState.OFFLINE) {
                    this.keepAlive();
                    this.updateState(LabyConnectState.HELLO);
                    ++this.connectTries;
                    Session session = this.sessionAccessor.getSession();
                    if (session == null) {
                        session = new DefaultSession("Player", UUID.randomUUID(), null, Session.Type.LEGACY);
                    }
                    this.session = new DefaultLabyConnectSession(this, session);
                    this.channelHandler = new LabyConnectChannelHandler(this, this.session);
                    this.lastDisconnectReason = null;
                    (this.bootstrap = new Bootstrap()).group((EventLoopGroup)this.nioEventLoopGroup);
                    this.bootstrap.option(ChannelOption.TCP_NODELAY, (Object)true);
                    this.bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (Object)10000);
                    this.bootstrap.channel((Class)NioSocketChannel.class);
                    this.bootstrap.handler((ChannelHandler)this.channelHandler);
                    ConnectAddressResolver.resolveAddress();
                    try {
                        this.bootstrap.connect(address, port).syncUninterruptibly();
                        this.sendPacket(new PacketHelloPing(TimeUtil.getMillis()));
                    }
                    catch (final Exception e) {
                        e.printStackTrace();
                        this.updateState(LabyConnectState.OFFLINE);
                    }
                }
            }
        });
    }
    
    @Subscribe
    public void onSessionUpdate(final SessionUpdateEvent event) {
        if (!event.isAnotherAccount()) {
            return;
        }
        this.disconnect(LabyConnectDisconnectEvent.Initiator.USER, I18n.translate("labymod.activity.labyconnect.protocol.disconnect.sessionSwitch", new Object[0]));
        if (event.newSession().isPremium()) {
            this.connect();
        }
    }
    
    @Subscribe
    public void onNetworkLogin(final NetworkLoginEvent event) {
        if (this.isConnected()) {
            this.session.sendCurrentServer(event.serverData(), null, false);
        }
        else {
            this.timeNextConnect = TimeUtil.getMillis() + 10000L;
        }
    }
    
    @Subscribe
    public void onNetworkDisconnect(final NetworkDisconnectEvent event) {
        if (this.isConnected()) {
            this.session.sendLeaveCurrentServer();
        }
    }
    
    @Override
    public void disconnect(final LabyConnectDisconnectEvent.Initiator initiator, final String reason) {
        final long delay = (long)(1000.0 * Math.random() * 60.0);
        this.timeNextConnect = TimeUtil.getMillis() + 10000L + delay;
        this.lastDisconnectReason = reason;
        if (this.state != LabyConnectState.OFFLINE) {
            if (this.session != null) {
                this.session.dispose();
            }
            this.fireEventSync(new LabyConnectDisconnectEvent(this, initiator, I18n.translate(reason, new Object[0])));
            this.updateState(LabyConnectState.OFFLINE);
            this.sendPacket(new PacketDisconnect("Logout"), channel -> {
                if (channel.isOpen()) {
                    channel.close();
                }
                return;
            });
            this.session = null;
        }
    }
    
    public void sendPacket(final Packet packet) {
        this.sendPacket(packet, null);
    }
    
    public void sendPacket(final Packet packet, final Consumer<NioSocketChannel> callback) {
        final NioSocketChannel channel = this.getChannel();
        if (channel == null || !channel.isActive()) {
            return;
        }
        if (channel.eventLoop().inEventLoop()) {
            channel.writeAndFlush((Object)packet).addListeners(new GenericFutureListener[] { (GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE });
            if (callback != null) {
                callback.accept(channel);
            }
        }
        else {
            channel.eventLoop().execute(() -> {
                channel.writeAndFlush((Object)packet).addListeners(new GenericFutureListener[] { (GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE });
                if (callback != null) {
                    callback.accept(channel);
                }
            });
        }
    }
    
    public void updateState(final LabyConnectState state) {
        synchronized (this) {
            this.state = state;
        }
        this.fireEventSync(new LabyConnectStateUpdateEvent(this, this.state));
    }
    
    public void keepAlive() {
        this.timeLastKeepAlive = TimeUtil.getMillis();
    }
    
    public void fireEventSync(final Event event) {
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> this.eventBus.fire(event));
    }
    
    @Override
    public boolean isAuthenticated() {
        return this.state == LabyConnectState.PLAY;
    }
    
    @Override
    public boolean isConnectionEstablished() {
        return this.state != LabyConnectState.OFFLINE && this.session != null && this.session.isConnectionEstablished();
    }
    
    @Override
    public LabyConnectState state() {
        return this.state;
    }
    
    @Nullable
    @Override
    public LabyConnectSession getSession() {
        return this.session;
    }
    
    public Protocol getPacketRegistry() {
        return this.protocol;
    }
    
    public NioSocketChannel getChannel() {
        return (this.channelHandler == null) ? null : this.channelHandler.getChannel();
    }
    
    @Nullable
    public Snooper getSnooper() {
        return this.snooper;
    }
    
    public byte[] getVerifyToken() {
        return this.verifyToken;
    }
    
    @Override
    public String getLastDisconnectReason() {
        return this.lastDisconnectReason;
    }
    
    @Override
    public ConfigProvider<LabyConnectConfigAccessor> configProvider() {
        return this.labyConnectConfigProvider;
    }
    
    static {
        LOGGER = DefaultLoggingFactory.createLogger(LabyConnect.class);
    }
}
