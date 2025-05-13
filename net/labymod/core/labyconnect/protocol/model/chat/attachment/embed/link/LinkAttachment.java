// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.link;

import net.labymod.api.Textures;
import java.net.URI;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment;

public class LinkAttachment extends AbstractURIAttachment
{
    private Icon icon;
    private Component title;
    private Component description;
    
    public LinkAttachment(final URI uri) {
        super(uri);
        this.icon = Textures.SpriteCommon.QUESTION_MARK;
        this.title = Component.text(uri.toString());
        this.description = Component.text("...");
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
        return false;
    }
    
    public void setIcon(final Icon icon) {
        this.icon = icon;
    }
    
    public void setTitle(final Component title) {
        this.title = title;
    }
    
    public void setDescription(final Component description) {
        this.description = description;
    }
}
