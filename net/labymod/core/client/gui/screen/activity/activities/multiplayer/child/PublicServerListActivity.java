// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.multiplayer.child;

import net.labymod.api.client.network.server.ServerInfoCache;
import java.util.ArrayList;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;
import java.util.Locale;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import java.util.function.BiConsumer;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.network.server.global.PublicServerListService;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.PublicServerInfoWidget;
import net.labymod.api.client.network.server.global.PublicServerData;

@AutoActivity
@Link("activity/multiplayer/public-server-list.lss")
public class PublicServerListActivity extends LabyNetServerListActivity<PublicServerData, PublicServerInfoWidget>
{
    private final PublicServerListService serverList;
    private final ButtonWidget saveButton;
    
    public PublicServerListActivity(final TextFieldWidget searchField) {
        super("public", searchField);
        this.serverList = this.serverController.publicServerListService();
        this.getOrRegisterServerData(null);
        this.saveButton = ButtonWidget.i18n(this.getTranslationKey("button.saveServer"), this::saveSelected);
        this.setServerButtonsEnabled(false);
        this.serverListWidget.setSelectCallback(widget -> this.setServerButtonsEnabled(true));
    }
    
    @Override
    protected void fillServerList(final VerticalListWidget<PublicServerInfoWidget> serverListWidget, final String searchQuery) {
        this.getOrRegisterServerData((data, cache) -> {
            if (searchQuery != null) {
                final String name = data.getName().trim().toLowerCase(Locale.ROOT);
                final String address = data.address().getHost().trim().toLowerCase(Locale.ROOT);
                if (!name.contains(searchQuery) && !address.contains(searchQuery)) {
                    return;
                }
            }
            final PublicServerInfoWidget widget = new PublicServerInfoWidget(data, cache, this::canSave, this::save);
            widget.setMovable(ServerInfoWidget.Movable.ADD, movable -> widget.connect());
            serverListWidget.addChild(widget, false);
        });
    }
    
    @Override
    protected void fillButtonContainer(final FlexibleContentWidget container) {
        this.setServerButtonsEnabled(this.session.getSelectedEntry() != null);
        container.addFlexibleContent(this.joinButton);
        container.addFlexibleContent(this.saveButton);
    }
    
    @Override
    protected void setServerButtonsEnabled(final boolean enabled) {
        super.setServerButtonsEnabled(enabled);
        this.saveButton.setEnabled(this.canSaveSelected());
    }
    
    private void saveSelected() {
        if (this.save((PublicServerInfoWidget)this.session.getSelectedEntry())) {
            this.saveButton.setEnabled(false);
        }
    }
    
    private boolean save(final PublicServerInfoWidget serverInfoWidget) {
        if (serverInfoWidget == null) {
            return false;
        }
        final PublicServerData publicServerData = serverInfoWidget.serverData();
        this.serverController.serverList().add(StorageServerData.of(publicServerData.getName(), publicServerData.address()));
        this.setServerButtonsEnabled(this.session.getSelectedEntry() != null);
        return true;
    }
    
    private boolean canSaveSelected() {
        return this.canSave((PublicServerInfoWidget)this.session.getSelectedEntry());
    }
    
    private boolean canSave(final PublicServerInfoWidget serverInfoWidget) {
        if (serverInfoWidget == null) {
            return false;
        }
        final PublicServerData publicServerData = serverInfoWidget.serverData();
        return !this.serverController.serverList().has(publicServerData.address());
    }
    
    private void getOrRegisterServerData(final BiConsumer<PublicServerData, LabyNetServerInfoCache<PublicServerData>> consumer) {
        final ArrayList<PublicServerData> servers = this.serverList.getServers();
        for (int i = 0; i < servers.size(); ++i) {
            final PublicServerData serverData = servers.get(i);
            final LabyNetServerInfoCache<PublicServerData> cache = ((ServerListActivity<PublicServerData, W>)this).registerCache(serverData, server -> {
                final ServerInfoWidget<?> serverInfoWidget = ((ServerListActivity<PublicServerData, W>)this).getServerInfoWidget(serverData);
                if (serverInfoWidget != null) {
                    serverInfoWidget.updateServerInfo(server.serverInfo());
                }
                return;
            });
            cache.setSortingValue(i);
            if (consumer != null) {
                consumer.accept(serverData, cache);
            }
        }
        this.sortCache();
    }
}
