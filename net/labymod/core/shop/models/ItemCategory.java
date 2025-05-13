// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.shop.models;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.Textures;
import java.util.Locale;
import net.labymod.api.client.gui.icon.Icon;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;

public class ItemCategory
{
    private final ItemType type;
    private final ItemType subType;
    private final String identifier;
    private final IntOpenHashSet items;
    private String localizedIdentifier;
    private Icon icon;
    
    public ItemCategory(final ItemType type, final String identifier) {
        this(type, null, identifier);
    }
    
    public ItemCategory(final ItemType type, final ItemType subType, final String identifier) {
        this.items = new IntOpenHashSet();
        this.type = type;
        this.subType = subType;
        this.identifier = ((identifier == null) ? "null" : identifier.toLowerCase(Locale.ROOT));
        final String identifier2 = this.identifier;
        switch (identifier2) {
            case "featured": {
                this.icon = Textures.SpriteShop.FEATURED;
                break;
            }
            case "partner": {
                this.icon = Textures.SpriteShop.PARTNER;
                break;
            }
            case "aura": {
                this.icon = Textures.SpriteShop.AURAS;
                break;
            }
            case "body": {
                this.icon = Textures.SpriteShop.BODY;
                break;
            }
            case "head": {
                this.icon = Textures.SpriteShop.HEAD;
                break;
            }
            case "pets": {
                this.icon = Textures.SpriteShop.PETS;
                break;
            }
            case "shoes": {
                this.icon = Textures.SpriteShop.SHOES;
                break;
            }
            case "underglow": {
                this.icon = Textures.SpriteShop.UNDERGLOWS;
                break;
            }
            case "wing": {
                this.icon = Textures.SpriteShop.WINGS;
                break;
            }
            case "epic": {
                this.icon = Textures.SpriteShop.EPIC;
                break;
            }
            case "legendary": {
                this.icon = Textures.SpriteShop.LEGENDARY;
                break;
            }
        }
        if (identifier == null && type == ItemType.EMOTE) {
            this.icon = Textures.SpriteShop.DAILY;
        }
    }
    
    public ItemType getType() {
        return this.type;
    }
    
    public ItemType getSubType() {
        return this.subType;
    }
    
    public String getIdentifier() {
        return this.identifier;
    }
    
    public IntOpenHashSet getItems() {
        return this.items;
    }
    
    @Nullable
    public Icon getIcon() {
        return this.icon;
    }
    
    public void addItem(final int itemId) {
        this.items.add(itemId);
    }
    
    public String getLocalizedIdentifier() {
        return this.localizedIdentifier;
    }
    
    public void setLocalizedIdentifier(final String localizedIdentifier) {
        this.localizedIdentifier = localizedIdentifier;
    }
}
