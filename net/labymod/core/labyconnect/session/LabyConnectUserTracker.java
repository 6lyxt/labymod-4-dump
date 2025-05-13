// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.session;

import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.Minecraft;
import net.labymod.api.Laby;
import net.labymod.api.event.client.world.EntityDestructEvent;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.event.client.world.EntitySpawnEvent;
import java.util.UUID;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoRemoveEvent;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoAddEvent;
import java.util.Iterator;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.labymod.LabyModRefreshEvent;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import net.labymod.api.event.client.network.server.NetworkDisconnectEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.world.WorldLoadEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.labymod.core.labyconnect.protocol.packets.PacketUserTracker;
import java.util.Map;
import net.labymod.core.labyconnect.DefaultLabyConnect;

public class LabyConnectUserTracker
{
    private static final long LIST_UPDATE_INTERVAL = 10000L;
    private static final long ENTITY_UPDATE_INTERVAL = 3000L;
    private final DefaultLabyConnect labyConnect;
    private final Map<PacketUserTracker.EnumTrackingAction, List<PacketUserTracker.PlayerEntityMeta>> listBuffer;
    private final Map<PacketUserTracker.EnumTrackingAction, List<PacketUserTracker.PlayerEntityMeta>> entityBuffer;
    private long nextTimeDrainList;
    private long nextTimeDrainEntities;
    private boolean empty;
    
    public LabyConnectUserTracker(final DefaultLabyConnect labyConnect) {
        this.listBuffer = new HashMap<PacketUserTracker.EnumTrackingAction, List<PacketUserTracker.PlayerEntityMeta>>();
        this.entityBuffer = new HashMap<PacketUserTracker.EnumTrackingAction, List<PacketUserTracker.PlayerEntityMeta>>();
        this.nextTimeDrainList = -1L;
        this.nextTimeDrainEntities = -1L;
        this.empty = true;
        this.labyConnect = labyConnect;
        for (final PacketUserTracker.EnumTrackingAction action : PacketUserTracker.EnumTrackingAction.values()) {
            this.listBuffer.put(action, new ArrayList<PacketUserTracker.PlayerEntityMeta>());
            this.entityBuffer.put(action, new ArrayList<PacketUserTracker.PlayerEntityMeta>());
        }
    }
    
    @Subscribe
    public void onWorldLoad(final WorldLoadEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        this.clearBuffers();
    }
    
    @Subscribe
    public void onNetworkDisconnect(final NetworkDisconnectEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        this.clearBuffers();
    }
    
    @Subscribe
    public void onLabyConnectStateUpdate(final LabyConnectStateUpdateEvent event) {
        if (event.state() != LabyConnectState.PLAY) {
            return;
        }
        this.clearBuffers();
        this.trackAllOnlinePlayers();
    }
    
    @Subscribe
    public void onLabyModRefresh(final LabyModRefreshEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        this.clearBuffers();
        this.trackAllOnlinePlayers();
    }
    
    @Subscribe
    public void onGameTick(final GameTickEvent event) {
        if (!this.labyConnect.isConnected()) {
            return;
        }
        if (this.nextTimeDrainList < TimeUtil.getMillis() || this.listBuffer.get(PacketUserTracker.EnumTrackingAction.ADD).size() > 10) {
            this.nextTimeDrainList = TimeUtil.getMillis() + 10000L;
            this.drain(PacketUserTracker.EnumTrackingChannel.LIST, this.listBuffer);
        }
        if (this.nextTimeDrainEntities < TimeUtil.getMillis() || this.entityBuffer.get(PacketUserTracker.EnumTrackingAction.ADD).size() > 10) {
            this.nextTimeDrainEntities = TimeUtil.getMillis() + 3000L;
            this.drain(PacketUserTracker.EnumTrackingChannel.ENTITIES, this.entityBuffer);
        }
    }
    
    private void drain(final PacketUserTracker.EnumTrackingChannel channel, final Map<PacketUserTracker.EnumTrackingAction, List<PacketUserTracker.PlayerEntityMeta>> map) {
        for (final Map.Entry<PacketUserTracker.EnumTrackingAction, List<PacketUserTracker.PlayerEntityMeta>> entry : map.entrySet()) {
            final List<PacketUserTracker.PlayerEntityMeta> buffer = entry.getValue();
            if (buffer.isEmpty()) {
                continue;
            }
            this.labyConnect.sendPacket(new PacketUserTracker(channel, entry.getKey(), buffer.toArray(new PacketUserTracker.PlayerEntityMeta[0])));
            buffer.clear();
        }
    }
    
    @Subscribe
    public void onPlayerInfoAdd(final PlayerInfoAddEvent event) {
        final NetworkPlayerInfo playerInfo = event.playerInfo();
        if (!this.isPlayerInfoValid(playerInfo)) {
            return;
        }
        this.update(this.listBuffer, new PacketUserTracker.PlayerEntityMeta(playerInfo.profile()), PacketUserTracker.EnumTrackingAction.REMOVE, PacketUserTracker.EnumTrackingAction.ADD, true);
    }
    
