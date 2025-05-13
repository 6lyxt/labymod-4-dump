// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.labyconnect.chat.attachment;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.labyconnect.protocol.model.chat.attachment.Attachment;

@AutoWidget
public class AttachmentWidget<T extends Attachment> extends SimpleWidget
{
    protected final T attachment;
    private int version;
    
    public AttachmentWidget(final T attachment) {
        this.attachment = attachment;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.version = this.attachment.getVersion();
    }
    
    public int getVersion() {
        return this.version;
    }
    
    public T attachment() {
        return this.attachment;
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        return this.attachment.getWidth() + this.bounds().getHorizontalOffset(type);
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        return this.attachment.getHeight() + this.bounds().getVerticalOffset(type);
    }
}
