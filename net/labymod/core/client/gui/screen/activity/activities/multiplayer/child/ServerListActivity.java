// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.multiplayer.child;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.Comparator;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import java.util.Iterator;
import net.labymod.api.client.network.server.ServerInfoCache;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import java.util.Locale;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.LabyAPI;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.ServerFolderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;
import net.labymod.api.client.gui.screen.widget.action.Equalizer;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.MultiplayerActivity;
import net.labymod.api.Laby;
import net.labymod.api.util.collection.Lists;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerListWidget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import java.util.List;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerEntryWidget;
import net.labymod.api.client.network.server.ServerData;

@Links({ @Link("activity/multiplayer/server-list.lss"), @Link("activity/multiplayer/server-info.lss"), @Link("activity/multiplayer/server-folder.lss") })
public abstract class ServerListActivity<T extends ServerData, W extends ServerEntryWidget> extends Activity
{
    private static final String TRANSLATION_KEY_PREFIX_PREFIX = "labymod.activity.multiplayer.";
    protected final List<LabyNetServerInfoCache<T>> serverInfos;
    protected final ServerListConfig settings;
    protected final ServerController serverController;
    protected final ListSession<W> session;
    protected final ServerListWidget<W> serverListWidget;
    protected final ButtonWidget joinButton;
    private final String identifier;
    private final String translationKeyPrefix;
    private final TextFieldWidget searchField;
    protected long lastRefreshTime;
    private String lastQuery;
    
