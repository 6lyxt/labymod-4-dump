// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client;

import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.component.Component;
import java.util.Collections;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.event.client.entity.player.ClientPlayerAbilitiesUpdateEvent;
import net.labymod.api.client.entity.player.ClientPlayer;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import net.labymod.v1_19_4.client.multiplayer.server.VersionedServerController;
import net.labymod.api.client.network.server.ConnectableServerData;
import java.util.List;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.SwordOldAnimation;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoRemoveEvent;
import java.util.Iterator;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoUpdateEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoAddEvent;
import net.labymod.v1_19_4.client.player.VersionedNetworkPlayerInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.watcher.set.WatchableSet;
import net.labymod.core.watcher.set.WatchableHashSet;
import net.labymod.v1_19_4.client.util.WatchablePlayerInfoSet;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.authlib.GameProfile;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.Laby;
import net.labymod.api.event.client.world.DimensionChangeEvent;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.volt.callback.InsertInfo;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import java.util.HashMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import java.util.Collection;
import java.util.Set;
import net.labymod.api.client.network.NetworkPlayerInfo;
import java.util.UUID;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.network.ClientPacketListener;

@Mixin({ fdk.class })
public abstract class MixinClientPacketListener implements ClientPacketListener
{
    private final Map<UUID, NetworkPlayerInfo> labyMod$playerInfos;
    private final Set<NetworkPlayerInfo> labyMod$shownPlayers;
    private Collection<NetworkPlayerInfo> labyMod$unmodifiablePlayerInfos;
    @Mutable
    @Shadow
    @Final
    private Set<fdo> q;
    @Final
    @Shadow
    private Map<UUID, fdo> p;
    @Shadow
    private fdj n;
    
    public MixinClientPacketListener() {
        this.labyMod$playerInfos = new HashMap<UUID, NetworkPlayerInfo>();
        this.labyMod$shownPlayers = (Set<NetworkPlayerInfo>)new ReferenceOpenHashSet();
    }
    
    @Shadow
    public abstract void a(final tj p0);
    
