// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import java.util.Objects;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketPlayServerStatusUpdate extends Packet
{
    private String serverIp;
    private int port;
    private String gamemode;
    private boolean viaServerlist;
    
    public PacketPlayServerStatusUpdate(final String serverIp, final int port) {
        this.serverIp = "";
        this.port = 25565;
        this.gamemode = null;
        this.serverIp = serverIp;
        this.port = port;
        this.gamemode = null;
    }
    
    public PacketPlayServerStatusUpdate() {
        this.serverIp = "";
        this.port = 25565;
        this.gamemode = null;
    }
    
    public PacketPlayServerStatusUpdate(final String serverIp, final int port, final String gamemode, final boolean viaServerlist) {
        this.serverIp = "";
        this.port = 25565;
        this.gamemode = null;
        this.serverIp = serverIp;
        this.port = port;
        this.gamemode = gamemode;
        this.viaServerlist = viaServerlist;
    }
    
    public String getServerIp() {
        return this.serverIp;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public String getGamemode() {
        return this.gamemode;
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.serverIp = buf.readString();
        this.port = buf.readInt();
        this.viaServerlist = buf.readBoolean();
        if (buf.readBoolean()) {
            this.gamemode = buf.readString();
        }
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeString(this.serverIp);
        buf.writeInt(this.port);
        buf.writeBoolean(this.viaServerlist);
        if (this.gamemode != null && !this.gamemode.isEmpty()) {
            buf.writeBoolean(true);
            buf.writeString(this.gamemode);
        }
        else {
            buf.writeBoolean(false);
        }
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
    }
    
    public boolean equals(final PacketPlayServerStatusUpdate packet) {
        return this.serverIp.equals(packet.serverIp) && this.port == packet.port && Objects.equals(this.gamemode, packet.gamemode);
    }
}
