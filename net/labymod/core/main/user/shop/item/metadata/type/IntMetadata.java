// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.metadata.type;

import net.labymod.core.main.user.shop.item.metadata.MetadataException;
import net.labymod.core.main.user.shop.item.metadata.AbstractMetadata;

public class IntMetadata extends AbstractMetadata<Integer>
{
    public IntMetadata(final String... keys) {
        super(keys);
    }
    
    @Override
    public void read(final String value) throws MetadataException {
        try {
            this.value = (T)Integer.valueOf(Integer.parseInt(value));
        }
        catch (final NumberFormatException exception) {
            this.value = (T)Integer.valueOf(0);
        }
    }
    
    @Override
    public Object write() throws MetadataException {
        return null;
    }
}
