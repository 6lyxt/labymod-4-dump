// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labyconnect;

import net.labymod.api.util.time.TimeUtil;
import com.google.gson.annotations.SerializedName;
import java.util.UUID;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface TokenStorage
{
    Token getToken(final Purpose p0, final UUID p1);
    
    default boolean hasValidToken(final Purpose purpose, final UUID uniqueId) {
        final Token token = this.getToken(purpose, uniqueId);
        return token != null && token.isValid();
    }
    
    public static class Token
    {
        private final String token;
        @SerializedName("expires_at")
        private final long expiresAt;
        
        public Token(final String token, final long expiresAt) {
            this.token = token;
            this.expiresAt = expiresAt;
        }
        
        public String getToken() {
            return this.token;
        }
        
        public long getExpiresAt() {
            return this.expiresAt;
        }
        
        public boolean isValid() {
            return this.expiresAt > TimeUtil.getCurrentTimeMillis();
        }
        
        public boolean isExpired() {
            return !this.isValid();
        }
    }
    
    public enum Purpose
    {
        CLIENT, 
        JWT;
    }
}
