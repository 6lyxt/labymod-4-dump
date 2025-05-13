// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.client.multiplayer.server;

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
import net.labymod.v1_17_1.client.multiplayer.server.storage.VersionedServerList;
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
        if (!(mojangServerData instanceof ejn)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "%s is not an instance of %s!", mojangServerData.getClass().getName(), ejn.class.getName()));
        }
        final ejn serverData = (ejn)mojangServerData;
        if (mojangServerData instanceof final VersionedServerData versionedServerData) {
            return versionedServerData.getServerData();
        }
        return ServerInfo.infoBuilder().name(serverData.a).serverAddress(serverData.b).lan(serverData.d()).icon(serverData.c()).ping((int)serverData.e).protocolVersion(serverData.f).description(this.fromMinecraftComponent(serverData.d)).build();
    }
    
    @Override
    public void sendPayload(final ResourceLocation identifier, final byte[] payload) {
        final ejj connection = dvp.C().w();
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
    
    private void sendCustomPayload(@NotNull final ejj connection, @NotNull final ResourceLocation identifier, final byte[] payload) {
        connection.a((pk)new ty((ww)identifier.getMinecraftLocation(), new og(Unpooled.wrappedBuffer(payload))));
    }
    
    private Component fromMinecraftComponent(final os component) {
        if (component == null) {
            return Component.text("");
        }
        return this.componentMapper.fromMinecraftComponent(component);
    }
    
    private List<Component> fromMinecraftComponentCollection(@Nullable final List<os> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<Component>();
        }
        final List<Component> result = new ArrayList<Component>(list.size());
        for (final os component : list) {
            final Component fromMinecraftComponent = this.fromMinecraftComponent(component);
            result.add(fromMinecraftComponent);
        }
        return result;
    }
    
    @Override
    public void leaveServer() {
        final dvp minecraft = dvp.C();
        if (minecraft.r == null) {
            return;
        }
        final boolean singleplayer = minecraft.F();
        final boolean realms = minecraft.ah();
        minecraft.r.U();
        if (singleplayer) {
            minecraft.b((eaq)new eaa((os)this.componentMapper.toMinecraftComponent(Component.translatable("menu.savingLevel", new Component[0]))));
        }
        else {
            minecraft.r();
        }
        final eav titleScreen = new eav();
        eaq display;
        if (singleplayer) {
            display = (eaq)titleScreen;
        }
        else if (realms) {
            display = (eaq)new dqv((eaq)titleScreen);
        }
        else {
            display = (eaq)new edc((eaq)titleScreen);
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
        dzq.a(dvp.C().y, dvp.C(), ejt.a(serverData.b), (ejn)serverData);
    }
    
    @Override
    public boolean isOfflineMode() {
        if (dvp.C().F()) {
            return false;
        }
        final ejj connection = dvp.C().w();
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
        return eyh.a("selectServer.defaultName", new Object[0]);
    }
    
    @Override
    public ScreenWrapper createEditServerScreen(final StorageServerData data, final Consumer<StorageServerData> callback) {
        final eaq prevScreen = dvp.C().y;
        final ejn mcData = new ejn(data.getName(), data.address().toString(), data.isLan());
        mcData.a(ejn.a.values()[data.resourcePackStatus().ordinal()]);
        final dzy screen = new dzy(prevScreen, addServer -> {
            if (addServer) {
                data.setName(mcData.a);
                data.setServerAddress(mcData.b);
                data.setResourcePackStatus(ServerResourcePackStatus.VALUES[mcData.b().ordinal()]);
                callback.accept(data);
            }
            dvp.C().a(prevScreen);
        }, mcData);
        return this.screenWrapperFactory.create(screen);
    }
}
