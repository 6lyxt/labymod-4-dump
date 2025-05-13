// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client;

import net.labymod.api.util.ThreadSafe;
import java.util.Collections;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.event.client.entity.player.ClientPlayerAbilitiesUpdateEvent;
import net.labymod.api.client.entity.player.ClientPlayer;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.client.component.Component;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import net.labymod.api.client.network.server.ConnectableServerData;
import java.util.List;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.SwordOldAnimation;
import java.util.Iterator;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoUpdateEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoRemoveEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoAddEvent;
import net.labymod.v1_18_2.client.player.VersionedNetworkPlayerInfo;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.Laby;
import net.labymod.api.event.client.world.DimensionChangeEvent;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.volt.callback.InsertInfo;
import java.util.HashMap;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import java.util.Collection;
import net.labymod.api.client.network.NetworkPlayerInfo;
import java.util.UUID;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.network.ClientPacketListener;

@Mixin({ emt.class })
public abstract class MixinClientPacketListener implements ClientPacketListener
{
    private final Map<UUID, NetworkPlayerInfo> labyMod$playerInfos;
    private Collection<NetworkPlayerInfo> labyMod$unmodifiablePlayerInfos;
    @Final
    @Shadow
    private Map<UUID, emw> i;
    @Shadow
    private ems g;
    
    public MixinClientPacketListener() {
        this.labyMod$playerInfos = new HashMap<UUID, NetworkPlayerInfo>();
    }
    
    @Shadow
    public abstract void a(final qk p0);
    
    @Insert(method = { "handleRespawn" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getScoreboard()Lnet/minecraft/world/scores/Scoreboard;", ordinal = 0))
    private void labyMod$fireDimensionChangeEvent(final tp packet, final InsertInfo ci) {
        final yt from = this.g.aa().a();
        final yt to = packet.c().a();
        Laby.fireEvent(new DimensionChangeEvent(ResourceLocation.create(from.b(), from.a()), ResourceLocation.create(to.b(), to.a())));
    }
    
    @Insert(method = { "handlePlayerInfo(Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoPacket;)V" }, at = @At("TAIL"))
    private void labyMod$handlePlayerInfo(final ti packet, final InsertInfo ci) {
        for (final ti.b entry : packet.b()) {
            switch (packet.c()) {
                case a: {
                    final emw info = this.i.get(entry.a().getId());
                    if (info == null) {
                        continue;
                    }
                    final NetworkPlayerInfo playerInfo = new VersionedNetworkPlayerInfo(info);
                    this.labyMod$playerInfos.put(playerInfo.profile().getUniqueId(), playerInfo);
                    Laby.fireEvent(new PlayerInfoAddEvent(playerInfo));
                    continue;
                }
                case e: {
                    final NetworkPlayerInfo info2 = this.labyMod$playerInfos.remove(entry.a().getId());
                    if (info2 == null) {
                        continue;
                    }
                    Laby.fireEvent(new PlayerInfoRemoveEvent(info2));
                    continue;
                }
                case d:
                case b:
                case c: {
                    final NetworkPlayerInfo info2 = this.labyMod$playerInfos.get(entry.a().getId());
                    if (info2 == null) {
                        continue;
                    }
                    final PlayerInfoUpdateEvent.UpdateType updateType = switch (packet.c()) {
                        default -> throw new MatchException(null, null);
                        case d -> PlayerInfoUpdateEvent.UpdateType.DISPLAY_NAME;
                        case b -> PlayerInfoUpdateEvent.UpdateType.GAME_MODE;
                        case c -> PlayerInfoUpdateEvent.UpdateType.PING;
                        case a,  e -> null;
                    };
                    if (updateType != null) {
                        Laby.fireEvent(new PlayerInfoUpdateEvent(info2, updateType));
                        continue;
                    }
                    continue;
                }
            }
        }
    }
    
    @Insert(method = { "handleSetEntityData(Lnet/minecraft/network/protocol/game/ClientboundSetEntityDataPacket;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getEntity(I)Lnet/minecraft/world/entity/Entity;", shift = At.Shift.AFTER))
    private void labyMod$handleClassicPvPBlocking(final uf packet, final InsertInfo insertInfo) {
        final int entityId = packet.c();
        final axk entity = this.g.a(entityId);
        if (!(entity instanceof boj)) {
            return;
        }
        final boj player = (boj)entity;
        final List<ye.a<?>> unpackedData = packet.b();
        if (unpackedData == null || unpackedData.isEmpty()) {
            return;
        }
        unpackedData.stream().filter(entry -> entry.a().a() == 8).forEach(entry -> {
            final Object value = entry.b();
            if (!(!(value instanceof Byte))) {
                final awg activeHand = player.eN();
                final int state = (int)value;
                final SwordOldAnimation animation = LabyMod.getInstance().getOldAnimationRegistry().get("sword");
                if (animation != null) {
                    try {
                        final buw heldItem = player.b(activeHand);
                        if (heldItem.c() == buy.sv || heldItem.c() instanceof bwd) {
                            animation.setBlockingState(player.cm(), state == 1 || state == 3);
                        }
                    }
                    catch (final IllegalArgumentException exception) {
                        animation.setBlockingState(player.cm(), state == 1 || state == 3);
                    }
                }
            }
        });
    }
    
    @ModifyVariable(method = { "onDisconnect" }, at = @At("HEAD"), argsOnly = true)
    public qk labyMod$modifyReason(final qk component) {
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
            if (dyr.D().F() == null) {
                return component;
            }
            serverData = ConnectableServerData.from(serverController.createServerData(dyr.D().F()));
        }
        return (qk)Laby.fireEvent(new ServerKickEvent(serverData, (Component)component, ServerKickEvent.Context.PLAY)).reason();
    }
    
    @Inject(method = { "send(Lnet/minecraft/network/protocol/game/ServerboundResourcePackPacket$Action;)V" }, at = { @At("HEAD") })
    private void labyMod$disableCustomFont(final wo.a action, final CallbackInfo ci) {
        if (action == wo.a.a) {
            LabyMod.references().clientNetworkPacketListener().onLoadServerResourcePack();
        }
    }
    
    @Inject(method = { "handlePlayerAbilities" }, at = { @At("RETURN") })
    private void labyMod$fireAbilityUpdateEvent(final te packet, final CallbackInfo ci) {
        Laby.fireEvent(new ClientPlayerAbilitiesUpdateEvent((ClientPlayer)dyr.D().s));
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
    public void simulateKick(final Component reason) {
        ThreadSafe.ensureRenderThread();
        this.a((qk)Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(reason));
    }
}
