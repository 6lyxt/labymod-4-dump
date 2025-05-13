// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.multiplayer.server.storage;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import java.nio.file.Path;
import java.util.Iterator;
import java.io.File;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.core.client.network.server.storage.DefaultServerList;

public class VersionedServerList extends DefaultServerList
{
    @Override
    public void load() {
        try {
            this.serverList.clear();
            final sn root = ta.a(this.gameDirectory.toPath().resolve("servers.dat"));
            if (root == null) {
                return;
            }
            final st list = root.c("servers", 10);
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
            final st list = new st();
            for (final StorageServerData data : this.serverList) {
                list.add((Object)this.serverDataToTag(data));
            }
            final sn root = new sn();
            root.a("servers", (tk)list);
            final Path tempFile = File.createTempFile("servers", ".dat", this.gameDirectory).toPath();
            ta.b(root, tempFile);
            final Path oldFile = new File(this.gameDirectory, "servers.dat_old").toPath();
            final Path newFile = new File(this.gameDirectory, "servers.dat").toPath();
            ac.a(newFile, tempFile, oldFile);
            tempFile.toFile().delete();
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        super.save();
    }
    
    private sn serverDataToTag(final StorageServerData data) {
        final sn tag = new sn();
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
            final sn metaTag = new sn();
            for (final Map.Entry<String, String> entry : data.metadata().entrySet()) {
                if (entry.getValue().length() > 65535) {
                    metaTag.a((String)entry.getKey(), entry.getValue().getBytes(StandardCharsets.UTF_8));
                }
                else {
                    metaTag.a((String)entry.getKey(), (String)entry.getValue());
                }
            }
            tag.a("labyModMetadata", (tk)metaTag);
        }
        return tag;
    }
    
    private StorageServerData tagToServerData(final sn tag) {
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
            final sn metaTag = tag.p("labyModMetadata");
            for (final String key : metaTag.e()) {
                final tk metaValue = metaTag.c(key);
                String value;
                if (metaValue instanceof ti) {
                    value = metaValue.t_();
                }
                else {
                    if (!(metaValue instanceof sk)) {
                        continue;
                    }
                    final sk byteArrayTag = (sk)metaValue;
                    value = new String(byteArrayTag.e(), StandardCharsets.UTF_8);
                }
                data.metadata().put(key, value);
            }
        }
        return data;
    }
}
