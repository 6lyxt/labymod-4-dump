// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.activity.settings;

import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public class SettingHeaderWidget extends AbstractWidget<Widget>
{
    private final Component displayName;
    private final Pressable pressable;
    
    public SettingHeaderWidget(final Component displayName) {
        this(displayName, Pressable.NOOP);
    }
    
    public SettingHeaderWidget(final Component displayName, final Pressable pressable) {
        this.displayName = displayName;
        this.pressable = pressable;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final ComponentWidget headerComponent = ComponentWidget.component(this.displayName);
        headerComponent.addId("title-header");
        headerComponent.setPressable(this.pressable);
        ((AbstractWidget<ComponentWidget>)this).addChild(headerComponent);
    }
}
