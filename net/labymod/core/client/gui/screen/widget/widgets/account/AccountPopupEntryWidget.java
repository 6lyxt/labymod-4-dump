// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.account;

import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.client.gui.screen.widget.widgets.account.entry.AccountEntry;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
@Link("activity/account/account-popup.lss")
public class AccountPopupEntryWidget extends AbstractWidget<Widget>
{
    private final AccountEntry entry;
    
    public AccountPopupEntryWidget(final AccountEntry entry) {
        this.entry = entry;
        this.addId("account-entry");
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final HorizontalListWidget list = new HorizontalListWidget();
        ((AbstractWidget<Widget>)list).addId("container");
        final IconWidget avatar = new IconWidget(this.entry.getIcon());
        avatar.addId("icon");
        list.addEntry(avatar);
        final ComponentWidget usernameWidget = ComponentWidget.component(this.entry.getComponent());
        usernameWidget.addId("component");
        list.addEntry(usernameWidget);
        ((AbstractWidget<HorizontalListWidget>)this).addChild(list);
    }
}
