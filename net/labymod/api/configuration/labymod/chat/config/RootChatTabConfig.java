// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.chat.config;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.property.PropertyConvention;
import net.labymod.api.property.NotNullPropertyConvention;
import net.labymod.api.configuration.labymod.chat.category.GeneralChatTabConfig;
import java.util.UUID;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.Config;

public class RootChatTabConfig extends Config
{
    private final ConfigProperty<UUID> uniqueId;
    private final GeneralChatTabConfig config;
    private final ConfigProperty<Type> type;
    private final ConfigProperty<Integer> index;
    
    public RootChatTabConfig() {
        this.uniqueId = ConfigProperty.create(UUID.randomUUID());
        this.type = new ConfigProperty<Type>(Type.SERVER, new NotNullPropertyConvention<Type>(Type.SERVER));
        this.index = ConfigProperty.create(-1);
        this.config = new GeneralChatTabConfig("");
    }
    
    public RootChatTabConfig(final int index, final Type type, final GeneralChatTabConfig config) {
        this.uniqueId = ConfigProperty.create(UUID.randomUUID());
        this.type = new ConfigProperty<Type>(Type.SERVER, new NotNullPropertyConvention<Type>(Type.SERVER));
        this.index = ConfigProperty.create(-1);
        this.config = config;
        this.type.set(type);
        this.index.set(index);
    }
    
    public RootChatTabConfig(final Type type, final String name) {
        this(0, type, new GeneralChatTabConfig(name));
    }
    
    public ConfigProperty<Integer> index() {
        return this.index;
    }
    
    public ConfigProperty<Type> type() {
        return this.type;
    }
    
    public GeneralChatTabConfig config() {
        return this.config;
    }
    
    public UUID getUniqueID() {
        return this.uniqueId.get();
    }
    
    @Override
    public int getConfigVersion() {
        return 2;
    }
    
    public static class Type
    {
        public static final Type SERVER;
        public static final Type CUSTOM;
        private final String identifier;
        
        private Type(@NotNull final String identifier) {
            Objects.requireNonNull(identifier, "Identifier cannot be null!");
            this.identifier = identifier;
        }
        
        public static Type of(@NotNull final String identifier) {
            return new Type(identifier);
        }
        
        @NotNull
        public String getIdentifier() {
            return this.identifier;
        }
        
        @Override
        public String toString() {
            return "Type{identifier='" + this.identifier + "'}";
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof final Type type) {
                return this.identifier.equals(type.identifier);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return this.identifier.hashCode();
        }
        
        static {
            SERVER = of("SERVER");
            CUSTOM = of("CUSTOM");
        }
    }
}
