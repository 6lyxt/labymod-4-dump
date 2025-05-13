// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.chat;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.activity.activities.ingame.chat.ChatAccessor;
import java.util.List;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.ChatOverlay;
import net.labymod.api.configuration.labymod.chat.config.ChatWindowConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import java.awt.Color;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.util.bounds.Rectangle;
import java.util.Iterator;
import net.labymod.core.client.gui.screen.widget.widgets.chat.tab.ChatTabWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.HorizontalListEntry;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.activity.activities.labymod.child.SettingContentActivity;
import net.labymod.api.configuration.settings.type.AbstractSettingRegistry;
import net.labymod.api.configuration.settings.type.RootSettingRegistry;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.ChatSettingActivity;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.configuration.labymod.chat.ChatWindow;
import net.labymod.api.configuration.labymod.main.laby.ingame.AdvancedChatConfig;
import net.labymod.api.util.Lazy;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.activity.activities.ingame.chat.WindowAccessor;
import net.labymod.core.client.gui.screen.widget.widgets.NewWindowWidget;

@AutoWidget
public class ChatWindowWidget extends NewWindowWidget implements WindowAccessor
{
    private static final Lazy<AdvancedChatConfig> SETTINGS;
    private final ChatWindowRendererWidget renderer;
    private final ChatWindow window;
    private AbstractWidget<?> contextWidget;
    
    public ChatWindowWidget(final ChatWindowRendererWidget renderer, final ChatWindow window) {
        super(DraggingType.CONTENT, window.isMainWindow() ? RescaleType.TOP_RIGHT_BOTTOM_EDGES : RescaleType.EDGES, BoundsType.OUTER, MouseButton.LEFT, MouseButton.LEFT);
        this.renderer = renderer;
        this.window = window;
        this.setInterpolateDuration(50.0f);
        this.displayActiveTab();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final Widget widget = this.titleBarWidget();
        if (widget != null) {
            ((AbstractWidget)widget).opacity().set(this.renderer.chatOverlay().isChatOpen() ? 1.0f : 0.0f);
            widget.setVisible(this.hasTitleBar());
        }
    }
    
    public void displayActiveTab() {
        final ChatTab activeTab = this.window.getActiveTab();
        this.displayTab(activeTab);
    }
    
    public void closeOverlays() {
        if (this.contextWidget != null) {
            this.contextWidget.closeContextMenu();
        }
    }
    
    public void displayTab(final ChatTab tab) {
        tab.resetUnread();
        if (this.window.getActiveTab() != tab) {
            this.window.switchToTab(tab);
        }
        final Widget widget = tab.createContentWidget(this);
        this.displayContent(widget);
        this.applyDefaultContextMenu(this.createContextMenu(), tab);
    }
    
    public void displayTabSettings(final ChatTab tab) {
        final RootChatTabConfig focusedTab = tab.rootConfig();
        final ScreenRendererWidget renderer = new ScreenRendererWidget();
        final RootSettingRegistry registry;
        final AbstractSettingRegistry settingRegistry = registry = focusedTab.config().asRegistry("chattab");
        final ChatWindowRendererWidget renderer2 = this.renderer;
        Objects.requireNonNull(renderer2);
        final SettingContentActivity activity = new ChatSettingActivity(registry, renderer2::save);
        activity.closeHandler(this::displayActiveTab);
        renderer.displayScreen(activity);
        this.displayContent(renderer);
        this.applySettingsContextMenu(this.createContextMenu());
    }
    
    @Override
    public void createNewTab() {
        final RootChatTabConfig chatTabConfig = this.renderer.createNewTab(this.window.getTabs().size());
        final ChatTab chatTab = this.window.initializeTab(chatTabConfig);
        this.window.switchToTab(chatTab);
        this.displayTabSettings(chatTab);
    }
    
