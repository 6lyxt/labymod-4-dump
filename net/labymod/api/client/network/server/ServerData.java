// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network.server;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ServerData
{
    private final ServerType type;
    protected String name;
    protected ServerAddress serverAddress;
    protected ServerAddress actualAddress;
    
    protected ServerData(@Nullable final String name, @NotNull final ServerAddress serverAddress, final ServerType type) {
        Objects.requireNonNull(serverAddress, "Server address cannot be null!");
        this.name = name;
        this.serverAddress = serverAddress;
        this.type = type;
    }
    
    public String getName() {
        return (this.name == null) ? this.serverAddress.getHost() : this.name;
    }
    
    public ServerAddress address() {
        return this.serverAddress;
    }
    
    public ServerAddress actualAddress() {
        if (this.actualAddress == null) {
            this.actualAddress = this.serverAddress.resolve();
        }
        return this.actualAddress;
    }
    
    public boolean isLan() {
        return this.type == ServerType.LAN;
    }
    
    public boolean isRealm() {
        return this.type == ServerType.REALM;
    }
    
    public ServerType type() {
        return this.type;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ServerData that = (ServerData)o;
        return this.type == that.type && Objects.equals(this.name, that.name) && this.serverAddress.equals(that.serverAddress);
    }
    
    @Override
    public int hashCode() {
        int result = (this.type != null) ? this.type.hashCode() : 0;
        result = 31 * result + ((this.name != null) ? this.name.hashCode() : 0);
        result = 31 * result + ((this.serverAddress != null) ? this.serverAddress.hashCode() : 0);
        return result;
    }
}
