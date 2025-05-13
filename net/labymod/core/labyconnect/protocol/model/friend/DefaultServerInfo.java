// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.model.friend;

import java.util.Objects;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.labyconnect.protocol.model.friend.ServerInfo;

public class DefaultServerInfo implements ServerInfo
{
    private String serverIp;
    private int serverPort;
    private String gameModeName;
    
    public DefaultServerInfo(final String serverIp, final int serverPort, final String gameModeName) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.gameModeName = gameModeName;
    }
    
    public DefaultServerInfo(final String serverIp, final int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.gameModeName = null;
    }
    
    @Override
    public int getPort() {
        return this.serverPort;
    }
    
    @Override
    public String getAddress() {
        return this.serverIp;
    }
    
    @Nullable
    @Override
    public String getGameModeName() {
        return this.gameModeName;
    }
    
    @Override
    public boolean isSameServer(final ServerInfo serverInfo) {
        return serverInfo != null && this.serverPort == serverInfo.getPort() && Objects.equals(this.serverIp, serverInfo.getAddress());
    }
    
    public void setServerIp(final String serverIp) {
        this.serverIp = serverIp;
    }
    
    public void setServerPort(final int serverPort) {
        this.serverPort = serverPort;
    }
    
    public void setGameModeName(final String specifiedServerName) {
        this.gameModeName = specifiedServerName;
    }
    
    public boolean isServerAvailable() {
        return this.serverIp != null && !this.serverIp.replaceAll(" ", "").isEmpty();
    }
    
    public String getDisplayAddress() {
        String formattedIp = this.serverIp;
        if (formattedIp.endsWith(".")) {
            formattedIp = formattedIp.substring(0, formattedIp.length() - 1);
        }
        if (this.serverPort != 25565) {
            formattedIp = formattedIp + ":" + this.serverPort;
        }
        return formattedIp;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final DefaultServerInfo that = (DefaultServerInfo)o;
        return this.serverPort == that.serverPort && Objects.equals(this.serverIp, that.serverIp) && Objects.equals(this.gameModeName, that.gameModeName);
    }
    
    @Override
    public int hashCode() {
        int result = (this.serverIp != null) ? this.serverIp.hashCode() : 0;
        result = 31 * result + this.serverPort;
        result = 31 * result + ((this.gameModeName != null) ? this.gameModeName.hashCode() : 0);
        return result;
    }
}
