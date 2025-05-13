// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.chat.tab;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.core.client.gui.screen.widget.widgets.chat.ChatWindowWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;

@AutoWidget
public class ChatTabWidget extends HorizontalListWidget
{
    private final ChatWindowWidget windowWidget;
    private final ChatTab tab;
    private int unread;
    private ComponentWidget unreadWidget;
    
    public ChatTabWidget(final ChatWindowWidget windowWidget, final ChatTab tab) {
        this.windowWidget = windowWidget;
        this.tab = tab;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final boolean active = this.windowWidget.window().getActiveTab() == this.tab;
        final HorizontalListWidget tabWidget = new HorizontalListWidget();
        ((AbstractWidget<Widget>)tabWidget).addId("tab");
        if (active) {
            ((AbstractWidget<Widget>)tabWidget).addId("active");
        }
        this.windowWidget.applyDefaultContextMenu(tabWidget.createContextMenu(), this.tab);
        String tabName = this.tab.getName();
        if (tabName.length() > 15) {
            tabName = tabName.substring(0, 15) + "...";
        }
        final ComponentWidget nameWidget = ComponentWidget.text(tabName);
        nameWidget.addId("name");
        tabWidget.addEntry(nameWidget);
        final int unread2;
        final int unread = unread2 = this.tab.getUnread();
        this.unread = unread2;
        (this.unreadWidget = ComponentWidget.text(String.valueOf(unread2))).addId("unread");
        this.unreadWidget.setVisible(unread != 0);
        tabWidget.addEntry(this.unreadWidget);
        this.addEntry(tabWidget);
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (super.mouseClicked(mouse, mouseButton)) {
            return true;
        }
        if (mouseButton != MouseButton.LEFT) {
            return false;
        }
        final ChatTab activeTab = this.windowWidget.window().getActiveTab();
        if (activeTab == this.tab) {
            return false;
        }
        this.windowWidget.displayTab(this.tab);
        return true;
    }
    
    public void updateUnread(final int unread) {
        if (this.unread == unread) {
            return;
        }
        this.unread = unread;
        if (this.unreadWidget != null) {
            this.unreadWidget.setText(String.valueOf(unread));
            this.unreadWidget.setVisible(unread != 0);
        }
    }
    
    public ChatTab tab() {
        return this.tab;
    }
}
