// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.embed.content.field;

import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.embed.content.field.FormButton;

public class DefaultFormButton extends DefaultFormField<Void> implements FormButton
{
    public DefaultFormButton(final DefaultFormFieldBuilder builder) {
        super(builder);
    }
    
    @Override
    protected Widget createWidgetBase() {
        final ButtonWidget widget = ButtonWidget.component(this.title());
        widget.setPressable(this::submit);
        return widget;
    }
    
    @Override
    public boolean isValid(final Void value) {
        return true;
    }
}
