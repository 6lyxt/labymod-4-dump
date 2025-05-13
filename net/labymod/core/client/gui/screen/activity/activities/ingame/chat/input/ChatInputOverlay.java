// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.ChatOverlay;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.core.client.chat.gui.DefaultChatInputRegistry;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.core.configuration.labymod.chat.DefaultChatConfig;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.chat.ChatButtonWidget;
import net.labymod.api.util.KeyValue;
import java.util.List;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity;

@Link("activity/chat/input/chat-input.lss")
@AutoActivity
public class ChatInputOverlay extends AbstractLayerActivity
{
    private final List<KeyValue<ChatButtonWidget>> elements;
    private boolean lssLoaded;
    private ScreenRendererWidget subActivity;
    private DivWidget inputUnderlay;
    
    public ChatInputOverlay(final ScreenInstance parentScreen) {
        super(parentScreen);
        this.renderMode = ParentMode.UNDERLAY;
        this.elements = this.labyAPI.chatProvider().chatInputService().getElements();
        for (final KeyValue<ChatButtonWidget> entry : this.elements) {
            final ChatButtonWidget widget = entry.getValue();
            if (!widget.isEnabled()) {
                continue;
            }
            widget.setPressable(() -> this.switchTabTo(entry.getKey()));
        }
        final Bounds lastBounds = this.elements.get(0).getValue().bounds();
        this.lssLoaded = (lastBounds.getWidth() > 0.0f);
    }
    
    private void switchTabTo(final String id) {
        final KeyValue<ChatButtonWidget> previousTab = this.getActiveTab();
        if (previousTab != null) {
            previousTab.getValue().setActive(false);
        }
        ChatButtonWidget activeTab = null;
        if (id != null && (previousTab == null || !previousTab.getKey().equals(id))) {
            activeTab = this.getTabById(id).getValue();
            activeTab.setActive(true);
        }
        if (activeTab == null) {
            this.subActivity.opacity().set(0.0f);
        }
        else {
            this.subActivity.opacity().set(1.0f);
            this.subActivity.displayScreen(activeTab.createScreen());
        }
    }
    
    @Override
    public void initialize(final Parent parent) {
        for (final KeyValue<ChatButtonWidget> value : this.elements) {
            value.getValue().reset();
        }
        super.initialize(parent);
        final FlexibleContentWidget screen = new FlexibleContentWidget().addId("screen");
        (this.subActivity = new ScreenRendererWidget()).setPreviousScreenHandler(ignored -> true);
        this.subActivity.addId("chat-input-tab-renderer");
        screen.addFlexibleContent(this.subActivity);
        final FlexibleContentWidget flexibleContentWidget;
        final FlexibleContentWidget container = flexibleContentWidget = new FlexibleContentWidget().addId("container");
        final DivWidget divWidget = new DivWidget();
        this.inputUnderlay = divWidget;
        flexibleContentWidget.addFlexibleContent(divWidget).addId("chat-input");
        final HorizontalListWidget list = new HorizontalListWidget();
        ((AbstractWidget<Widget>)list).addId("chat-input-list");
        for (final KeyValue<ChatButtonWidget> button : this.elements) {
            final ChatButtonWidget value2 = button.getValue();
            if (value2.getProperty() != null && !value2.getProperty().get()) {
                continue;
            }
            list.addEntry(value2);
        }
        container.addContent(list);
        final KeyValue<ChatButtonWidget> activeEntry = this.getActiveTab();
        if (activeEntry != null) {
            final ChatButtonWidget activeWidget = activeEntry.getValue();
            this.subActivity.displayScreen(activeWidget.createScreen());
        }
        screen.addContent(container);
        ((AbstractWidget<FlexibleContentWidget>)this.document).addChild(screen);
        super.setPreviousScreen(null);
    }
    
    @Override
    public void onCloseScreen() {
        super.onCloseScreen();
        this.switchTabTo(null);
        DefaultChatConfig.ChatConfigProvider.INSTANCE.save();
    }
    
    protected KeyValue<ChatButtonWidget> getTabById(final String id) {
        for (final KeyValue<ChatButtonWidget> element : this.elements) {
            if (element.getKey().equals(id)) {
                return element;
            }
        }
        return null;
    }
    
    protected KeyValue<ChatButtonWidget> getActiveTab() {
        for (final KeyValue<ChatButtonWidget> element : this.elements) {
            final ChatButtonWidget value = element.getValue();
            if (!value.isActive()) {
                continue;
            }
            final ConfigProperty<Boolean> property = value.getProperty();
            if (property == null || property.get()) {
                return element;
            }
            this.subActivity.displayScreen((ScreenInstance)null);
            value.setActive(false);
        }
        return null;
    }
    
    @Override
    protected void postInitialize() {
        super.postInitialize();
        if (!this.lssLoaded) {
            this.lssLoaded = true;
            final DefaultChatInputRegistry chatInputRegistry = (DefaultChatInputRegistry)this.labyAPI.chatProvider().chatInputService();
            if (chatInputRegistry.getChatWidthRunnable() != null) {
                chatInputRegistry.getChatWidthRunnable().run();
            }
        }
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        final boolean consumed = super.mouseClicked(mouse, mouseButton);
        return consumed || ((ChatOverlay)Laby.references().chatAccessor()).superMouseClicked(mouse, mouseButton);
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        final boolean consumed = super.mouseScrolled(mouse, scrollDelta);
        return consumed || ((ChatOverlay)Laby.references().chatAccessor()).superMouseScrolled(mouse, scrollDelta);
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }
    
    @Subscribe
    public void onGameTick(final GameTickEvent event) {
        if (!NamedScreen.CHAT_INPUT_IN_BED.isScreen(this)) {
            return;
        }
        if (this.labyAPI.minecraft().getClientPlayer().isSleeping()) {
            return;
        }
        final ChatExecutor chatExecutor = this.labyAPI.minecraft().chatExecutor();
        final String chatInputMessage = chatExecutor.getChatInputMessage();
        final Window window = this.labyAPI.minecraft().minecraftWindow();
        if (chatInputMessage == null || chatInputMessage.isEmpty()) {
            window.displayScreenRaw(null);
        }
        else {
            window.displayScreen(NamedScreen.CHAT_INPUT.create());
            chatExecutor.insertText(chatInputMessage);
        }
    }
    
    public DivWidget getInputUnderlay() {
        return this.inputUnderlay;
    }
    
    public ScreenRendererWidget getTabRenderer() {
        return this.subActivity;
    }
    
    @Override
    public boolean allowCustomFont() {
        return false;
    }
}
