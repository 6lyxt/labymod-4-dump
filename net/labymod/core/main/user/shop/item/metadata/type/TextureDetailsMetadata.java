// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.metadata.type;

import net.labymod.core.main.user.shop.item.metadata.MetadataException;
import java.util.Locale;
import java.util.UUID;
import net.labymod.core.main.user.shop.item.model.TextureDetails;
import net.labymod.core.main.user.shop.item.metadata.AbstractMetadata;

public class TextureDetailsMetadata extends AbstractMetadata<TextureDetails>
{
    public TextureDetailsMetadata(final String... keys) {
        super(keys);
    }
    
    @Override
    public void read(String value) throws MetadataException {
        final String unmodifiedValue = value;
        try {
            final boolean isTextureDetails = this.is("texture_details");
            if (!isTextureDetails && value.contains(";")) {
                throw new IllegalStateException("Value contains ';': " + value);
            }
            final boolean multipleArguments = value.contains(";");
            TextureDetails.Type type = TextureDetails.Type.LEGACY;
            if (multipleArguments) {
                final String[] entries = value.split(";");
                type = TextureDetails.Type.byName(entries[0]);
                value = entries[1];
            }
            this.value = (T)TextureDetails.of(type, UUID.fromString(value));
        }
        catch (final Throwable throwable) {
            throw new MetadataException(String.format(Locale.ROOT, "Failed to parse the value \"%s\"", unmodifiedValue), throwable);
        }
    }
    
    @Override
    public Object write() throws MetadataException {
        return String.valueOf(((TextureDetails)this.value).getType()) + ";" + String.valueOf(((TextureDetails)this.value).getUuid());
    }
}
