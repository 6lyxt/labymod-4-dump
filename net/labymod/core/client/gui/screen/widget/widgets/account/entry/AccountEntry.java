// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.account.entry;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;

public interface AccountEntry
{
    Component getComponent();
    
    Icon getIcon();
    
    void interact(final Runnable p0);
}
