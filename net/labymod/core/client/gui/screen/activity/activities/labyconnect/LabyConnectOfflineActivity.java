// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labyconnect;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectRejectAuthenticationEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.util.TextFormat;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectDisconnectEvent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;

@Link("activity/labyconnect/laby-connect-offline.lss")
@AutoActivity
public class LabyConnectOfflineActivity extends SimpleActivity
{
    private final ComponentWidget title;
    private ButtonWidget buttonConnect;
    
    public LabyConnectOfflineActivity() {
        (this.title = ComponentWidget.empty()).addId("title");
    }
    
    @Override
    public boolean shouldRenderBackground() {
        return false;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final DivWidget background = new DivWidget();
        background.addId("background");
        final DivWidget div = new DivWidget();
        div.addId("offline-container");
        ((AbstractWidget<ComponentWidget>)div).addChild(this.title);
        ((AbstractWidget<Widget>)(this.buttonConnect = ButtonWidget.component(Component.empty()))).addId("button-connect");
        this.buttonConnect.setPressable(() -> {
            if (this.labyAPI.labyConnect().isConnectionEstablished()) {
                this.labyAPI.labyConnect().disconnect(LabyConnectDisconnectEvent.Initiator.USER, "Force quit");
            }
            else {
                this.labyAPI.labyConnect().connect();
            }
            return;
        });
        ((AbstractWidget<ButtonWidget>)div).addChild(this.buttonConnect);
        this.updateState(this.labyAPI.labyConnect().state());
        ((AbstractWidget<DivWidget>)background).addChild(div);
        fillContextMenu(background.createContextMenu());
        ((AbstractWidget<DivWidget>)this.document).addChild(background);
    }
    
    private void updateState(final LabyConnectState state) {
        final String stateId = TextFormat.SNAKE_CASE.toCamelCase(state.name(), true);
        final Component componentState = Component.translatable("labymod.activity.labyconnect.offline.state." + stateId, new Component[0]);
        final TextColor color = (state == LabyConnectState.OFFLINE) ? NamedTextColor.RED : ((state == LabyConnectState.HELLO) ? NamedTextColor.AQUA : NamedTextColor.YELLOW);
        final String lastDisconnectReason = this.labyAPI.labyConnect().getLastDisconnectReason();
        final LabyConnectSession session = this.labyAPI.labyConnect().getSession();
        final boolean isUnauthenticated = session != null && !session.isPremium() && session.isConnectionEstablished();
        if (isUnauthenticated) {
            this.title.setComponent(((BaseComponent<Component>)Component.translatable("labymod.activity.labyconnect.offline.unauthenticated", new Component[0])).color(NamedTextColor.YELLOW));
            this.buttonConnect.updateComponent(Component.translatable("labymod.activity.labyconnect.offline.buttons.disconnect", new Component[0]));
        }
        else {
            final Component reason = (lastDisconnectReason == null) ? Component.translatable("labymod.activity.labyconnect.offline.title", new Component[0]) : Component.translatable(lastDisconnectReason, new Component[0]).color(color);
            if (state == LabyConnectState.OFFLINE) {
                this.title.setComponent(reason);
                this.buttonConnect.updateComponent(Component.translatable("labymod.activity.labyconnect.offline.buttons.connect", new Component[0]));
            }
            else {
                this.title.setComponent(componentState.color(color));
                this.buttonConnect.updateComponent(Component.translatable("labymod.ui.button.cancel", new Component[0]));
            }
        }
    }
    
    @Subscribe
    public void onLabyConnectStateUpdate(final LabyConnectStateUpdateEvent event) {
        this.updateState(event.state());
    }
    
    @Subscribe
    public void onLabyConnectDisconnect(final LabyConnectDisconnectEvent event) {
        this.title.setComponent(Component.text(event.getReason()));
        this.title.reInitialize();
    }
    
    @Subscribe
    public void onLabyConnectRejectAuthentication(final LabyConnectRejectAuthenticationEvent event) {
        this.updateState(event.labyConnect().state());
    }
    
    public static void fillContextMenu(final ContextMenu contextMenu) {
        final LabyAPI labyAPI = Laby.labyAPI();
        if (labyAPI.labyConnect().state() != LabyConnectState.OFFLINE) {
            contextMenu.addEntry(ContextMenuEntry.builder().text(Component.text("Disconnect")).clickHandler((entry, source) -> labyAPI.labyConnect().disconnect(LabyConnectDisconnectEvent.Initiator.USER, "Force quit")).build());
        }
        if (labyAPI.labyConnect().state() != LabyConnectState.PLAY) {
            contextMenu.addEntry(ContextMenuEntry.builder().text(Component.text("Connect to backup server")).clickHandler((entry, source) -> {
                final LabyConnect labyConnect = labyAPI.labyConnect();
                labyConnect.disconnect(LabyConnectDisconnectEvent.Initiator.USER, "Force quit");
                labyConnect.connect("chat2.labymod.net", 30336);
                return;
            }).build());
        }
        if (Laby.references().gameUserService().clientGameUser().visibleGroup().isStaff()) {
            contextMenu.addEntry(ContextMenuEntry.builder().text(Component.text("Connect to TestServer")).clickHandler((entry, source) -> {
                final LabyConnect labyConnect2 = labyAPI.labyConnect();
                labyConnect2.disconnect(LabyConnectDisconnectEvent.Initiator.USER, "Force quit");
                labyConnect2.connect("chat.labymod.net", 30337);
            }).build());
        }
    }
}
