// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.metadata.serialization;

import net.labymod.core.main.user.shop.item.metadata.type.ColorArrayMetadata;
import java.util.ArrayList;
import java.util.List;
import net.labymod.core.main.user.shop.item.metadata.MetadataException;
import java.util.Iterator;
import net.labymod.core.main.user.shop.item.metadata.AbstractMetadata;
import net.labymod.core.main.user.shop.item.ItemDetails;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;

public final class DefaultItemMetadataSerializer implements ItemMetadataSerializer
{
    @Override
    public void serialize(final ItemMetadata metadata, final ItemDetails itemDetails, final String[] userOptions) throws MetadataException {
        final String[] itemOptions = itemDetails.getOptions();
        final String[] defaultOptions = itemDetails.getDefaultData();
        for (int min = Math.min(itemOptions.length, userOptions.length), i = 0; i < min; ++i) {
            final String key = itemOptions[i];
            final String value = (userOptions[i] != null || defaultOptions == null || defaultOptions.length != userOptions.length) ? userOptions[i] : defaultOptions[i];
            for (final AbstractMetadata<?> data : metadata.dataList()) {
                if (!data.hasKey(key)) {
                    continue;
                }
                data.read(itemDetails, key, value);
                break;
            }
        }
    }
    
    @Override
    public List<Object> deserialize(final ItemMetadata metadata) throws MetadataException {
        final List<Object> out = new ArrayList<Object>();
        int colorIndex = 0;
        for (final String key : metadata.getDetails().getOptions()) {
            for (final AbstractMetadata<?> data : metadata.dataList()) {
                if (!data.hasKey(key)) {
                    continue;
                }
                if (data instanceof final ColorArrayMetadata colorArrayMetadata) {
                    out.add(colorArrayMetadata.write(colorIndex));
                    ++colorIndex;
                }
                else {
                    out.add(data.write());
                }
            }
        }
        return out;
    }
}
