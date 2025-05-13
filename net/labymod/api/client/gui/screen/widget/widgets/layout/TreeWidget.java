// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout;

import java.util.Iterator;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

@AutoWidget
public class TreeWidget extends VerticalListWidget<Widget>
{
    private final Widget root;
    
    public TreeWidget(final Widget root) {
        this.root = root;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        for (final Widget child : this.children) {
            child.addId("tree-child");
        }
        this.addChild(0, this.root);
    }
}
