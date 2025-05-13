// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network.server;

import java.util.Objects;
import net.labymod.api.LabyAPI;
import java.util.concurrent.TimeUnit;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;

public class ConnectableServerData extends ServerData
{
    protected ServerResourcePackStatus resourcePackStatus;
    
    protected ConnectableServerData(@Nullable final String name, @NotNull final ServerAddress serverAddress, final ServerType type, @Nullable final ServerResourcePackStatus resourcePackStatus) {
        super(name, serverAddress, type);
        this.resourcePackStatus = resourcePackStatus;
        if (this.resourcePackStatus == null) {
            this.resourcePackStatus = ServerResourcePackStatus.PROMPT;
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static ConnectableServerData from(@NotNull final ServerData serverData) {
        if (serverData instanceof final ConnectableServerData connectableServerData) {
            return connectableServerData;
        }
        return new ConnectableServerData(serverData.name, serverData.address(), serverData.type(), null);
    }
    
    public ServerResourcePackStatus resourcePackStatus() {
        return this.resourcePackStatus;
    }
    
    public void connect() {
        this.connect(null);
    }
    
    public void connect(final String command) {
        this.connect(command, 0);
    }
    
    public void connect(final String command, final int delay) {
        Laby.labyAPI().serverController().joinServer(this, (command == null) ? null : (() -> {
            if (delay == 0) {
                Laby.labyAPI().minecraft().chatExecutor().chat(command);
            }
            else {
                Task.builder(() -> {
                    final LabyAPI labyAPI = Laby.labyAPI();
                    final ServerController serverController = labyAPI.serverController();
                    final ServerData currentServerData = serverController.getCurrentServerData();
                    if (currentServerData == this) {
                        labyAPI.minecraft().chatExecutor().chat(command);
                    }
                }).delay(delay, TimeUnit.MILLISECONDS).build().execute();
            }
        }));
    }
    
    public ConnectableServerData copy() {
        return new ConnectableServerData(this.name, this.serverAddress, this.type(), this.resourcePackStatus);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof final ConnectableServerData that) {
            return super.equals(o) && this.resourcePackStatus == that.resourcePackStatus;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + ((this.resourcePackStatus != null) ? this.resourcePackStatus.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    public static class Builder
    {
        private String name;
        private ServerAddress serverAddress;
        private ServerResourcePackStatus resourcePackStatus;
        private ServerType type;
        
        protected Builder() {
            this.type = ServerType.THIRD_PARTY;
        }
        
        public Builder name(@Nullable final String name) {
            this.name = name;
            return this;
        }
        
        public Builder address(@NotNull final ServerAddress serverAddress) {
            Objects.requireNonNull(serverAddress, "Server address cannot be null!");
            this.serverAddress = serverAddress;
            return this;
        }
        
        public Builder address(@NotNull final String serverAddress) {
            Objects.requireNonNull(serverAddress, "Server address cannot be null!");
            return this.address(ServerAddress.parse(serverAddress));
        }
        
        public Builder resourcePackStatus(@Nullable ServerResourcePackStatus resourcePackStatus) {
            if (resourcePackStatus == null) {
                resourcePackStatus = ServerResourcePackStatus.PROMPT;
            }
            this.resourcePackStatus = resourcePackStatus;
            return this;
        }
        
        public Builder lan(final boolean lan) {
            this.type = (lan ? ServerType.LAN : ServerType.THIRD_PARTY);
            return this;
        }
        
        public Builder type(final ServerType type) {
            this.type = type;
            return this;
        }
        
        public ConnectableServerData build() {
            Objects.requireNonNull(this.serverAddress, "Server address cannot be null!");
            return new ConnectableServerData(this.name, this.serverAddress, this.type, this.resourcePackStatus);
        }
    }
}
