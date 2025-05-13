// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.client.multiplayer.server;

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
import io.netty.buffer.Unpooled;
import net.labymod.api.event.client.network.server.NetworkPayloadEvent;
import net.labymod.api.client.resources.ResourceLocation;
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
import net.labymod.v1_18_2.client.multiplayer.server.storage.VersionedServerList;
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
        if (!(mojangServerData instanceof emx)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "%s is not an instance of %s!", mojangServerData.getClass().getName(), emx.class.getName()));
        }
        final emx serverData = (emx)mojangServerData;
        if (mojangServerData instanceof final VersionedServerData versionedServerData) {
            return versionedServerData.getServerData();
        }
        return ServerInfo.infoBuilder().name(serverData.a).serverAddress(serverData.b).lan(serverData.d()).icon(serverData.c()).ping((int)serverData.e).protocolVersion(serverData.f).description(this.fromMinecraftComponent(serverData.d)).build();
    }
    
    @Override
    public void sendPayload(final ResourceLocation identifier, final byte[] payload) {
        final emt connection = dyr.D().x();
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
    
    private void sendCustomPayload(@NotNull final emt connection, @NotNull final ResourceLocation identifier, final byte[] payload) {
        connection.a((rc)new vu((yt)identifier.getMinecraftLocation(), new py(Unpooled.wrappedBuffer(payload))));
    }
    
    private Component fromMinecraftComponent(final qk component) {
        if (component == null) {
            return Component.text("");
        }
        return this.componentMapper.fromMinecraftComponent(component);
    }
    
    private List<Component> fromMinecraftComponentCollection(@Nullable final List<qk> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<Component>();
        }
        final List<Component> result = new ArrayList<Component>(list.size());
        for (final qk component : list) {
            final Component fromMinecraftComponent = this.fromMinecraftComponent(component);
            result.add(fromMinecraftComponent);
        }
        return result;
    }
    
    @Override
    public void leaveServer() {
        final dyr minecraft = dyr.D();
        if (minecraft.r == null) {
            return;
        }
        final boolean singleplayer = minecraft.G();
        final boolean realms = minecraft.ag();
        minecraft.r.T();
        if (singleplayer) {
            minecraft.b((edw)new edf((qk)this.componentMapper.toMinecraftComponent(Component.translatable("menu.savingLevel", new Component[0]))));
        }
        else {
            minecraft.s();
        }
        final eeb titleScreen = new eeb();
        edw display;
        if (singleplayer) {
            display = (edw)titleScreen;
        }
        else if (realms) {
            display = (edw)new dtw((edw)titleScreen);
        }
        else {
            display = (edw)new egk((edw)titleScreen);
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
        final VersionedServerData serverData = new VersionedServerData(connectableServerData);
        ecv.a(dyr.D().y, dyr.D(), end.a(serverData.b), (emx)serverData);
    }
    
    @Override
    public boolean isOfflineMode() {
        if (dyr.D().G()) {
            return false;
        }
        final emt connection = dyr.D().x();
        return connection == null || !connection.a().g();
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
        return fbt.a("selectServer.defaultName", new Object[0]);
    }
    
    @Override
    public ScreenWrapper createEditServerScreen(final StorageServerData data, final Consumer<StorageServerData> callback) {
        final edw prevScreen = dyr.D().y;
        final emx mcData = new emx(data.getName(), data.address().toString(), data.isLan());
        mcData.a(emx.a.values()[data.resourcePackStatus().ordinal()]);
        final edd screen = new edd(prevScreen, addServer -> {
            if (addServer) {
                data.setName(mcData.a);
                data.setServerAddress(mcData.b);
                data.setResourcePackStatus(ServerResourcePackStatus.VALUES[mcData.b().ordinal()]);
                callback.accept(data);
            }
            dyr.D().a(prevScreen);
        }, mcData);
        return this.screenWrapperFactory.create(screen);
    }
}
