// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.multiplayer.server.storage;

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
            final qr root = rb.b(new File(this.gameDirectory, "servers.dat"));
            if (root == null) {
                return;
            }
            final qx list = root.c("servers", 10);
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
            final qx list = new qx();
            for (final StorageServerData data : this.serverList) {
                list.add((Object)this.serverDataToTag(data));
            }
            final qr root = new qr();
            root.a("servers", (rk)list);
            final File tempFile = File.createTempFile("servers", ".dat", this.gameDirectory);
            rb.b(root, tempFile);
            final File oldFile = new File(this.gameDirectory, "servers.dat_old");
            final File newFile = new File(this.gameDirectory, "servers.dat");
            ac.a(newFile, tempFile, oldFile);
            tempFile.delete();
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        super.save();
    }
    
    private qr serverDataToTag(final StorageServerData data) {
        final qr tag = new qr();
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
            final qr metaTag = new qr();
            for (final Map.Entry<String, String> entry : data.metadata().entrySet()) {
                if (entry.getValue().length() > 65535) {
                    metaTag.a((String)entry.getKey(), entry.getValue().getBytes(StandardCharsets.UTF_8));
                }
                else {
                    metaTag.a((String)entry.getKey(), (String)entry.getValue());
                }
            }
            tag.a("labyModMetadata", (rk)metaTag);
        }
        return tag;
    }
    
    private StorageServerData tagToServerData(final qr tag) {
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
            final qr metaTag = tag.p("labyModMetadata");
            for (final String key : metaTag.e()) {
                final rk metaValue = metaTag.c(key);
                String value;
                if (metaValue instanceof ri) {
                    value = metaValue.m_();
                }
                else {
                    if (!(metaValue instanceof qo)) {
                        continue;
                    }
                    final qo byteArrayTag = (qo)metaValue;
                    value = new String(byteArrayTag.e(), StandardCharsets.UTF_8);
                }
                data.metadata().put(key, value);
            }
        }
        return data;
    }
}
