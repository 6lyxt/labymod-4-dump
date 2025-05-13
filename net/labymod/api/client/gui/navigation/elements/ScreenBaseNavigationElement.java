// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.navigation.elements;

import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.widget.Widget;

public abstract class ScreenBaseNavigationElement<T extends Widget> extends AbstractNavigationElement<T>
{
    protected ScreenInstance screen;
    protected long timeLastOpened;
    
    protected ScreenBaseNavigationElement(final ScreenInstance screen) {
        this.timeLastOpened = -1L;
        this.screen = screen;
    }
    
    public ScreenInstance getScreen() {
        return this.screen;
    }
    
    public long getTimeLastOpened() {
        return this.timeLastOpened;
    }
    
    public boolean isScreen(final Object raw) {
        return this.screen.mostInnerScreen().getClass().equals(raw.getClass());
    }
    
    public boolean shouldDocumentHandleEscape() {
        return false;
    }
    
    public void onUpdate(final ScreenInstance instance) {
        this.screen = instance;
    }
    
    public void onCloseScreen() {
    }
    
    public boolean isFallback() {
        return false;
    }
}
