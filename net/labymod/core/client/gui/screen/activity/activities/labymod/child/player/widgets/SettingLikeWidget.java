// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets;

import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;

@AutoWidget
public class SettingLikeWidget extends HorizontalListWidget
{
    private final Icon icon;
    private final Component component;
    private final Widget settingWidget;
    
    public SettingLikeWidget(final Icon icon, final Component component, final Widget settingWidget) {
        this.icon = icon;
        this.component = component;
        this.settingWidget = settingWidget;
        if (this.settingWidget != null) {
            this.settingWidget.addId("input-widget");
        }
    }
    
    public SettingLikeWidget(final Component component, final Widget settingWidget) {
        this(null, component, settingWidget);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final DivWidget indicator = new DivWidget();
        indicator.addId("status-indicator");
        this.addEntry(indicator);
        if (this.icon != null) {
            final IconWidget iconWidget = new IconWidget(this.icon);
            iconWidget.addId("setting-icon");
            this.addEntry(iconWidget);
        }
        final ComponentWidget displayName = ComponentWidget.component(this.component);
        displayName.addId("display-name");
        this.addEntry(displayName);
        if (this.settingWidget != null) {
            this.addEntry(this.settingWidget);
        }
    }
}
