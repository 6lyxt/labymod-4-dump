// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.uri.loader;

import org.jetbrains.annotations.Nullable;
import java.net.URI;
import net.labymod.api.labyconnect.protocol.model.chat.attachment.URIAttachment;

public interface AttachmentLoader<T extends URIAttachment>
{
    @Nullable
    T create(final URI p0);
}
