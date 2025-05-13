// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.window;

import net.labymod.api.client.gui.screen.widget.window.AbstractScreenWindow;
import net.labymod.api.client.gui.screen.widget.window.AbstractScreenWindowOverlay;

@Deprecated
public abstract class AbstractCoreScreenWindowOverlay extends AbstractScreenWindowOverlay
{
    public AbstractCoreScreenWindowOverlay(final int identifier) {
        super(identifier);
    }
    
    @Override
    public void registerWindow(final AbstractScreenWindow window) {
        this.windows.add(window);
        this.labyAPI.eventBus().registerListener(window);
    }
}
