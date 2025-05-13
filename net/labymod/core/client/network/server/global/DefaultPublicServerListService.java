// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.network.server.global;

import net.labymod.api.util.io.web.exception.WebRequestException;
import java.util.Iterator;
import com.google.gson.JsonElement;
import java.util.Map;
import javax.inject.Inject;
import net.labymod.api.Constants;
import net.labymod.api.client.network.server.global.PublicServerData;
import java.util.ArrayList;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.network.server.global.PublicServerListService;
import com.google.gson.JsonObject;
import net.labymod.api.service.FetchService;

@Singleton
@Implements(PublicServerListService.class)
public class DefaultPublicServerListService extends FetchService<JsonObject> implements PublicServerListService
{
    private ArrayList<PublicServerData> servers;
    
    @Inject
    public DefaultPublicServerListService() {
        super(Constants.LegacyUrls.PUBLIC_SERVERS, JsonObject.class);
        this.servers = new ArrayList<PublicServerData>();
    }
    
    @Override
    public void success(final JsonObject result) {
        final JsonObject serversObject = result.get("servers").getAsJsonObject();
        final ArrayList<PublicServerData> servers = new ArrayList<PublicServerData>();
        for (final Map.Entry<String, JsonElement> entry : serversObject.entrySet()) {
            final String address = entry.getKey();
            final JsonObject data = entry.getValue().getAsJsonObject();
            final boolean isPartner = data.get("partner").getAsBoolean();
            final PublicServerData serverData = new PublicServerData(address, isPartner);
            servers.add(serverData);
        }
        this.servers = servers;
    }
    
    @Override
    public void failed(final WebRequestException exception) {
        exception.printStackTrace();
    }
    
    @Override
    public ArrayList<PublicServerData> getServers() {
        return this.servers;
    }
}
