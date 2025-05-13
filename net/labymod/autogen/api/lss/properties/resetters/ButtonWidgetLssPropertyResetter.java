// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class ButtonWidgetLssPropertyResetter extends HorizontalListWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final ButtonWidget buttonWidget) {
            if (buttonWidget.scaleToFit() != null) {
                buttonWidget.scaleToFit().reset();
            }
            if (((ButtonWidget)widget).disabledColor() != null) {
                ((ButtonWidget)widget).disabledColor().reset();
            }
            if (((ButtonWidget)widget).icon() != null) {
                ((ButtonWidget)widget).icon().reset();
            }
            if (((ButtonWidget)widget).text() != null) {
                ((ButtonWidget)widget).text().reset();
            }
            if (((ButtonWidget)widget).alignment() != null) {
                ((ButtonWidget)widget).alignment().reset();
            }
        }
        super.reset(widget);
    }
}
