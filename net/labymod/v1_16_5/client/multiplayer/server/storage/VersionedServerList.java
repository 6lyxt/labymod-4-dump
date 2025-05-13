// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.multiplayer.server.storage;

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
            final md root = mn.b(new File(this.gameDirectory, "servers.dat"));
            if (root == null) {
                return;
            }
            final mj list = root.d("servers", 10);
            for (int i = 0; i < list.size(); ++i) {
                this.serverList.add(this.tagToServerData(list.a(i)));
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
            final mj list = new mj();
            for (final StorageServerData data : this.serverList) {
                list.add((Object)this.serverDataToTag(data));
            }
            final md root = new md();
            root.a("servers", (mt)list);
            final File tempFile = File.createTempFile("servers", ".dat", this.gameDirectory);
            mn.b(root, tempFile);
            final File oldFile = new File(this.gameDirectory, "servers.dat_old");
            final File newFile = new File(this.gameDirectory, "servers.dat");
            x.a(newFile, tempFile, oldFile);
            tempFile.delete();
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        super.save();
    }
    
    private md serverDataToTag(final StorageServerData data) {
        final md tag = new md();
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
            final md metaTag = new md();
            for (final Map.Entry<String, String> entry : data.metadata().entrySet()) {
                if (entry.getValue().length() > 65535) {
                    metaTag.a((String)entry.getKey(), entry.getValue().getBytes(StandardCharsets.UTF_8));
                }
                else {
                    metaTag.a((String)entry.getKey(), (String)entry.getValue());
                }
            }
            tag.a("labyModMetadata", (mt)metaTag);
        }
        return tag;
    }
    
    private StorageServerData tagToServerData(final md tag) {
        final StorageServerData data = StorageServerData.of(tag.l("name"), tag.l("ip"));
        if (tag.c("icon", 8)) {
            data.setIconBase64(tag.l("icon"));
        }
        if (tag.c("acceptTextures", 1)) {
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
        if (tag.c("labyModMetadata", 10)) {
            final md metaTag = tag.p("labyModMetadata");
            for (final String key : metaTag.d()) {
                final mt metaValue = metaTag.c(key);
                String value;
                if (metaValue instanceof ms) {
                    value = metaValue.f_();
                }
                else {
                    if (!(metaValue instanceof ma)) {
                        continue;
                    }
                    value = new String(((ma)metaValue).d(), StandardCharsets.UTF_8);
                }
                data.metadata().put(key, value);
            }
        }
        return data;
    }
}
