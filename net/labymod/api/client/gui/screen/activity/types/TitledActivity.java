// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.types;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.attributes.PriorityLayer;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.component.Component;

public abstract class TitledActivity extends SimpleActivity
{
    protected final Component title;
    
    public TitledActivity(final String title) {
        this(Component.text(title));
    }
    
    public TitledActivity(final Component title) {
        this.title = title;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final ComponentWidget widget = ComponentWidget.component(this.title);
        widget.addId("activity-title");
        widget.priorityLayer().set(PriorityLayer.VERY_FRONT);
        ((AbstractWidget<ComponentWidget>)this.document).addChild(widget);
    }
}
