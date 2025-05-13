// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.session;

import java.util.Locale;
import org.jetbrains.annotations.Nullable;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public interface Session
{
    @NotNull
    String getUsername();
    
    UUID getUniqueId();
    
    boolean hasUniqueId();
    
    @Nullable
    String getAccessToken();
    
    Type getType();
    
    boolean isPremium();
    
    public enum Type
    {
        MOJANG, 
        MICROSOFT, 
        LEGACY;
        
        public static Type fromName(String name) {
            name = name.toUpperCase(Locale.ENGLISH);
            for (final Type value : values()) {
                if (value.name().equals(name)) {
                    return value;
                }
            }
            return Type.LEGACY;
        }
        
        @NotNull
        public String lowercase() {
            return this.name().toLowerCase(Locale.ENGLISH);
        }
    }
    
    public interface Factory
    {
        Session create(final String p0, final UUID p1, final String p2, final Type p3);
    }
}
