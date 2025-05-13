// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.renderer.VariableIconWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class VariableIconWidgetLssPropertyResetter extends IconWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final VariableIconWidget variableIconWidget) {
            if (variableIconWidget.iconWidth() != null) {
                variableIconWidget.iconWidth().reset();
            }
            if (((VariableIconWidget)widget).iconHeight() != null) {
                ((VariableIconWidget)widget).iconHeight().reset();
            }
        }
        super.reset(widget);
    }
}
