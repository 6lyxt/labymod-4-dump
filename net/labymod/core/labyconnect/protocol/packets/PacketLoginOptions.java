// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import java.util.TimeZone;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketLoginOptions extends Packet
{
    private boolean showServer;
    private UserStatus status;
    private TimeZone timeZone;
    
    public PacketLoginOptions(final boolean showServer, final UserStatus status, final TimeZone timeZone) {
        this.showServer = showServer;
        this.status = status;
        this.timeZone = timeZone;
    }
    
    public PacketLoginOptions() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.showServer = buf.readBoolean();
        this.status = buf.readUserStatus();
        this.timeZone = TimeZone.getTimeZone(buf.readString());
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeBoolean(this.showServer);
        buf.writeUserStatus(this.status);
        buf.writeString(this.timeZone.getID());
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
    }
    
    public Options getOptions() {
        return new Options(this.showServer, this.status, this.timeZone);
    }
    
    public static class Options
    {
        private final boolean showServer;
        private final UserStatus onlineStatus;
        private final TimeZone timeZone;
        
        public Options(final boolean showServer, final UserStatus onlineStatus, final TimeZone timeZone) {
            this.showServer = showServer;
            this.timeZone = timeZone;
            this.onlineStatus = onlineStatus;
        }
        
        public boolean isShowServer() {
            return this.showServer;
        }
        
        public UserStatus getOnlineStatus() {
            return this.onlineStatus;
        }
        
        public TimeZone getTimeZone() {
            return this.timeZone;
        }
    }
}
