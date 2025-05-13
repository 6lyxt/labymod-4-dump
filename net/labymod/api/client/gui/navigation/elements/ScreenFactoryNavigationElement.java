// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.navigation.elements;

import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenFactory;

public abstract class ScreenFactoryNavigationElement extends ScreenNavigationElement
{
    private final ScreenFactory factory;
    
    public ScreenFactoryNavigationElement(final ScreenFactory factory) {
        super(factory.create());
        this.factory = factory;
    }
    
    @Override
    public void onUpdate(final ScreenInstance instance) {
        this.screen = this.factory.create();
    }
}
