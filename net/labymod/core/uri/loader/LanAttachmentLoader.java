// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.uri.loader;

import net.labymod.api.labyconnect.protocol.model.chat.attachment.URIAttachment;
import net.labymod.core.labyconnect.lanworld.SharedLanWorldInvite;
import net.labymod.core.main.LabyMod;
import java.util.UUID;
import java.net.URI;
import net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.lan.LanAttachment;
import net.labymod.api.uri.loader.AttachmentLoader;

public class LanAttachmentLoader implements AttachmentLoader<LanAttachment>
{
    @Override
    public LanAttachment create(final URI uri) {
        try {
            final String authority = uri.getAuthority();
            if (authority == null) {
                return null;
            }
            final UUID sender = UUID.fromString(authority);
            final SharedLanWorldInvite invite = LabyMod.references().sharedLanWorldService().getInviteOfSender(sender);
            return (invite == null) ? LanAttachment.createExpired(uri) : LanAttachment.createOf(uri, invite);
        }
        catch (final Throwable exception) {
            return null;
        }
    }
}
