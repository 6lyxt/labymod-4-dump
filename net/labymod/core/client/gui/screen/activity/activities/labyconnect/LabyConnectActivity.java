// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labyconnect;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.LabyConnectDesktopActivity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;

@Link("activity/labyconnect/laby-connect.lss")
@AutoActivity
public class LabyConnectActivity extends SimpleActivity
{
    private static final String OFFLINE_ID = "offline";
    private static final String ONLINE_ID = "online";
    private final LabyConnectDesktopActivity desktopActivity;
    private final LabyConnectOfflineActivity offlineActivity;
    private ScreenRendererWidget screenRenderer;
    
    public LabyConnectActivity() {
        this.desktopActivity = new LabyConnectDesktopActivity();
        this.offlineActivity = new LabyConnectOfflineActivity();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        ((Document)this.document).addChild(new DivWidget().addId("background", "background-left"));
        ((Document)this.document).addChild(new DivWidget().addId("background", "background-top"));
        ((Document)this.document).addChild(new DivWidget().addId("background", "background-right"));
        ((Document)this.document).addChild(new DivWidget().addId("background", "background-bottom"));
        (this.screenRenderer = new ScreenRendererWidget()).addId("screen-renderer");
        ((AbstractWidget<ScreenRendererWidget>)this.document).addChild(this.screenRenderer);
        this.updateScreen();
    }
    
    @Override
    public void onOpenScreen() {
        super.onOpenScreen();
    }
    
    @Subscribe
    public void onLabyConnectStateUpdate(final LabyConnectStateUpdateEvent event) {
        this.updateScreen();
    }
    
    private void updateScreen() {
        if (this.screenRenderer == null) {
            return;
        }
        final ScreenInstance currentScreen = this.screenRenderer.getScreen();
        final boolean previousIsConnected = currentScreen instanceof LabyConnectDesktopActivity;
        final boolean isConnected = this.labyAPI.labyConnect().isConnected();
        if (currentScreen != null && previousIsConnected == isConnected) {
            return;
        }
        if (isConnected) {
            this.screenRenderer.displayScreen(this.desktopActivity);
            this.updateScreenRendererIds("online", "offline");
        }
        else {
            this.screenRenderer.displayScreen(this.offlineActivity);
            this.updateScreenRendererIds("offline", "online");
        }
    }
    
    private void updateScreenRendererIds(final String add, final String remove) {
        this.screenRenderer.removeId(remove);
        this.screenRenderer.addId(add);
    }
    
    public LabyConnectDesktopActivity desktopActivity() {
        return this.desktopActivity;
    }
}
