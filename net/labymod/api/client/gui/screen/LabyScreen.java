// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen;

import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.Laby;

public abstract class LabyScreen implements ScreenInstance, ParentScreen
{
    protected ParentScreen parent;
    protected Object previousScreen;
    
    protected LabyScreen() {
    }
    
    public void updatePreviousScreen() {
        this.previousScreen = Laby.labyAPI().minecraft().minecraftWindow().getCurrentVersionedScreen(true);
    }
    
    @Override
    public abstract void tick();
    
    public void load(final Parent parent) {
        this.parent = (ParentScreen)parent;
        if (parent instanceof final LabyScreen labyScreen) {
            this.previousScreen = labyScreen.previousScreen;
        }
        if (parent instanceof final ScreenRendererWidget screenRenderer) {
            this.previousScreen = (screenRenderer.handlesPreviousScreen() ? screenRenderer.getPreviousScreen() : null);
        }
        this.initialize(parent);
        this.postInitialize();
    }
    
    protected abstract void postInitialize();
    
    public abstract void updateKeyRepeatingMode(final boolean p0);
    
    public boolean isPauseScreen() {
        return true;
    }
    
    @Override
    public void displayScreen(final ScreenInstance screen) {
        if (this.parent != null) {
            this.parent.displayScreen(screen);
        }
    }
    
    @Override
    public void displayScreenRaw(final Object screen) {
        if (this.parent != null) {
            this.parent.displayScreenRaw(screen);
        }
    }
    
    @Override
    public LabyScreen currentLabyScreen() {
        return this.parent.currentLabyScreen();
    }
    
    @Override
    public ScreenWrapper currentScreen() {
        return this.parent.currentScreen();
    }
    
    @Override
    public String getCurrentScreenName() {
        return this.parent.getCurrentScreenName();
    }
    
    public Object getPreviousScreen() {
        return this.previousScreen;
    }
    
    public void setPreviousScreen(final Object previousScreen) {
        this.previousScreen = previousScreen;
    }
    
    public boolean allowCustomFont() {
        if (this.parent instanceof ScreenRendererWidget) {
            final Parent root = this.parent.getRoot();
            if (root instanceof final LabyScreen labyScreen) {
                return labyScreen.allowCustomFont();
            }
        }
        return true;
    }
}
