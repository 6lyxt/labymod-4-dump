// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.object.lootbox.content;

import java.util.Objects;
import net.labymod.api.labynet.models.textures.Skin;
import net.labymod.api.client.gui.icon.Icon;

public final class LootBoxShopItem
{
    private final short id;
    private final String name;
    private final PoolCategory category;
    private final Integer color;
    private final Icon icon;
    
    public LootBoxShopItem(final short id, final String name, final String imageUrl, final Integer color, final PoolCategory category) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.category = category;
        if (imageUrl == null) {
            this.icon = Icon.texture(Skin.LOADING).resolution(135, 257);
        }
        else {
            this.icon = Icon.url(imageUrl);
        }
    }
    
    public Icon getIcon() {
        return this.icon;
    }
    
    public short id() {
        return this.id;
    }
    
    public String name() {
        return this.name;
    }
    
    public Integer getColor() {
        return this.color;
    }
    
    public PoolCategory category() {
        return this.category;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        final LootBoxShopItem that = (LootBoxShopItem)obj;
        return this.id == that.id && Objects.equals(this.name, that.name) && Objects.equals(this.category, that.category);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.category);
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
