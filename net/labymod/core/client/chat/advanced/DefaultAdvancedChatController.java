// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat.advanced;

import net.labymod.api.configuration.labymod.chat.AdvancedChatMessage;
import net.labymod.api.event.client.chat.advanced.AdvancedChatReceiveEvent;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.chat.advanced.IngameChatTab;
import java.util.function.Consumer;
import java.util.List;
import java.util.Comparator;
import net.labymod.api.event.client.chat.advanced.AdvancedChatReloadEvent;
import net.labymod.api.event.labymod.config.SettingResetEvent;
import net.labymod.api.event.client.lifecycle.GameShutdownEvent;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.client.chat.ChatClearEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.chat.ChatMessageUpdateEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.event.client.chat.ChatMessageAddEvent;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import java.util.Iterator;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.configuration.labymod.chat.ChatConfigAccessor;
import net.labymod.api.client.gui.VerticalAlignment;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.configuration.labymod.chat.config.ChatWindowConfig;
import net.labymod.api.configuration.labymod.chat.category.GeneralChatTabConfig;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import java.util.function.Supplier;
import net.labymod.core.configuration.labymod.chat.DefaultChatConfig;
import javax.inject.Inject;
import java.util.HashSet;
import net.labymod.api.event.EventBus;
import net.labymod.api.configuration.labymod.chat.ChatWindow;
import java.util.Set;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.chat.advanced.AdvancedChatController;

@Singleton
@Implements(AdvancedChatController.class)
public class DefaultAdvancedChatController implements AdvancedChatController
{
    private static final String CHAT_MESSAGE_KEY = "Minecraft-ChatMessage";
    private final Set<ChatWindow> windows;
    
    @Inject
    public DefaultAdvancedChatController(final EventBus eventBus) {
        this.windows = new HashSet<ChatWindow>();
        this.loadConfiguration();
        eventBus.registerListener(new ChatDuplicateMessageHandler());
    }
    
    @Override
    public Set<ChatWindow> getWindows() {
        return this.windows;
    }
    
    @Override
    public void saveConfig() {
        DefaultChatConfig.ChatConfigProvider.INSTANCE.save();
    }
    
    @Override
    public boolean hasSecondaryWindow() {
        return this.windows.size() > 1;
    }
    
    @NotNull
    @Override
    public ChatWindow getOrCreateSecondaryWindow(@Nullable final Supplier<RootChatTabConfig> defaultTabSupplier) {
        ChatWindow secondaryWindow = null;
        for (final ChatWindow window : this.windows) {
            if (window.isMainWindow()) {
                continue;
            }
            secondaryWindow = window;
        }
        if (secondaryWindow != null) {
            return secondaryWindow;
        }
        RootChatTabConfig defaultTab = (defaultTabSupplier == null) ? null : defaultTabSupplier.get();
        if (defaultTab == null) {
            defaultTab = new RootChatTabConfig(0, RootChatTabConfig.Type.CUSTOM, new GeneralChatTabConfig("Second chat"));
        }
        final ChatWindowConfig config = new ChatWindowConfig(new RootChatTabConfig[] { defaultTab });
        config.setPosition(0.21f, 2.15f, HorizontalAlignment.RIGHT, VerticalAlignment.BOTTOM);
        DefaultChatConfig.ChatConfigProvider.INSTANCE.get().getWindows().add(config);
        this.saveConfig();
        this.reload();
        for (final ChatWindow window2 : this.windows) {
            for (final ChatTab tab : window2.getTabs()) {
                if (tab.rootConfig().getUniqueID().equals(defaultTab.getUniqueID())) {
                    return window2;
                }
            }
        }
        throw new NullPointerException("Could not find the newly created secondary window");
    }
    
    public void addWindow(final ChatWindow chatWindow) {
        this.windows.add(chatWindow);
        final ChatConfigAccessor chatConfigAccessor = Laby.labyAPI().chatProvider().chatConfigAccessor();
        chatConfigAccessor.getWindows().add(chatWindow.config());
        this.saveConfig();
    }
    
    public void deleteWindow(final ChatWindow chatWindow) {
        this.windows.remove(chatWindow);
        final ChatConfigAccessor chatConfigAccessor = Laby.labyAPI().chatProvider().chatConfigAccessor();
        chatConfigAccessor.getWindows().remove(chatWindow.config());
        this.saveConfig();
    }
    
    @Subscribe
    public void handleChatMessageReceive(final ChatMessageAddEvent event) {
        if (ThreadSafe.isRenderThread()) {
            this.fireAdvancedChatReceiveEvent(event.chatMessage());
        }
        else {
            ThreadSafe.executeOnRenderThread(() -> this.fireAdvancedChatReceiveEvent(event.chatMessage()));
        }
    }
    
