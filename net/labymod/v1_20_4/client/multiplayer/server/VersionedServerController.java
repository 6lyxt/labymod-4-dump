// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.multiplayer.server;

import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import java.util.function.Consumer;
import net.labymod.api.client.network.server.storage.ServerList;
import net.labymod.api.client.network.server.storage.StorageServerData;
import java.util.Objects;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.network.server.ConnectableServerData;
import java.util.Iterator;
import java.util.ArrayList;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.v1_20_4.client.multiplayer.server.custompayload.LabyModCustomPacketPayload;
import net.labymod.api.event.client.network.server.NetworkPayloadEvent;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.v1_20_4.client.util.MinecraftUtil;
import net.labymod.api.client.network.server.ServerInfo;
import java.util.Locale;
import javax.inject.Inject;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.api.serverapi.LabyModProtocolService;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.v1_20_4.client.multiplayer.server.storage.VersionedServerList;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.network.server.DefaultAbstractServerController;

@Singleton
@Implements(ServerController.class)
public class VersionedServerController extends DefaultAbstractServerController
{
    private final ComponentMapper componentMapper;
    private final ScreenWrapper.Factory screenWrapperFactory;
    private VersionedServerList serverList;
    private ServerData connectingServerData;
    
    @Inject
    public VersionedServerController(@NotNull final LabyAPI labyAPI, @NotNull final LabyModProtocolService protocolService, @NotNull final PermissionRegistry permissionRegistry, @NotNull final ResourceLocationFactory factory, @NotNull final ComponentMapper componentMapper) {
        super(labyAPI, protocolService, permissionRegistry, factory);
        this.componentMapper = componentMapper;
        this.screenWrapperFactory = Laby.references().screenWrapperFactory();
    }
    
    @Override
    public ServerData createServerData(final Object mojangServerData) {
        if (mojangServerData == null) {
            return null;
        }
        if (!(mojangServerData instanceof fod)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "%s is not an instance of %s!", mojangServerData.getClass().getName(), fod.class.getName()));
        }
        final fod serverData = (fod)mojangServerData;
        if (mojangServerData instanceof final VersionedServerData versionedServerData) {
            return versionedServerData.getServerData();
        }
        return ServerInfo.infoBuilder().name(serverData.a).serverAddress(serverData.b).type(MinecraftUtil.fromMinecraft(serverData)).icon((serverData.c() == null) ? null : new String(serverData.c())).ping((int)serverData.f).protocolVersion(serverData.g).description(this.fromMinecraftComponent(serverData.d)).build();
    }
    
    @Override
    public void sendPayload(final ResourceLocation identifier, final byte[] payload) {
        final fnt connection = evi.O().J();
        if (connection == null) {
            return;
        }
        final NetworkPayloadEvent event = NetworkPayloadEvent.createSend(identifier, payload);
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            return;
        }
        this.sendCustomPayload(connection, identifier, payload);
    }
    
    private void sendCustomPayload(@NotNull final fnt connection, @NotNull final ResourceLocation identifier, final byte[] payload) {
        final LabyModCustomPacketPayload customPacketPayload = LabyModCustomPacketPayload.of(identifier, payload);
        connection.b((xf)new xs((ya)customPacketPayload));
    }
    
    private Component fromMinecraftComponent(final vf component) {
        if (component == null) {
            return Component.text("");
        }
        return this.componentMapper.fromMinecraftComponent(component);
    }
    
    private List<Component> fromMinecraftComponentCollection(@Nullable final List<vf> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<Component>();
        }
        final List<Component> result = new ArrayList<Component>(list.size());
        for (final vf component : list) {
            final Component fromMinecraftComponent = this.fromMinecraftComponent(component);
            result.add(fromMinecraftComponent);
        }
        return result;
    }
    
    @Override
    public void leaveServer() {
        final evi minecraft = evi.O();
        if (minecraft.r == null) {
            return;
        }
        final boolean singleplayer = minecraft.R();
        final fod currentServer = minecraft.Q();
        final boolean realms = currentServer != null && currentServer.e();
        minecraft.r.W();
        if (singleplayer) {
            minecraft.b((fdb)new fch((vf)this.componentMapper.toMinecraftComponent(Component.translatable("menu.savingLevel", new Component[0]))));
        }
        else {
            minecraft.y();
        }
        final fdg titleScreen = new fdg();
        fdb display;
        if (singleplayer) {
            display = (fdb)titleScreen;
        }
        else if (realms) {
            display = (fdb)new eqm((fdb)titleScreen);
        }
        else {
            display = (fdb)new ffz((fdb)titleScreen);
        }
        minecraft.a(display);
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
        this.connectingServerData = connectableServerData;
        final VersionedServerData serverData = new VersionedServerData(connectableServerData);
        fbv.a(evi.O().y, evi.O(), fpf.a(serverData.b), (fod)serverData, false);
        if (evi.O().y instanceof fcd) {
            this.connectingServerData = null;
        }
    }
    
    @Override
    public boolean isOfflineMode() {
        if (evi.O().R()) {
            return false;
        }
        final fnt connection = evi.O().J();
        return connection == null || !connection.m().j();
    }
    
    @Override
    public ServerList serverList() {
        if (this.serverList == null) {
            this.serverList = new VersionedServerList();
        }
        return this.serverList;
    }
    
    @Override
    protected String getDefaultServerName() {
        return gfs.a("selectServer.defaultName", new Object[0]);
    }
    
    @Override
    public ScreenWrapper createEditServerScreen(final StorageServerData data, final Consumer<StorageServerData> callback) {
        final fdb prevScreen = evi.O().y;
        final fod mcData = new fod(data.getName(), data.address().toString(), MinecraftUtil.toMinecraft(data.type()));
        mcData.a(fod.a.values()[data.resourcePackStatus().ordinal()]);
        final fce screen = new fce(prevScreen, addServer -> {
            if (addServer) {
                data.setName(mcData.a);
                data.setServerAddress(mcData.b);
                data.setResourcePackStatus(ServerResourcePackStatus.VALUES[mcData.b().ordinal()]);
                callback.accept(data);
            }
            evi.O().a(prevScreen);
        }, mcData);
        return this.screenWrapperFactory.create(screen);
    }
    
    @Override
    public void setCurrentServerData(final ServerData currentServerData) {
        super.setCurrentServerData(currentServerData);
        if (currentServerData == null) {
            this.connectingServerData = null;
        }
    }
    
    public ServerData getConnectingServerData() {
        return this.connectingServerData;
    }
}