    @Insert(method = { "handleRespawn" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getScoreboard()Lnet/minecraft/world/scores/Scoreboard;", ordinal = 0))
    private void labyMod$fireDimensionChangeEvent(final xs packet, final InsertInfo ci) {
        final add from = this.n.ab().a();
        final add to = packet.c().a();
        Laby.fireEvent(new DimensionChangeEvent(ResourceLocation.create(from.b(), from.a()), ResourceLocation.create(to.b(), to.a())));
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$watchableListedPlayers(final emh $$0, final etd $$1, final sq $$2, final fdq $$3, final GameProfile $$4, final fxw $$5, final CallbackInfo ci) {
        this.q = new WatchableHashSet<fdo>(new WatchablePlayerInfoSet(this.labyMod$shownPlayers));
    }
    
    @Inject(method = { "handlePlayerInfoUpdate" }, at = { @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;)V", shift = At.Shift.BEFORE) }, cancellable = true)
    private void labyMod$disableWarn(final xl $$0, final CallbackInfo ci) {
        ci.cancel();
    }
    
    @Inject(method = { "handlePlayerInfoUpdate" }, at = { @At("TAIL") })
    private void labyMod$handleAddPlayer(final xl packet, final CallbackInfo ci) {
        for (final xl.b entry : packet.d()) {
            final UUID profileId = entry.a();
            if (this.labyMod$playerInfos.containsKey(profileId)) {
                continue;
            }
            final NetworkPlayerInfo playerInfo = new VersionedNetworkPlayerInfo(this.p.get(profileId));
            this.labyMod$playerInfos.put(profileId, playerInfo);
            Laby.fireEvent(new PlayerInfoAddEvent(playerInfo));
        }
        for (final xl.b entry : packet.c()) {
            final NetworkPlayerInfo playerInfo2 = this.labyMod$playerInfos.get(entry.a());
            if (playerInfo2 == null) {
                continue;
            }
            for (final xl.a action : packet.a()) {
                final PlayerInfoUpdateEvent.UpdateType updateType = switch (action) {
                    default -> throw new MatchException(null, null);
                    case a,  b -> null;
                    case d -> PlayerInfoUpdateEvent.UpdateType.UPDATE_LISTED;
                    case c -> PlayerInfoUpdateEvent.UpdateType.GAME_MODE;
                    case e -> PlayerInfoUpdateEvent.UpdateType.PING;
                    case f -> PlayerInfoUpdateEvent.UpdateType.DISPLAY_NAME;
                };
                if (updateType == null) {
                    continue;
                }
                Laby.fireEvent(new PlayerInfoUpdateEvent(playerInfo2, updateType));
            }
        }
    }
    
    @Redirect(method = { "handlePlayerInfoRemove" }, at = @At(value = "INVOKE", target = "Ljava/util/Map;remove(Ljava/lang/Object;)Ljava/lang/Object;"))
    public Object labyMod$handlePlayerInfoRemove(final Map<?, ?> instance, Object value) {
        final UUID profileId = (UUID)value;
        final NetworkPlayerInfo networkPlayerInfo = this.labyMod$playerInfos.remove(profileId);
        if (networkPlayerInfo != null) {
            Laby.fireEvent(new PlayerInfoRemoveEvent(networkPlayerInfo));
        }
        return instance.remove(value);
    }
    
    @Insert(method = { "handleSetEntityData(Lnet/minecraft/network/protocol/game/ClientboundSetEntityDataPacket;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getEntity(I)Lnet/minecraft/world/entity/Entity;", shift = At.Shift.AFTER))
    private void labyMod$handleClassicPvPBlocking(final yj packet, final InsertInfo insertInfo) {
        final int entityId = packet.a();
        final bfh entity = this.n.a(entityId);
        if (!(entity instanceof bym)) {
            return;
        }
        final bym player = (bym)entity;
        final List<aco.b<?>> unpackedData = packet.c();
        if (unpackedData == null || unpackedData.isEmpty()) {
            return;
        }
        unpackedData.stream().filter(entry -> entry.a() == 8).forEach(entry -> {
            final Object value = entry.c();
            if (!(!(value instanceof Byte))) {
                final bdx activeHand = player.ff();
                final int state = (int)value;
                final SwordOldAnimation animation = LabyMod.getInstance().getOldAnimationRegistry().get("sword");
                if (animation != null) {
                    try {
                        final cfv heldItem = player.b(activeHand);
                        if (heldItem.c() == cfy.ut || heldItem.c() instanceof chf) {
                            animation.setBlockingState(player.cs(), state == 1 || state == 3);
                        }
                    }
                    catch (final IllegalArgumentException exception) {
                        animation.setBlockingState(player.cs(), state == 1 || state == 3);
                    }
                }
            }
        });
    }
    
    @ModifyVariable(method = { "onDisconnect" }, at = @At("HEAD"), argsOnly = true)
    public tj labyMod$modifyReason(final tj component) {
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
            final ServerData data = ((VersionedServerController)serverController).getConnectingServerData();
            if (data == null) {
                return component;
            }
            serverData = ConnectableServerData.from(data);
        }
        final ComponentMapper componentMapper = Laby.labyAPI().minecraft().componentMapper();
        return (tj)componentMapper.toMinecraftComponent(Laby.fireEvent(new ServerKickEvent(serverData, componentMapper.fromMinecraftComponent(component), ServerKickEvent.Context.PLAY)).reason());
    }
    
    @Inject(method = { "send(Lnet/minecraft/network/protocol/game/ServerboundResourcePackPacket$Action;)V" }, at = { @At("HEAD") })
    private void labyMod$disableCustomFont(final aax.a action, final CallbackInfo ci) {
        if (action == aax.a.a) {
            LabyMod.references().clientNetworkPacketListener().onLoadServerResourcePack();
        }
    }
    
    @Inject(method = { "handlePlayerAbilities" }, at = { @At("RETURN") })
    private void labyMod$fireAbilityUpdateEvent(final xf packet, final CallbackInfo ci) {
        Laby.fireEvent(new ClientPlayerAbilitiesUpdateEvent((ClientPlayer)emh.N().t));
    }
    
    @Nullable
    @Override
    public NetworkPlayerInfo getNetworkPlayerInfo(final UUID uniqueId) {
        return this.labyMod$playerInfos.get(uniqueId);
    }
    
    @Nullable
    @Override
    public NetworkPlayerInfo getNetworkPlayerInfo(final String username) {
        for (final NetworkPlayerInfo playerInfo : this.labyMod$playerInfos.values()) {
            if (username.equals(playerInfo.profile().getUsername())) {
                return playerInfo;
            }
        }
        return null;
    }
    
    @Override
    public Collection<NetworkPlayerInfo> getNetworkPlayerInfos() {
        if (this.labyMod$unmodifiablePlayerInfos == null) {
            this.labyMod$unmodifiablePlayerInfos = Collections.unmodifiableCollection((Collection<? extends NetworkPlayerInfo>)this.labyMod$playerInfos.values());
        }
        return this.labyMod$unmodifiablePlayerInfos;
    }
    
    @Override
    public Collection<NetworkPlayerInfo> getShownOnlinePlayers() {
        return this.labyMod$shownPlayers;
    }
    
    @Override
    public void simulateKick(final Component reason) {
        ThreadSafe.ensureRenderThread();
        this.a((tj)Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(reason));
    }
}
