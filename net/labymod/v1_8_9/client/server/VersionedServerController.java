// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.server;

import net.labymod.v1_8_9.client.gui.screen.VersionedFunctionalConfirmScreen;
import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import java.util.function.Consumer;
import net.labymod.api.client.network.server.storage.ServerList;
import net.labymod.api.client.network.server.storage.StorageServerData;
import java.util.Objects;
import net.labymod.api.util.ThreadSafe;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.minecraft.realms.RealmsBridge;
import org.jetbrains.annotations.Contract;
import io.netty.buffer.Unpooled;
import net.labymod.api.event.client.network.server.NetworkPayloadEvent;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.server.ServerInfo;
import java.util.Locale;
import net.labymod.api.client.network.server.ServerData;
import javax.inject.Inject;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.api.serverapi.LabyModProtocolService;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.LabyAPI;
import net.labymod.v1_8_9.client.server.storage.VersionedServerList;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.network.server.DefaultAbstractServerController;

@Singleton
@Implements(ServerController.class)
public class VersionedServerController extends DefaultAbstractServerController
{
    private final ScreenWrapper.Factory screenWrapperFactory;
    private VersionedServerList labyMod$serverList;
    
    @Inject
    public VersionedServerController(@NotNull final LabyAPI labyAPI, @NotNull final LabyModProtocolService protocolService, @NotNull final PermissionRegistry permissionRegistry, @NotNull final ResourceLocationFactory factory) {
        super(labyAPI, protocolService, permissionRegistry, factory);
        this.screenWrapperFactory = Laby.references().screenWrapperFactory();
    }
    
    @Override
    public ServerData createServerData(final Object mojangServerData) {
        if (mojangServerData == null) {
            return null;
        }
        if (!(mojangServerData instanceof bde)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "%s is not an instance of %s!", mojangServerData.getClass().getName(), bde.class.getName()));
        }
        final bde sd = (bde)mojangServerData;
        if (mojangServerData instanceof final VersionedServerData versionedServerData) {
            return versionedServerData.getServerData();
        }
        return ServerInfo.infoBuilder().name(sd.a).serverAddress(sd.b).lan(sd.d()).icon(sd.c()).ping((int)sd.e).protocolVersion(sd.f).protocolInfo(this.nullCheck(sd.g)).description(Component.text(this.nullCheck(sd.d))).build();
    }
    
    @Override
    public void sendPayload(ResourceLocation identifier, byte[] payload) {
        final bcy connection = ave.A().u();
        if (connection == null) {
            return;
        }
        final NetworkPayloadEvent event = Laby.fireEvent(NetworkPayloadEvent.createSend(identifier, payload));
        if (event.isCancelled()) {
            return;
        }
        identifier = event.identifier();
        payload = event.getPayload();
        String channelName = identifier.toString();
        channelName = this.getLegacyChannelName(channelName);
        connection.a((ff)new im(channelName, new em(Unpooled.wrappedBuffer(payload))));
    }
    
    @Contract(value = "!null -> param1", pure = true)
    @NotNull
    private String nullCheck(String value) {
        return (value == null) ? "" : value;
    }
    
    @Override
    public void leaveServer() {
        final ave minecraft = ave.A();
        if (minecraft.f == null) {
            return;
        }
        final boolean singleplayer = minecraft.E();
        final boolean realms = minecraft.al();
        minecraft.f.H();
        minecraft.a((bdb)null);
        if (singleplayer) {
            minecraft.a((axu)new aya());
        }
        else if (realms) {
            final RealmsBridge bridge = new RealmsBridge();
            bridge.switchToRealms((axu)new aya());
        }
        else {
            minecraft.a((axu)new azh((axu)new aya()));
        }
    }
    
    @Override
    public void joinServer(@NotNull final ConnectableServerData connectableServerData, @Nullable final Runnable loginExecutor) {
        ThreadSafe.ensureRenderThread();
        Objects.requireNonNull(connectableServerData, "Server data must not be null!");
        this.leaveServer();
        this.loginExecutor = loginExecutor;
        if (connectableServerData instanceof final StorageServerData storageServerData) {
            this.setCurrentStorageServerData(storageServerData);
        }
        final VersionedServerData serverData = new VersionedServerData(connectableServerData);
        final awz connecting = new awz(ave.A().m, ave.A(), (bde)serverData);
        if (connecting.h) {
            return;
        }
        ave.A().a((axu)connecting);
    }
    
    @Override
    public boolean isOfflineMode() {
        if (ave.A().E()) {
            return false;
        }
        final bcy connection = ave.A().u();
        return connection == null || !connection.a().f();
    }
    
    @Override
    public ServerList serverList() {
        if (this.labyMod$serverList == null) {
            this.labyMod$serverList = new VersionedServerList();
        }
        return this.labyMod$serverList;
    }
    
    @Override
    protected String getDefaultServerName() {
        return bnq.a("selectServer.defaultName", new Object[0]);
    }
    
    @Override
    public ScreenWrapper createEditServerScreen(final StorageServerData data, final Consumer<StorageServerData> callback) {
        final axu prevScreen = ave.A().m;
        final bde mcData = new bde(data.getName(), data.address().toString(), data.isLan());
        mcData.a(bde.a.values()[data.resourcePackStatus().ordinal()]);
        final axi screen = new axi((axu)new VersionedFunctionalConfirmScreen(0, addServer -> {
            if (addServer) {
                data.setName(mcData.a);
                data.setServerAddress(mcData.b);
                data.setResourcePackStatus(ServerResourcePackStatus.values()[mcData.b().ordinal()]);
                callback.accept(data);
            }
            ave.A().a(prevScreen);
            return;
        }), mcData);
        return this.screenWrapperFactory.create(screen);
    }
}
