// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labyconnect.protocol.model.chat.attachment;

import java.net.URI;

public interface URIAttachment extends Attachment
{
    default String getUrl() {
        return this.getURI().toString();
    }
    
    URI getURI();
    
    void open();
    
    boolean shouldHideURI();
}
