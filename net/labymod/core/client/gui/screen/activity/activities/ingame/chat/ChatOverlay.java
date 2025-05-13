// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.ingame.chat;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.event.client.chat.advanced.AdvancedChatReloadEvent;
import net.labymod.api.event.client.render.overlay.IngameOverlayElementRenderEvent;
import net.labymod.api.event.client.gui.screen.theme.ThemeUpdateEvent;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.event.Subscribe;
import java.util.Iterator;
import net.labymod.core.client.gui.screen.widget.widgets.chat.ChatWindowWidget;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.core.client.gui.screen.widget.widgets.chat.ChatWindowRendererWidget;
import net.labymod.api.client.chat.ChatProvider;
import net.labymod.api.configuration.labymod.main.laby.ingame.AdvancedChatConfig;
import net.labymod.api.util.Lazy;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.activity.activities.ingame.chat.ChatAccessor;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;

@Singleton
@Implements(ChatAccessor.class)
@Link("activity/overlay/chat/chat.lss")
@AutoActivity
public class ChatOverlay extends IngameOverlayActivity implements ChatAccessor
{
    private static final Lazy<AdvancedChatConfig> SETTINGS;
    private final ChatProvider provider;
    private ChatWindowRendererWidget renderer;
    private DivWidget borderWidget;
    private float mouseX;
    private float mouseY;
    private boolean open;
    private boolean peekOpen;
    
    public ChatOverlay() {
        this.provider = Laby.references().chatProvider();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        ((AbstractWidget<Widget>)(this.renderer = new ChatWindowRendererWidget(this))).addId("renderer");
        ((AbstractWidget<ChatWindowRendererWidget>)this.document).addChild(this.renderer);
        this.borderWidget = new DivWidget().addId("border");
        ((AbstractWidget<DivWidget>)this.document).addChild(this.borderWidget);
    }
    
    @Subscribe
    public void onChatPeek(final KeyEvent event) {
        if (this.labyAPI.minecraft().minecraftWindow().isScreenOpened()) {
            return;
        }
        if (!ChatOverlay.SETTINGS.get().enabled().get()) {
            return;
        }
        final Key chatPeekKey = ChatOverlay.SETTINGS.get().chatPeekKey().get();
        if (chatPeekKey == Key.NONE || event.key() != chatPeekKey) {
            return;
        }
        this.open = (event.state() == KeyEvent.State.PRESS || event.state() == KeyEvent.State.HOLDING);
        this.peekOpen = this.open;
        if (event.state() != KeyEvent.State.HOLDING) {
            for (final ChatWindowWidget window : this.renderer.getChildren()) {
                window.displayActiveTab();
            }
        }
    }
    
    @Subscribe
    public void onScreenOpen(final ScreenDisplayEvent event) {
        if (!this.isVisible()) {
            return;
        }
        final boolean nowOpen = NamedScreen.CHAT_INPUT.isScreen(event.getScreen()) || NamedScreen.CHAT_INPUT_IN_BED.isScreen(event.getScreen());
        if (this.open && !nowOpen) {
            final Mouse mouse = this.labyAPI.minecraft().absoluteMouse();
            this.mouseX = (float)mouse.getX();
            this.mouseY = (float)mouse.getY();
            this.open = false;
            for (final ChatWindowWidget window : this.renderer.getChildren()) {
                window.displayActiveTab();
                window.closeOverlays();
            }
        }
        else if (nowOpen) {
            this.open = true;
            for (final ChatWindowWidget window2 : this.renderer.getChildren()) {
                window2.displayActiveTab();
            }
        }
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        return false;
    }
    
    public boolean superMouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        return super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        return false;
    }
    
    public boolean superMouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        return super.mouseScrolled(mouse, scrollDelta);
    }
    
    @Subscribe
    public void onThemeUpdate(final ThemeUpdateEvent event) {
        this.renderer.invalidateCache();
    }
    
    @Subscribe
    public void onCrosshairRender(final IngameOverlayElementRenderEvent event) {
        if (event.elementType() != IngameOverlayElementRenderEvent.OverlayElementType.CROSSHAIR) {
            return;
        }
        if (!ChatOverlay.SETTINGS.get().disableCrosshair().get() || !this.isVisible() || !this.isChatOpen()) {
            return;
        }
        event.setCancelled(true);
    }
    
    @Override
    public boolean isAcceptingInput() {
        return this.isChatOpen() && this.labyAPI.minecraft().minecraftWindow().isScreenOpened();
    }
    
    @Override
    public boolean isVisible() {
        return this.labyAPI.config().ingame().advancedChat().enabled().get() && (!this.labyAPI.minecraft().options().isHideGUI() || this.labyAPI.config().ingame().advancedChat().showChatOnHiddenGui().get());
    }
    
    @Override
    public boolean isRenderedHidden() {
        return this.peekOpen;
    }
    
    @Override
    public boolean isChatOpen() {
        return this.open;
    }
    
    @Override
    public ChatProvider getProvider() {
        return this.provider;
    }
    
    @Override
    public int getPriority() {
        return 75;
    }
    
    public DivWidget getBorderWidget() {
        return this.borderWidget;
    }
    
    @Subscribe
    public void onAdvancedChatReload(final AdvancedChatReloadEvent event) {
        if (this.renderer == null || !this.renderer.isVisible()) {
            return;
        }
        this.renderer.reInitialize();
    }
    
    static {
        SETTINGS = Lazy.of(() -> Laby.labyAPI().config().ingame().advancedChat());
    }
}
