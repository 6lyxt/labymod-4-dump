// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.model.chat.attachment.embed;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat.attachment.URIAttachmentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.labyconnect.chat.attachment.AttachmentWidget;
import java.net.URI;
import net.labymod.api.labyconnect.protocol.model.chat.attachment.URIAttachment;
import net.labymod.core.labyconnect.protocol.model.chat.attachment.AbstractAttachment;

public abstract class AbstractURIAttachment extends AbstractAttachment implements URIAttachment
{
    protected final URI uri;
    
    protected AbstractURIAttachment(final URI uri) {
        this.uri = uri;
    }
    
    @NotNull
    @Override
    public AttachmentWidget<?> createWidget() {
        return new URIAttachmentWidget(this);
    }
    
    @Override
    public float getWidth() {
        return 200.0f;
    }
    
    @Override
    public float getHeight() {
        return 50.0f;
    }
    
    @NotNull
    @Override
    public Component toComponent() {
        return Component.text(this.getUrl());
    }
    
    @Override
    public void open() {
        Laby.labyAPI().minecraft().chatExecutor().openUrl(this.getUrl(), true);
    }
    
    public abstract Icon getIcon();
    
    public abstract Component getTitle();
    
    public abstract Component getDescription();
    
    @Nullable
    public Component getButtonComponent() {
        return null;
    }
    
    public boolean isClickable() {
        return true;
    }
    
    @Override
    public URI getURI() {
        return this.uri;
    }
}
