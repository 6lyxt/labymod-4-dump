// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.metadata.type;

import net.labymod.core.main.user.shop.item.metadata.MetadataException;
import net.labymod.core.main.user.shop.item.metadata.AbstractMetadata;

public class BooleanMetadata extends AbstractMetadata<Boolean>
{
    public BooleanMetadata(final String... keys) {
        super(keys);
    }
    
    @Override
    public void read(final String value) throws MetadataException {
        if (value.equals("1")) {
            this.value = (T)Boolean.valueOf(true);
            return;
        }
        this.value = (T)Boolean.valueOf(Boolean.parseBoolean(value));
    }
    
    @Override
    public Object write() throws MetadataException {
        return ((boolean)this.value) ? 1 : 0;
    }
}
