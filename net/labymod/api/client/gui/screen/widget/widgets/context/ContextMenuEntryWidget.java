// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.context;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;

@AutoWidget
public class ContextMenuEntryWidget extends HorizontalListWidget
{
    private final ContextMenuEntry entry;
    private boolean disabledOnInitialize;
    
    public ContextMenuEntryWidget(final ContextMenuEntry entry) {
        this.entry = entry;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final Icon icon = this.entry.getIcon();
        if (icon != null) {
            this.addEntry(new IconWidget(icon)).addId("icon");
        }
        final Component text = this.entry.getText();
        if (text != null) {
            this.addEntry(ComponentWidget.component(text)).addId("text");
        }
        final Icon subIcon = this.entry.getSubIcon();
        if (subIcon != null) {
            this.addEntry(new IconWidget(subIcon)).addId("sub-icon");
        }
        else if (this.entry.hasSubMenu()) {
            this.addEntry(new IconWidget(Textures.SpriteCommon.FORWARD_BUTTON)).addId("sub-icon");
        }
        this.disabledOnInitialize = this.entry.isDisabled();
        if (this.disabledOnInitialize) {
            ((AbstractWidget<Widget>)this).addId("disabled-entry");
        }
        else {
            ((AbstractWidget<Widget>)this).addId("enabled-entry");
        }
        if (this.entry.getHoverComponent() != null) {
            this.setHoverComponent(this.entry.getHoverComponent());
        }
    }
    
    public boolean isDisabledOnInitialize() {
        return this.disabledOnInitialize;
    }
    
    public ContextMenuEntry entry() {
        return this.entry;
    }
}
