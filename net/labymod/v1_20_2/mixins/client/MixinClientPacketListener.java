// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client;

import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.component.Component;
import java.util.Collections;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.event.client.entity.player.ClientPlayerAbilitiesUpdateEvent;
import net.labymod.api.client.entity.player.ClientPlayer;
import java.util.List;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.SwordOldAnimation;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoRemoveEvent;
import java.util.Iterator;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoUpdateEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoAddEvent;
import net.labymod.v1_20_2.client.player.VersionedNetworkPlayerInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.watcher.set.WatchableSet;
import net.labymod.core.watcher.set.WatchableHashSet;
import net.labymod.v1_20_2.client.util.WatchablePlayerInfoSet;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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

@Mixin({ fiy.class })
public abstract class MixinClientPacketListener implements ClientPacketListener
{
    private final Map<UUID, NetworkPlayerInfo> labyMod$playerInfos;
    private final Set<NetworkPlayerInfo> labyMod$shownPlayers;
    private Collection<NetworkPlayerInfo> labyMod$unmodifiablePlayerInfos;
    @Mutable
    @Shadow
    @Final
    private Set<fjf> r;
    @Final
    @Shadow
    private Map<UUID, fjf> q;
    @Shadow
    private fix o;
    
    public MixinClientPacketListener() {
        this.labyMod$playerInfos = new HashMap<UUID, NetworkPlayerInfo>();
        this.labyMod$shownPlayers = (Set<NetworkPlayerInfo>)new ReferenceOpenHashSet();
    }
    
    @Shadow
    public abstract sm l();
    
    @Insert(method = { "handleRespawn" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getScoreboard()Lnet/minecraft/world/scores/Scoreboard;", ordinal = 0))
    private void labyMod$fireDimensionChangeEvent(final zg packet, final InsertInfo ci) {
        final aew from = this.o.ac().a();
        final aew to = packet.a().b().a();
        Laby.fireEvent(new DimensionChangeEvent(ResourceLocation.create(from.b(), from.a()), ResourceLocation.create(to.b(), to.a())));
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$watchableListedPlayers(final eqv $$0, final sm $$1, final fjb $$2, final CallbackInfo ci) {
        this.r = new WatchableHashSet<fjf>(new WatchablePlayerInfoSet(this.labyMod$shownPlayers));
    }
    
    @Inject(method = { "handlePlayerInfoUpdate" }, at = { @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;)V", shift = At.Shift.BEFORE) }, cancellable = true)
    private void labyMod$disableWarn(final za $$0, final CallbackInfo ci) {
        ci.cancel();
    }
    
    @Inject(method = { "handlePlayerInfoUpdate" }, at = { @At("TAIL") })
    private void labyMod$handleAddPlayer(final za packet, final CallbackInfo ci) {
        for (final za.b entry : packet.e()) {
            final UUID profileId = entry.a();
            if (this.labyMod$playerInfos.containsKey(profileId)) {
                continue;
            }
            final NetworkPlayerInfo playerInfo = new VersionedNetworkPlayerInfo(this.q.get(profileId));
            this.labyMod$playerInfos.put(profileId, playerInfo);
            Laby.fireEvent(new PlayerInfoAddEvent(playerInfo));
        }
        for (final za.b entry : packet.d()) {
            final NetworkPlayerInfo playerInfo2 = this.labyMod$playerInfos.get(entry.a());
            if (playerInfo2 == null) {
                continue;
            }
            for (final za.a action : packet.a()) {
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
    private void labyMod$handleClassicPvPBlocking(final zx packet, final InsertInfo insertInfo) {
        final int entityId = packet.a();
        final biq entity = this.o.a(entityId);
        if (!(entity instanceof cbu)) {
            return;
        }
        final cbu player = (cbu)entity;
        final List<aeh.b<?>> unpackedData = packet.d();
        if (unpackedData == null || unpackedData.isEmpty()) {
            return;
        }
        unpackedData.stream().filter(entry -> entry.a() == 8).forEach(entry -> {
            final Object value = entry.c();
            if (!(!(value instanceof Byte))) {
                final bgx activeHand = player.fn();
                final int state = (int)value;
                final SwordOldAnimation animation = LabyMod.getInstance().getOldAnimationRegistry().get("sword");
                if (animation != null) {
                    try {
                        final cjf heldItem = player.b(activeHand);
                        if (heldItem.d() == cji.uy || heldItem.d() instanceof ckq) {
                            animation.setBlockingState(player.cv(), state == 1 || state == 3);
                        }
                    }
                    catch (final IllegalArgumentException exception) {
                        animation.setBlockingState(player.cv(), state == 1 || state == 3);
                    }
                }
            }
        });
    }
    
    @Inject(method = { "handlePlayerAbilities" }, at = { @At("RETURN") })
    private void labyMod$fireAbilityUpdateEvent(final yu packet, final CallbackInfo ci) {
        Laby.fireEvent(new ClientPlayerAbilitiesUpdateEvent((ClientPlayer)eqv.O().s));
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
        final sm connection = this.l();
        if (connection != null) {
            connection.a((tl)Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(reason));
        }
    }
}
