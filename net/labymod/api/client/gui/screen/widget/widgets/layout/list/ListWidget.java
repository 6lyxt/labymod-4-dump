// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout.list;

import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

@AutoWidget
public abstract class ListWidget<T extends Widget> extends AbstractWidget<T>
{
    protected ListWidget() {
    }
    
    public void updateVisibility(final ListWidget<?> widget, final Parent parent) {
        for (final T child : this.children) {
            if (child instanceof final ListWidget listWidget) {
                listWidget.updateVisibility(widget, parent);
            }
        }
    }
    
    @Nullable
    protected Widget getClosestContentWidget() {
        for (Parent parent = this.parent; parent != null; parent = parent.getParent()) {
            if (!(parent instanceof ListWidget)) {
                return (Widget)parent;
            }
        }
        return null;
    }
}
