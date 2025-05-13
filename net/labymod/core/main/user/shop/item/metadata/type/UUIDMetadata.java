// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.metadata.type;

import net.labymod.core.main.user.shop.item.metadata.MetadataException;
import java.util.Locale;
import java.util.UUID;
import net.labymod.core.main.user.shop.item.metadata.AbstractMetadata;

public class UUIDMetadata extends AbstractMetadata<UUID>
{
    public UUIDMetadata(final String... keys) {
        super(keys);
    }
    
    @Override
    public void read(final String value) throws MetadataException {
        try {
            this.value = (T)UUID.fromString(value);
        }
        catch (final Exception exception) {
            throw new MetadataException(String.format(Locale.ROOT, "The value \"%s\" could not be converted to a UUID", value), exception);
        }
    }
    
    @Override
    public Object write() throws MetadataException {
        return ((UUID)this.value).toString();
    }
}
