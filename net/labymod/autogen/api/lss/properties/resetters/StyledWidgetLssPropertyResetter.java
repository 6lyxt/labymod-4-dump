// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.StyledWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;

public class StyledWidgetLssPropertyResetter implements LssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof StyledWidget) {}
    }
}
