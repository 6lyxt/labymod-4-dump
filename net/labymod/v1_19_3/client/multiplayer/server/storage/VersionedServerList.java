// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.multiplayer.server.storage;

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
            final qp root = qz.b(new File(this.gameDirectory, "servers.dat"));
            if (root == null) {
                return;
            }
            final qv list = root.c("servers", 10);
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
            final qv list = new qv();
            for (final StorageServerData data : this.serverList) {
                list.add((Object)this.serverDataToTag(data));
            }
            final qp root = new qp();
            root.a("servers", (ri)list);
            final File tempFile = File.createTempFile("servers", ".dat", this.gameDirectory);
            qz.b(root, tempFile);
            final File oldFile = new File(this.gameDirectory, "servers.dat_old");
            final File newFile = new File(this.gameDirectory, "servers.dat");
            aa.a(newFile, tempFile, oldFile);
            tempFile.delete();
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        super.save();
    }
    
    private qp serverDataToTag(final StorageServerData data) {
        final qp tag = new qp();
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
            final qp metaTag = new qp();
            for (final Map.Entry<String, String> entry : data.metadata().entrySet()) {
                if (entry.getValue().length() > 65535) {
                    metaTag.a((String)entry.getKey(), entry.getValue().getBytes(StandardCharsets.UTF_8));
                }
                else {
                    metaTag.a((String)entry.getKey(), (String)entry.getValue());
                }
            }
            tag.a("labyModMetadata", (ri)metaTag);
        }
        return tag;
    }
    
    private StorageServerData tagToServerData(final qp tag) {
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
            final qp metaTag = tag.p("labyModMetadata");
            for (final String key : metaTag.e()) {
                final ri metaValue = metaTag.c(key);
                String value;
                if (metaValue instanceof rg) {
                    value = metaValue.f_();
                }
                else {
                    if (!(metaValue instanceof qm)) {
                        continue;
                    }
                    final qm byteArrayTag = (qm)metaValue;
                    value = new String(byteArrayTag.e(), StandardCharsets.UTF_8);
                }
                data.metadata().put(key, value);
            }
        }
        return data;
    }
}
