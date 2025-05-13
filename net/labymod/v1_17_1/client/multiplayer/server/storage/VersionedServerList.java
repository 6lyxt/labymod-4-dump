// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.client.multiplayer.server.storage;

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
            final na root = nk.b(new File(this.gameDirectory, "servers.dat"));
            if (root == null) {
                return;
            }
            final ng list = root.c("servers", 10);
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
            final ng list = new ng();
            for (final StorageServerData data : this.serverList) {
                list.add((Object)this.serverDataToTag(data));
            }
            final na root = new na();
            root.a("servers", (ns)list);
            final File tempFile = File.createTempFile("servers", ".dat", this.gameDirectory);
            nk.b(root, tempFile);
            final File oldFile = new File(this.gameDirectory, "servers.dat_old");
            final File newFile = new File(this.gameDirectory, "servers.dat");
            ad.a(newFile, tempFile, oldFile);
            tempFile.delete();
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        super.save();
    }
    
    private na serverDataToTag(final StorageServerData data) {
        final na tag = new na();
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
            final na metaTag = new na();
            for (final Map.Entry<String, String> entry : data.metadata().entrySet()) {
                if (entry.getValue().length() > 65535) {
                    metaTag.a((String)entry.getKey(), entry.getValue().getBytes(StandardCharsets.UTF_8));
                }
                else {
                    metaTag.a((String)entry.getKey(), (String)entry.getValue());
                }
            }
            tag.a("labyModMetadata", (ns)metaTag);
        }
        return tag;
    }
    
    private StorageServerData tagToServerData(final na tag) {
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
            final na metaTag = tag.p("labyModMetadata");
            for (final String key : metaTag.d()) {
                final ns metaValue = metaTag.c(key);
                String value;
                if (metaValue instanceof nq) {
                    value = metaValue.d_();
                }
                else {
                    if (!(metaValue instanceof mx)) {
                        continue;
                    }
                    final mx byteArrayTag = (mx)metaValue;
                    value = new String(byteArrayTag.d(), StandardCharsets.UTF_8);
                }
                data.metadata().put(key, value);
            }
        }
        return data;
    }
}
