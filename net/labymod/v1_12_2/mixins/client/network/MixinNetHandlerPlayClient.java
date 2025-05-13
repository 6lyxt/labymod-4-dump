// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.network;

import net.labymod.api.util.ThreadSafe;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Collections;
import net.labymod.api.event.client.entity.player.ClientPlayerAbilitiesUpdateEvent;
import net.labymod.api.client.entity.player.ClientPlayer;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.client.component.Component;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.v1_12_2.client.chat.VersionedGuiNewChat;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.v1_12_2.client.gui.screen.VersionedScreenWrapper;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.labymod.v1_12_2.client.gui.screen.LabyScreenRenderer;
import java.util.Iterator;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoUpdateEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoRemoveEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoAddEvent;
import net.labymod.v1_12_2.client.player.VersionedNetworkPlayerInfo;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import net.labymod.core.client.network.server.DefaultAbstractServerController;
import java.util.Locale;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.network.server.ServerController;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.event.client.world.DimensionChangeEvent;
import net.labymod.api.Laby;
import net.labymod.api.volt.callback.InsertInfo;
import java.util.HashMap;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import java.util.Collection;
import net.labymod.api.client.network.NetworkPlayerInfo;
import java.util.UUID;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.network.ClientPacketListener;

@Mixin({ brz.class })
public abstract class MixinNetHandlerPlayClient implements ClientPacketListener
{
    private final Map<UUID, NetworkPlayerInfo> labymod$playerInfos;
    private Collection<NetworkPlayerInfo> labymod$unmodifiablePlayerInfos;
    @Final
    @Shadow
    private Map<UUID, bsc> i;
    @Shadow
    @Final
    private static Logger b;
    @Shadow
    private bib f;
    
    public MixinNetHandlerPlayClient() {
        this.labymod$playerInfos = new HashMap<UUID, NetworkPlayerInfo>();
    }
    
    @Shadow
    public abstract void a(final hh p0);
    
    @Insert(method = { "handleRespawn" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getScoreboard()Lnet/minecraft/scoreboard/Scoreboard;", ordinal = 0))
    private void labyMod$fireDimensionChangeEvent(final jw packet, final InsertInfo ci) {
        final ayn from = ayn.a(this.f.h.am);
        final ayn to = ayn.a(packet.a());
        final ResourceLocationFactory factory = Laby.references().resourceLocationFactory();
        Laby.fireEvent(new DimensionChangeEvent(factory.createMinecraft(from.b()), factory.createMinecraft(to.b())));
    }
    
    @Inject(method = { "handleJoinGame" }, at = { @At("TAIL") })
    private void labyMod$handleNetworkJoin(final jh packet, final CallbackInfo ci) {
        final ServerController serverController = Laby.labyAPI().serverController();
        serverController.loginOrServerSwitch(serverController.createServerData(bib.z().C()));
    }
    
    @Inject(method = { "handleCustomPayload" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V", shift = At.Shift.AFTER) })
    private void labyMod$handlePayloadReceive(final iw packet, final CallbackInfo ci) {
        final String channelName = packet.a();
        final gy bufferData = packet.b();
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
    
    @Inject(method = { "handlePlayerListItem" }, at = { @At("TAIL") })
    private void labyMod$handlePlayerInfo(final jp packet, final CallbackInfo ci) {
        for (final jp.b entry : packet.a()) {
            switch (packet.b()) {
                case a: {
                    final bsc info = this.i.get(entry.a().getId());
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
    private void labyMod$handleTabComplete(final im packet, final InsertInfo ci) {
        final String[] strings = packet.a();
        final blk currentScreen = this.f.m;
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
        if (!(versionedScreen instanceof bkn)) {
            return;
        }
        ((bkn)versionedScreen).a(strings);
    }
    
    @Insert(method = { "handleChat" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;addChatMessage(Lnet/minecraft/util/text/ChatType;Lnet/minecraft/util/text/ITextComponent;)V", shift = At.Shift.BEFORE))
    private void labyMod$storeChatVisibility(final in packet, final InsertInfo ci) {
        final bjb chat = this.f.q.d();
        ((VersionedGuiNewChat)chat).setChatVisibility(packet.a(), aed.b.a(packet.c().ordinal()));
    }
    
    @Insert(method = { "handleChat" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;addChatMessage(Lnet/minecraft/util/text/ChatType;Lnet/minecraft/util/text/ITextComponent;)V", shift = At.Shift.AFTER))
    private void labyMod$clearChatVisibility(final in packet, final InsertInfo ci) {
        final bjb chat = this.f.q.d();
        ((VersionedGuiNewChat)chat).clearChatVisibility(packet.a());
    }
    
    @ModifyVariable(method = { "onDisconnect" }, at = @At("HEAD"), argsOnly = true)
    public hh labyMod$modifyReadson(final hh component) {
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
            if (bib.z().C() == null) {
                return component;
            }
            serverData = ConnectableServerData.from(serverController.createServerData(bib.z().C()));
        }
        return (hh)Laby.fireEvent(new ServerKickEvent(serverData, (Component)component, ServerKickEvent.Context.PLAY)).reason();
    }
    
    @Inject(method = { "handlePlayerAbilities" }, at = { @At("RETURN") })
    private void labyMod$fireAbilityUpdateEvent(final jn packet, final CallbackInfo ci) {
        Laby.fireEvent(new ClientPlayerAbilitiesUpdateEvent((ClientPlayer)bib.z().h));
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
        this.a((hh)Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(reason));
    }
}
