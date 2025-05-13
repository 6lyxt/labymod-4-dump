// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.uri;

import net.labymod.api.uri.loader.AttachmentLoader;
import java.net.URI;
import net.labymod.api.labyconnect.protocol.model.chat.attachment.URIAttachment;
import java.util.List;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface AttachmentParser
{
    List<URIAttachment> parse(final String p0);
    
    AttachmentLoader<?> getLoader(final URI p0);
    
    void registerLoader(final String p0, final AttachmentLoader<?> p1);
}
