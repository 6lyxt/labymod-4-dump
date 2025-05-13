// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.metadata.type;

import net.labymod.core.main.user.shop.item.metadata.MetadataException;
import java.util.Locale;
import java.util.Arrays;
import net.labymod.api.util.Color;
import net.labymod.core.main.user.shop.item.metadata.AbstractMetadata;

public class ColorArrayMetadata extends AbstractMetadata<Color[]>
{
    public ColorArrayMetadata(final String... keys) {
        super(keys);
    }
    
    @Override
    public void read(final String value) throws MetadataException {
        try {
            final int length = ((Color[])(Object)this.value).length;
            this.value = (T)(Object)Arrays.copyOf((Color[])(Object)this.value, length + 1);
            ((Color[])(Object)this.value)[length] = Color.of("#" + value);
        }
        catch (final Exception exception) {
            throw new MetadataException(String.format(Locale.ROOT, "The value \"%s\" could not be converted to a color array", value), exception);
        }
    }
    
    @Override
    public Object write() throws MetadataException {
        throw new MetadataException("Color array metadata requires an index to write");
    }
    
    public Object write(final int index) throws MetadataException {
        try {
            final Color color = ((Color[])(Object)this.value)[index];
            return String.format(Locale.ROOT, "%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        }
        catch (final Exception exception) {
            throw new MetadataException(String.format(Locale.ROOT, "The array at position %s with length %s could not be converted to hex color", index, ((Color[])(Object)this.value).length), exception);
        }
    }
}
