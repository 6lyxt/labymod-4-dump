// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.input.FileChooserWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class FileChooserWidgetLssPropertyResetter extends ButtonWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof FileChooserWidget) {}
        super.reset(widget);
    }
}
