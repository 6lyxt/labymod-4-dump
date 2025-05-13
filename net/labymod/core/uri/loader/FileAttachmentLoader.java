// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.uri.loader;

import net.labymod.api.labyconnect.protocol.model.chat.attachment.URIAttachment;
import net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.file.ImageAttachment;
import java.util.UUID;
import java.net.URI;
import net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.file.FileAttachment;
import net.labymod.api.uri.loader.AttachmentLoader;

public class FileAttachmentLoader implements AttachmentLoader<FileAttachment>
{
    @Override
    public FileAttachment create(final URI uri) {
        try {
            final String query = uri.getQuery();
            if (query == null) {
                return null;
            }
            final String authority = uri.getAuthority();
            if (authority == null) {
                return null;
            }
            final String type = query.replace("type=", "");
            final UUID identifier = UUID.fromString(authority);
            if (type.equals("png") || type.equals("jpg")) {
                return new ImageAttachment(uri, type, identifier);
            }
            return new FileAttachment(uri, type, identifier);
        }
        catch (final Throwable exception) {
            return null;
        }
    }
}
