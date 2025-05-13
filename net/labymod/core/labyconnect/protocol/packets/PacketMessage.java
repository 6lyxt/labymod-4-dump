// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.model.DefaultUser;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketMessage extends Packet
{
    private DefaultUser sender;
    private DefaultUser to;
    private String message;
    private long sentTime;
    private long fileSize;
    private double audioTime;
    
    public PacketMessage(final DefaultUser sender, final DefaultUser to, final String message, final long fileSize, final double time, final long sentTime) {
        this.sender = sender;
        this.to = to;
        this.message = message;
        this.fileSize = fileSize;
        this.audioTime = time;
        this.sentTime = sentTime;
    }
    
    public PacketMessage() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.sender = buf.readChatUser();
        this.to = buf.readChatUser();
        this.message = buf.readString();
        this.fileSize = buf.readLong();
        this.audioTime = buf.readDouble();
        this.sentTime = buf.readLong();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeUser(this.sender);
        buf.writeUser(this.to);
        buf.writeString(this.message);
        buf.writeLong(this.fileSize);
        buf.writeDouble(this.audioTime);
        buf.writeLong(this.sentTime);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public double getAudioTime() {
        return this.audioTime;
    }
    
    public long getFileSize() {
        return this.fileSize;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public User getSender() {
        return this.sender;
    }
    
    public User getTo() {
        return this.to;
    }
    
    public long getSentTime() {
        return this.sentTime;
    }
}
