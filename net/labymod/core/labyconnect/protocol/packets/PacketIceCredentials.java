// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import java.util.Iterator;
import net.labymod.labypeer.ice.CandidateType;
import net.labymod.labypeer.ice.Transport;
import net.labymod.labypeer.ice.SerializableCandidate;
import net.labymod.labypeer.ice.SerializableComponent;
import java.util.List;
import java.util.ArrayList;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.labypeer.ice.IceCredentials;
import java.util.UUID;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketIceCredentials extends Packet
{
    private UUID targetUser;
    private IceCredentials credentials;
    
    public PacketIceCredentials(final UUID targetUser, final IceCredentials credentials) {
        this.targetUser = targetUser;
        this.credentials = credentials;
    }
    
    public PacketIceCredentials() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.targetUser = buf.readUUID();
        final int componentSize;
        this.credentials = new IceCredentials(buf.readUUID(), buf.readString(), buf.readString(), (List)new ArrayList(componentSize = buf.readByte()));
        for (int i = 0; i < componentSize; ++i) {
            final int candidateSize;
            final SerializableComponent component = new SerializableComponent(buf.readString(), (int)buf.readByte(), this.readCandidate(buf), (List)new ArrayList(candidateSize = buf.readShort()));
            this.credentials.getComponents().add(component);
            for (int j = 0; j < candidateSize; ++j) {
                component.getCandidates().add(this.readCandidate(buf));
            }
        }
    }
    
    private SerializableCandidate readCandidate(final PacketBuffer buf) {
        return new SerializableCandidate(buf.readString(), buf.readInt(), Transport.values()[buf.readByte()], CandidateType.values()[buf.readByte()], buf.readBoolean() ? buf.readString() : null, buf.readLong());
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeUUID(this.targetUser);
        buf.writeUUID(this.credentials.getOwningUser());
        buf.writeString(this.credentials.getUfrag());
        buf.writeString(this.credentials.getPassword());
        buf.writeByte(this.credentials.getComponents().size());
        for (final SerializableComponent component : this.credentials.getComponents()) {
            buf.writeString(component.getStreamName());
            buf.writeByte(component.getComponentId());
            this.writeCandidate(component.getDefaultCandidate(), buf);
            buf.writeShort((short)component.getCandidates().size());
            for (final SerializableCandidate candidate : component.getCandidates()) {
                this.writeCandidate(candidate, buf);
            }
        }
    }
    
    private void writeCandidate(final SerializableCandidate candidate, final PacketBuffer buf) {
        buf.writeString(candidate.getAddress());
        buf.writeInt(candidate.getPort());
        buf.writeByte(candidate.getTransport().ordinal());
        buf.writeByte(candidate.getType().ordinal());
        buf.writeBoolean(candidate.getFoundation() != null);
        if (candidate.getFoundation() != null) {
            buf.writeString(candidate.getFoundation());
        }
        buf.writeLong(candidate.getPriority());
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public UUID getTargetUser() {
        return this.targetUser;
    }
    
    public IceCredentials getCredentials() {
        return this.credentials;
    }
}
