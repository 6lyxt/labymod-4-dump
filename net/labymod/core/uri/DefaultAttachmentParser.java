// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.uri;

import java.util.Iterator;
import java.net.URI;
import net.labymod.api.uri.URIParser;
import java.util.ArrayList;
import net.labymod.api.labyconnect.protocol.model.chat.attachment.URIAttachment;
import java.util.List;
import javax.inject.Inject;
import net.labymod.core.uri.loader.FileAttachmentLoader;
import net.labymod.core.uri.loader.LanAttachmentLoader;
import net.labymod.core.uri.loader.LinkAttachmentLoader;
import java.util.HashMap;
import net.labymod.api.uri.loader.AttachmentLoader;
import java.util.Map;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.uri.AttachmentParser;

@Singleton
@Implements(AttachmentParser.class)
public class DefaultAttachmentParser implements AttachmentParser
{
    private final Map<String, AttachmentLoader<?>> loaders;
    
    @Inject
    public DefaultAttachmentParser() {
        this.loaders = new HashMap<String, AttachmentLoader<?>>();
        this.registerLoader("http", new LinkAttachmentLoader());
        this.registerLoader("https", new LinkAttachmentLoader());
        this.registerLoader("lan", new LanAttachmentLoader());
        this.registerLoader("file", new FileAttachmentLoader());
    }
    
    @Override
    public List<URIAttachment> parse(final String text) {
        final List<URIAttachment> attachments = new ArrayList<URIAttachment>();
        for (final URI uri : URIParser.parse(text)) {
            final AttachmentLoader<?> loader = this.getLoader(uri);
            if (loader == null) {
                continue;
            }
            final URIAttachment attachment = (URIAttachment)loader.create(uri);
            if (attachment == null) {
                continue;
            }
            attachments.add(attachment);
        }
        return attachments;
    }
    
    @Override
    public AttachmentLoader<?> getLoader(final URI uri) {
        final String scheme = uri.getScheme();
        return (scheme == null) ? null : this.loaders.get(scheme);
    }
    
    @Override
    public void registerLoader(final String scheme, final AttachmentLoader<?> loader) {
        this.loaders.put(scheme, loader);
    }
}
