// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import java.util.Iterator;
import net.labymod.api.mojang.Property;
import java.util.Collection;
import net.labymod.api.mojang.GameProfile;
import java.util.UUID;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import java.util.Base64;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketUserTracker extends Packet
{
    private static final Base64.Decoder BASE64_DECODER;
    private EnumTrackingChannel channel;
    private EnumTrackingAction action;
    private PlayerEntityMeta[] users;
    
    public PacketUserTracker() {
    }
    
    public PacketUserTracker(final EnumTrackingChannel channel, final EnumTrackingAction action) {
        this(channel, action, new PlayerEntityMeta[0]);
    }
    
    public PacketUserTracker(final EnumTrackingChannel channel, final EnumTrackingAction action, final PlayerEntityMeta[] users) {
        this.channel = channel;
        this.action = action;
        this.users = users;
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        final int trackerVersion = buf.readByte();
        this.channel = EnumTrackingChannel.values()[buf.readByte()];
        this.action = EnumTrackingAction.values()[buf.readByte()];
        if (this.action != EnumTrackingAction.CLEAR) {
            this.users = new PlayerEntityMeta[buf.readInt()];
            for (int i = 0; i < this.users.length; ++i) {
                this.users[i] = new PlayerEntityMeta(buf.readLong(), buf.readLong());
                if (this.channel == EnumTrackingChannel.LIST && this.action == EnumTrackingAction.ADD) {
                    final boolean hasCape = buf.readBoolean();
                    if (hasCape) {
                        this.users[i].setCapeId(buf.readShort());
                    }
                }
            }
        }
        if (this.action == EnumTrackingAction.UPDATE) {
            buf.readByte();
        }
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeByte(6);
        buf.writeByte(this.channel.ordinal());
        buf.writeByte(this.action.ordinal());
        if (this.action != EnumTrackingAction.CLEAR) {
            buf.writeInt(this.users.length);
            for (final PlayerEntityMeta user : this.users) {
                buf.writeLong(user.getMostSignificantBits());
                buf.writeLong(user.getLeastSignificantBits());
                if (this.channel == EnumTrackingChannel.LIST && this.action == EnumTrackingAction.ADD) {
                    final boolean hasCape = user.hasCape();
                    buf.writeBoolean(hasCape);
                    if (hasCape) {
                        buf.writeShort(user.getCapeId());
                    }
                }
            }
        }
        if (this.action == EnumTrackingAction.UPDATE) {
            buf.writeByte(0);
        }
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
    }
    
    public EnumTrackingChannel getChannel() {
        return this.channel;
    }
    
    public EnumTrackingAction getAction() {
        return this.action;
    }
    
    public PlayerEntityMeta[] getUsers() {
        return this.users;
    }
    
    static {
        BASE64_DECODER = Base64.getDecoder();
    }
    
    public enum EnumTrackingChannel
    {
        ENTITIES, 
        LIST;
    }
    
    public enum EnumTrackingAction
    {
        ADD, 
        REMOVE, 
        UPDATE, 
        CLEAR, 
        SYNC;
    }
    
    public static class PlayerEntityMeta
    {
        private final UUID uuid;
        private boolean hasCape;
        private short capeId;
        
        public PlayerEntityMeta(final UUID uuid) {
            this.capeId = 0;
            this.uuid = uuid;
        }
        
        public PlayerEntityMeta(final long mostSignificantBits, final long leastSignificantBits) {
            this.capeId = 0;
            this.uuid = new UUID(mostSignificantBits, leastSignificantBits);
        }
        
        public PlayerEntityMeta(final GameProfile profile) {
            this.capeId = 0;
            this.uuid = profile.getUniqueId();
            try {
                final Collection<Property> textures = profile.getProperties().get("textures");
                if (textures != null) {
                    final Iterator<Property> iterator = textures.iterator();
                    if (iterator.hasNext()) {
                        final Property texture = iterator.next();
                        final String json = new String(PacketUserTracker.BASE64_DECODER.decode(texture.getValue()));
                        final Short capeId = this.getCapeId(json);
                        if (capeId != null) {
                            this.hasCape = true;
                            this.capeId = capeId;
                        }
                    }
                }
            }
            catch (final Exception e) {
                e.printStackTrace();
            }
        }
        
        public void setCapeId(final short capeId) {
            this.capeId = capeId;
        }
        
        @Override
        public int hashCode() {
            return this.uuid.hashCode();
        }
        
        @Override
        public boolean equals(final Object obj) {
            return obj instanceof PlayerEntityMeta && this.uuid.equals(((PlayerEntityMeta)obj).uuid);
        }
        
        public long getMostSignificantBits() {
            return this.uuid.getMostSignificantBits();
        }
        
        public long getLeastSignificantBits() {
            return this.uuid.getLeastSignificantBits();
        }
        
        public boolean hasCape() {
            return this.hasCape;
        }
        
        public short getCapeId() {
            return this.capeId;
        }
        
        public UUID getUuid() {
            return this.uuid;
        }
        
        private Short getCapeId(String json) {
            try {
                final int capePos = json.indexOf("\"CAPE\" : {");
                if (capePos == -1) {
                    return null;
                }
                json = json.substring(capePos);
                final String urlPrefix = "textures.minecraft.net/texture/";
                final int urlPos = json.indexOf(urlPrefix);
                if (urlPos == -1) {
                    return null;
                }
                json = json.substring(urlPos + urlPrefix.length());
                final int hashEnd = json.indexOf("\"");
                if (hashEnd == -1) {
                    return null;
                }
                json = json.substring(0, hashEnd);
                if (json.length() < 4) {
                    return null;
                }
                return (short)Integer.parseInt(json.substring(0, 4), 16);
            }
            catch (final Exception e) {
                return null;
            }
        }
    }
}