    protected ServerListActivity(final String identifier, final TextFieldWidget searchField) {
        this.serverInfos = (List<LabyNetServerInfoCache<T>>)Lists.newArrayList();
        this.identifier = identifier;
        this.translationKeyPrefix = "labymod.activity.multiplayer." + identifier;
        this.searchField = searchField;
        final LabyAPI labyAPI = Laby.labyAPI();
        this.settings = labyAPI.config().multiplayer().serverList();
        this.serverController = labyAPI.serverController();
        this.session = new ListSession<W>(new MultiplayerActivity.ServerInfoWidgetEqualizer<W>());
        (this.serverListWidget = new ServerListWidget<W>(this.session)).addId("server-list");
        this.serverListWidget.setDoubleClickCallback(widget -> {
            if (widget instanceof ServerInfoWidget) {
                final ServerInfoWidget<?> serverInfo = (ServerInfoWidget<?>)widget;
                serverInfo.connect();
            }
            if (widget instanceof final ServerFolderWidget serverFolder) {
                serverFolder.enter();
            }
            return;
        });
        this.joinButton = ButtonWidget.i18n("labymod.activity.multiplayer.joinServer", () -> {
            final W selectedWidget = this.session.getSelectedEntry();
            if (selectedWidget instanceof ServerInfoWidget) {
                final ServerInfoWidget<?> serverInfo2 = (ServerInfoWidget<?>)selectedWidget;
                serverInfo2.connect();
            }
        });
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final FlexibleContentWidget container = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)container).addId(this.identifier + "-server-list-container", "server-list-container");
        final DivWidget backgroundContainer = new DivWidget();
        backgroundContainer.addId("background-container");
        final FlexibleContentWidget window = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)window).addId("window");
        String query;
        if (this.searchField != null) {
            this.searchField.updateListener(value -> {
                if (this.lastQuery.equals(value.trim())) {
                    return;
                }
                else {
                    this.reload();
                    return;
                }
            });
            final String actualQuery = this.searchField.getText().trim();
            this.lastQuery = actualQuery;
            final boolean visible = !actualQuery.isEmpty();
            this.searchField.setFocused(visible);
            this.searchField.setVisible(visible);
            window.addContent(this.searchField);
            if (visible) {
                query = actualQuery.toLowerCase(Locale.ROOT);
            }
            else {
                query = null;
            }
        }
        else {
            query = null;
        }
        this.initializeHeader(window, query);
        this.fillServerList(this.serverListWidget, query);
        final DivWidget content = new DivWidget();
        content.addId("content");
        final ScrollWidget scrollWidget = new ScrollWidget(this.serverListWidget);
        scrollWidget.addId("center");
        ((AbstractWidget<ScrollWidget>)content).addChild(scrollWidget);
        window.addFlexibleContent(content);
        ((AbstractWidget<FlexibleContentWidget>)backgroundContainer).addChild(window);
        container.addFlexibleContent(backgroundContainer);
        final DivWidget buttonWrapper = new DivWidget();
        buttonWrapper.addId("button-wrapper");
        final FlexibleContentWidget buttonContainer = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)buttonContainer).addId("button-container", "center");
        this.fillButtonContainer(buttonContainer);
        ((AbstractWidget<FlexibleContentWidget>)buttonWrapper).addChild(buttonContainer);
        container.addContent(buttonWrapper);
        ((AbstractWidget<FlexibleContentWidget>)this.document).addChild(container);
    }
    
    protected void initializeHeader(final FlexibleContentWidget container, final String searchQuery) {
    }
    
    @Override
    public void render(final ScreenContext context) {
        super.render(context);
        Laby.gfx().clearDepth();
        final W draggingWidget = this.serverListWidget.getDraggingWidget();
        if (draggingWidget != null) {
            final Stack stack = context.stack();
            stack.push();
            final ScrollWidget parent1 = (ScrollWidget)this.serverListWidget.getParent();
            final float offsetY = parent1.session().getScrollPositionY();
            stack.translate(0.0f, -offsetY, 0.0f);
            draggingWidget.render(context);
            stack.pop();
        }
    }
    
    @Override
    public void onOpenScreen() {
        super.onOpenScreen();
        if (!this.canRefresh()) {
            return;
        }
        this.lastRefreshTime = TimeUtil.getMillis();
        for (final ServerInfoCache<T> serverInfo : this.serverInfos) {
            serverInfo.update();
        }
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (key == Key.F5) {
            this.refresh(true);
            return true;
        }
        return super.keyPressed(key, type);
    }
    
    protected abstract void fillServerList(final VerticalListWidget<W> p0, final String p1);
    
    protected abstract void fillButtonContainer(final FlexibleContentWidget p0);
    
    protected final String getTranslationKey(final String key) {
        return this.translationKeyPrefix + "." + key;
    }
    
    protected boolean canRefresh() {
        return this.lastRefreshTime == 0L || TimeUtil.getMillis() - this.lastRefreshTime >= 5000L;
    }
    
    protected LabyNetServerInfoCache<T> registerCache(final T serverData, final Consumer<ServerInfoCache<T>> callback) {
        LabyNetServerInfoCache<T> cache = this.getCache(serverData);
        if (cache != null) {
            cache.setCallback(callback);
            return cache;
        }
        cache = new LabyNetServerInfoCache<T>(serverData, callback);
        this.serverInfos.add(cache);
        return cache;
    }
    
    protected LabyNetServerInfoCache<T> getCache(final T serverData) {
        for (final LabyNetServerInfoCache<T> serverInfoCache : this.serverInfos) {
            if (serverInfoCache.matches(serverData)) {
                return serverInfoCache;
            }
        }
        return null;
    }
    
    public ServerInfoCache<T> unregisterCache(final T serverData) {
        final ServerInfoCache<T> cache = this.getCache(serverData);
        if (cache != null) {
            this.serverInfos.remove(cache);
        }
        return cache;
    }
    
    protected void sortCache() {
        this.serverInfos.sort(Comparator.comparingInt(LabyNetServerInfoCache::getSortingValue));
    }
    
    protected ServerInfoWidget<?> getServerInfoWidget(final T serverData) {
        for (final W genericChild : this.serverListWidget.getGenericChildren()) {
            if (genericChild instanceof ServerInfoWidget) {
                final ServerInfoWidget<?> serverInfo = (ServerInfoWidget<?>)genericChild;
                if (((ServerData)serverInfo.serverData()).equals(serverData)) {
                    return serverInfo;
                }
                continue;
            }
        }
        return null;
    }
    
    protected abstract void refresh(final boolean p0);
    
    protected void setServerButtonsEnabled(final boolean enabled) {
        this.joinButton.setEnabled(enabled);
    }
}
