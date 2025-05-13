// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.metadata.serialization;

import java.util.List;
import net.labymod.core.main.user.shop.item.metadata.MetadataException;
import net.labymod.core.main.user.shop.item.ItemDetails;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;

public interface ItemMetadataSerializer
{
    void serialize(final ItemMetadata p0, final ItemDetails p1, final String[] p2) throws MetadataException;
    
    List<Object> deserialize(final ItemMetadata p0) throws MetadataException;
}
