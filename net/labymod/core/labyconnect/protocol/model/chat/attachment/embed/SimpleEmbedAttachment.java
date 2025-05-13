// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.model.chat.attachment.embed;

import java.net.URI;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;

public final class SimpleEmbedAttachment extends AbstractURIAttachment
{
    private final Icon icon;
    private final Component title;
    private final Component description;
    
    private SimpleEmbedAttachment(final Icon icon, final Component title, final Component description, final String url) {
        super(URI.create(url));
        this.icon = icon;
        this.title = title;
        this.description = description;
    }
    
    @Override
    public Icon getIcon() {
        return this.icon;
    }
    
    @Override
    public Component getTitle() {
        return this.title;
    }
    
    @Override
    public Component getDescription() {
        return this.description;
    }
    
    @Override
    public boolean shouldHideURI() {
        return true;
    }
    
    public static final class Builder
    {
        private Icon icon;
        private Component title;
        private Component description;
        private String url;
        
        public Builder icon(final Icon icon) {
            this.icon = icon;
            return this;
        }
        
        public Builder title(final Component title) {
            this.title = title;
            return this;
        }
        
        public Builder description(final Component description) {
            this.description = description;
            return this;
        }
        
        public Builder url(final String url) {
            this.url = url;
            return this;
        }
        
        public SimpleEmbedAttachment build() {
            return new SimpleEmbedAttachment(this.icon, this.title, this.description, this.url);
        }
    }
}
