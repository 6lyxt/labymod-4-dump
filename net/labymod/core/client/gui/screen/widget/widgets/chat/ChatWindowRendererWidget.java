// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.chat;

import net.labymod.core.main.LabyMod;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.activity.types.chatinput.ChatInputTabActivity;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.core.client.chat.advanced.DefaultChatWindow;
import net.labymod.api.configuration.labymod.chat.config.ChatWindowConfig;
import net.labymod.api.configuration.labymod.chat.category.GeneralChatTabConfig;
import net.labymod.api.util.I18n;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import java.util.Iterator;
import net.labymod.api.configuration.labymod.chat.ChatWindow;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.ChatOverlay;
import net.labymod.core.client.chat.advanced.DefaultAdvancedChatController;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class ChatWindowRendererWidget extends AbstractWidget<ChatWindowWidget>
{
    private static final ModifyReason INITIALIZE_WINDOWS;
    private static final DefaultAdvancedChatController CONTROLLER;
    private final ChatOverlay chatOverlay;
    
    public ChatWindowRendererWidget(final ChatOverlay chatOverlay) {
        this.chatOverlay = chatOverlay;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        for (final ChatWindow window : ChatWindowRendererWidget.CONTROLLER.getWindows()) {
            final ChatWindowWidget widget = new ChatWindowWidget(this, window);
            widget.addId("window");
            this.addChild(widget);
        }
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        if (this.isHoveringChatInputTab()) {
            return false;
        }
        ChatWindowWidget mainWindow = null;
        boolean handled = false;
        for (final ChatWindowWidget windowWidget : this.getGenericChildren()) {
            if (windowWidget.window().isMainWindow()) {
                mainWindow = windowWidget;
            }
            if (!windowWidget.isHovered()) {
                continue;
            }
            handled = true;
            windowWidget.mouseScrolled(mouse, scrollDelta);
        }
        if (!handled && mainWindow != null) {
            mainWindow.mouseScrolled(mouse, scrollDelta);
            handled = true;
        }
        return handled;
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        return !this.isHoveringChatInputTab() && super.mouseClicked(mouse, mouseButton);
    }
    
    public RootChatTabConfig createNewTab(final int index) {
        return new RootChatTabConfig(index, RootChatTabConfig.Type.CUSTOM, new GeneralChatTabConfig(I18n.translate("labymod.activity.chat.context.newTab", new Object[0])));
    }
    
    public void createNewWindow() {
        final RootChatTabConfig tabConfig = this.createNewTab(0);
        final ChatWindowConfig config = new ChatWindowConfig(new RootChatTabConfig[] { tabConfig });
        final ChatWindow window = new DefaultChatWindow(config);
        final ChatTab chatTab = window.initializeTab(tabConfig, null, false);
        ChatWindowRendererWidget.CONTROLLER.addWindow(window);
        final Bounds bounds = this.bounds();
        final float width = 200.0f;
        final float height = 80.0f;
        final float centerX = bounds.getCenterX();
        final float centerY = bounds.getCenterY();
        final ChatWindowWidget widget = new ChatWindowWidget(this, window);
        widget.addId("window");
        widget.bounds().setBounds(centerX - width / 2.0f, centerY - height / 2.0f, centerX + width / 2.0f, centerY + height / 2.0f, ChatWindowRendererWidget.INITIALIZE_WINDOWS);
        this.addChildInitialized(widget);
        widget.displayTabSettings(chatTab);
    }
    
    public void deleteWindow(final ChatWindow window) {
        ChatWindowRendererWidget.CONTROLLER.deleteWindow(window);
        this.removeChildIf(widget -> widget.getConfig() == window.config());
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        final Bounds bounds = this.borderBounds();
        for (final ChatWindowWidget widget : this.getGenericChildren()) {
            final ChatWindowConfig config = widget.getConfig();
            final MutableRectangle rectangle = config.getPosition(bounds);
            if (widget.window().isMainWindow()) {
                rectangle.setX(bounds.getX());
            }
            widget.bounds().set(rectangle, ChatWindowRendererWidget.INITIALIZE_WINDOWS);
        }
    }
    
    public void save() {
        ChatWindowRendererWidget.CONTROLLER.saveConfig();
    }
    
    public ChatOverlay chatOverlay() {
        return this.chatOverlay;
    }
    
    public boolean isHoveringChatInputTab() {
        final LabyScreen labyScreen = this.labyAPI.minecraft().minecraftWindow().currentLabyScreen();
        if (labyScreen instanceof final ChatInputOverlay chatInputOverlay) {
            final ScreenRendererWidget tabRenderer = chatInputOverlay.getTabRenderer();
            return tabRenderer != null && tabRenderer.getScreen() instanceof ChatInputTabActivity && ((ChatInputTabActivity)tabRenderer.getScreen()).isHovered();
        }
        return false;
    }
    
    public Bounds borderBounds() {
        final DivWidget borderWidget = this.chatOverlay.getBorderWidget();
        if (borderWidget == null) {
            return this.bounds();
        }
        return borderWidget.bounds();
    }
    
    public void invalidateCache() {
        for (final ChatWindowWidget windowWidget : this.children) {
            windowWidget.invalidateCache();
        }
    }
    
    static {
        INITIALIZE_WINDOWS = ModifyReason.of("initializeWindows");
        CONTROLLER = (DefaultAdvancedChatController)LabyMod.references().advancedChatController();
    }
}
