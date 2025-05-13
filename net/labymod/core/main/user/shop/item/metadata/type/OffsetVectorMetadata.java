// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.metadata.type;

import net.labymod.core.main.user.shop.item.metadata.MetadataException;
import net.labymod.core.main.user.shop.item.model.OffsetVector;
import net.labymod.core.main.user.shop.item.metadata.AbstractMetadata;

public class OffsetVectorMetadata extends AbstractMetadata<OffsetVector>
{
    public OffsetVectorMetadata(final String... keys) {
        super(keys);
    }
    
    @Override
    public void read(final String value) throws MetadataException {
        final String[] split = value.split(";");
        if (split.length != 3) {
            return;
        }
        this.value = (T)new OffsetVector(split[0], split[1], split[2]);
    }
    
    @Override
    public Object write() throws MetadataException {
        return ((OffsetVector)this.value).getX() + ";" + ((OffsetVector)this.value).getY() + ";" + ((OffsetVector)this.value).getZ();
    }
}
