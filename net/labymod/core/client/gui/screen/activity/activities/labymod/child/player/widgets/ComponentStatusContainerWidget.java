// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets;

import net.labymod.api.client.component.Component;
import java.util.function.Supplier;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

@AutoWidget
public class ComponentStatusContainerWidget<T extends Widget> extends StatusContainerWidget<T, ComponentWidget>
{
    private final ComponentWidget statusWidget;
    
    public ComponentStatusContainerWidget() {
        super(null);
        this.statusWidget = ComponentWidget.empty();
        this.statusWidgetSupplier = (Supplier<S>)(() -> this.statusWidget);
    }
    
    public ComponentStatusContainerWidget<T> displayStatus(final Component status) {
        this.statusWidget.setComponent(status);
        return (ComponentStatusContainerWidget)this.displayStatus();
    }
}
