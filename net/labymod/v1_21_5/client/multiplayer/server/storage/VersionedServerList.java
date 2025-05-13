// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.multiplayer.server.storage;

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
            final ua root = un.a(this.gameDirectory.toPath().resolve("servers.dat"));
            if (root == null) {
                return;
            }
            root.o("servers").ifPresent(list -> {
                for (int index = 0; index < list.size(); ++index) {
                    list.a(index).ifPresent(tag -> this.serverList.add(this.tagToServerData(tag)));
                }
                return;
            });
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        super.load();
    }
    
    @Override
    public void save() {
        try {
            final ug list = new ug();
            for (final StorageServerData data : this.serverList) {
                list.add((Object)this.serverDataToTag(data));
            }
            final ua root = new ua();
            root.a("servers", (va)list);
            final Path tempFile = File.createTempFile("servers", ".dat", this.gameDirectory).toPath();
            un.b(root, tempFile);
            final Path oldFile = new File(this.gameDirectory, "servers.dat_old").toPath();
            final Path newFile = new File(this.gameDirectory, "servers.dat").toPath();
            ag.a(newFile, tempFile, oldFile);
            tempFile.toFile().delete();
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        super.save();
    }
    
    private ua serverDataToTag(final StorageServerData data) {
        final ua tag = new ua();
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
            final ua metaTag = new ua();
            for (final Map.Entry<String, String> entry : data.metadata().entrySet()) {
                if (entry.getValue().length() > 65535) {
                    metaTag.a((String)entry.getKey(), entry.getValue().getBytes(StandardCharsets.UTF_8));
                }
                else {
                    metaTag.a((String)entry.getKey(), (String)entry.getValue());
                }
            }
            tag.a("labyModMetadata", (va)metaTag);
        }
        return tag;
    }
    
    private StorageServerData tagToServerData(final ua tag) {
        final StorageServerData data = StorageServerData.of(tag.b("name", ""), tag.b("ip", ""));
        if (tag.b("icon")) {
            data.setIconBase64(tag.i("icon").orElse(null));
        }
        if (tag.b("acceptTextures")) {
            if (tag.q("acceptTextures").orElse(false)) {
                data.setResourcePackStatus(ServerResourcePackStatus.ENABLED);
            }
            else {
                data.setResourcePackStatus(ServerResourcePackStatus.DISABLED);
            }
        }
        else {
            data.setResourcePackStatus(ServerResourcePackStatus.PROMPT);
        }
        if (!tag.b("labyModMetadata")) {
            return data;
        }
        final ua metaTag = tag.n("labyModMetadata");
        final Iterator iterator = metaTag.e().iterator();
    Label_0228_Outer:
        while (true) {
            if (!iterator.hasNext()) {
                return data;
            }
            final String key = (String)iterator.next();
            final va metaValue = metaTag.a(key);
            Label_0196: {
                if (!(metaValue instanceof uy)) {
                    break Label_0196;
                }
                final uy uy = (uy)metaValue;
                try {
                    String value;
                    final String strValue = value = uy.k();
                    while (true) {
                        data.metadata().put(key, value);
                        continue Label_0228_Outer;
                        iftrue(Label_0133:)(!(metaValue instanceof tx));
                        final tx byteArrayTag = (tx)metaValue;
                        value = new String(byteArrayTag.e(), StandardCharsets.UTF_8);
                        continue;
                    }
                }
                catch (final Throwable cause) {
                    throw new MatchException(cause.toString(), cause);
                }
            }
            break;
        }
    }
}
