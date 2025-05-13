// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.multiplayer.server;

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
import net.labymod.api.event.client.network.server.NetworkPayloadEvent;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.v1_21_3.client.util.MinecraftUtil;
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
import net.labymod.v1_21_3.client.multiplayer.server.storage.VersionedServerList;
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
        if (!(mojangServerData instanceof gfz)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "%s is not an instance of %s!", mojangServerData.getClass().getName(), gfz.class.getName()));
        }
        final gfz serverData = (gfz)mojangServerData;
        if (mojangServerData instanceof final VersionedServerData versionedServerData) {
            return versionedServerData.getServerData();
        }
        return ServerInfo.infoBuilder().name(serverData.a).serverAddress(serverData.b).type(MinecraftUtil.fromMinecraft(serverData)).icon((serverData.c() == null) ? null : new String(serverData.c())).ping((int)serverData.f).protocolVersion(serverData.g).description(this.fromMinecraftComponent(serverData.d)).build();
    }
    
    @Override
    public void sendPayload(final ResourceLocation identifier, final byte[] payload) {
        final gfl connection = fmg.Q().L();
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
    
    private void sendCustomPayload(@NotNull final gfl connection, @NotNull final ResourceLocation identifier, final byte[] payload) {
        connection.b((aac)new aax((abf)new LabyModCustomPacketPayload(identifier, payload)));
    }
    
    private Component fromMinecraftComponent(final xv component) {
        if (component == null) {
            return Component.text("");
        }
        return this.componentMapper.fromMinecraftComponent(component);
    }
    
    private List<Component> fromMinecraftComponentCollection(@Nullable final List<xv> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<Component>();
        }
        final List<Component> result = new ArrayList<Component>(list.size());
        for (final xv component : list) {
            final Component fromMinecraftComponent = this.fromMinecraftComponent(component);
            result.add(fromMinecraftComponent);
        }
        return result;
    }
    
    @Override
    public void leaveServer() {
        final fmg minecraft = fmg.Q();
        if (minecraft.s == null) {
            return;
        }
        final boolean singleplayer = minecraft.T();
        final gfz currentServer = minecraft.S();
        final boolean realms = currentServer != null && currentServer.e();
        minecraft.s.ab();
        if (singleplayer) {
            minecraft.b((fty)new ftj((xv)this.componentMapper.toMinecraftComponent(Component.translatable("menu.savingLevel", new Component[0]))));
        }
        else {
            minecraft.y();
        }
        final fua titleScreen = new fua();
        fty display;
        if (singleplayer) {
            display = (fty)titleScreen;
        }
        else if (realms) {
            display = (fty)new fhd((fty)titleScreen);
        }
        else {
            display = (fty)new fwo((fty)titleScreen);
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
        fsx.a(fmg.Q().z, fmg.Q(), ghc.a(serverData.b), (gfz)serverData, false, (ggd)null);
        if (fmg.Q().z instanceof ftf) {
            this.connectingServerData = null;
        }
    }
    
    @Override
    public boolean isOfflineMode() {
        if (fmg.Q().T()) {
            return false;
        }
        final gfl connection = fmg.Q().L();
        return connection == null || !connection.k().h();
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
        return hcs.a("selectServer.defaultName", new Object[0]);
    }
    
    @Override
    public ScreenWrapper createEditServerScreen(final StorageServerData data, final Consumer<StorageServerData> callback) {
        final fty prevScreen = fmg.Q().z;
        final gfz mcData = new gfz(data.getName(), data.address().toString(), MinecraftUtil.toMinecraft(data.type()));
        mcData.a(gfz.a.values()[data.resourcePackStatus().ordinal()]);
        final ftg screen = new ftg(prevScreen, addServer -> {
            if (addServer) {
                data.setName(mcData.a);
                data.setServerAddress(mcData.b);
                data.setResourcePackStatus(ServerResourcePackStatus.VALUES[mcData.b().ordinal()]);
                callback.accept(data);
            }
            fmg.Q().a(prevScreen);
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
