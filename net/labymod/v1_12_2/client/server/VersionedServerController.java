// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.server;

import net.labymod.v1_12_2.client.gui.screen.VersionedFunctionalConfirmScreen;
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
import net.labymod.v1_12_2.client.server.storage.VersionedServerList;
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
        if (!(mojangServerData instanceof bse)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "%s is not an instance of %s!", mojangServerData.getClass().getName(), bse.class.getName()));
        }
        final bse sd = (bse)mojangServerData;
        if (mojangServerData instanceof final VersionedServerData versionedServerData) {
            return versionedServerData.getServerData();
        }
        return ServerInfo.infoBuilder().name(sd.a).serverAddress(sd.b).lan(sd.d()).icon(sd.c()).ping((int)sd.e).protocolVersion(sd.f).protocolInfo(this.nullCheck(sd.g)).description(Component.text(this.nullCheck(sd.d))).build();
    }
    
    @Override
    public void sendPayload(ResourceLocation identifier, byte[] payload) {
        final brz connection = bib.z().v();
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
        connection.a((ht)new lh(channelName, new gy(Unpooled.wrappedBuffer(payload))));
    }
    
    @Contract(value = "!null -> param1", pure = true)
    @NotNull
    private String nullCheck(String value) {
        return (value == null) ? "" : value;
    }
    
    @Override
    public void leaveServer() {
        final bib minecraft = bib.z();
        if (minecraft.f == null) {
            return;
        }
        final boolean singleplayer = minecraft.D();
        final boolean realms = minecraft.ah();
        minecraft.f.O();
        minecraft.a((bsb)null);
        if (singleplayer) {
            minecraft.a((blk)new blr());
        }
        else if (realms) {
            final RealmsBridge bridge = new RealmsBridge();
            bridge.switchToRealms((blk)new blr());
        }
        else {
            minecraft.a((blk)new bnf((blk)new blr()));
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
        final bkr connecting = new bkr(bib.z().m, bib.z(), (bse)serverData);
        if (connecting.h) {
            return;
        }
        bib.z().a((blk)connecting);
    }
    
    @Override
    public boolean isOfflineMode() {
        if (bib.z().D()) {
            return false;
        }
        final brz connection = bib.z().v();
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
        return cey.a("selectServer.defaultName", new Object[0]);
    }
    
    @Override
    public ScreenWrapper createEditServerScreen(final StorageServerData data, final Consumer<StorageServerData> callback) {
        final blk prevScreen = bib.z().m;
        final bse mcData = new bse(data.getName(), data.address().toString(), data.isLan());
        mcData.a(bse.a.values()[data.resourcePackStatus().ordinal()]);
        final bkz screen = new bkz((blk)new VersionedFunctionalConfirmScreen(0, addServer -> {
            if (addServer) {
                data.setName(mcData.a);
                data.setServerAddress(mcData.b);
                data.setResourcePackStatus(ServerResourcePackStatus.values()[mcData.b().ordinal()]);
                callback.accept(data);
            }
            bib.z().a(prevScreen);
            return;
        }), mcData);
        return this.screenWrapperFactory.create(screen);
    }
}
