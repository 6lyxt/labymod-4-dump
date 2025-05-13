// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.multiplayer;

import net.labymod.api.service.Registry;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;
import net.labymod.api.client.gui.screen.widget.action.Equalizer;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerEntryWidget;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.TabWidget;
import net.labymod.api.client.network.server.lan.LanServerCallback;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerAddressResolver;
import net.labymod.api.client.network.server.storage.ServerList;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.PublicServerListActivity;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.DefaultComponentTab;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.Tab;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.PrivateServerListActivity;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.LanServerListActivity;
import net.labymod.api.client.network.server.lan.LanServerDetector;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.types.TabbedActivity;

@AutoActivity
public class MultiplayerActivity extends TabbedActivity
{
    private static final LanServerDetector LAN_SERVER_DETECTOR;
    public static final MultiplayerActivity INSTANCE;
    private final LanServerListActivity lanServerListActivity;
    private final PrivateServerListActivity privateServerListActivity;
    private final TextFieldWidget searchField;
    private final Tab lanTab;
    
    private MultiplayerActivity() {
        this.lanServerListActivity = new LanServerListActivity();
        this.register("lan", this.lanTab = new DefaultComponentTab(Component.translatable("labymod.activity.multiplayer.tab.lan", new Component[0]), this.lanServerListActivity));
        (this.searchField = new TextFieldWidget()).addId("search-field");
        this.privateServerListActivity = new PrivateServerListActivity(this, this.searchField);
        final Tab privateServerTab = new DefaultComponentTab(Component.translatable("labymod.activity.multiplayer.tab.private", new Component[0]), this.privateServerListActivity);
        this.register("private", privateServerTab);
        ((Registry<DefaultComponentTab>)this).register("public", new DefaultComponentTab(Component.translatable("labymod.activity.multiplayer.tab.public", new Component[0]), new PublicServerListActivity(this.searchField)));
        this.switchTab(privateServerTab);
        MultiplayerActivity.LAN_SERVER_DETECTOR.reset();
        this.lanServerListActivity.setServerRemoveCallback(() -> Laby.labyAPI().minecraft().executeOnRenderThread(this::refreshLanTab));
    }
    
    @Override
    public boolean charTyped(final Key key, final char character) {
        if (!this.searchField.isFocused()) {
            this.searchField.setFocused(true);
            this.searchField.setVisible(true);
        }
        final boolean handled = super.charTyped(key, character);
        if (this.searchField.isVisible() && this.searchField.getText().trim().isEmpty()) {
            this.searchField.setFocused(false);
            this.searchField.setVisible(false);
            this.searchField.setText("");
        }
        return handled;
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        final boolean handled = super.keyPressed(key, type);
        if (this.searchField.isVisible() && this.searchField.getText().trim().isEmpty()) {
            this.searchField.setFocused(false);
            this.searchField.setVisible(false);
            this.searchField.setText("");
        }
        if (!this.searchField.isFocused() && key == Key.F && KeyHandler.isControlDown()) {
            this.searchField.setFocused(true);
            this.searchField.setVisible(true);
        }
        return handled;
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        final boolean consumed = super.mouseClicked(mouse, mouseButton);
        if (!this.searchField.isFocused() && this.searchField.isVisible() && this.searchField.getText().trim().isEmpty()) {
            this.searchField.setFocused(false);
            this.searchField.setVisible(false);
            this.searchField.setText("");
        }
        return consumed;
    }
    
    public void cacheServerList() {
        final ServerList serverList = this.labyAPI.serverController().serverList();
        final ServerAddressResolver serverAddressResolver = Laby.references().serverAddressResolver();
        Task.builder(() -> {
            for (int i = 0; i < serverList.size() && i != 50; ++i) {
                final ConnectableServerData serverData = serverList.get(i);
                serverAddressResolver.register(serverData.address());
            }
        }).build().execute();
    }
    
    @Override
    public Object getPreviousScreen() {
        return null;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.refreshLanTab();
    }
    
    @Override
    public void onOpenScreen() {
        MultiplayerActivity.LAN_SERVER_DETECTOR.startDetectionTask(new LanServerCallback() {
            @Override
            public void onServerAdd(final ConnectableServerData data) {
                MultiplayerActivity.this.lanServerListActivity.addLanServer(data, () -> {
                    final TabWidget lanTabWidget = MultiplayerActivity.this.getTabWidget(MultiplayerActivity.this.lanTab);
                    if (lanTabWidget != null && !lanTabWidget.isVisible()) {
                        MultiplayerActivity.this.refreshLanTab();
                    }
                });
            }
            
            @Override
            public void onServerRemove(final ConnectableServerData data) {
                MultiplayerActivity.this.lanServerListActivity.removeLanServer(data);
                MultiplayerActivity.this.labyAPI.minecraft().executeOnRenderThread(MultiplayerActivity.this::refreshLanTab);
            }
        });
        super.onOpenScreen();
    }
    
    @Override
    public void onCloseScreen() {
        MultiplayerActivity.LAN_SERVER_DETECTOR.terminateDetectionTask();
        super.onCloseScreen();
    }
    
    @Override
    public void tick() {
        if (this.getActiveTab() != this.lanTab) {
            this.lanServerListActivity.tick();
        }
        super.tick();
    }
    
    private void refreshLanTab() {
        final boolean empty = this.lanServerListActivity.getLanServers().isEmpty();
        this.lanTab.setHidden(empty);
        final TabWidget lanTabWidget = this.getTabWidget(this.lanTab);
        if (empty) {
            if (lanTabWidget != null) {
                lanTabWidget.setVisible(false);
            }
            if (this.getActiveTab() == this.lanTab) {
                this.switchTab("private");
            }
        }
        else if (lanTabWidget != null) {
            lanTabWidget.setVisible(true);
        }
    }
    
    static {
        LAN_SERVER_DETECTOR = Laby.references().lanServerDetector();
        INSTANCE = new MultiplayerActivity();
    }
    
    public static class ServerInfoWidgetEqualizer<T extends ServerEntryWidget> implements Equalizer<T>
    {
        @Override
        public boolean equals(final T a, final T b) {
            if (a instanceof ServerInfoWidget && b instanceof ServerInfoWidget) {
                return a.getListIndex() == b.getListIndex();
            }
            return a.equals(b);
        }
    }
}
