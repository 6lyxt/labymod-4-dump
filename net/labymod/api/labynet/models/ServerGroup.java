// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labynet.models;

import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.texture.TextureRepository;
import java.util.Optional;
import net.labymod.api.util.io.web.result.ResultCallback;
import java.util.Iterator;
import net.labymod.api.models.version.Version;
import net.labymod.api.Laby;
import org.spongepowered.include.com.google.common.collect.Lists;
import net.labymod.api.util.StringUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import com.google.gson.annotations.SerializedName;

public class ServerGroup
{
    private final ServerChat chat;
    @SerializedName("server_name")
    private String serverName;
    @SerializedName("nice_name")
    private String niceName;
    @SerializedName("direct_ip")
    private String directIp;
    @SerializedName("wildcards")
    @Nullable
    private String[] wildcardIps;
    private List<Attachment> attachments;
    private Map<String, String> social;
    @SerializedName("gamemodes")
    private Map<String, GameMode> gameModes;
    @SerializedName("command_delay")
    private int commandDelay;
    @SerializedName("user_stats")
    private String userStatsUrl;
    private transient List<GameMode> visibleGameModes;
    
    public ServerGroup() {
        this.chat = new ServerChat();
        this.gameModes = new HashMap<String, GameMode>();
        this.visibleGameModes = null;
    }
    
    public static boolean addressMatches(String address, final String directIp, final String[] wildcardIps) {
        if (directIp.equalsIgnoreCase(address)) {
            return true;
        }
        final String[] ipSplit = address.split("\\.");
        final boolean subdomain = ipSplit.length > 2;
        if (subdomain && StringUtil.endsWithIgnoreCase(address, "." + directIp)) {
            return true;
        }
        if (wildcardIps == null) {
            return false;
        }
        if (address.contains(":")) {
            final String[] split = address.split(":");
            if (split.length == 0) {
                return false;
            }
            address = split[0];
        }
        for (int length = wildcardIps.length, i = 0; i < length; ++i) {
            final String wildcard = wildcardIps[i];
            final String[] wildcardSplit = wildcard.split("%.");
            final String wildcardIp = (wildcardSplit.length == 1) ? wildcardSplit[0] : wildcardSplit[1];
            if (subdomain) {
                if (StringUtil.endsWithIgnoreCase(address, "." + wildcardIp)) {
                    return true;
                }
            }
            else if (wildcardIp.equalsIgnoreCase(address)) {
                return true;
            }
        }
        return false;
    }
    
    public String getServerName() {
        return this.serverName;
    }
    
    public String getNiceName() {
        return this.niceName;
    }
    
    public String getDirectIp() {
        return this.directIp;
    }
    
    public String[] getWildcards() {
        return this.wildcardIps;
    }
    
    public List<Attachment> getAttachments() {
        return this.attachments;
    }
    
    public Map<String, String> getSocial() {
        return this.social;
    }
    
    public List<GameMode> getGameModes() {
        if (this.visibleGameModes == null) {
            this.visibleGameModes = Lists.newArrayList();
            final Version version = Laby.labyAPI().labyModLoader().version();
            for (final GameMode gameMode : this.gameModes.values()) {
                if (gameMode.getVersions() != null && !gameMode.getVersions().isCompatible(version)) {
                    continue;
                }
                this.visibleGameModes.add(gameMode);
            }
        }
        return this.visibleGameModes;
    }
    
    public Map<String, GameMode> getAllGameModes() {
        return this.gameModes;
    }
    
    public ServerChat getChat() {
        return this.chat;
    }
    
    public int getCommandDelay() {
        return this.commandDelay;
    }
    
    public String getUserStatsUrl() {
        return this.userStatsUrl;
    }
    
    public void manifest(final ResultCallback<ServerManifest> callback) {
        Laby.labyAPI().labyNetController().getOrLoadManifest(this, callback);
    }
    
    public Optional<Attachment> getAttachment(String name) {
        if (!name.endsWith(".json") && !name.endsWith(".png")) {
            name += ".png";
        }
        for (final Attachment attachment : this.attachments) {
            if (attachment.getFileName().equals(name)) {
                return Optional.of(attachment);
            }
        }
        return Optional.empty();
    }
    
    public boolean hasIp(final String ip) {
        return addressMatches(ip, this.directIp, this.wildcardIps);
    }
    
    public static class Attachment
    {
        private static final TextureRepository TEXTURE_REPOSITORY;
        @SerializedName("file_name")
        private String fileName;
        private String url;
        private String hash;
        @Exclude
        private CompletableResourceLocation resourceLocation;
        
        public String getFileName() {
            return this.fileName;
        }
        
        public String getUrl() {
            return this.url;
        }
        
        public String getHash() {
            return this.hash;
        }
        
        public Icon getIcon() {
            if (this.hash == null || this.url == null) {
                return null;
            }
            return Icon.completable(this.completableResourceLocation());
        }
        
        public CompletableResourceLocation completableResourceLocation() {
            if (this.hash == null || this.url == null) {
                return null;
            }
            if (this.resourceLocation == null) {
                this.resourceLocation = Attachment.TEXTURE_REPOSITORY.loadCacheResourceAsync("labymod", this.hash, this.url, Textures.EMPTY);
            }
            return this.resourceLocation;
        }
        
        static {
            TEXTURE_REPOSITORY = Laby.references().textureRepository();
        }
    }
}