    public void applyDefaultContextMenu(final ContextMenu contextMenu, final ChatTab tab) {
        final RootChatTabConfig focusedTab = tab.rootConfig();
        final boolean isMainTab = focusedTab.type().get() == RootChatTabConfig.Type.SERVER;
        contextMenu.addEntry(ContextMenuEntry.builder().icon(Textures.SpriteCommon.ADD).text(Component.translatable("labymod.activity.chat.context.newTab", new Component[0])).clickHandler((mouse, entry) -> {
            this.createNewTab();
            this.window.save();
            return;
        }).build());
        contextMenu.addEntry(ContextMenuEntry.builder().icon(Textures.SpriteCommon.ADD).text(Component.translatable("labymod.activity.chat.context.newWindow", new Component[0])).clickHandler((mouse, entry) -> this.renderer.createNewWindow()).build());
        contextMenu.addEntry(ContextMenuEntry.builder().icon(Textures.SpriteCommon.SETTINGS).text(Component.translatable("labymod.ui.button.settings", new Component[0])).clickHandler((mouse, entry) -> {
            final ChatTab activeTab = this.window.getActiveTab();
            this.displayTabSettings(activeTab);
            return;
        }).build());
        if (!isMainTab) {
            contextMenu.addEntry(ContextMenuEntry.builder().icon(Textures.SpriteCommon.X).text(Component.translatable("labymod.ui.button.delete", new Component[0])).clickHandler((mouse, entry) -> this.deleteTab(tab)).build());
        }
    }
    
    private void deleteTab(final ChatTab tab) {
        final PopupWidget popup = PopupWidget.builder().title(Component.translatable("labymod.activity.chat.delete.title", new Component[0])).text(Component.translatable("labymod.activity.chat.delete.text", new Component[0])).confirmCallback(() -> {
            this.window.deleteTab(tab);
            if (this.window.getTabs().isEmpty()) {
                this.renderer.deleteWindow(this.window);
                this.renderer.save();
            }
            else {
                this.window.save();
                this.displayActiveTab();
            }
            return;
        }).build();
        popup.displayInOverlay();
    }
    
    @Override
    protected void renderWidgetTheme(final ScreenContext context) {
        if (this.isDragging() || this.isRescaling()) {
            final Bounds bounds = this.bounds();
            this.labyAPI.renderPipeline().rectangleRenderer().pos(bounds.getLeft(), bounds.getTop(), bounds.getRight(), bounds.getBottom()).color(ColorFormat.ARGB32.pack(255, 255, 255, 30)).render(context.stack());
        }
        if (!this.renderer.chatOverlay().isChatOpen()) {
            this.renderUnreadTabs(context);
        }
        else {
            this.updateTitleBar(false);
        }
        super.renderWidgetTheme(context);
    }
    
    private void renderUnreadTabs(final ScreenContext context) {
        this.updateTitleBar(true);
        final Widget widget = this.titleBarWidget();
        if (widget != null && this.hasUnread()) {
            final Stack stack = context.stack();
            stack.push();
            stack.translate(0.0f, -widget.bounds().getY() + this.bounds().getBottom(), 0.0f);
            widget.renderWidget(context);
            stack.pop();
        }
    }
    
    private void updateTitleBar(final boolean hide) {
        final HorizontalListWidget widget = (HorizontalListWidget)this.titleBarWidget();
        if (widget == null) {
            return;
        }
        final ChatTab activeTab = this.window.getActiveTab();
        activeTab.resetUnread();
        for (final HorizontalListEntry entry : widget.getChildren()) {
            final Widget child = entry.childWidget();
            if (child instanceof final ChatTabWidget tabWidget) {
                final ChatTab tab = tabWidget.tab();
                final int unread = tab.getUnread();
                tabWidget.updateUnread(unread);
                entry.setVisible(!hide || unread != 0);
            }
            else {
                if (!hide) {
                    continue;
                }
                entry.setVisible(false);
            }
        }
    }
    
    @Override
    protected float getEdge(final Edge edge, final Edge side) {
        if ((edge == Edge.LEFT && side == Edge.TOP) || (edge == Edge.RIGHT && side == Edge.TOP) || (edge == Edge.TOP && (side == Edge.TOP || side == Edge.BOTTOM))) {
            final Rectangle rectangle = this.bounds().rectangle(this.boundsType);
            final Widget titleBarWidget = this.titleBarWidget();
            final boolean hasTitleBar = titleBarWidget != null && this.hasTitleBar();
            final float titleBarHeight = hasTitleBar ? titleBarWidget.getContentHeight(BoundsType.OUTER) : 0.0f;
            final Widget contentWidget = this.contentWidget();
            float contentHeight = contentWidget.getContentHeight(BoundsType.OUTER);
            if (contentHeight == 0.0f) {
                contentHeight = contentWidget.bounds().getHeight();
            }
            return (hasTitleBar || this.isRescaling()) ? (rectangle.getTop() + titleBarHeight) : Math.max(rectangle.getBottom() - contentHeight, rectangle.getTop() + titleBarHeight);
        }
        return super.getEdge(edge, side);
    }
    
