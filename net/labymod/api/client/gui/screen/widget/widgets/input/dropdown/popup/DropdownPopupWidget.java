// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.popup;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class DropdownPopupWidget<T> extends AbstractWidget<Widget>
{
    private final DropdownWidget<T> dropdown;
    private final DropdownListWidget<T> listWidget;
    
    public DropdownPopupWidget(final DropdownWidget<T> dropdown) {
        this.dropdown = dropdown;
        (this.listWidget = new DropdownListWidget<T>(dropdown)).addId("list");
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final ScrollWidget scrollWidget = new ScrollWidget(this.listWidget);
        scrollWidget.addId("scroll");
        this.addChild(scrollWidget);
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        return this.listWidget.getContentHeight(type);
    }
    
    public DropdownWidget<T> getDropdown() {
        return this.dropdown;
    }
}
