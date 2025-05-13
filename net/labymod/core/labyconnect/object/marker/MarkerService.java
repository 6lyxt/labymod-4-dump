// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.object.marker;

import net.labymod.api.service.Registry;
import net.labymod.api.util.KeyValue;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.Minecraft;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import java.util.Iterator;
import java.util.Collection;
import net.labymod.api.labyconnect.LabyConnectSession;
import java.util.ArrayList;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.core.packet.serverbound.game.feature.marker.ClientAddMarkerPacket;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.Constants;
import net.labymod.api.client.world.object.WorldObject;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.event.client.network.server.NetworkPayloadEvent;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import io.netty.buffer.ByteBuf;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonDevelopment;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.entity.player.Player;
import java.util.UUID;
import net.labymod.api.client.entity.Entity;
import io.netty.buffer.Unpooled;
import net.labymod.core.event.labymod.PacketAddonDevelopmentEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.client.world.object.WorldObjectRegistry;
import net.labymod.serverapi.core.packet.clientbound.game.feature.marker.MarkerPacket;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class MarkerService
{
    private static final MarkerPacket.MarkerSendType DEFAULT_SEND_TYPE;
    private static final float STEPS_PER_BLOCK = 2.0f;
    private static final float MAX_PLACE_DISTANCE = 100.0f;
    private static final int OUTSIDE_HEIGHT_BLOCKS = 20;
    private static final float ENTITY_RANGE = 2.0f;
    private static final int PROTOCOL_VERSION = 2;
    private static final String LABYCONNECT_KEY = "labymod:marker";
    private static final float MAX_RECEIVE_DISTANCE = 64.0f;
    private final WorldObjectRegistry worldObjectRegistry;
    private MarkerPacket.MarkerSendType markerSendType;
    
    public MarkerService(final WorldObjectRegistry worldObjectRegistry) {
        this.markerSendType = MarkerService.DEFAULT_SEND_TYPE;
        this.worldObjectRegistry = worldObjectRegistry;
    }
    
    @Subscribe
    public void handleMarkerKey(final KeyEvent event) {
        if (event.state() != KeyEvent.State.PRESS || !this.isEnabled()) {
            return;
        }
        final Key key = Laby.labyAPI().config().hotkeys().markerKey().get();
        if (key == Key.NONE || key != event.key()) {
            return;
        }
        if (Laby.labyAPI().minecraft().minecraftWindow().isScreenOpened() || !Laby.labyAPI().minecraft().isIngame()) {
            return;
        }
        this.placeMarker();
    }
    
    @Subscribe
    public void handleLabyConnectMarker(final PacketAddonDevelopmentEvent event) {
        final PacketAddonDevelopment packet = event.packet();
        final ClientPlayer self = Laby.labyAPI().minecraft().getClientPlayer();
        final ClientWorld world = Laby.labyAPI().minecraft().clientWorld();
        if (!packet.getKey().equals("labymod:marker") || self == null || world == null || !this.isEnabled()) {
            return;
        }
        final ByteBuf buffer = Unpooled.wrappedBuffer(packet.getData());
        final int version = buffer.readInt();
        if (version < 0 || version > 2) {
            buffer.release();
            return;
        }
        double x;
        double y;
        double z;
        if (version == 0) {
            x = buffer.readInt() + 0.5f;
            y = buffer.readInt() + 1.0f;
            z = buffer.readInt() + 0.5f;
        }
        else if (version == 1) {
            x = buffer.readFloat();
            y = buffer.readFloat();
            z = buffer.readFloat();
        }
        else {
            x = buffer.readDouble();
            y = buffer.readDouble();
            z = buffer.readDouble();
        }
        final boolean large = buffer.readBoolean();
        final Entity target = buffer.readBoolean() ? world.getEntity(new UUID(buffer.readLong(), buffer.readLong())).orElse(null) : null;
        buffer.release();
        final Player sender = world.getPlayer(packet.getSender()).orElse(null);
        if (sender == null || sender.getDistanceSquared(self) > MathHelper.square(64.0f)) {
            return;
        }
        MarkerObject marker;
        if (target != null) {
            marker = MarkerObject.forEntity(sender.getUniqueId(), target, large);
        }
        else {
            marker = MarkerObject.fixed(sender.getUniqueId(), new DoubleVector3(x, y, z), large);
        }
        this.displayMarker(marker);
    }
    
    @Subscribe
    public void resetServerData(final ServerDisconnectEvent event) {
        this.setSendType(null);
    }
    
    @Subscribe
    public void resetServerData(final NetworkPayloadEvent event) {
        if (event.identifier().getNamespace().equals("minecraft") && event.identifier().getPath().equals("brand")) {
            this.setSendType(null);
        }
    }
    
    public boolean isEnabled() {
        return Laby.labyAPI().config().multiplayer().marker().enabled().get();
    }
    
    @NotNull
    public MarkerPacket.MarkerSendType sendType() {
        return this.markerSendType;
    }
    
    public void setSendType(@Nullable MarkerPacket.MarkerSendType markerSendType) {
        if (markerSendType == null) {
            markerSendType = MarkerService.DEFAULT_SEND_TYPE;
        }
        this.markerSendType = markerSendType;
    }
    
    public void displayMarker(@NotNull final MarkerObject marker) {
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
            this.worldObjectRegistry.unregister(v -> {
                final WorldObject object = v.getValue();
                return object instanceof MarkerObject && ((MarkerObject)object).getOwner().equals(marker.getOwner());
            });
            ((Registry<MarkerObject>)this.worldObjectRegistry).register(marker);
            if (!marker.getOwner().equals(Laby.labyAPI().getUniqueId())) {
                Laby.labyAPI().minecraft().sounds().playSound(Constants.Resources.SOUND_MARKER_NOTIFY, 0.1f, 1.0f, marker.position());
            }
        });
    }
    
    public void placeMarker() {
        ThreadSafe.ensureRenderThread();
        final MarkerObject marker = this.createMarker();
        if (marker == null) {
            return;
        }
        this.displayMarker(marker);
        if (this.markerSendType == MarkerPacket.MarkerSendType.SERVER) {
            final DoubleVector3 position = marker.position();
            final ClientAddMarkerPacket packet = new ClientAddMarkerPacket(0, MathHelper.floor(position.getX() - 0.5), MathHelper.floor(position.getY() - 1.0), MathHelper.floor(position.getZ() - 0.5), marker.isLarge(), (marker.getTargetEntity() != null) ? marker.getTargetEntity().getUniqueId() : null);
            Laby.references().labyModProtocolService().sendLabyModPacket((Packet)packet);
        }
        else {
            this.sendLabyConnect(marker);
        }
        Laby.labyAPI().minecraft().sounds().playSound(Constants.Resources.SOUND_PLACE_MARKER, 0.5f, 1.0f, marker.position());
    }
    
    private void sendLabyConnect(final MarkerObject marker) {
        final ClientWorld world = Laby.labyAPI().minecraft().clientWorld();
        final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (world == null || session == null) {
            return;
        }
        final Collection<UUID> receivers = new ArrayList<UUID>();
        for (final Player player : world.getPlayers()) {
            final Friend friend = session.getFriend(player.getUniqueId());
            if (friend != null && friend.isOnline()) {
                receivers.add(friend.getUniqueId());
            }
        }
        if (receivers.isEmpty()) {
            return;
        }
        final ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(2);
        final DoubleVector3 position = marker.position();
        buffer.writeDouble(position.getX());
        buffer.writeDouble(position.getY());
        buffer.writeDouble(position.getZ());
        buffer.writeBoolean(marker.isLarge());
        buffer.writeBoolean(marker.getTargetEntity() != null);
        if (marker.getTargetEntity() != null) {
            buffer.writeLong(marker.getTargetEntity().getUniqueId().getMostSignificantBits());
            buffer.writeLong(marker.getTargetEntity().getUniqueId().getLeastSignificantBits());
        }
        session.sendAddonDevelopment("labymod:marker", receivers.toArray(new UUID[0]), buffer.array());
        buffer.release();
    }
    
    private MarkerObject createMarker() {
        final Minecraft minecraft = Laby.labyAPI().minecraft();
        final ClientPlayer player = minecraft.getClientPlayer();
        final ClientWorld world = minecraft.clientWorld();
        if (player == null || world == null) {
            return null;
        }
        final DoubleVector3 targetPoint = this.getTargetPoint(world, player.perspectiveVector(0.0f), new DoubleVector3(player.eyePosition()));
        if (targetPoint == null) {
            return null;
        }
        final int y = world.getTopBlockYOf(MathHelper.floor(targetPoint.getX()), MathHelper.floor(targetPoint.getY()), MathHelper.floor(targetPoint.getZ()));
        final DoubleVector3 targetLocation = new DoubleVector3(targetPoint.getX(), y, targetPoint.getZ());
        final boolean large = this.isOutside(world, targetLocation);
        final Entity targetEntity = this.getTargetEntity(world, targetLocation);
        if (targetEntity != null) {
            return MarkerObject.forEntity(player.getUniqueId(), targetEntity, large);
        }
        targetLocation.set(MathHelper.floor(targetLocation.getX()) + 0.5f, (int)targetLocation.getY() + 1, MathHelper.floor(targetLocation.getZ()) + 0.5f);
        return MarkerObject.fixed(player.getUniqueId(), targetLocation, large);
    }
    
    private Entity getTargetEntity(final ClientWorld world, final DoubleVector3 location) {
        if (!Laby.references().permissionRegistry().isPermissionEnabled("entity_marker")) {
            return null;
        }
        Entity nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        for (final Entity allEntity : world.getEntities()) {
            final double distance = allEntity.getDistanceSquared(location);
            if (distance < MathHelper.square(2.0f) && distance < nearestDistance && allEntity != Laby.labyAPI().minecraft().getClientPlayer()) {
                nearest = allEntity;
                nearestDistance = distance;
            }
        }
        return nearest;
    }
    
    private boolean isOutside(final ClientWorld world, final DoubleVector3 source) {
        final DoubleVector3 target = source.copy();
        for (int i = 0; i < 20; ++i) {
            if (world.hasSolidBlockAt(MathHelper.floor(target.getX()), MathHelper.floor(target.getY()), MathHelper.floor(target.getZ()))) {
                return false;
            }
            target.add(0.0, 1.0, 0.0);
        }
        return true;
    }
    
    private DoubleVector3 getTargetPoint(final ClientWorld world, final FloatVector3 direction, final DoubleVector3 source) {
        direction.multiply(0.5f);
        for (int max = 200, i = 0; i < max * 2 && !world.hasSolidBlockAt(MathHelper.floor(source.getX()), MathHelper.floor(source.getY()), MathHelper.floor(source.getZ())) && (this.getTargetEntity(world, source) == null || i <= 5); ++i) {
            source.add(direction);
            if (i + 1 == max) {
                return null;
            }
        }
        return source;
    }
    
    static {
        DEFAULT_SEND_TYPE = MarkerPacket.MarkerSendType.LABY_CONNECT;
    }
}
