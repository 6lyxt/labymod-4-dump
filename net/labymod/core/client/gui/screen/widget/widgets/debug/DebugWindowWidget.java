// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.debug;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.WindowWidget;

public class DebugWindowWidget extends WindowWidget<ComponentWidget, DivWidget>
{
    private final ComponentWidget componentWidget;
    
    public DebugWindowWidget(final ComponentWidget componentWidget) {
        super(MouseButton.LEFT, DraggingType.TAB_WIDGET, RescaleType.NONE);
        this.componentWidget = componentWidget;
    }
    
    @Override
    protected ComponentWidget createTabWidget() {
        return this.componentWidget;
    }
}
