// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.file;

import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.Component;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import java.net.URI;
import java.util.UUID;
import net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment;

public class FileAttachment extends AbstractURIAttachment
{
    protected final String type;
    protected final UUID identifier;
    
    public FileAttachment(final URI uri, final String type, final UUID identifier) {
        super(uri);
        this.type = type;
        this.identifier = identifier;
    }
    
    @Override
    public Icon getIcon() {
        return Textures.SpriteCommon.QUESTION_MARK;
    }
    
    @Override
    public Component getTitle() {
        final byte[] fileData = this.getFileData();
        if (fileData == null) {
            return Component.text("Receiving...");
        }
        return Component.text(fileData.length + " bytes");
    }
    
    @Override
    public boolean isClickable() {
        return false;
    }
    
    @Override
    public Component getDescription() {
        return Component.text(this.type);
    }
    
    @Override
    public void open() {
    }
    
    @Override
    public boolean shouldHideURI() {
        return true;
    }
    
    @NotNull
    @Override
    public Component toComponent() {
        return this.getTitle();
    }
    
    public byte[] getFileData() {
        final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null) {
            return null;
        }
        return session.getFile(this.identifier);
    }
}
