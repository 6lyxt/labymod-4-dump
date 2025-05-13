// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat.advanced;

import java.util.Comparator;
import net.labymod.api.client.chat.advanced.IngameChatTab;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.core.main.LabyMod;
import java.util.Iterator;
import java.util.ArrayList;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import java.util.List;
import net.labymod.api.configuration.labymod.chat.config.ChatWindowConfig;
import net.labymod.api.configuration.labymod.chat.ChatWindow;

public class DefaultChatWindow implements ChatWindow
{
    private final ChatWindowConfig config;
    private final List<ChatTab> tabs;
    private ChatTab activeTab;
    
    public DefaultChatWindow(final ChatWindowConfig config) {
        this.config = config;
        this.tabs = new ArrayList<ChatTab>();
    }
    
    @Override
    public ChatWindowConfig config() {
        return this.config;
    }
    
    @Override
    public List<ChatTab> getTabs() {
        return this.tabs;
    }
    
    @Override
    public ChatTab getActiveTab() {
        final int focusedTabIndex = this.config.getFocusedTabIndex();
        if (this.activeTab != null && this.activeTab.rootConfig().index().get() == focusedTabIndex) {
            return this.activeTab;
        }
        for (final ChatTab tab : this.tabs) {
            if (tab.rootConfig().index().get() == focusedTabIndex) {
                return tab;
            }
        }
        if (this.tabs.isEmpty()) {
            this.config.checkForFocusedTab();
            return this.initializeTab(this.config.getFocusedTab());
        }
        final ChatTab chatTab = this.tabs.get(0);
        this.config.setFocusedTab(chatTab.rootConfig().index().get());
        return chatTab;
    }
    
    @Override
    public void save() {
        LabyMod.references().advancedChatController().saveConfig();
    }
    
    @Override
    public void switchToTab(final ChatTab chatTab) {
        this.activeTab = chatTab;
        this.config.setFocusedTab(chatTab.rootConfig().index().get());
        this.save();
    }
    
    @Override
    public void deleteTab(final ChatTab chatTab) {
        if (chatTab == this.activeTab) {
            for (int i = 0; i < this.tabs.size(); ++i) {
                final ChatTab tab = this.tabs.get(i);
                if (tab == chatTab) {
                    this.config.setFocusedTab((i == 0) ? 1 : (i - 1));
                    this.activeTab = null;
                    break;
                }
            }
        }
        this.tabs.remove(chatTab);
        this.config.getTabs().remove(chatTab.rootConfig());
        this.sortTabs(true);
    }
    
    @Override
    public ChatTab initializeTab(final RootChatTabConfig config, final ChatTab template, final boolean sort) {
        RootChatTabConfig.Type type = config.type().get();
        if (type == null) {
            type = RootChatTabConfig.Type.SERVER;
            config.type().set(type);
        }
        final ChatTab chatTab = new IngameChatTab(this, config);
        if (template != null) {
            chatTab.copy(template);
        }
        this.tabs.add(chatTab);
        if (sort) {
            this.config.getTabs().add(chatTab.rootConfig());
        }
        this.sortTabs(sort);
        return chatTab;
    }
    
    private void sortTabs(final boolean sortConfig) {
        this.tabs.sort(Comparator.comparingInt(tab -> tab.rootConfig().index().get()));
        if (sortConfig) {
            this.config.getTabs().sort(Comparator.comparingInt(rootConfig -> rootConfig.index().get()));
        }
    }
}
