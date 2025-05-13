// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.activity.settings;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.HorizontalAlignment;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;

@Deprecated
@AutoWidget
public class SettingOpenActivityWidget extends HorizontalListWidget
{
    private final Icon icon;
    private final Component displayName;
    private final Runnable callback;
    
    public SettingOpenActivityWidget(final Icon icon, final Component displayName, final Runnable callback) {
        this.icon = icon;
        this.displayName = displayName;
        this.callback = callback;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final DivWidget statusIndicator = new DivWidget();
        statusIndicator.addId("status-indicator");
        this.addEntry(statusIndicator);
        if (this.icon != null) {
            this.addEntry(new IconWidget(this.icon));
        }
        this.addEntry(ComponentWidget.component(this.displayName));
        final ButtonWidget advancedButton = ButtonWidget.icon(Textures.SpriteCommon.SETTINGS);
        ((AbstractWidget<Widget>)advancedButton).addId("advanced-button");
        final ButtonWidget buttonWidget = advancedButton;
        final Runnable callback = this.callback;
        Objects.requireNonNull(callback);
        buttonWidget.setPressable(callback::run);
        this.addEntry(advancedButton).alignment().set(HorizontalAlignment.RIGHT);
    }
}
