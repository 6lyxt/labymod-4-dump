// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.model;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public final class TextureDetails
{
    private final Type type;
    private final UUID uuid;
    
    private TextureDetails(final Type type, final UUID uuid) {
        this.type = type;
        this.uuid = uuid;
    }
    
    @NotNull
    public static TextureDetails of(final Type type, final UUID uuid) {
        return new TextureDetails(type, uuid);
    }
    
    public Type getType() {
        return this.type;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final TextureDetails textureDetails = (TextureDetails)o;
        return this.type == textureDetails.type && Objects.equals(this.uuid, textureDetails.uuid);
    }
    
    @Override
    public int hashCode() {
        int result = Objects.hashCode(this.type);
        result = 31 * result + Objects.hashCode(this.uuid);
        return result;
    }
    
    public enum Type
    {
        LEGACY, 
        TEXTURE, 
        SKIN;
        
        private static final Type[] VALUES;
        
        @NotNull
        public static Type byName(final String name) {
            for (final Type value : Type.VALUES) {
                if (name.equalsIgnoreCase(value.name())) {
                    return value;
                }
            }
            throw new UnsupportedOperationException(name + " is not a valid texture type");
        }
        
        static {
            VALUES = values();
        }
    }
}
