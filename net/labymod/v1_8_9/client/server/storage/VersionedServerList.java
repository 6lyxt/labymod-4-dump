// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.server.storage;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import java.util.Iterator;
import net.labymod.api.client.network.server.storage.StorageServerData;
import java.io.File;
import net.labymod.core.client.network.server.storage.DefaultServerList;

public class VersionedServerList extends DefaultServerList
{
    @Override
    public void load() {
        try {
            this.serverList.clear();
            final dn root = dx.a(new File(this.gameDirectory, "servers.dat"));
            if (root == null) {
                return;
            }
            final du list = root.c("servers", 10);
            for (int i = 0; i < list.c(); ++i) {
                this.serverList.add(this.tagToServerData(list.b(i)));
            }
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        super.load();
    }
    
    @Override
    public void save() {
        try {
            final du list = new du();
            for (final StorageServerData data : this.serverList) {
                list.a((eb)this.serverDataToTag(data));
            }
            final dn root = new dn();
            root.a("servers", (eb)list);
            dx.a(root, new File(this.gameDirectory, "servers.dat"));
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        super.save();
    }
    
    private dn serverDataToTag(final StorageServerData data) {
        final dn tag = new dn();
        tag.a("name", data.getName());
        tag.a("ip", data.address().toString());
        if (data.getIconBase64() != null) {
            tag.a("icon", data.getIconBase64());
        }
        if (data.resourcePackStatus() == ServerResourcePackStatus.ENABLED) {
            tag.a("acceptTextures", true);
        }
        else if (data.resourcePackStatus() == ServerResourcePackStatus.DISABLED) {
            tag.a("acceptTextures", false);
        }
        if (data.hasMetadata()) {
            final dn metaTag = new dn();
            for (final Map.Entry<String, String> entry : data.metadata().entrySet()) {
                if (entry.getValue().length() > 65535) {
                    metaTag.a((String)entry.getKey(), entry.getValue().getBytes(StandardCharsets.UTF_8));
                }
                else {
                    metaTag.a((String)entry.getKey(), (String)entry.getValue());
                }
            }
            tag.a("labyModMetadata", (eb)metaTag);
        }
        return tag;
    }
    
    private StorageServerData tagToServerData(final dn tag) {
        final StorageServerData data = StorageServerData.of(tag.j("name"), tag.j("ip"));
        if (tag.b("icon", 8)) {
            data.setIconBase64(tag.j("icon"));
        }
        if (tag.b("acceptTextures", 1)) {
            if (tag.n("acceptTextures")) {
                data.setResourcePackStatus(ServerResourcePackStatus.ENABLED);
            }
            else {
                data.setResourcePackStatus(ServerResourcePackStatus.DISABLED);
            }
        }
        else {
            data.setResourcePackStatus(ServerResourcePackStatus.PROMPT);
        }
        if (tag.b("labyModMetadata", 10)) {
            final dn metaTag = tag.m("labyModMetadata");
            for (final String key : metaTag.c()) {
                final eb metaValue = metaTag.a(key);
                String value;
                if (metaValue instanceof final ea ea) {
                    value = ea.a_();
                }
                else {
                    if (!(metaValue instanceof dl)) {
                        continue;
                    }
                    value = new String(((dl)metaValue).c(), StandardCharsets.UTF_8);
                }
                data.metadata().put(key, value);
            }
        }
        return data;
    }
}
