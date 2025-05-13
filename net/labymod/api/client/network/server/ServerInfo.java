// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network.server;

import java.util.UUID;
import net.labymod.api.Laby;
import java.security.NoSuchAlgorithmException;
import net.labymod.api.util.HexUtil;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import net.labymod.api.Textures;
import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.texture.TextureRepository;

public class ServerInfo extends ConnectableServerData
{
    public static final String ICON_PREFIX = "data:image/png;base64,";
    private static final ServerPinger SERVER_PINGER;
    private static final TextureRepository TEXTURE_REPOSITORY;
    private final CompletableResourceLocation icon;
    private final String rawIcon;
    private final Component description;
    private final int playerCount;
    private final int maxPlayers;
    private final Player[] onlinePlayers;
    private final int ping;
    private final String protocolInfo;
    private final int protocolVersion;
    private final Status status;
    protected Status tempStatus;
    
    private ServerInfo(final String name, final ServerAddress serverAddress, final CompletableResourceLocation icon, final String rawIcon, final Component description, final int playerCount, final int maxPlayers, final Player[] onlinePlayers, final int ping, final String protocolInfo, final int protocolVersion, final Status status, final ServerType type) {
        super(name, serverAddress, type, null);
        this.icon = icon;
        this.rawIcon = rawIcon;
        this.description = description;
        this.playerCount = playerCount;
        this.maxPlayers = maxPlayers;
        this.onlinePlayers = onlinePlayers;
        this.ping = ping;
        this.protocolInfo = protocolInfo;
        this.protocolVersion = protocolVersion;
        this.status = status;
    }
    
    public static Builder infoBuilder() {
        return new Builder();
    }
    
    public static ServerInfo success(final String name, final ServerAddress address, final CompletableResourceLocation icon, final String rawIcon, final Component description, final int playerCount, final int maxPlayers, final Player[] onlinePlayers, final int ping, final String protocolInfo, final int protocolVersion, final boolean versionValid) {
        return new ServerInfo(name, address, icon, rawIcon, description, playerCount, maxPlayers, onlinePlayers, ping, protocolInfo, protocolVersion, versionValid ? Status.SUCCESS : Status.WRONG_VERSION, ServerType.THIRD_PARTY);
    }
    
    public static ServerInfo loading(final String name, final ServerAddress address) {
        return failed(name, address, Status.LOADING);
    }
    
    public static ServerInfo failed(final String name, final ServerAddress address, final Status status) {
        return new ServerInfo(name, address, new CompletableResourceLocation(ServerInfo.SERVER_PINGER.getDefaultServerIcon()), null, null, -1, -1, null, -1, null, -1, status, ServerType.THIRD_PARTY);
    }
    
    public CompletableResourceLocation getIcon() {
        return this.icon;
    }
    
    public String getRawIcon() {
        return this.rawIcon;
    }
    
    public Component getDescription() {
        return this.description;
    }
    
    public int getPlayerCount() {
        return this.playerCount;
    }
    
    public int getMaxPlayers() {
        return this.maxPlayers;
    }
    
    public Player[] getOnlinePlayers() {
        return this.onlinePlayers;
    }
    
    public int getPing() {
        return this.ping;
    }
    
    public String getProtocolInfo() {
        return this.protocolInfo;
    }
    
    public int getProtocolVersion() {
        return this.protocolVersion;
    }
    
    public Status getStatus() {
        return (this.tempStatus != null) ? this.tempStatus : this.status;
    }
    
