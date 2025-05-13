// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.widget.widgets.MultilineTextFieldWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class MultilineTextFieldWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof MultilineTextFieldWidget && ((MultilineTextFieldWidget)widget).textColor() != null) {
            ((MultilineTextFieldWidget)widget).textColor().reset();
        }
        super.reset(widget);
    }
}
