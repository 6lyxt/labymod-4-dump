// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.metadata.type;

import net.labymod.core.main.user.shop.item.metadata.MetadataException;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.core.main.user.shop.item.metadata.AbstractMetadata;

public class SizeMetadata extends AbstractMetadata<FloatVector4>
{
    public SizeMetadata(final String... keys) {
        super(keys);
    }
    
    @Override
    public void read(final String value) throws MetadataException {
        final String[] split = value.split(";");
        if (split.length == 0) {
            return;
        }
        this.value = (T)new FloatVector4(this.getValue(split, 0), this.getValue(split, 1), this.getValue(split, 2), this.getValue(split, 3));
    }
    
    @Override
    public Object write() throws MetadataException {
        return null;
    }
    
    private float getValue(final String[] options, final int index) {
        return this.getValue(options, index, 0.0f);
    }
    
    private float getValue(final String[] options, final int index, final float defaultValue) {
        if (options.length >= index + 1) {
            try {
                return Float.parseFloat(options[index]);
            }
            catch (final NumberFormatException exception) {
                return defaultValue;
            }
        }
        return defaultValue;
    }
    
    private float parseFloat(final String value) {
        try {
            return Float.parseFloat(value);
        }
        catch (final NumberFormatException exception) {
            return 0.0f;
        }
    }
}
