// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labyconnect.protocol.model.chat.attachment;

import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.widgets.labyconnect.chat.attachment.AttachmentWidget;

public interface Attachment
{
    @NotNull
    AttachmentWidget<?> createWidget();
    
    float getWidth();
    
    float getHeight();
    
    void update();
    
    int getVersion();
    
    @NotNull
    Component toComponent();
}
