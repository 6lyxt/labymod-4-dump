// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.navigation.tab;

import net.labymod.api.client.component.Component;

public abstract class ComponentTab extends Tab
{
    public ComponentTab() {
    }
    
    public ComponentTab(final boolean useSingleInstance) {
        super(useSingleInstance);
    }
    
    public abstract Component createComponent();
}
