// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.popup;

import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.ScreenInstance;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlayHandler;
import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;

public abstract class AdvancedPopup
{
    private AdvancedPopupWidget widget;
    private WidgetReference overlayReference;
    private AdvancedPopupActivity activity;
    
    @Nullable
    public abstract Widget initialize();
    
    protected void onClose() {
    }
    
    protected void setup() {
    }
    
    protected void tick() {
    }
    
    @NotNull
    public WidgetReference displayInOverlay() {
        final AdvancedPopupWidget widget = AdvancedPopupWidget.of(this);
        final ScreenOverlayHandler screenOverlayHandler = Laby.references().screenOverlayHandler();
        (this.overlayReference = screenOverlayHandler.displayInOverlay(widget)).addDestroyHandler(this::onClose);
        this.overlayReference.clickRemoveStrategy(WidgetReference.ClickRemoveStrategy.NEVER);
        this.overlayReference.keyPressRemoveStrategy(WidgetReference.KeyPressRemoveStrategy.ESCAPE);
        return this.overlayReference;
    }
    
    public void displayAsActivity() {
        final LabyAPI labyAPI = Laby.labyAPI();
        if (!labyAPI.minecraft().isIngame()) {
            throw new IllegalStateException("Can't display popup as activity while not ingame");
        }
        final AdvancedPopupWidget widget = AdvancedPopupWidget.of(this);
        this.activity = new AdvancedPopupActivity(widget);
        labyAPI.minecraft().minecraftWindow().displayScreen(this.activity);
    }
    
    @NotNull
    protected final AdvancedPopupWidget widget() {
        if (this.widget == null) {
            throw new IllegalStateException("This method can only be called after the object has been handed to a AdvancedPopupWidget");
        }
        return this.widget;
    }
    
    protected final void close() {
        if (this.overlayReference != null && this.overlayReference.isAlive()) {
            this.overlayReference.remove();
        }
        if (this.activity != null) {
            this.activity.displayPreviousScreen();
            this.activity = null;
            this.onClose();
        }
    }
    
    @ApiStatus.Internal
    final void setup(final AdvancedPopupWidget widget) {
        if (this.widget != null) {
            throw new IllegalStateException("Please don't reuse AdvancedPopup objects");
        }
        this.widget = widget;
        this.setup();
    }
    
    @Nullable
    public final WidgetReference getReference() {
        return this.overlayReference;
    }
}
