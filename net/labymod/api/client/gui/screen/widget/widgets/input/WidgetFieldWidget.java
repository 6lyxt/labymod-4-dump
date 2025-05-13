// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input;

import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class WidgetFieldWidget extends SimpleWidget
{
    private Widget widget;
    
    public WidgetFieldWidget(final Widget widget) {
        (this.widget = widget).addId("widget-field-widget");
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.addChild(this.widget);
    }
    
    public void setWidget(final Widget widget) {
        (this.widget = widget).addId("widget-field-widget");
    }
    
    public Widget getWidget() {
        return this.widget;
    }
}
