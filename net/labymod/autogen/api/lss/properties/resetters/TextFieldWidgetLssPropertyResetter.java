// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class TextFieldWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final TextFieldWidget textFieldWidget) {
            if (textFieldWidget.type() != null) {
                textFieldWidget.type().reset();
            }
            if (((TextFieldWidget)widget).textAlignmentX() != null) {
                ((TextFieldWidget)widget).textAlignmentX().reset();
            }
            if (((TextFieldWidget)widget).textAlignmentY() != null) {
                ((TextFieldWidget)widget).textAlignmentY().reset();
            }
            if (((TextFieldWidget)widget).placeHolderColor() != null) {
                ((TextFieldWidget)widget).placeHolderColor().reset();
            }
            if (((TextFieldWidget)widget).textColor() != null) {
                ((TextFieldWidget)widget).textColor().reset();
            }
            if (((TextFieldWidget)widget).markTextColor() != null) {
                ((TextFieldWidget)widget).markTextColor().reset();
            }
            if (((TextFieldWidget)widget).markColor() != null) {
                ((TextFieldWidget)widget).markColor().reset();
            }
            if (((TextFieldWidget)widget).cursorColor() != null) {
                ((TextFieldWidget)widget).cursorColor().reset();
            }
            if (((TextFieldWidget)widget).clearButton() != null) {
                ((TextFieldWidget)widget).clearButton().reset();
            }
            if (((TextFieldWidget)widget).submitButton() != null) {
                ((TextFieldWidget)widget).submitButton().reset();
            }
            if (((TextFieldWidget)widget).fontSize() != null) {
                ((TextFieldWidget)widget).fontSize().reset();
            }
            if (((TextFieldWidget)widget).textShadow() != null) {
                ((TextFieldWidget)widget).textShadow().reset();
            }
        }
        super.reset(widget);
    }
}
