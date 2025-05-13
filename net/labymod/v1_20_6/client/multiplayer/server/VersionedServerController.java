// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.client.multiplayer.server;

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
import net.labymod.v1_20_6.client.util.MinecraftUtil;
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
import net.labymod.v1_20_6.client.multiplayer.server.storage.VersionedServerList;
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
        if (!(mojangServerData instanceof fyl)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "%s is not an instance of %s!", mojangServerData.getClass().getName(), fyl.class.getName()));
        }
        final fyl serverData = (fyl)mojangServerData;
        if (mojangServerData instanceof final VersionedServerData versionedServerData) {
            return versionedServerData.getServerData();
        }
        return ServerInfo.infoBuilder().name(serverData.a).serverAddress(serverData.b).type(MinecraftUtil.fromMinecraft(serverData)).icon((serverData.c() == null) ? null : new String(serverData.c())).ping((int)serverData.f).protocolVersion(serverData.g).description(this.fromMinecraftComponent(serverData.d)).build();
    }
    
    @Override
    public void sendPayload(final ResourceLocation identifier, final byte[] payload) {
        final fxy connection = ffh.Q().L();
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
    
    private void sendCustomPayload(@NotNull final fxy connection, @NotNull final ResourceLocation identifier, final byte[] payload) {
        connection.b((zw)new aap((aax)new LabyModCustomPacketPayload(identifier, payload)));
    }
    
    private Component fromMinecraftComponent(final xp component) {
        if (component == null) {
            return Component.text("");
        }
        return this.componentMapper.fromMinecraftComponent(component);
    }
    
    private List<Component> fromMinecraftComponentCollection(@Nullable final List<xp> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<Component>();
        }
        final List<Component> result = new ArrayList<Component>(list.size());
        for (final xp component : list) {
            final Component fromMinecraftComponent = this.fromMinecraftComponent(component);
            result.add(fromMinecraftComponent);
        }
        return result;
    }
    
    @Override
    public void leaveServer() {
        final ffh minecraft = ffh.Q();
        if (minecraft.r == null) {
            return;
        }
        final boolean singleplayer = minecraft.T();
        final fyl currentServer = minecraft.S();
        final boolean realms = currentServer != null && currentServer.e();
        minecraft.r.Y();
        if (singleplayer) {
            minecraft.b((fnf)new fml((xp)this.componentMapper.toMinecraftComponent(Component.translatable("menu.savingLevel", new Component[0]))));
        }
        else {
            minecraft.y();
        }
        final fnk titleScreen = new fnk();
        fnf display;
        if (singleplayer) {
            display = (fnf)titleScreen;
        }
        else if (realms) {
            display = (fnf)new fal((fnf)titleScreen);
        }
        else {
            display = (fnf)new fqd((fnf)titleScreen);
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
        fly.a(ffh.Q().y, ffh.Q(), fzo.a(serverData.b), (fyl)serverData, false, (fyp)null);
        if (ffh.Q().y instanceof fmg) {
            this.connectingServerData = null;
        }
    }
    
    @Override
    public boolean isOfflineMode() {
        if (ffh.Q().T()) {
            return false;
        }
        final fxy connection = ffh.Q().L();
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
        return gqh.a("selectServer.defaultName", new Object[0]);
    }
    
    @Override
    public ScreenWrapper createEditServerScreen(final StorageServerData data, final Consumer<StorageServerData> callback) {
        final fnf prevScreen = ffh.Q().y;
        final fyl mcData = new fyl(data.getName(), data.address().toString(), MinecraftUtil.toMinecraft(data.type()));
        mcData.a(fyl.a.values()[data.resourcePackStatus().ordinal()]);
        final fmh screen = new fmh(prevScreen, addServer -> {
            if (addServer) {
                data.setName(mcData.a);
                data.setServerAddress(mcData.b);
                data.setResourcePackStatus(ServerResourcePackStatus.VALUES[mcData.b().ordinal()]);
                callback.accept(data);
            }
            ffh.Q().a(prevScreen);
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
