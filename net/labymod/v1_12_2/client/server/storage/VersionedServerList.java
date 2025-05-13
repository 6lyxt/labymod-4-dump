// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.server.storage;

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
            final fy root = gi.a(new File(this.gameDirectory, "servers.dat"));
            if (root == null) {
                return;
            }
            final ge list = root.c("servers", 10);
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
            final ge list = new ge();
            for (final StorageServerData data : this.serverList) {
                list.a((gn)this.serverDataToTag(data));
            }
            final fy root = new fy();
            root.a("servers", (gn)list);
            gi.a(root, new File(this.gameDirectory, "servers.dat"));
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        super.save();
    }
    
    private fy serverDataToTag(final StorageServerData data) {
        final fy tag = new fy();
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
            final fy metaTag = new fy();
            for (final Map.Entry<String, String> entry : data.metadata().entrySet()) {
                if (entry.getValue().length() > 65535) {
                    metaTag.a((String)entry.getKey(), entry.getValue().getBytes(StandardCharsets.UTF_8));
                }
                else {
                    metaTag.a((String)entry.getKey(), (String)entry.getValue());
                }
            }
            tag.a("labyModMetadata", (gn)metaTag);
        }
        return tag;
    }
    
    private StorageServerData tagToServerData(final fy tag) {
        final StorageServerData data = StorageServerData.of(tag.l("name"), tag.l("ip"));
        if (tag.b("icon", 8)) {
            data.setIconBase64(tag.l("icon"));
        }
        if (tag.b("acceptTextures", 1)) {
            if (tag.q("acceptTextures")) {
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
            final fy metaTag = tag.p("labyModMetadata");
            for (final String key : metaTag.c()) {
                final gn metaValue = metaTag.c(key);
                String value;
                if (metaValue instanceof final gm gm) {
                    value = gm.c_();
                }
                else {
                    if (!(metaValue instanceof fw)) {
                        continue;
                    }
                    value = new String(((fw)metaValue).c(), StandardCharsets.UTF_8);
                }
                data.metadata().put(key, value);
            }
        }
        return data;
    }
}
