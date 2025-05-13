// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet.insight.model;

import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerAddress;
import com.google.gson.JsonObject;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.core.labynet.insight.Insight;

public class ServerInsight implements Insight
{
    private ServerData serverData;
    private String gameMode;
    
    public ServerInsight(final JsonObject serverData) {
        this.fromJsonObject(serverData);
    }
    
    public ServerInsight(final ServerData serverData, final String gameMode) {
        this.serverData = serverData;
        this.gameMode = gameMode;
    }
    
    public ServerInsight(final ServerData serverData) {
        this(serverData, null);
    }
    
    public ServerData getServerData() {
        return this.serverData;
    }
    
    public String getGameMode() {
        return this.gameMode;
    }
    
    @Override
    public JsonObject toJsonObject() {
        final JsonObject server = new JsonObject();
        if (this.serverData != null) {
            final ServerAddress address = this.serverData.address();
            server.addProperty("address", address.getHost());
            server.addProperty("port", (Number)address.getPort());
            if (this.gameMode != null) {
                server.addProperty("gamemode", this.gameMode);
            }
        }
        return server;
    }
    
    @Override
    public void fromJsonObject(final JsonObject object) {
        if (object.has("address") && object.has("port")) {
            final String address = object.get("address").getAsString();
            final int port = object.get("port").getAsInt();
            this.serverData = ConnectableServerData.builder().address(new ServerAddress(address, port)).build();
            if (object.has("gamemode")) {
                this.gameMode = object.get("gamemode").getAsString();
            }
        }
    }
}
