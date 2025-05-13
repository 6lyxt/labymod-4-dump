// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.emotes;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;

@AutoWidget
public class EmotePlaceholderSegmentWidget extends WheelWidget.Segment
{
    private final Icon icon;
    private final Component component;
    
    public EmotePlaceholderSegmentWidget(final Icon icon, final Component component) {
        this.icon = icon;
        this.component = component;
        if (this.icon == null && this.component == null) {
            throw new IllegalArgumentException("Either icon or component must be set");
        }
        if (this.icon == null) {
            this.addId("component-placeholder");
        }
        else if (this.component == null) {
            this.addId("icon-placeholder");
        }
        else {
            this.addId("icon-component-placeholder");
        }
    }
    
    public EmotePlaceholderSegmentWidget(final Icon icon) {
        this(icon, null);
    }
    
    public EmotePlaceholderSegmentWidget(final Component component) {
        this(null, component);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.children.clear();
        IconWidget iconWidget = null;
        if (this.icon != null) {
            iconWidget = new IconWidget(this.icon);
            iconWidget.addId("placeholder-icon");
        }
        ComponentWidget componentWidget = null;
        if (this.component != null) {
            componentWidget = ComponentWidget.component(this.component);
            componentWidget.addId("placeholder-component");
        }
        if (iconWidget == null) {
            ((AbstractWidget<ComponentWidget>)this).addChild(componentWidget);
        }
        else if (componentWidget == null) {
            ((AbstractWidget<IconWidget>)this).addChild(iconWidget);
        }
        else {
            final VerticalListWidget<Widget> container = new VerticalListWidget<Widget>();
            container.addId("placeholder-container");
            container.addChild(iconWidget);
            container.addChild(componentWidget);
            ((AbstractWidget<VerticalListWidget<Widget>>)this).addChild(container);
        }
    }
}
