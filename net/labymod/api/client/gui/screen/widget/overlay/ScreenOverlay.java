// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.overlay;

import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.activity.Activity;

public abstract class ScreenOverlay extends Activity
{
    private final int zLayer;
    private boolean active;
    private boolean closing;
    
    public ScreenOverlay(final int zLayer) {
        this.zLayer = zLayer;
    }
    
    public ScreenOverlay(final int zLayer, final boolean handleStyleSheet) {
        super(handleStyleSheet);
        this.zLayer = zLayer;
    }
    
    @Override
    public boolean displayPreviousScreen() {
        return false;
    }
    
    public int getZLayer() {
        return this.zLayer;
    }
    
    public boolean isActive() {
        return !Laby.labyAPI().minecraft().isLoadingOverlayPresent() && this.active;
    }
    
    public void setActive(final boolean active) {
        final boolean reInitialize = this.active != active;
        this.active = active;
        if (active && reInitialize) {
            final Window window = Laby.labyAPI().minecraft().minecraftWindow();
            this.reloadMarkup();
            this.resize(window.getAbsoluteScaledWidth(), window.getAbsoluteScaledHeight());
            this.load(window);
            this.onOpenScreen();
        }
        else {
            this.onCloseScreen();
        }
    }
    
    public void reloadOverlayStyles() {
        final boolean active = this.active;
        this.setActive(!active);
        this.setActive(active);
    }
    
    public boolean isClosing() {
        return this.closing;
    }
    
    protected void setClosing(final boolean closing) {
        this.closing = closing;
    }
    
    public boolean isHideGui() {
        return true;
    }
}
