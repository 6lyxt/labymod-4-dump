// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.navigation.tab;

import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.component.Component;

public class DefaultComponentTab extends ComponentTab
{
    private final Component component;
    private final Factory screenInstanceFactory;
    
    public DefaultComponentTab(final String title, final ScreenInstance screenInstance) {
        this(Component.text(title), screenInstance);
    }
    
    public DefaultComponentTab(final String title, final Factory screenInstanceFactory) {
        this(Component.text(title), screenInstanceFactory);
    }
    
    public DefaultComponentTab(final Component component, final ScreenInstance screenInstance) {
        this(component, () -> screenInstance);
    }
    
    public DefaultComponentTab(final Component component, final Factory screenInstanceFactory) {
        this.component = component;
        this.screenInstanceFactory = screenInstanceFactory;
    }
    
    @Override
    public Component createComponent() {
        return this.component;
    }
    
    @Override
    public ScreenInstance createScreen() {
        return this.screenInstanceFactory.create();
    }
    
    public interface Factory
    {
        ScreenInstance create();
    }
}
