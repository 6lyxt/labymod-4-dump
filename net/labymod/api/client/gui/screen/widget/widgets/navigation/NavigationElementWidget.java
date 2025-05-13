// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.navigation;

import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.navigation.NavigationElement;
import net.labymod.api.client.gui.navigation.NavigationWrapper;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class NavigationElementWidget extends SimpleWidget
{
    private final NavigationWrapper wrapper;
    private final NavigationElement<?> element;
    private Widget widget;
    
    public NavigationElementWidget(final NavigationWrapper wrapper, final NavigationElement<?> element) {
        this.wrapper = wrapper;
        this.element = element;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        (this.widget = (Widget)this.element.createWidget(this.wrapper)).addId("navigation-element-content");
        this.addChild(this.widget);
        this.setVisible(this.element.isVisible());
    }
    
    public NavigationElement<?> getElement() {
        return this.element;
    }
    
    public Widget getWidget() {
        return this.widget;
    }
}
