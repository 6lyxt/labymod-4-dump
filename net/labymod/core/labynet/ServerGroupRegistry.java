// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet;

import com.google.gson.JsonPrimitive;
import java.util.List;
import com.google.gson.JsonArray;
import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;
import com.google.gson.JsonElement;
import net.labymod.api.util.GsonUtil;
import com.google.gson.JsonObject;
import java.util.Iterator;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.labymod.api.labynet.models.ServerGroup;
import java.util.Map;
import net.labymod.api.util.logging.Logging;

public class ServerGroupRegistry
{
    private static final Logging LOGGER;
    private final Map<String, ServerGroup> serverGroups;
    private final Map<String, DummyServerGroup> tempServerGroups;
    
    public ServerGroupRegistry() {
        this.serverGroups = (Map<String, ServerGroup>)new Object2ObjectOpenHashMap();
        this.tempServerGroups = (Map<String, DummyServerGroup>)new Object2ObjectOpenHashMap();
    }
    
    public ServerGroup getServerByName(final String name) {
        final ServerGroup serverGroup = this.serverGroups.get(name);
        if (serverGroup != null) {
            return serverGroup;
        }
        return this.mapServerGroup(this.tempServerGroups.get(name));
    }
    
    public ServerGroup getServerByIp(final String ip) {
        for (final ServerGroup serverGroup : this.serverGroups.values()) {
            if (serverGroup.hasIp(ip)) {
                return serverGroup;
            }
        }
        for (final DummyServerGroup temp : this.tempServerGroups.values()) {
            if (temp.matches(ip)) {
                return this.mapServerGroup(temp);
            }
        }
        return null;
    }
    
    public void initialize(final ServerGroupIndex index) {
        if (index.serverGroups == null) {
            return;
        }
        for (final Map.Entry<String, JsonObject> entry : index.serverGroups.entrySet()) {
            final String key = entry.getKey();
            final JsonObject jsonObject = entry.getValue();
            this.tempServerGroups.put(key, new DummyServerGroup(key, jsonObject));
        }
    }
    
    private ServerGroup mapServerGroup(final DummyServerGroup temp) {
        if (temp == null) {
            return null;
        }
        try {
            final ServerGroup serverGroup = (ServerGroup)GsonUtil.DEFAULT_GSON.fromJson((JsonElement)temp.object, (Class)ServerGroup.class);
            if (serverGroup == null) {
                return null;
            }
            this.serverGroups.put(temp.key, serverGroup);
            return serverGroup;
        }
        catch (final Exception e) {
            ServerGroupRegistry.LOGGER.warn("Failed to map server group " + temp.key, (Throwable)e);
        }
        finally {
            this.tempServerGroups.remove(temp.key);
        }
        return null;
    }
    
    static {
        LOGGER = Logging.create(ServerGroupRegistry.class);
    }
    
    public static class ServerGroupIndex
    {
        @SerializedName("server_groups")
        private Map<String, JsonObject> serverGroups;
    }
    
    private static class DummyServerGroup
    {
        private final String key;
        private final JsonObject object;
        private String address;
        private String[] wildcards;
        private boolean extractedAddresses;
        
        public DummyServerGroup(final String key, final JsonObject jsonObject) {
            this.key = key;
            this.object = jsonObject;
        }
        
        public boolean matches(final String address) {
            if (!this.extractedAddresses) {
                try {
                    this.address = this.object.get("direct_ip").getAsString();
                    if (this.object.has("wildcards")) {
                        final JsonArray jsonArray = this.object.get("wildcards").getAsJsonArray();
                        final List<String> wildcards = new ArrayList<String>();
                        for (final JsonElement element : jsonArray) {
                            if (!element.isJsonPrimitive()) {
                                continue;
                            }
                            final JsonPrimitive primitive = element.getAsJsonPrimitive();
                            if (!primitive.isString()) {
                                continue;
                            }
                            wildcards.add(primitive.getAsString());
                        }
                        this.wildcards = wildcards.toArray(new String[0]);
                    }
                }
                catch (final Exception e) {
                    ServerGroupRegistry.LOGGER.warn("Failed to extract addresses from server group " + this.key, (Throwable)e);
                }
                finally {
                    this.extractedAddresses = true;
                }
            }
            return this.address != null && ServerGroup.addressMatches(address, this.address, this.wildcards);
        }
    }
}