    public static CompletableResourceLocation resourceLocationFromBase64(final String base64Icon) {
        if (base64Icon == null || !base64Icon.startsWith("data:image/png;base64,")) {
            return new CompletableResourceLocation(Textures.DEFAULT_SERVER_ICON);
        }
        try {
            final MessageDigest md5 = MessageDigest.getInstance("MD5");
            final byte[] md5Bytes = md5.digest(base64Icon.getBytes(StandardCharsets.UTF_8));
            return ServerInfo.TEXTURE_REPOSITORY.loadCacheResourceAsync("labymod", new String(HexUtil.encodeHex(md5Bytes)), base64Icon, Textures.DEFAULT_SERVER_ICON);
        }
        catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new CompletableResourceLocation(Textures.DEFAULT_SERVER_ICON);
        }
    }
    
    static {
        SERVER_PINGER = Laby.references().serverPinger();
        TEXTURE_REPOSITORY = Laby.references().textureRepository();
    }
    
    public static class Players
    {
        public static final Players EMPTY;
        private final int online;
        private final int max;
        private final Player[] samples;
        
        public Players(final int online, final int max) {
            this(online, max, new Player[0]);
        }
        
        public Players(final int online, final int max, final Player[] samples) {
            this.online = online;
            this.max = max;
            this.samples = samples;
        }
        
        public int getOnline() {
            return this.online;
        }
        
        public int getMax() {
            return this.max;
        }
        
        public Player[] getSamples() {
            return this.samples;
        }
        
        static {
            EMPTY = new Players(0, 0, new Player[0]);
        }
    }
    
    public static class Player
    {
        private final UUID uniqueId;
        private final String name;
        
        public Player(final UUID uniqueId, final String name) {
            this.uniqueId = uniqueId;
            this.name = name;
        }
        
        public UUID getUniqueId() {
            return this.uniqueId;
        }
        
        public String getName() {
            return this.name;
        }
    }
    
    public enum Status
    {
        SUCCESS, 
        LOADING, 
        CANNOT_CONNECT, 
        UNKNOWN_HOST, 
        WRONG_VERSION;
    }
    
    public static class Builder
    {
        private String name;
        private ServerAddress serverAddress;
        private String icon;
        private Component description;
        private int playerCount;
        private int maxPlayers;
        private Player[] onlinePlayers;
        private int ping;
        private String protocolInfo;
        private int protocolVersion;
        private Status status;
        private ServerType type;
        
        protected Builder() {
            this.type = ServerType.THIRD_PARTY;
        }
        
        public Builder name(final String name) {
            this.name = name;
            return this;
        }
        
        public Builder serverAddress(final ServerAddress serverAddress) {
            this.serverAddress = serverAddress;
            return this;
        }
        
        public Builder serverAddress(final String serverAddress) {
            return this.serverAddress(ServerAddress.parse(serverAddress));
        }
        
        public Builder icon(final String icon) {
            this.icon = icon;
            return this;
        }
        
        public Builder lan(final boolean lan) {
            this.type = (lan ? ServerType.LAN : ServerType.THIRD_PARTY);
            return this;
        }
        
        public Builder type(final ServerType type) {
            this.type = type;
            return this;
        }
        
        public Builder description(final Component description) {
            this.description = description;
            return this;
        }
        
        public Builder playerCount(final int playerCount) {
            this.playerCount = playerCount;
            return this;
        }
        
        public Builder maxPlayers(final int maxPlayers) {
            this.maxPlayers = maxPlayers;
            return this;
        }
        
        public Builder onlinePlayers(final Player[] onlinePlayers) {
            this.onlinePlayers = onlinePlayers;
            return this;
        }
        
        public Builder ping(final int ping) {
            this.ping = ping;
            return this;
        }
        
        public Builder protocolInfo(final String protocolInfo) {
            this.protocolInfo = protocolInfo;
            return this;
        }
        
        public Builder protocolVersion(final int protocolVersion) {
            this.protocolVersion = protocolVersion;
            return this;
        }
        
        public Builder status(final Status status) {
            this.status = status;
            return this;
        }
        
        public ServerInfo build() {
            return new ServerInfo(this.name, this.serverAddress, ServerInfo.resourceLocationFromBase64(this.icon), this.icon, this.description, this.playerCount, this.maxPlayers, this.onlinePlayers, this.ping, this.protocolInfo, this.protocolVersion, this.status, this.type);
        }
    }
}
