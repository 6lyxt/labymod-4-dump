// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.model.chat.attachment;

import net.labymod.api.labyconnect.protocol.model.chat.attachment.Attachment;

public abstract class AbstractAttachment implements Attachment
{
    private int version;
    
    public AbstractAttachment() {
        this.version = 0;
    }
    
    @Override
    public void update() {
        ++this.version;
    }
    
    @Override
    public int getVersion() {
        return this.version;
    }
}
