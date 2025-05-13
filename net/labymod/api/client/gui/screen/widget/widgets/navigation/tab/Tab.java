// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.navigation.tab;

import net.labymod.api.client.gui.screen.ScreenInstance;

public abstract class Tab
{
    private final boolean useSingleInstance;
    private ScreenInstance instance;
    private int index;
    private boolean transitionToRight;
    private boolean hidden;
    
    public Tab() {
        this(true);
    }
    
    public Tab(final boolean useSingleInstance) {
        this.useSingleInstance = useSingleInstance;
    }
    
    public abstract ScreenInstance createScreen();
    
    public ScreenInstance provideScreen() {
        if (!this.useSingleInstance || this.instance == null) {
            this.instance = this.createScreen();
        }
        return this.instance;
    }
    
    public boolean isScreen(final Class<? extends ScreenInstance> screenClass) {
        return this.provideScreen().getClass() == screenClass;
    }
    
    public void setIndex(final int index) {
        this.index = index;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }
    
    public void setTransitionToRight(final boolean transitionToRight) {
        this.transitionToRight = transitionToRight;
    }
    
    public boolean isTransitionToRight() {
        return this.transitionToRight;
    }
}
