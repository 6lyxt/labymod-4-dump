// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network.server.storage;

import java.util.Objects;
import java.io.File;
import net.labymod.api.util.GsonUtil;
import java.util.HashMap;
import java.util.UUID;
import net.labymod.api.client.network.server.ServerType;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.network.server.ServerAddress;
import org.jetbrains.annotations.Nullable;
import java.util.Map;
import net.labymod.api.client.network.server.ConnectableServerData;

public class StorageServerData extends ConnectableServerData
{
    public static final String LAST_SCREENSHOT_KEY = "LastScreenshotFile";
    public static final String BOUND_ACCOUNT_KEY = "LabyMod-BoundAccount";
    private String iconBase64;
    private Map<String, String> metadata;
    
    private StorageServerData(@Nullable final String name, @NotNull final ServerAddress serverAddress) {
        super(name, serverAddress, ServerType.THIRD_PARTY, null);
    }
    
    public static StorageServerData of(@Nullable final String name, @NotNull final ServerAddress serverAddress) {
        return new StorageServerData(name, serverAddress);
    }
    
    public static StorageServerData of(@Nullable final String name, @NotNull final String serverAddress) {
        return new StorageServerData(name, ServerAddress.parse(serverAddress));
    }
    
    public String getIconBase64() {
        return this.iconBase64;
    }
    
    public void setIconBase64(final String iconBase64) {
        this.iconBase64 = iconBase64;
    }
    
    public void setResourcePackStatus(final ServerResourcePackStatus resourcePackStatus) {
        this.resourcePackStatus = resourcePackStatus;
    }
    
    public void setServerAddress(final ServerAddress serverAddress) {
        this.serverAddress = serverAddress;
        this.actualAddress = null;
    }
    
    public void setServerAddress(final String serverAddress) {
        this.setServerAddress(ServerAddress.parse(serverAddress));
    }
    
    @Nullable
    public UUID getBoundUniqueId() {
        if (!this.hasMetadata()) {
            return null;
        }
        final String value = this.metadata().get("LabyMod-BoundAccount");
        if (value == null) {
            return null;
        }
        try {
            return UUID.fromString(value);
        }
        catch (final IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public Map<String, String> metadata() {
        if (this.metadata == null) {
            this.metadata = new HashMap<String, String>();
        }
        return this.metadata;
    }
    
    public boolean hasMetadata() {
        return this.metadata != null && !this.metadata.isEmpty();
    }
    
    public <T> T getJsonMeta(final String key, final Class<T> type) {
        return (T)GsonUtil.DEFAULT_GSON.fromJson((String)this.metadata().get(key), (Class)type);
    }
    
    public <T> void setJsonMeta(final String key, final T value) {
        this.metadata().put(key, GsonUtil.DEFAULT_GSON.toJson((Object)value));
    }
    
    @Nullable
    public File getLastScreenshotFile() {
        if (this.metadata == null) {
            return null;
        }
        final String path = this.metadata().get("LastScreenshotFile");
        if (path != null) {
            final File file = new File(path);
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StorageServerData)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final StorageServerData that = (StorageServerData)o;
        return Objects.equals(this.iconBase64, that.iconBase64);
    }
    
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + ((this.iconBase64 != null) ? this.iconBase64.hashCode() : 0);
        return result;
    }
}