    @Subscribe(Byte.MAX_VALUE)
    public void handleChatUpdate(final ChatMessageUpdateEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        this.ingameChatTab(tab -> {
            if (event.newComponent() == null) {
                tab.handleMessageDelete(event.message());
            }
            else {
                tab.handleMessageUpdate(event.message());
            }
        });
    }
    
    @Subscribe(Byte.MAX_VALUE)
    public void handleChatClear(final ChatClearEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final boolean force = Laby.labyAPI().minecraft().isMouseLocked() && Key.F3.isPressed() && Key.D.isPressed();
        this.ingameChatTab(tab -> {
            if (force || !tab.config().antiChatClear().get()) {
                tab.getMessages().clear();
            }
        });
    }
    
    @Subscribe
    public void handleGameShutdown(final GameShutdownEvent event) {
        this.saveConfig();
    }
    
    @Subscribe
    public void handleChatConfigReset(final SettingResetEvent event) {
        if (event.setting().getPath().equals("settings.ingame.advancedChat.enabled")) {
            Laby.labyAPI().chatProvider().chatConfigAccessor().getWindows().clear();
            this.reload();
        }
    }
    
    @Override
    public void reload() {
        this.windows.clear();
        this.loadConfiguration();
        Laby.references().chatAccessor().reload();
        Laby.fireEvent(new AdvancedChatReloadEvent());
    }
    
    private void loadConfiguration() {
        final List<ChatWindowConfig> windowConfigs = Laby.labyAPI().chatProvider().chatConfigAccessor().getWindows();
        boolean foundServerTab = false;
        this.removeEmptyWindows(windowConfigs);
        for (final ChatWindowConfig windowConfig : windowConfigs) {
            boolean foundWindow = false;
            for (final ChatWindow chatWindowWidget : this.windows) {
                if (chatWindowWidget.config() != windowConfig) {
                    continue;
                }
                if (!foundServerTab) {
                    for (final ChatTab tab : chatWindowWidget.getTabs()) {
                        if (tab.rootConfig().type().get() == RootChatTabConfig.Type.SERVER) {
                            foundServerTab = true;
                            break;
                        }
                    }
                }
                foundWindow = true;
                break;
            }
            if (foundWindow) {
                continue;
            }
            windowConfig.checkForFocusedTab();
            final DefaultChatWindow chatWindowWidget2 = new DefaultChatWindow(windowConfig);
            this.windows.add(chatWindowWidget2);
            windowConfig.getTabs().sort(Comparator.comparingInt(config -> config.index().get()));
            for (final RootChatTabConfig tabConfig : windowConfig.getTabs()) {
                if (tabConfig.type().get() == RootChatTabConfig.Type.SERVER) {
                    if (foundServerTab) {
                        tabConfig.type().set(RootChatTabConfig.Type.CUSTOM);
                    }
                    else {
                        foundServerTab = true;
                    }
                }
                boolean foundTab = false;
                for (final ChatTab tab2 : chatWindowWidget2.getTabs()) {
                    if (tab2.rootConfig() != tabConfig) {
                        continue;
                    }
                    foundTab = true;
                    break;
                }
                if (foundTab) {
                    continue;
                }
                chatWindowWidget2.initializeTab(tabConfig, null, false);
            }
        }
        if (windowConfigs.isEmpty()) {
            windowConfigs.add(new ChatWindowConfig());
            this.loadConfiguration();
        }
    }
    
    private void removeEmptyWindows(final List<ChatWindowConfig> windowConfigs) {
        this.windows.removeIf(window -> {
            if (window.getTabs().isEmpty()) {
                windowConfigs.remove(window.config());
                return true;
            }
            else {
                return false;
            }
        });
        windowConfigs.removeIf(config -> config.getTabs().isEmpty());
    }
    
    private void ingameChatTab(final Consumer<IngameChatTab> tab) {
        for (final ChatWindow window : this.windows) {
            for (final ChatTab chatTab : window.getTabs()) {
                if (!(chatTab instanceof IngameChatTab)) {
                    continue;
                }
                tab.accept((IngameChatTab)chatTab);
            }
        }
    }
    
    private void fireAdvancedChatReceiveEvent(@NotNull final ChatMessage vanillaChatMessage) {
        final AdvancedChatReceiveEvent receiveEvent = new AdvancedChatReceiveEvent(vanillaChatMessage);
        Laby.fireEvent(receiveEvent);
        if (receiveEvent.isCancelled()) {
            return;
        }
        final ChatMessage chatMessage = receiveEvent.chatMessage();
        this.ingameChatTab(tab -> {
            if (!tab.config().antiChatClear().get() || !chatMessage.getPlainText().trim().isEmpty() || chatMessage.containsIcon()) {
                final AdvancedChatMessage chat = AdvancedChatMessage.chat(chatMessage);
                chat.metadata().set("Minecraft-ChatMessage", chatMessage);
                tab.handleInput(chat);
            }
        });
    }
}
