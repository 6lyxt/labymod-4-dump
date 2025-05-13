// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.session;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.UUIDHelper;
import java.util.UUID;
import net.labymod.api.client.session.Session;

public class DefaultSession implements Session
{
    private final String username;
    private final UUID uniqueId;
    private final boolean hasUniqueId;
    private final String accessToken;
    private final Type type;
    private final boolean premium;
    
    public DefaultSession(final String username, final UUID uniqueId, final String accessToken, final Type type) {
        this.username = username;
        this.uniqueId = ((uniqueId == null) ? UUIDHelper.createUniqueId(username) : uniqueId);
        this.hasUniqueId = (uniqueId != null);
        this.accessToken = accessToken;
        this.type = type;
        this.premium = SessionUtil.isPremium(this.accessToken);
    }
    
    @NotNull
    @Override
    public String getUsername() {
        return this.username;
    }
    
    @Override
    public UUID getUniqueId() {
        return this.uniqueId;
    }
    
    @Override
    public boolean hasUniqueId() {
        return this.hasUniqueId;
    }
    
    @Override
    public String getAccessToken() {
        return this.accessToken;
    }
    
    @Override
    public Type getType() {
        return this.type;
    }
    
    @Override
    public boolean isPremium() {
        return this.premium;
    }
}