    @Subscribe
    public void onPlayerInfoRemove(final PlayerInfoRemoveEvent event) {
        final NetworkPlayerInfo playerInfo = event.playerInfo();
        if (!this.isPlayerInfoValid(playerInfo)) {
            return;
        }
        final UUID uniqueId = playerInfo.profile().getUniqueId();
        this.update(this.listBuffer, new PacketUserTracker.PlayerEntityMeta(uniqueId), PacketUserTracker.EnumTrackingAction.ADD, PacketUserTracker.EnumTrackingAction.REMOVE, false);
    }
    
    @Subscribe
    public void onVisiblePlayer(final EntitySpawnEvent event) {
        final Entity entity = event.entity();
        if (!(entity instanceof Player) || !this.isUniqueIdValid(entity.getUniqueId())) {
            return;
        }
        final UUID uniqueId = entity.getUniqueId();
        this.update(this.entityBuffer, new PacketUserTracker.PlayerEntityMeta(uniqueId), PacketUserTracker.EnumTrackingAction.REMOVE, PacketUserTracker.EnumTrackingAction.ADD, true);
    }
    
    @Subscribe
    public void onEntityDestruct(final EntityDestructEvent event) {
        final Entity entity = event.entity();
        if (!(entity instanceof Player) || !this.isUniqueIdValid(entity.getUniqueId())) {
            return;
        }
        final UUID uniqueId = entity.getUniqueId();
        this.update(this.entityBuffer, new PacketUserTracker.PlayerEntityMeta(uniqueId), PacketUserTracker.EnumTrackingAction.ADD, PacketUserTracker.EnumTrackingAction.REMOVE, false);
    }
    
    private void update(final Map<PacketUserTracker.EnumTrackingAction, List<PacketUserTracker.PlayerEntityMeta>> buffers, final PacketUserTracker.PlayerEntityMeta uniqueId, final PacketUserTracker.EnumTrackingAction cleanTarget, final PacketUserTracker.EnumTrackingAction actionTarget, final boolean forceAdd) {
        if (!this.labyConnect.isConnected()) {
            return;
        }
        final List<PacketUserTracker.PlayerEntityMeta> buffer = buffers.get(cleanTarget);
        if (buffer.contains(uniqueId)) {
            buffer.remove(uniqueId);
            if (!forceAdd) {
                return;
            }
        }
        final List<PacketUserTracker.PlayerEntityMeta> target = buffers.get(actionTarget);
        if (!target.contains(uniqueId)) {
            target.add(uniqueId);
        }
        this.empty = false;
    }
    
    private void clearBuffers() {
        for (final List<PacketUserTracker.PlayerEntityMeta> buffer : this.listBuffer.values()) {
            buffer.clear();
        }
        for (final List<PacketUserTracker.PlayerEntityMeta> buffer : this.entityBuffer.values()) {
            buffer.clear();
        }
        if (this.labyConnect.isConnected() && !this.empty) {
            this.labyConnect.sendPacket(new PacketUserTracker(PacketUserTracker.EnumTrackingChannel.LIST, PacketUserTracker.EnumTrackingAction.CLEAR));
            this.labyConnect.sendPacket(new PacketUserTracker(PacketUserTracker.EnumTrackingChannel.ENTITIES, PacketUserTracker.EnumTrackingAction.CLEAR));
        }
        this.empty = true;
    }
    
    private void trackAllOnlinePlayers() {
        final Minecraft minecraft = Laby.labyAPI().minecraft();
        final ClientPacketListener packetListener = minecraft.getClientPacketListener();
        final ClientWorld world = minecraft.clientWorld();
        if (packetListener != null) {
            for (final NetworkPlayerInfo playerInfo : packetListener.getNetworkPlayerInfos()) {
                if (this.isPlayerInfoValid(playerInfo)) {
                    this.listBuffer.get(PacketUserTracker.EnumTrackingAction.ADD).add(new PacketUserTracker.PlayerEntityMeta(playerInfo.profile()));
                    this.empty = false;
                }
            }
        }
        if (world != null) {
            for (final Entity entity : world.getEntities()) {
                this.entityBuffer.get(PacketUserTracker.EnumTrackingAction.ADD).add(new PacketUserTracker.PlayerEntityMeta(entity.getUniqueId()));
                this.empty = false;
            }
        }
    }
    
    private boolean isPlayerInfoValid(final NetworkPlayerInfo playerInfo) {
        final UUID uuid = playerInfo.profile().getUniqueId();
        return this.isUniqueIdValid(uuid);
    }
    
    private boolean isUniqueIdValid(final UUID uniqueId) {
        return uniqueId.getMostSignificantBits() != 0L && uniqueId.getLeastSignificantBits() != 0L && (uniqueId.getMostSignificantBits() >> 12 & 0xFL) == 0x4L;
    }
}