    @Override
    protected boolean hasEdgeOffset() {
        final Widget titleBarWidget = this.titleBarWidget();
        return titleBarWidget != null && this.hasTitleBar();
    }
    
    @Override
    protected void renderEdge(final Stack stack, final MutableMouse mouse, final Edge edge) {
        if (this.isRescaling()) {
            float left = this.getEdge(edge, Edge.LEFT);
            float top = this.getEdge(edge, Edge.TOP);
            float right = this.getEdge(edge, Edge.RIGHT);
            float bottom = this.getEdge(edge, Edge.BOTTOM);
            switch (edge) {
                case LEFT: {
                    --left;
                    break;
                }
                case TOP: {
                    --top;
                    break;
                }
                case RIGHT: {
                    ++right;
                    break;
                }
                case BOTTOM: {
                    ++bottom;
                    break;
                }
            }
            this.labyAPI.renderPipeline().rectangleRenderer().pos(left, top, right, bottom).color(Color.WHITE).render(stack);
        }
        super.renderEdge(stack, mouse, edge);
    }
    
    @Override
    protected void dragToPosition(float x, final float y, final Bounds bounds) {
        if (this.window.isMainWindow()) {
            x = bounds.getX();
        }
        super.dragToPosition(x, y, bounds);
    }
    
    public boolean isDraggingEnabled() {
        return ChatWindowWidget.SETTINGS.get().draggable().get();
    }
    
    public boolean isRescalingEnabled() {
        return ChatWindowWidget.SETTINGS.get().resizeable().get() && !this.renderer.isHoveringChatInputTab();
    }
    
    @Override
    protected void onWindowPositionChanged() {
        this.window.config().setPosition(this.renderer.borderBounds(), this.bounds());
        super.onWindowPositionChanged();
    }
    
    @Override
    protected Widget createTitleBar() {
        final HorizontalListWidget bar = new HorizontalListWidget();
        for (final ChatTab tab : this.window.getTabs()) {
            bar.addEntry(new ChatTabWidget(this, tab));
        }
        if (ChatWindowWidget.SETTINGS.get().showMenuButton().get()) {
            final ButtonWidget contextWidget = ButtonWidget.icon(Textures.SpriteCommon.SMALL_BURGER);
            ((AbstractWidget<Widget>)contextWidget).addId("context");
            this.applyDefaultContextMenu(contextWidget.createContextMenu(), this.window.getActiveTab());
            final ButtonWidget buttonWidget = contextWidget;
            final ButtonWidget obj = contextWidget;
            Objects.requireNonNull(obj);
            buttonWidget.setPressable(obj::openContextMenu);
            bar.addEntry(contextWidget);
            this.contextWidget = contextWidget;
        }
        return bar;
    }
    
    private void displayContent(final Widget widget) {
        this.setContentWidget(widget);
        if (this.initialized) {
            this.labyAPI.minecraft().executeNextTick(this::reInitialize);
        }
    }
    
    public ChatWindowConfig getConfig() {
        return this.window.config();
    }
    
    public ChatWindow window() {
        return this.window;
    }
    
    @Override
    public ChatOverlay chat() {
        return this.renderer.chatOverlay();
    }
    
    @Override
    public boolean hasTitleBar() {
        final List<ChatTab> tabs = this.window.getTabs();
        final int amount = tabs.size();
        return amount != 0 && (amount > 1 || tabs.get(0).rootConfig().type().get() != RootChatTabConfig.Type.SERVER);
    }
    
    private boolean hasUnread() {
        for (final ChatTab tab : this.window.getTabs()) {
            if (tab.getUnread() != 0) {
                return true;
            }
        }
        return false;
    }
    
    private void applySettingsContextMenu(final ContextMenu contextMenu) {
        contextMenu.addEntry(ContextMenuEntry.builder().icon(Textures.SpriteCommon.X).text(Component.translatable("labymod.ui.button.close", new Component[0])).clickHandler((mouse, entry) -> this.displayActiveTab()).build());
    }
    
    public void invalidateCache() {
        for (final ChatTab tab : this.window.getTabs()) {
            tab.invalidateCache();
        }
    }
    
    static {
        SETTINGS = Lazy.of(() -> Laby.labyAPI().config().ingame().advancedChat());
    }
}
