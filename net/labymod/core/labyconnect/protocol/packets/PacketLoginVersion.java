// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketLoginVersion extends Packet
{
    private int versionId;
    private String versionName;
    private String betaToken;
    private int minecraftVersion;
    
    public PacketLoginVersion(final int internalVersion, final String externalVersion, final int minecraftVersion) {
        this.versionId = internalVersion;
        this.versionName = externalVersion;
        this.betaToken = "";
        this.minecraftVersion = minecraftVersion;
    }
    
    public PacketLoginVersion() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.versionId = buf.readInt();
        this.versionName = buf.readString();
        this.betaToken = buf.readString();
        if (this.versionId >= 28) {
            this.minecraftVersion = buf.readInt();
        }
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeInt(this.versionId);
        buf.writeString(this.versionName);
        buf.writeString(this.betaToken);
        if (this.versionId >= 28) {
            buf.writeInt(this.minecraftVersion);
        }
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
    }
    
    public String getVersionName() {
        return this.versionName;
    }
    
    public int getVersionID() {
        return this.versionId;
    }
    
    public String getBetaToken() {
        return this.betaToken;
    }
    
    public int getMinecraftVersion() {
        return this.minecraftVersion;
    }
}
