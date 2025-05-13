// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.metadata.type;

import net.labymod.core.main.user.shop.item.metadata.MetadataException;
import net.labymod.api.util.ArrayUtil;
import net.labymod.core.main.user.shop.item.model.ItemNameTag;
import net.labymod.core.main.user.shop.item.metadata.AbstractMetadata;

public class ItemNameTagMetadata extends AbstractMetadata<ItemNameTag>
{
    public ItemNameTagMetadata(final String... keys) {
        super(keys);
    }
    
    @Override
    public void read(final String value) throws MetadataException {
        final String[] split = value.split(";");
        final String content = ArrayUtil.getOrDefault(split, 0, "");
        final int topBackgroundColor = ArrayUtil.getOrDefault(split, 1, 0, this::parseInt);
        final int bottomBackgroundColor = ArrayUtil.getOrDefault(split, 2, 0, this::parseInt);
        final int borderColor = ArrayUtil.getOrDefault(split, 3, 0, this::parseInt);
        this.value = (T)new ItemNameTag(content, topBackgroundColor, bottomBackgroundColor, borderColor);
    }
    
    @Override
    public Object write() throws MetadataException {
        return ((ItemNameTag)this.value).content() + ";" + ((ItemNameTag)this.value).backgroundTopColor() + ";" + ((ItemNameTag)this.value).backgroundBottomColor() + ";" + ((ItemNameTag)this.value).borderColor();
    }
    
    private int parseInt(final String value) {
        try {
            return Integer.parseInt(value);
        }
        catch (final NumberFormatException exception) {
            return 0;
        }
    }
}
