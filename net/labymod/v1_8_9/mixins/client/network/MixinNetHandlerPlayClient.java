// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.network;

import net.labymod.api.util.ThreadSafe;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Collections;
import net.labymod.api.event.client.entity.player.ClientPlayerAbilitiesUpdateEvent;
import net.labymod.api.client.entity.player.ClientPlayer;
import java.net.URISyntaxException;
import java.net.URI;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.client.component.Component;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.v1_8_9.client.chat.VersionedGuiNewChat;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.v1_8_9.client.gui.screen.VersionedScreenWrapper;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.labymod.v1_8_9.client.gui.screen.LabyScreenRenderer;
import java.util.Iterator;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoUpdateEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoRemoveEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoAddEvent;
import net.labymod.v1_8_9.client.player.VersionedNetworkPlayerInfo;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import net.labymod.core.client.network.server.DefaultAbstractServerController;
import java.util.Locale;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.network.server.ServerController;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.Laby;
import net.labymod.api.event.client.world.DimensionChangeEvent;
import net.labymod.v1_8_9.client.world.VersionedClientWorld;
import net.labymod.api.volt.callback.InsertInfo;
import java.util.HashMap;
import java.util.Collection;
import net.labymod.api.client.network.NetworkPlayerInfo;
import java.util.UUID;
import java.util.Map;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.network.ClientPacketListener;

@Mixin({ bcy.class })
public abstract class MixinNetHandlerPlayClient implements ClientPacketListener
{
    @Shadow
    @Final
    private static Logger b;
    private final Map<UUID, NetworkPlayerInfo> labymod$playerInfos;
    private Collection<NetworkPlayerInfo> labymod$unmodifiablePlayerInfos;
    @Final
    @Shadow
    private Map<UUID, bdc> i;
    @Shadow
    @Final
    private ave f;
    @Shadow
    @Final
    private ek c;
    
    public MixinNetHandlerPlayClient() {
        this.labymod$playerInfos = new HashMap<UUID, NetworkPlayerInfo>();
    }
    
    @Shadow
    public abstract void a(final eu p0);
    
