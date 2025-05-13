// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.types.chatinput;

import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.Widget;

public abstract class ChatInputTabActivity<T extends Widget> extends Activity
{
    protected T contentWidget;
    
    public boolean isHovered() {
        return ((Document)this.document).isHovered() && this.contentWidget != null && this.contentWidget.isHovered();
    }
    
    @Override
    public boolean allowCustomFont() {
        return false;
    }
}
