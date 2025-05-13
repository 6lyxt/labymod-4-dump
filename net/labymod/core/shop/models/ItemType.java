// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.shop.models;

import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;
import java.util.HashSet;
import java.util.Objects;
import java.util.Iterator;
import org.jetbrains.annotations.Nullable;
import java.util.Locale;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import java.util.Set;

public class ItemType
{
    public static final Set<ItemType> VALUES;
    public static final ItemType SEASON;
    public static final ItemType FEATURED;
    public static final ItemType COSMETIC;
    public static final ItemType EMOTE;
    public static final ItemType BUNDLE;
    public static final ItemType PLUS;
    public static final ItemType FLATRATE;
    private final String type;
    private boolean visible;
    private Icon icon;
    
    private ItemType(final String type, final boolean visible) {
        this.visible = true;
        this.type = type;
        this.visible = visible;
        switch (type) {
            case "FEATURED": {
                this.icon = Textures.SpriteShop.FEATURED;
                break;
            }
        }
    }
    
    public String getNiceName() {
        return this.type.charAt(0) + this.type.substring(1).toLowerCase(Locale.ROOT);
    }
    
    public boolean isVisible() {
        return this.visible;
    }
    
    @Nullable
    public Icon getIcon() {
        return this.icon;
    }
    
    public static ItemType of(final String type) {
        for (final ItemType value : ItemType.VALUES) {
            if (value.type.equalsIgnoreCase(type)) {
                return value;
            }
        }
        return new ItemType(type, false);
    }
    
    static ItemType register(final String type, final boolean visible) {
        final ItemType value = new ItemType(type, visible);
        ItemType.VALUES.add(value);
        return value;
    }
    
    public String getType() {
        return this.type;
    }
    
    @Override
    public String toString() {
        return this.type.toLowerCase(Locale.ROOT);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof final ItemType type1) {
            return Objects.equals(this.type, type1.type);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.type.hashCode();
    }
    
    static {
        VALUES = new HashSet<ItemType>();
        SEASON = register("SEASON", true);
        FEATURED = register("FEATURED", true);
        COSMETIC = register("COSMETIC", true);
        EMOTE = register("EMOTE", true);
        BUNDLE = register("BUNDLE", false);
        PLUS = register("PLUS", false);
        FLATRATE = register("FLATRATE", false);
    }
    
    public static class ItemTypeTypeAdapter implements JsonDeserializer<ItemType>, JsonSerializer<ItemType>
    {
        public ItemType deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
            if (json.isJsonNull()) {
                return null;
            }
            return ItemType.of(json.getAsString());
        }
        
        public JsonElement serialize(final ItemType src, final Type typeOfSrc, final JsonSerializationContext context) {
            return (JsonElement)new JsonPrimitive(src.type);
        }
    }
}