    @Insert(method = { "handleRespawn" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getScoreboard()Lnet/minecraft/scoreboard/Scoreboard;", ordinal = 0))
    private void labyMod$fireDimensionChangeEvent(final he packet, final InsertInfo ci) {
        final int from = this.f.h.am;
        final int to = packet.a();
        Laby.fireEvent(new DimensionChangeEvent(VersionedClientWorld.getDimensionFromId(from), VersionedClientWorld.getDimensionFromId(to)));
    }
    
    @Inject(method = { "handleJoinGame(Lnet/minecraft/network/play/server/S01PacketJoinGame;)V" }, at = { @At("TAIL") })
    private void labyMod$handleNetworkJoin(final gt packet, final CallbackInfo ci) {
        final ServerController serverController = Laby.labyAPI().serverController();
        serverController.loginOrServerSwitch(serverController.createServerData(ave.A().D()));
    }
    
    @Inject(method = { "handleCustomPayload(Lnet/minecraft/network/play/server/S3FPacketCustomPayload;)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V", shift = At.Shift.AFTER) })
    private void labyMod$handlePayloadReceive(final gg packet, final CallbackInfo ci) {
        final String channelName = packet.a();
        final em bufferData = packet.b();
        final ResourceLocationFactory provider = Laby.labyAPI().renderPipeline().resources().resourceLocationFactory();
        ResourceLocation identifier;
        if (channelName.equalsIgnoreCase("MC|Brand")) {
            identifier = provider.createMinecraft("brand");
        }
        else if (channelName.equalsIgnoreCase("MC|TriList")) {
            identifier = provider.createMinecraft("trilist");
        }
        else if (channelName.equalsIgnoreCase("MC|BOpen")) {
            identifier = provider.createMinecraft("bopen");
        }
        else if (channelName.contains(":")) {
            final String[] split = channelName.split(":");
            if (split.length != 2) {
                System.err.println("Weird channel name: " + channelName);
                return;
            }
            identifier = provider.create(split[0].toLowerCase(Locale.US), split[1].toLowerCase(Locale.US));
        }
        else {
            identifier = provider.create("minecraft", channelName.toLowerCase(Locale.US));
        }
        ((DefaultAbstractServerController)Laby.labyAPI().serverController()).payloadReceive(identifier.getNamespace(), identifier.getPath(), this.readByteBuf(bufferData.copy()));
        final boolean canHandleCustomPayload = this.labyMod$getServerController().handleCustomPayload(PayloadChannelIdentifier.create(identifier.getNamespace(), identifier.getPath()), this.readByteBuf(bufferData.copy()));
        if (!canHandleCustomPayload && !this.labymod$isMinecraftIdentifier(identifier)) {
            MixinNetHandlerPlayClient.b.warn("Unknown custom payload channel: " + String.valueOf(identifier));
        }
    }
    
    @Inject(method = { "handlePlayerListItem(Lnet/minecraft/network/play/server/S38PacketPlayerListItem;)V" }, at = { @At("TAIL") })
    private void labyMod$handlePlayerInfo(final gz packet, final CallbackInfo ci) {
        for (final gz.b entry : packet.a()) {
            switch (packet.b()) {
                case a: {
                    final bdc info = this.i.get(entry.a().getId());
                    if (info == null) {
                        continue;
                    }
                    final NetworkPlayerInfo playerInfo = new VersionedNetworkPlayerInfo(info);
                    this.labymod$playerInfos.put(playerInfo.profile().getUniqueId(), playerInfo);
                    Laby.fireEvent(new PlayerInfoAddEvent(playerInfo));
                    continue;
                }
                case e: {
                    final NetworkPlayerInfo info2 = this.labymod$playerInfos.remove(entry.a().getId());
                    if (info2 == null) {
                        continue;
                    }
                    Laby.fireEvent(new PlayerInfoRemoveEvent(info2));
                    continue;
                }
                case d:
                case b:
                case c: {
                    final NetworkPlayerInfo info2 = this.labymod$playerInfos.get(entry.a().getId());
                    if (info2 == null) {
                        continue;
                    }
                    PlayerInfoUpdateEvent.UpdateType updateType = null;
                    switch (packet.b()) {
                        case d: {
                            updateType = PlayerInfoUpdateEvent.UpdateType.DISPLAY_NAME;
                            break;
                        }
                        case b: {
                            updateType = PlayerInfoUpdateEvent.UpdateType.GAME_MODE;
                            break;
                        }
                        case c: {
                            updateType = PlayerInfoUpdateEvent.UpdateType.PING;
                            break;
                        }
                    }
                    if (updateType != null) {
                        Laby.fireEvent(new PlayerInfoUpdateEvent(info2, updateType));
                        continue;
                    }
                    continue;
                }
            }
        }
    }
    
    @Insert(method = { "handleTabComplete" }, at = @At("TAIL"))
    private void labyMod$handleTabComplete(final fx packet, final InsertInfo ci) {
        final String[] strings = packet.a();
        final axu currentScreen = this.f.m;
        if (!(currentScreen instanceof LabyScreenRenderer)) {
            return;
        }
        final LabyScreen labyScreen = ((LabyScreenRenderer)currentScreen).screen();
        if (!(labyScreen instanceof ChatInputOverlay)) {
            return;
        }
        final ChatInputOverlay overlay = (ChatInputOverlay)labyScreen;
        final ScreenInstance mostInnerScreenInstance = overlay.mostInnerScreenInstance();
        if (!(mostInnerScreenInstance instanceof VersionedScreenWrapper)) {
            return;
        }
        final VersionedScreenWrapper screenWrapper = (VersionedScreenWrapper)mostInnerScreenInstance;
        final Object versionedScreen = screenWrapper.getVersionedScreen();
        if (!(versionedScreen instanceof awv)) {
            return;
        }
        ((awv)versionedScreen).a(strings);
    }
    
    @Insert(method = { "handleChat" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;printChatMessage(Lnet/minecraft/util/IChatComponent;)V", shift = At.Shift.BEFORE))
    private void labyMod$storeChatVisibility(final fy packet, final InsertInfo ci) {
        final avt chat = this.f.q.d();
        ((VersionedGuiNewChat)chat).setChatVisibility(packet.a(), wn.b.a((int)packet.c()));
    }
    
    @Insert(method = { "handleChat" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;printChatMessage(Lnet/minecraft/util/IChatComponent;)V", shift = At.Shift.AFTER))
    private void labyMod$clearChatVisibility(final fy packet, final InsertInfo ci) {
        final avt chat = this.f.q.d();
        ((VersionedGuiNewChat)chat).clearChatVisibility(packet.a());
    }
    
    @ModifyVariable(method = { "onDisconnect" }, at = @At("HEAD"), argsOnly = true)
    public eu labyMod$modifyReason(final eu component) {
        final ServerController serverController = Laby.labyAPI().serverController();
        final StorageServerData currentStorageServerData = serverController.getCurrentStorageServerData();
        ConnectableServerData serverData;
        if (currentStorageServerData != null) {
            serverData = currentStorageServerData;
        }
        else if (serverController.getCurrentServerData() != null) {
            serverData = ConnectableServerData.from(serverController.getCurrentServerData());
        }
        else {
            if (ave.A().D() == null) {
                return component;
            }
            serverData = ConnectableServerData.from(serverController.createServerData(ave.A().D()));
        }
        return (eu)Laby.fireEvent(new ServerKickEvent(serverData, (Component)component, ServerKickEvent.Context.PLAY)).reason();
    }
    
    @Inject(method = { "handleResourcePack" }, at = { @At("HEAD") }, cancellable = true)
    public void labyMod$fixResourcePackFileAccess(final hd packet, final CallbackInfo ci) {
        try {
            final String url = packet.a();
            final URI uri = new URI(url);
            final String scheme = uri.getScheme();
            final boolean isLevelProtocol = "level".equals(scheme);
            if (!"http".equals(scheme) && !"https".equals(scheme) && !isLevelProtocol) {
                this.c.a((ff)new iu(packet.b(), iu.a.c));
                throw new URISyntaxException(url, "Wrong protocol");
            }
            if (isLevelProtocol && (url.contains("..") || !url.endsWith("/resources.zip"))) {
                throw new URISyntaxException(url, "Invalid levelstorage resourcepack path");
            }
        }
        catch (final URISyntaxException e) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "handlePlayerAbilities" }, at = { @At("RETURN") })
    private void labyMod$fireAbilityUpdateEvent(final gx packet, final CallbackInfo ci) {
        Laby.fireEvent(new ClientPlayerAbilitiesUpdateEvent((ClientPlayer)ave.A().h));
    }
    
    @Override
    public Collection<NetworkPlayerInfo> getNetworkPlayerInfos() {
        if (this.labymod$unmodifiablePlayerInfos == null) {
            this.labymod$unmodifiablePlayerInfos = Collections.unmodifiableCollection((Collection<? extends NetworkPlayerInfo>)this.labymod$playerInfos.values());
        }
        return this.labymod$unmodifiablePlayerInfos;
    }
    
    @Nullable
    @Override
    public NetworkPlayerInfo getNetworkPlayerInfo(final UUID uniqueId) {
        return this.labymod$playerInfos.get(uniqueId);
    }
    
    @Nullable
    @Override
    public NetworkPlayerInfo getNetworkPlayerInfo(final String username) {
        for (final NetworkPlayerInfo playerInfo : this.labymod$playerInfos.values()) {
            if (username.equals(playerInfo.profile().getUsername())) {
                return playerInfo;
            }
        }
        return null;
    }
    
    private boolean labymod$isMinecraftIdentifier(@NotNull final ResourceLocation location) {
        if (!location.getNamespace().equals("minecraft")) {
            return false;
        }
        final String path = location.getPath();
        return path.equals("register") || path.equals("brand") || path.equals("bopen") || path.equals("trilist");
    }
    
    private ServerController labyMod$getServerController() {
        return Laby.labyAPI().serverController();
    }
    
    private byte[] readByteBuf(@NotNull final ByteBuf buffer) {
        final byte[] data = new byte[buffer.readableBytes()];
        buffer.readBytes(data);
        return data;
    }
    
    @Override
    public void simulateKick(final Component reason) {
        ThreadSafe.ensureRenderThread();
        this.a((eu)Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(reason));
    }
}
